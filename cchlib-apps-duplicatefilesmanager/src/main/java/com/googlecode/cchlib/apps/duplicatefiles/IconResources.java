// $codepro.audit.disable staticFieldNamingConvention
package com.googlecode.cchlib.apps.duplicatefiles;

import javax.swing.Icon;

public class IconResources
{
    private static IconResources instance;

    private Icon emptyIcon;
    private Icon emptySelectedIcon;
    @Deprecated private Icon emptySelectedByUserIcon;
    @Deprecated private Icon emptySelectedAndSelectedByUserIcon;

    private Icon emptyLeafIcon;
    private Icon emptyLeafSelectedIcon;
    @Deprecated private Icon emptyLeafSelectedByUserIcon;
    @Deprecated private Icon emptyLeafSelectedAndSelectedByUserIcon;

    private Icon duplicateFilesPanelIcon;
    private Icon removeEmptyDirectoriesPanelIcon;
    private Icon deleteEmptyFilesPanelIcon;
    private Icon deletedFileIcon;
    private Icon fileIcon;

    private IconResources() {}

    public static IconResources getInstance() {
        if( instance == null ) {
            instance = new IconResources();
            }
        return instance;
    }

    public Icon getEmptyIcon()
    {
        if( emptyIcon == null ) {
            emptyIcon = MyResourcesLoader.getImageIcon( "emptyIcon.png" );
            }
        return emptyIcon;
    }

    public Icon getEmptySelectedIcon()
    {
        if( emptySelectedIcon == null ) {
            emptySelectedIcon = MyResourcesLoader.getImageIcon( "emptySelectedIcon.png" );
            }
        return emptySelectedIcon;
    }

    public Icon getEmptyLeafIcon()
    {
        if( emptyLeafIcon == null ) {
            emptyLeafIcon = MyResourcesLoader.getImageIcon( "emptyLeafIcon.png" );
            }
        return emptyLeafIcon;
    }

    public Icon getEmptyLeafSelectedIcon()
    {
        if( emptyLeafSelectedIcon == null ) {
            emptyLeafSelectedIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedIcon.png" );
            }
        return emptyLeafSelectedIcon;
    }

    public Icon getDuplicateFilesPanelIcon()
    {
        if( duplicateFilesPanelIcon == null ) {
            duplicateFilesPanelIcon = MyResourcesLoader.getImageIcon( "duplicateFilesPanelIcon.png" );
            }
        return duplicateFilesPanelIcon;
    }

    public Icon getRemoveEmptyDirectoriesPanelIcon()
    {
        if( removeEmptyDirectoriesPanelIcon == null ) {
            removeEmptyDirectoriesPanelIcon = MyResourcesLoader.getImageIcon( "removeEmptyDirectoriesPanelIcon.png" );
            }
        return removeEmptyDirectoriesPanelIcon;
    }

    public Icon getDeleteEmptyFilesPanelIcon()
    {
        if( deleteEmptyFilesPanelIcon == null ) {
            deleteEmptyFilesPanelIcon = MyResourcesLoader.getImageIcon( "deleteEmptyFilesPanelIcon.png" );
            }
        return deleteEmptyFilesPanelIcon;
    }

    public Icon getDeletedFileIcon()
    {
        if( deletedFileIcon == null ) {
            deletedFileIcon = MyResourcesLoader.getImageIcon( "deletedFileIcon.png" );
            }
        return deletedFileIcon;
    }

    public Icon getFileIcon()
    {
        if( fileIcon == null ) {
            fileIcon = MyResourcesLoader.getImageIcon( "fileIcon.png" );
            }
        return fileIcon;
    }

    @Deprecated
    public Icon getEmptySelectedByUserIcon()
    {
        if( emptySelectedByUserIcon == null ) {
            emptySelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptySelectedByUserIcon.png" );
            }
        return emptySelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptySelectedAndSelectedByUserIcon()
    {
        if( emptySelectedAndSelectedByUserIcon == null ) {
            emptySelectedAndSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptySelectedAndSelectedByUserIcon.png" );
            }
        return emptySelectedAndSelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptyLeafSelectedByUserIcon()
    {
        if( emptyLeafSelectedByUserIcon == null ) {
            emptyLeafSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedByUserIcon.png" );
            }
        return emptyLeafSelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptyLeafSelectedAndSelectedByUserIcon()
    {
        if( emptyLeafSelectedAndSelectedByUserIcon == null ) {
            emptyLeafSelectedAndSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedAndSelectedByUserIcon.png" );
            }
        return emptyLeafSelectedAndSelectedByUserIcon;
    }
}
