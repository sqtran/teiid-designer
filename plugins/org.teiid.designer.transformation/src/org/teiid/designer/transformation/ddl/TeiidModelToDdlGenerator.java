package org.teiid.designer.transformation.ddl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.teiid.core.designer.ModelerCoreException;
import org.teiid.core.designer.util.CoreArgCheck;
import org.teiid.core.designer.util.ModelType;
import org.teiid.core.designer.util.StringConstants;
import org.teiid.core.designer.util.StringUtilities;
import org.teiid.designer.core.ModelerCore;
import org.teiid.designer.core.util.ModelContents;
import org.teiid.designer.core.workspace.ModelResource;
import org.teiid.designer.core.workspace.ModelWorkspaceException;
import org.teiid.designer.metamodels.relational.Column;
import org.teiid.designer.metamodels.relational.Procedure;
import org.teiid.designer.metamodels.relational.ProcedureParameter;
import org.teiid.designer.metamodels.relational.SearchabilityType;
import org.teiid.designer.metamodels.relational.Table;
import org.teiid.designer.metamodels.transformation.TransformationMappingRoot;
import org.teiid.designer.transformation.TransformationPlugin;
import org.teiid.designer.transformation.util.TransformationHelper;
import org.teiid.designer.type.IDataTypeManagerService.DataTypeName;

public class TeiidModelToDdlGenerator implements TeiidDDLConstants, TeiidReservedConstants  {
	
	private StringBuilder ddlBuffer = new StringBuilder();

    private boolean includeTables = true;

    private boolean includeProcedures = true;

    private boolean includeFunctions = true;

    private Set<String> lengthDataTypes;

    private Set<String> precisionDataTypes;
    
    private boolean isVirtual = false;
    
    private List<IStatus> issues;


    public TeiidModelToDdlGenerator() {
		super();
		this.issues = new ArrayList<IStatus>();
	}
    
    private Set<String> getLengthDataTypes() {
        if (lengthDataTypes == null) {
            lengthDataTypes = new HashSet<String>();
            lengthDataTypes.add(DataTypeName.CHAR.name());
            lengthDataTypes.add(DataTypeName.CLOB.name());
            lengthDataTypes.add(DataTypeName.BLOB.name());
            lengthDataTypes.add(DataTypeName.OBJECT.name());
            lengthDataTypes.add(DataTypeName.XML.name());
            lengthDataTypes.add(DataTypeName.STRING.name());
            lengthDataTypes.add(DataTypeName.VARBINARY.name());
            lengthDataTypes.add(DataTypeName.BIG_INTEGER.name());
        }

        return lengthDataTypes;
    }

    private Set<String> getPrecisionDataTypes() {
        if (precisionDataTypes == null) {
            precisionDataTypes = new HashSet<String>();
            precisionDataTypes.add(DataTypeName.BIG_DECIMAL.name());
        }

        return precisionDataTypes;
    }
    
	public String generate(ModelResource modelResource) throws ModelWorkspaceException {
	    CoreArgCheck.isNotNull(modelResource);

		final ModelContents contents = ModelContents.getModelContents(modelResource);
		isVirtual = modelResource.getModelType().getValue() == ModelType.VIRTUAL;

		append(StringConstants.NEW_LINE);
		
		for( Object obj : contents.getAllRootEObjects() ) {
			String statement = getStatement((EObject)obj);
			if( ! StringUtilities.isEmpty(statement) ) {
				append(statement);
				append(StringConstants.NEW_LINE);
			}
		}
		
		return ddlBuffer.toString();
	}
	
	private String getStatement(EObject eObj) {
		if( eObj instanceof Table ) {
			if( isVirtual ) {
				// generate DDL for a View including SQL statement
				return view((Table)eObj);
			} else {
				// Generate simple CREATE FOREIGN TABLE
				return table((Table)eObj);
			}
		} else if( eObj instanceof Procedure) {
			// Generate CREATE FOREIGN PROCEDURE 
			return procedure((Procedure)eObj);
		}
		
		return null;
	}
	
	private String getColumnDdl(Column col) {
        
        StringBuilder sb = new StringBuilder();

        sb.append(getName(col));
        sb.append(SPACE);
        EObject dataType = col.getType();
        String runtimeTypeName = ModelerCore.getBuiltInTypesManager().getRuntimeTypeName(dataType);
        sb.append(getColumnDatatypeDdl(runtimeTypeName, col.getLength(), col.getPrecision(), col.getScale()));
        
        String options = getColumnOptions(col);
        if( !StringUtilities.isEmpty(options) ) sb.append(SPACE).append(options);
        
		return sb.toString();
	}
	
	private String getColumnDatatypeDdl(String name, int length, int precision, int scale) {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if( getLengthDataTypes().contains(name.toUpperCase()) ) {
			sb.append(OPEN_BRACKET).append(length).append(CLOSE_BRACKET);
		} else if( getPrecisionDataTypes().contains(name.toUpperCase())  ) {
			sb.append(OPEN_BRACKET).append(precision).append(CLOSE_BRACKET);
		}
		return sb.toString();
	}
	
