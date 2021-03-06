/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.query.resolver.v86;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.query.eval.Evaluator;
import org.teiid.query.metadata.TransformationMetadata;
import org.teiid.query.resolver.util.ResolverVisitor;
import org.teiid.query.resolver.v85.Test85FunctionResolving;
import org.teiid.query.sql.symbol.Function;

@SuppressWarnings( {"nls", "javadoc"} )
public class Test86FunctionResolving extends Test85FunctionResolving {

    protected Test86FunctionResolving(Version teiidVersion) {
        super(teiidVersion);
    }

    public Test86FunctionResolving() {
        this(Version.TEIID_8_6);
    }

    /*
     * UDF function for testVarArgsFunction
     */
    public static String vararg(Object... vals) {
        return String.valueOf(vals.length);
    }

    @Override
    @Test
    public void testVarArgsFunction() throws Exception {
        String ddl = "create foreign function func (VARIADIC z object) returns string options (JAVA_CLASS '"
                     + this.getClass().getName() + "', JAVA_METHOD 'vararg');\n";
        TransformationMetadata tm = getMetadataFactory().fromDDL(ddl, "x", "y");

        String sql = "func(('a', 'b'))";

        Function func = (Function)getQueryParser().parseExpression(sql);
        ResolverVisitor visitor = new ResolverVisitor(getTeiidVersion());
        visitor.resolveLanguageObject(func, tm);
        assertEquals(1, func.getArgs().length);

        assertEquals("2", new Evaluator(getTeiidVersion()).evaluate(func));
    }
}
