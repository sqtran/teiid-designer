/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.transformation;

import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Data Flow Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.teiid.designer.metamodels.transformation.DataFlowNode#getName <em>Name</em>}</li>
 * <li>{@link org.teiid.designer.metamodels.transformation.DataFlowNode#getOwner <em>Owner</em>}</li>
 * <li>{@link org.teiid.designer.metamodels.transformation.DataFlowNode#getInputLinks <em>Input Links</em>}</li>
 * <li>{@link org.teiid.designer.metamodels.transformation.DataFlowNode#getOutputLinks <em>Output Links</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.teiid.designer.metamodels.transformation.TransformationPackage#getDataFlowNode()
 * @model
 * @generated
 *
 * @since 8.0
 */
public interface DataFlowNode extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.teiid.designer.metamodels.transformation.TransformationPackage#getDataFlowNode_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.teiid.designer.metamodels.transformation.DataFlowNode#getName <em>Name</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName( String value );

    /**
     * Returns the value of the '<em><b>Owner</b></em>' container reference. It is bidirectional and its opposite is '
     * {@link org.teiid.designer.metamodels.transformation.DataFlowMappingRoot#getNodes <em>Nodes</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owner</em>' container reference isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Owner</em>' container reference.
     * @see #setOwner(DataFlowMappingRoot)
     * @see org.teiid.designer.metamodels.transformation.TransformationPackage#getDataFlowNode_Owner()
     * @see org.teiid.designer.metamodels.transformation.DataFlowMappingRoot#getNodes
     * @model opposite="nodes"
     * @generated
     */
    DataFlowMappingRoot getOwner();

    /**
     * Sets the value of the '{@link org.teiid.designer.metamodels.transformation.DataFlowNode#getOwner <em>Owner</em>}' container
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Owner</em>' container reference.
     * @see #getOwner()
     * @generated
     */
    void setOwner( DataFlowMappingRoot value );

    /**
     * Returns the value of the '<em><b>Input Links</b></em>' reference list. The list contents are of type
     * {@link org.teiid.designer.metamodels.transformation.DataFlowLink}. It is bidirectional and its opposite is '
     * {@link org.teiid.designer.metamodels.transformation.DataFlowLink#getOutputNode <em>Output Node</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Input Links</em>' reference list isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Input Links</em>' reference list.
     * @see org.teiid.designer.metamodels.transformation.TransformationPackage#getDataFlowNode_InputLinks()
     * @see org.teiid.designer.metamodels.transformation.DataFlowLink#getOutputNode
     * @model type="org.teiid.designer.metamodels.transformation.DataFlowLink" opposite="outputNode"
     * @generated
     */
    EList getInputLinks();

    /**
     * Returns the value of the '<em><b>Output Links</b></em>' reference list. The list contents are of type
     * {@link org.teiid.designer.metamodels.transformation.DataFlowLink}. It is bidirectional and its opposite is '
     * {@link org.teiid.designer.metamodels.transformation.DataFlowLink#getInputNode <em>Input Node</em>}'. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Output Links</em>' reference list isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Output Links</em>' reference list.
     * @see org.teiid.designer.metamodels.transformation.TransformationPackage#getDataFlowNode_OutputLinks()
     * @see org.teiid.designer.metamodels.transformation.DataFlowLink#getInputNode
     * @model type="org.teiid.designer.metamodels.transformation.DataFlowLink" opposite="inputNode"
     * @generated
     */
    EList getOutputLinks();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @model dataType="org.teiid.designer.metamodels.transformation.List" many="false" parameters=""
     * @generated
     */
    List getInputNodes(); // NO_UCD

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @model dataType="org.teiid.designer.metamodels.transformation.List" many="false" parameters=""
     * @generated
     */
    List getOutputNodes(); // NO_UCD

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @model dataType="org.teiid.designer.metamodels.transformation.List" many="false" parameters=""
     * @generated
     */
    List getProjectedSymbols(); // NO_UCD

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @model parameters=""
     * @generated
     */
    String getSqlString(); // NO_UCD

} // DataFlowNode