	private String getParameterDatatypeDdl(String name, int length, int precision, int scale) {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if( getLengthDataTypes().contains(name.toUpperCase()) ) {
			sb.append(OPEN_BRACKET).append(length).append(CLOSE_BRACKET);
		} else if( getPrecisionDataTypes().contains(name.toUpperCase())  ) {
			sb.append(OPEN_BRACKET).append(precision).append(CLOSE_BRACKET);
		}
		return sb.toString();
	}
	
	private String getParameterDdl(ProcedureParameter param) {
        
        StringBuilder sb = new StringBuilder();
        sb.append(getName(param));
        sb.append(SPACE);
        sb.append(getParameterDatatypeDdl(getName(param.getType()), param.getLength(), param.getPrecision(), param.getScale()));
        
		return sb.toString();
	}
	
	private String getName(EObject eObj) {
		return ModelerCore.getModelEditor().getName(eObj);
	}
	
	private String getDescription(EObject eObj) {
    	try {
			return ModelerCore.getModelEditor().getDescription(eObj);
		} catch (ModelerCoreException e) {
			issues.add(new Status(IStatus.ERROR, TransformationPlugin.PLUGIN_ID, "Error finding description for " + getName(eObj), e));
		}
    	
    	return null;
	}
	
    private void append(Object o) {
        ddlBuffer.append(o);
    }
	
    private String table(Table table) {
        if (! includeTables)
            return null;
        
        StringBuilder sb = new StringBuilder();

		// generate DDL for a Table
        sb.append(CREATE_FOREIGN_TABLE).append(SPACE);
        sb.append(getName(table));
        sb.append(SPACE + OPEN_BRACKET);
		@SuppressWarnings("unchecked")
		List<Column> columns = table.getColumns();
		int nColumns = columns.size();
		int count = 0;
		for( Column col : columns ) {
			if( count == 0 ) sb.append(NEW_LINE);
			
			String columnStr = getColumnDdl(col);
			count++;
			sb.append(TAB).append(columnStr);
			
			if( count < nColumns ) sb.append(COMMA + NEW_LINE);
		}
		sb.append(NEW_LINE + CLOSE_BRACKET);
		
		String options = getTableOptions(table);
		if( !StringUtilities.isEmpty(options)) {
			sb.append(SPACE).append(options);
		}
		
		sb.append(NEW_LINE);

		return sb.toString();
    }
    
    private String view(Table table) {
        if (! includeTables)
            return null;

        StringBuilder sb = new StringBuilder();

		// generate DDL for a View including SQL statement
		sb.append(CREATE_VIEW).append(SPACE);
        sb.append(getName(table));
        sb.append(SPACE + OPEN_BRACKET);
		@SuppressWarnings("unchecked")
		List<Column> columns = table.getColumns();
		int nColumns = columns.size();
		int count = 0;
		for( Column col : columns ) {
			if( count == 0 ) sb.append(NEW_LINE);
			
			String columnStr = getColumnDdl(col);
			count++;
			sb.append(TAB).append(columnStr);
			
			if( count < nColumns ) sb.append(COMMA + NEW_LINE);

		}
		sb.append(NEW_LINE + CLOSE_BRACKET);
		
		TransformationMappingRoot tRoot = (TransformationMappingRoot)TransformationHelper.getTransformationMappingRoot(table);
		String sqlString = TransformationHelper.getSelectSqlString(tRoot);
		if( sqlString != null ) {
			sb.append(SPACE).append(NEW_LINE + Reserved.AS).append(NEW_LINE + TAB + TAB).append(sqlString);
			sb.append(SEMI_COLON + NEW_LINE);
		}
		
		return sb.toString();
    }
    
    /*
     * 
	 *   Source Procedure ("CREATE FOREIGN PROCEDURE") - a stored procedure in source
	 *   Source Function ("CREATE FOREIGN FUNCTION") - A function that is supported by the source, where Teiid will pushdown to source instead of evaluating in Teiid engine
	 *   Virtual Procedure ("CREATE VIRTUAL PROCEDURE") - Similar to stored procedure, however this is defined using the Teiid's Procedure language and evaluated in the Teiid's engine.
	 *   Function/UDF ("CREATE VIRTUAL FUNCTION") - A user defined function, that can be defined using the Teiid procedure language or can have the implementation defined using a JAVA Class.
     */
    private String procedure(Procedure procedure) {
        if (! includeProcedures)
            return null;
        
        StringBuilder sb = new StringBuilder();
        boolean isFunction = procedure.isFunction();

		// generate DDL for a Table
        if( isFunction ) {
        	if( isVirtual ) sb.append(CREATE_VIRTUAL_FUNCTION).append(SPACE);
        	else sb.append(CREATE_FOREIGN_TABLE).append(SPACE);
        } else {
        	if( isVirtual ) sb.append(CREATE_VIRTUAL_PROCEDURE).append(SPACE);
        	else sb.append(CREATE_FOREIGN_PROCEDURE).append(SPACE);
        }
        sb.append(getName(procedure));
        sb.append(SPACE + OPEN_BRACKET);
		@SuppressWarnings("unchecked")
		List<ProcedureParameter> params = procedure.getParameters();
		int nParams = params.size();
		int count = 0;
		for( ProcedureParameter param : params ) {
			String paramStr = getParameterDdl(param);
			count++;
			sb.append(paramStr);
			if( count < nParams ) sb.append(COMMA + SPACE);
		}
		sb.append(CLOSE_BRACKET);
		
		// Depending on the procedure type, need to append either one of the following:
		//   > returns datatype
		//   > returns a result set, either named or not
		//   > an AS <SQL STATEMENT> if a virtual procedure
		//   > ???
		if( isVirtual && !isFunction ) {
			TransformationMappingRoot tRoot = (TransformationMappingRoot)TransformationHelper.getTransformationMappingRoot(procedure);
			String sqlString = TransformationHelper.getSelectSqlString(tRoot);
			if( sqlString != null ) {
				sb.append(NEW_LINE + TAB).append(Reserved.AS).append(NEW_LINE + SPACE).append(sqlString);
				sb.append(SEMI_COLON + NEW_LINE);
			}
		}

		return sb.toString();
    }
    
