/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.compare;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.EReference;

/**
 * UuidMatcherFactory
 *
 * @since 8.0
 */
public class UuidMatcherFactory implements EObjectMatcherFactory {

    private final List standardMatchers;

    /**
     * Construct an instance of UuidMatcherFactory.
     * 
     */
    public UuidMatcherFactory() {
        super();
        this.standardMatchers = new LinkedList();
        this.standardMatchers.add(new UuidEObjectMatcher());
        this.standardMatchers.add(new EProxyUriMatcher());        
    }

    /**
     * @see org.teiid.designer.core.compare.EObjectMatcherFactory#createEObjectMatchersForRoots()
     */
    @Override
	public List createEObjectMatchersForRoots() {
        return this.standardMatchers;
    }

    /**
     * @see org.teiid.designer.core.compare.EObjectMatcherFactory#createEObjectMatchers(org.eclipse.emf.ecore.EReference)
     */
    @Override
	public List createEObjectMatchers(final EReference reference) {
        return this.standardMatchers;
    }

}
