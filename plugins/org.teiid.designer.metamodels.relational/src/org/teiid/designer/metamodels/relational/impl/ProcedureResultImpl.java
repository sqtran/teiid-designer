/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.relational.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.teiid.designer.metamodels.relational.Procedure;
import org.teiid.designer.metamodels.relational.ProcedureResult;
import org.teiid.designer.metamodels.relational.RelationalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Procedure Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.teiid.designer.metamodels.relational.impl.ProcedureResultImpl#getProcedure <em>Procedure</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 *
 * @since 8.0
 */
public class ProcedureResultImpl extends ColumnSetImpl implements ProcedureResult {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProcedureResultImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RelationalPackage.eINSTANCE.getProcedureResult();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
	public Procedure getProcedure() {
        if (eContainerFeatureID != RelationalPackage.PROCEDURE_RESULT__PROCEDURE) return null;
        return (Procedure)eContainer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
	public void setProcedure(Procedure newProcedure) {
        if (newProcedure != eContainer || (eContainerFeatureID != RelationalPackage.PROCEDURE_RESULT__PROCEDURE && newProcedure != null)) {
            if (EcoreUtil.isAncestor(this, newProcedure))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eContainer != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newProcedure != null)
                msgs = ((InternalEObject)newProcedure).eInverseAdd(this, RelationalPackage.PROCEDURE__RESULT, Procedure.class, msgs);
            msgs = eBasicSetContainer((InternalEObject)newProcedure, RelationalPackage.PROCEDURE_RESULT__PROCEDURE, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RelationalPackage.PROCEDURE_RESULT__PROCEDURE, newProcedure, newProcedure));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case RelationalPackage.PROCEDURE_RESULT__COLUMNS:
                    return ((InternalEList)getColumns()).basicAdd(otherEnd, msgs);
                case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                    if (eContainer != null)
                        msgs = eBasicRemoveFromContainer(msgs);
                    return eBasicSetContainer(otherEnd, RelationalPackage.PROCEDURE_RESULT__PROCEDURE, msgs);
                default:
                    return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
            }
        }
        if (eContainer != null)
            msgs = eBasicRemoveFromContainer(msgs);
        return eBasicSetContainer(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case RelationalPackage.PROCEDURE_RESULT__COLUMNS:
                    return ((InternalEList)getColumns()).basicRemove(otherEnd, msgs);
                case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                    return eBasicSetContainer(null, RelationalPackage.PROCEDURE_RESULT__PROCEDURE, msgs);
                default:
                    return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
            }
        }
        return eBasicSetContainer(null, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
        if (eContainerFeatureID >= 0) {
            switch (eContainerFeatureID) {
                case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                    return eContainer.eInverseRemove(this, RelationalPackage.PROCEDURE__RESULT, Procedure.class, msgs);
                default:
                    return eDynamicBasicRemoveFromContainer(msgs);
            }
        }
        return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case RelationalPackage.PROCEDURE_RESULT__NAME:
                return getName();
            case RelationalPackage.PROCEDURE_RESULT__NAME_IN_SOURCE:
                return getNameInSource();
            case RelationalPackage.PROCEDURE_RESULT__COLUMNS:
                return getColumns();
            case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                return getProcedure();
        }
        return eDynamicGet(eFeature, resolve);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(EStructuralFeature eFeature, Object newValue) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case RelationalPackage.PROCEDURE_RESULT__NAME:
                setName((String)newValue);
                return;
            case RelationalPackage.PROCEDURE_RESULT__NAME_IN_SOURCE:
                setNameInSource((String)newValue);
                return;
            case RelationalPackage.PROCEDURE_RESULT__COLUMNS:
                getColumns().clear();
                getColumns().addAll((Collection)newValue);
                return;
            case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                setProcedure((Procedure)newValue);
                return;
        }
        eDynamicSet(eFeature, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case RelationalPackage.PROCEDURE_RESULT__NAME:
                setName(NAME_EDEFAULT);
                return;
            case RelationalPackage.PROCEDURE_RESULT__NAME_IN_SOURCE:
                setNameInSource(NAME_IN_SOURCE_EDEFAULT);
                return;
            case RelationalPackage.PROCEDURE_RESULT__COLUMNS:
                getColumns().clear();
                return;
            case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                setProcedure((Procedure)null);
                return;
        }
        eDynamicUnset(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case RelationalPackage.PROCEDURE_RESULT__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case RelationalPackage.PROCEDURE_RESULT__NAME_IN_SOURCE:
                return NAME_IN_SOURCE_EDEFAULT == null ? nameInSource != null : !NAME_IN_SOURCE_EDEFAULT.equals(nameInSource);
            case RelationalPackage.PROCEDURE_RESULT__COLUMNS:
                return columns != null && !columns.isEmpty();
            case RelationalPackage.PROCEDURE_RESULT__PROCEDURE:
                return getProcedure() != null;
        }
        return eDynamicIsSet(eFeature);
    }

} //ProcedureResultImpl
