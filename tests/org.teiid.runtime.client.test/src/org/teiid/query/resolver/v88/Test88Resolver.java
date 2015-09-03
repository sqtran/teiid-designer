/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.query.resolver.v88;

import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.query.resolver.v87.Test87Resolver;

@SuppressWarnings( {"javadoc"} )
public class Test88Resolver extends Test87Resolver {

    protected Test88Resolver(ITeiidServerVersion teiidVersion) {
        super(teiidVersion);
    }

    public Test88Resolver() {
        this(Version.TEIID_8_8.get());
    }
}
