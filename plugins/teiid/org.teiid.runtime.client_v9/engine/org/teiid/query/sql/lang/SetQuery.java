/* Generated By:JJTree: Do not edit this line. SetQuery.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.teiid.core.types.DataTypeManagerService;
import org.teiid.designer.query.metadata.IQueryMetadataInterface;
import org.teiid.designer.query.sql.lang.ISetQuery;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidNodeFactory;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.query.resolver.util.ResolverUtil;
import org.teiid.query.sql.symbol.AliasSymbol;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.Symbol;
import org.teiid.query.sql.util.SymbolMap;

/**
 *
 */
public class SetQuery extends QueryCommand
    implements ISetQuery<QueryCommand, OrderBy, Query, Expression, LanguageVisitor>{

    private boolean all = true;

    private Operation operation;                     

    private QueryCommand leftQuery;

    private QueryCommand rightQuery;

    private List<Class<?>> projectedTypes = null;  //set during resolving

    private IQueryMetadataInterface metadata = null; // set during resolving

    /**
     * @param p
     * @param id
     */
    public SetQuery(ITeiidServerVersion p, int id) {
        super(p, id);
    }

    /**
     * Return type of command.
     * @return TYPE_QUERY
     */
    @Override
    public int getType() {
        return TYPE_QUERY;
    }
    
    /**
     * Set type of operation
     * @param operation Operation constant as defined in this class
     */
    public void setOperation(Operation operation) { 
        this.operation = operation;
    }
    
    /**
     * Get operation for this set
     * @return Operation as defined in this class
     */
    @Override
    public Operation getOperation() { 
        return this.operation;
    }

    /**
     * @return all
     */
    @Override
    public boolean isAll() {
        return this.all;
    }

    /**
     * @param all
     */
    @Override
    public void setAll(boolean all) {
        this.all = all;
    }

    /**
     * @return left query
     */
    @Override
    public QueryCommand getLeftQuery() {
        return this.leftQuery;
    }

    /**
     * @param leftQuery
     */
    @Override
    public void setLeftQuery(QueryCommand leftQuery) {
        this.leftQuery = leftQuery;
    }
    
    /**
     * @return right query
     */
    @Override
    public QueryCommand getRightQuery() {
        return this.rightQuery;
    }
    
    /**
     * @param rightQuery
     */
    @Override
    public void setRightQuery(QueryCommand rightQuery) {
        this.rightQuery = rightQuery;
    }

    /**
     * @return the left and right queries as a list.  This list cannot be modified.
     */
    @Override
    public List<QueryCommand> getQueryCommands() {
        return Collections.unmodifiableList(Arrays.asList(leftQuery, rightQuery));
    }

    /**
     * @return projected query
     */
    @Override
    public Query getProjectedQuery() {
        if (leftQuery instanceof SetQuery) {
            return ((SetQuery)leftQuery).getProjectedQuery();
        }
        return (Query)leftQuery;
    }

    /**
     * Get the ordered list of all elements returned by this query.  These elements
     * may be ElementSymbols or ExpressionSymbols but in all cases each represents a 
     * single column.
     * @return Ordered list of SingleElementSymbol
     */
    @Override
    public List<Expression> getProjectedSymbols() {
        Query query = getProjectedQuery();
        List projectedSymbols = query.getProjectedSymbols();
        if (projectedTypes != null) {
            return getTypedProjectedSymbols(projectedSymbols, projectedTypes, metadata);
        } 
        return projectedSymbols;
    }

    /**
     * @param projectedTypes The projectedSymbols to set.
     * @param metadata
     */
    public void setProjectedTypes(List<Class<?>> projectedTypes, IQueryMetadataInterface metadata) {
        this.projectedTypes = projectedTypes;
        this.metadata = metadata;
    }

    /** 
     * @return Returns the projectedTypes.
     */
    public List<Class<?>>  getProjectedTypes() {
        return this.projectedTypes;
    }

    public static List<Expression> getTypedProjectedSymbols(List<? extends Expression> acutal, List<Class<?>> projectedTypes, IQueryMetadataInterface metadata) {
        List<Expression> newProject = new ArrayList<Expression>();
        for (int i = 0; i < acutal.size(); i++) {
            Expression originalSymbol = acutal.get(i);
            Expression symbol = originalSymbol;
            Class<?> type = projectedTypes.get(i);
            if (symbol.getType() != type) {
                symbol = SymbolMap.getExpression(originalSymbol);
                try {
                    symbol = ResolverUtil.convertExpression(symbol, DataTypeManagerService.getInstance(symbol.getTeiidVersion()).getDataTypeName(type), metadata);
                } catch (Exception err) {
                     throw new RuntimeException(err);
                }

                if (originalSymbol instanceof Symbol) {
                    AliasSymbol aliasSymbol = TeiidNodeFactory.createASTNode(originalSymbol.getTeiidVersion(), ASTNodes.ALIAS_SYMBOL);
                    aliasSymbol.setName(Symbol.getShortName(originalSymbol));
                    aliasSymbol.setSymbol(symbol);
                    symbol = aliasSymbol;
                }
            }
            newProject.add(symbol);
        }
        return newProject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.all ? 1231 : 1237);
        result = prime * result + ((this.leftQuery == null) ? 0 : this.leftQuery.hashCode());
        result = prime * result + ((this.operation == null) ? 0 : this.operation.hashCode());
        result = prime * result + ((this.rightQuery == null) ? 0 : this.rightQuery.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        SetQuery other = (SetQuery)obj;
        if (this.all != other.all) return false;
        if (this.leftQuery == null) {
            if (other.leftQuery != null) return false;
        } else if (!this.leftQuery.equals(other.leftQuery)) return false;
        if (this.operation != other.operation) return false;
        if (this.rightQuery == null) {
            if (other.rightQuery != null) return false;
        } else if (!this.rightQuery.equals(other.rightQuery)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SetQuery clone() {
        SetQuery clone = new SetQuery(getTeiidVersion(), this.id);

        this.copyMetadataState(clone);

        if(getOperation() != null)
            clone.setOperation(getOperation());
        clone.setAll(isAll());
        if(getLeftQuery() != null)
            clone.setLeftQuery(getLeftQuery().clone());
        if(getRightQuery() != null)
            clone.setRightQuery(getRightQuery().clone());
        if(getOrderBy() != null)
            clone.setOrderBy(getOrderBy().clone());
        if(getLimit() != null)
            clone.setLimit(getLimit().clone());
        if(getWith() != null)
            clone.setWith(cloneList(getWith()));
        if(getSourceHint() != null)
            clone.setSourceHint(getSourceHint());
        if(getOption() != null)
            clone.setOption(getOption().clone());
        if (getProjectedTypes() != null) {
            clone.setProjectedTypes(new ArrayList<Class<?>>(getProjectedTypes()), this.metadata);
        }

        return clone;
    }

}
/* JavaCC - OriginalChecksum=15a0b6183a79ec40affab72ee83028a6 (do not edit this line) */