    private String getColumnOptions(Column col) {
    	OptionsStatement options = new OptionsStatement();
    	options.add(NAMEINSOURCE, col.getNameInSource());
    	options.add(NATIVE_TYPE, col.getNativeType());
    	options.add(CASE_SENSITIVE, Boolean.toString(col.isCaseSensitive()));
    	options.add(SELECTABLE, Boolean.toString(col.isSelectable()));
    	options.add(UPDATABLE, Boolean.toString(col.isUpdateable()));
    	options.add(SIGNED, Boolean.toString(col.isSigned()));
    	options.add(CURRENCY, Boolean.toString(col.isCurrency()));
    	options.add(FIXED_LENGTH, Boolean.toString(col.isFixedLength()));
    	String desc = getDescription(col);
    	if( !StringUtilities.isEmpty(desc) ) {
    		options.add(ANNOTATION, desc);
    	}
    	if( !col.getSearchability().equals(SearchabilityType.SEARCHABLE) ) {
    		options.add(SEARCHABLE, col.getSearchability().getLiteral());
    	}

    	return options.toString();
    }
    
    private String getTableOptions(Table table) {
    	OptionsStatement options = new OptionsStatement();
    	options.add(NAMEINSOURCE, table.getNameInSource());
    	options.add(MATERIALIZED, Boolean.toString(table.isMaterialized()));
    	options.add(UPDATABLE, Boolean.toString(table.isSupportsUpdate()));
    	String desc = getDescription(table);
    	if( !StringUtilities.isEmpty(desc) ) {
    		options.add(ANNOTATION, desc);
    	}

    	return options.toString();
    }
    
    private void tab(int nTabs) {
    	for(int i=0; i<nTabs; i++) {
    		append(TAB);
    	}
    }
    

    private String escapeStringValue(String str, String tick) {
        return StringUtilities.replaceAll(str, tick, tick + tick);
    }
    
    private String escapeSinglePart(String token) {
        if (TeiidSQLConstants.isReservedWord(token)) {
            return TeiidSQLConstants.Tokens.ID_ESCAPE_CHAR + token + TeiidSQLConstants.Tokens.ID_ESCAPE_CHAR;
        }
        boolean escape = true;
        char start = token.charAt(0);
        if (HASH.equals(Character.toString(start)) || AMPERSAND.equals(Character.toString(start)) || StringUtilities.isLetter(start)) {
            escape = false;
            for (int i = 1; !escape && i < token.length(); i++) {
                char c = token.charAt(i);
                escape = !StringUtilities.isLetterOrDigit(c) && c != '_';
            }
        }
        if (escape) {
            return TeiidSQLConstants.Tokens.ID_ESCAPE_CHAR + escapeStringValue(token, SPEECH_MARK) + TeiidSQLConstants.Tokens.ID_ESCAPE_CHAR;
        }
        return token;
    }
    
    class OptionsStatement {
    	boolean hasOptions;
    	StringBuilder sb;
    	
    	public OptionsStatement() {
    		super();
    		sb = new StringBuilder();
    		sb.append(Reserved.OPTIONS).append(OPEN_BRACKET);
    	}
    	
    	public void add(String key, String value) {
    		if( StringUtilities.isEmpty(value) ) return;
    			
    		if( hasOptions ) sb.append(COMMA + SPACE);
    		
    		hasOptions = true;
    		
            sb.append(escapeSinglePart(key)).append(SPACE);
            if (Reserved.FALSE.equalsIgnoreCase(value) || Reserved.TRUE.equalsIgnoreCase(value)) {
                sb.append(value);
                return;
            }

            // Default to a string value which should be placed in quotes
            sb.append(QUOTE_MARK + value + QUOTE_MARK);
    		
//    		sb.append(key).append(SPACE).append(value);

    	}
    	
    	public String toString() {
    		sb.append(CLOSE_BRACKET);
    		
    		if( !hasOptions) return null;
    		
    		return sb.toString();
    	}
    }
    
    
}