/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.ui.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.teiid.designer.ui.UiConstants;

/**
 * @since 8.0
 */
public class DeselectDescendantsDialog extends Dialog implements UiConstants {
    Button alwaysUseResponseButton;
    boolean yesPressed = false;
    boolean alwaysUseResponse = false;

    public DeselectDescendantsDialog( Shell shell ) {
        super(shell);
    }

    @Override
    protected void configureShell( Shell shell ) {
        super.configureShell(shell);
        String title = Util.getString("StructuralCopyWizardPage.deselectTitle"); //$NON-NLS-1$
        shell.setText(title);
    }

    @Override
    protected Control createDialogArea( Composite parent ) {
        Composite contents = (Composite)super.createDialogArea(parent);
        Label question = new Label(contents, SWT.NONE);
        String questionText = Util.getString("StructuralCopyWizardPage.deselectQuestion"); //$NON-NLS-1$
        question.setText(questionText);
        return contents;
    }

    @Override
    protected Control createButtonBar( Composite parent ) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        composite.setLayout(layout);
        layout.marginHeight = 10;
        alwaysUseResponseButton = new Button(composite, SWT.CHECK);
        String alwaysText = Util.getString("StructuralCopyWizardPage.alwaysDeselect"); //$NON-NLS-1$
        alwaysUseResponseButton.setText(alwaysText);
        alwaysUseResponseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                alwaysUseResponse = alwaysUseResponseButton.getSelection();
            }
        });
        Composite buttonsComposite = new Composite(composite, SWT.NONE);
        GridLayout buttonsLayout = new GridLayout();
        buttonsLayout.numColumns = 2;
        buttonsComposite.setLayout(buttonsLayout);
        GridData buttonsGridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        buttonsComposite.setLayoutData(buttonsGridData);
        Button yesButton = createButton(buttonsComposite, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, false);
        yesButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                yesPressed = true;
                closeDialog();
            }
        });
        Button noButton = createButton(buttonsComposite, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
        noButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                closeDialog();
            }
        });
        return composite;
    }

    void closeDialog() {
        int returnCode;
        if (yesPressed) {
            returnCode = SWT.YES;
        } else {
            returnCode = SWT.NO;
        }
        setReturnCode(returnCode);
        close();
    }

    public boolean alwaysUseResponse() {
        return alwaysUseResponse;
    }
}// end DeselectDescendantsDialog
