/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package com.metamatrix.modeler.ui.viewsupport;

import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import com.metamatrix.modeler.internal.core.workspace.ModelUtil;
import com.metamatrix.modeler.internal.core.workspace.WorkspaceResourceFinderUtil;
import com.metamatrix.modeler.internal.ui.viewsupport.ModelIdentifier;
import com.metamatrix.modeler.internal.ui.viewsupport.ModelUtilities;

/**
 * Contains static helper methods for working with IPropertiesContext properties
 */
public class DesignerPropertiesUtil {

    private static final DesignerPropertiesUtil INSTANCE = new DesignerPropertiesUtil();

    /**
     * Get the DesignerPropertiesUtil instance for this VM.
     * 
     * @return the singleton instance for this VM; never null
     */
    public static DesignerPropertiesUtil getInstance() {
        return INSTANCE;
    }

    public static String getProjectName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_PROJECT_NAME);
    }

    public static String getVdbName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_LAST_VDB_NAME);
    }

    public static String getSourceModelName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_LAST_SOURCE_MODEL_NAME);
    }

    public static String getViewModelName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_LAST_VIEW_MODEL_NAME);
    }

    public static String getSourcesFolderName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_HAS_SOURCES_FOLDER);
    }

    public static String getViewsFolderName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_HAS_VIEWS_FOLDER);
    }

    public static String getPreviewTargetObjectName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_PREVIEW_TARGET_OBJECT);
    }

    public static String getPreviewTargetModelName( Properties properties ) {
        return properties.getProperty(IPropertiesContext.KEY_PREVIEW_TARGET_MODEL);
    }

    public static void setProjectName( Properties properties,
                                       String projectName ) {
        properties.put(IPropertiesContext.KEY_PROJECT_NAME, projectName);
    }

    public static void setVdbName( Properties properties,
                                   String vdbName ) {
        properties.put(IPropertiesContext.KEY_LAST_VDB_NAME, vdbName);
    }

    public static void setSourceModelName( Properties properties,
                                           String sourceModelName ) {
        properties.put(IPropertiesContext.KEY_LAST_SOURCE_MODEL_NAME, sourceModelName);
    }

    public static void setViewModelName( Properties properties,
                                         String viewModelName ) {
        properties.put(IPropertiesContext.KEY_LAST_VIEW_MODEL_NAME, viewModelName);
    }

    public static void setSourcesFolderName( Properties properties,
                                             String sourcesFolderName ) {
        properties.put(IPropertiesContext.KEY_HAS_SOURCES_FOLDER, sourcesFolderName);
    }

    public static void setViewsFolderName( Properties properties,
                                           String sourcesFolderName ) {
        properties.put(IPropertiesContext.KEY_HAS_VIEWS_FOLDER, sourcesFolderName);
    }

    public static void setPreviewTargetObjectName( Properties properties,
                                                   String previewTargetObjectName ) {
        properties.put(IPropertiesContext.KEY_PREVIEW_TARGET_OBJECT, previewTargetObjectName);
    }

    public static void setPreviewTargetModelName( Properties properties,
                                                  String previewTargetModelName ) {
        properties.put(IPropertiesContext.KEY_PREVIEW_TARGET_MODEL, previewTargetModelName);
    }

    /**
     * Get the Sources Folder, if the properties are defined
     * 
     * @param properties the Designer properties
     * @return the Sources Folder Container, null if not defined
     */
    public static IContainer getSourcesFolder( Properties properties ) {
        IContainer folder = null;
        // check for project property and if sources folder property exists
        String projectName = properties.getProperty(IPropertiesContext.KEY_PROJECT_NAME);
        if (projectName != null && !projectName.isEmpty()) {
            String folderName = projectName;
            String sourcesFolder = properties.getProperty(IPropertiesContext.KEY_HAS_SOURCES_FOLDER);
            if (sourcesFolder != null && !sourcesFolder.isEmpty()) {
                folderName = new Path(projectName).append(sourcesFolder).toString();
            }
            final IResource resrc = ResourcesPlugin.getWorkspace().getRoot().findMember(folderName);
            if (resrc != null) {
                folder = (IContainer)resrc;
            }
        }
        return folder;
    }

    /**
     * Get the Views Folder, if the properties are defined
     * 
     * @param properties the Designer properties
     * @return the Views Folder Container, null if not defined
     */
    public static IContainer getViewsFolder( Properties properties ) {
        IContainer folder = null;
        // check for project property and if sources folder property exists
        String projectName = properties.getProperty(IPropertiesContext.KEY_PROJECT_NAME);
        if (projectName != null && !projectName.isEmpty()) {
            String folderName = projectName;
            String viewsFolder = properties.getProperty(IPropertiesContext.KEY_HAS_VIEWS_FOLDER);
            if (viewsFolder != null && !viewsFolder.isEmpty()) {
                folderName = new Path(projectName).append(viewsFolder).toString();
            }
            final IResource resrc = ResourcesPlugin.getWorkspace().getRoot().findMember(folderName);
            if (resrc != null) {
                folder = (IContainer)resrc;
            }
        }
        return folder;
    }

    /**
     * Get the Project, if the properties are defined and project can be found
     * 
     * @param properties the Designer properties
     * @return the IProject, null if not defined or found
     */
    public static IProject getProject( Properties properties ) {
        IProject project = null;
        String projectName = properties.getProperty(IPropertiesContext.KEY_PROJECT_NAME);
        if (projectName != null) {
            final IResource resrc = ResourcesPlugin.getWorkspace().getRoot().findMember(projectName);
            if (resrc != null && resrc instanceof IProject) {
                project = (IProject)resrc;
            }
        }
        return project;
    }

    /**
     * Get the View Model, if the properties are defined and model can be found
     * 
     * @param properties the Designer properties
     * @return the IFile, null if not defined or found
     */
    public static IFile getViewModel( Properties properties ) {
        IFile viewModel = null;
        String modelName = properties.getProperty(IPropertiesContext.KEY_LAST_VIEW_MODEL_NAME);
        if (modelName != null) {
            final IResource resrc = ModelUtilities.findModelByName(modelName);
            if (resrc != null && ModelUtil.isModelFile(resrc) && ModelIdentifier.isVirtualModelType(resrc)) {
                viewModel = (IFile)resrc;
            }
        }
        return viewModel;
    }

    /**
     * Get the Source Model, if the properties are defined and model can be found
     * 
     * @param properties the Designer properties
     * @return the IFile, null if not defined or found
     */
    public static IFile getSourceModel( Properties properties ) {
        IFile sourceModel = null;
        String modelName = properties.getProperty(IPropertiesContext.KEY_LAST_SOURCE_MODEL_NAME);
        if (modelName != null) {
            final IResource resrc = ModelUtilities.findModelByName(modelName);
            if (resrc != null && ModelUtil.isModelFile(resrc) && ModelIdentifier.isPhysicalModelType(resrc)) {
                sourceModel = (IFile)resrc;
            }
        }
        return sourceModel;
    }

    /**
     * Get the VDB, if the properties are defined and vdb can be found
     * 
     * @param properties the Designer properties
     * @return the IResource, null if not defined or found
     */
    public static IResource getVDB( Properties properties ) {
        IResource vdbResource = null;
        // check for vdb name property
        String vdbName = properties.getProperty(IPropertiesContext.KEY_LAST_VDB_NAME);
        if (vdbName != null) {
            // Try to find VDB in workspace - collect only vdb resources from the workspace
            // Collect only vdb archive resources from the workspace
            final Collection vdbResources = WorkspaceResourceFinderUtil.getAllWorkspaceResources(WorkspaceResourceFinderUtil.VDB_RESOURCE_FILTER);
            for (final Iterator iter = vdbResources.iterator(); iter.hasNext();) {
                final IResource vdb = (IResource)iter.next();
                if (vdb.getFullPath().lastSegment().equalsIgnoreCase(vdbName)) {
                    vdbResource = vdb;
                    break;
                }
            }
        }
        return vdbResource;
    }

}
