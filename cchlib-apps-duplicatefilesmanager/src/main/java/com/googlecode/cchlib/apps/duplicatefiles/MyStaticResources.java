// $codepro.audit.disable staticFieldNamingConvention
package com.googlecode.cchlib.apps.duplicatefiles;

import javax.swing.Icon;

public class MyStaticResources
{
    private static Icon emptyIcon;
    private static Icon emptySelectedIcon;
    private static Icon emptySelectedByUserIcon;
    private static Icon emptySelectedAndSelectedByUserIcon;

    private static Icon emptyLeafIcon;
    private static Icon emptyLeafSelectedIcon;
    private static Icon emptyLeafSelectedByUserIcon;
    private static Icon emptyLeafSelectedAndSelectedByUserIcon;

    private static Icon duplicateFilesPanelIcon;
    private static Icon removeEmptyDirectoriesPanelIcon;
    private static Icon deleteEmptyFilesPanelIcon;
    private static Icon deletedFileIcon;
    private static Icon fileIcon;

    private MyStaticResources() {}

    public static Icon getEmptyIcon()
    {
        if( emptyIcon == null ) {
            emptyIcon = MyResourcesLoader.getImageIcon( "emptyIcon.png" );
            }
        return emptyIcon;
    }

    public static Icon getEmptySelectedIcon()
    {
        if( emptySelectedIcon == null ) {
            emptySelectedIcon = MyResourcesLoader.getImageIcon( "emptySelectedIcon.png" );
            }
        return emptySelectedIcon;
    }

    public static Icon getEmptyLeafIcon()
    {
        if( emptyLeafIcon == null ) {
            emptyLeafIcon = MyResourcesLoader.getImageIcon( "emptyLeafIcon.png" );
            }
        return emptyLeafIcon;
    }

    public static Icon getEmptyLeafSelectedIcon()
    {
        if( emptyLeafSelectedIcon == null ) {
            emptyLeafSelectedIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedIcon.png" );
            }
        return emptyLeafSelectedIcon;
    }

    public static Icon getDuplicateFilesPanelIcon()
    {
        if( duplicateFilesPanelIcon == null ) {
            duplicateFilesPanelIcon = MyResourcesLoader.getImageIcon( "duplicateFilesPanelIcon.png" );
            }
        return duplicateFilesPanelIcon;
    }

    public static Icon getRemoveEmptyDirectoriesPanelIcon()
    {
        if( removeEmptyDirectoriesPanelIcon == null ) {
            removeEmptyDirectoriesPanelIcon = MyResourcesLoader.getImageIcon( "removeEmptyDirectoriesPanelIcon.png" );
            }
        return removeEmptyDirectoriesPanelIcon;
    }

    public static Icon getDeleteEmptyFilesPanelIcon()
    {
        if( deleteEmptyFilesPanelIcon == null ) {
            deleteEmptyFilesPanelIcon = MyResourcesLoader.getImageIcon( "deleteEmptyFilesPanelIcon.png" );
            }
        return deleteEmptyFilesPanelIcon;
    }

    public static Icon getDeletedFileIcon()
    {
        if( deletedFileIcon == null ) {
            deletedFileIcon = MyResourcesLoader.getImageIcon( "deletedFileIcon.png" );
            }
        return deletedFileIcon;
    }

    public static Icon getFileIcon()
    {
        if( fileIcon == null ) {
            fileIcon = MyResourcesLoader.getImageIcon( "fileIcon.png" );
            }
        return fileIcon;
    }

    public static Icon getEmptySelectedByUserIcon()
    {
        if( emptySelectedByUserIcon == null ) {
            emptySelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptySelectedByUserIcon.png" );
            }
        return emptySelectedByUserIcon;
    }

    public static Icon getEmptySelectedAndSelectedByUserIcon()
    {
        if( emptySelectedAndSelectedByUserIcon == null ) {
            emptySelectedAndSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptySelectedAndSelectedByUserIcon.png" );
            }
        return emptySelectedAndSelectedByUserIcon;
    }

    public static Icon getEmptyLeafSelectedByUserIcon()
    {
        if( emptyLeafSelectedByUserIcon == null ) {
            emptyLeafSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedByUserIcon.png" );
            }
        return emptyLeafSelectedByUserIcon;
    }

    public static Icon getEmptyLeafSelectedAndSelectedByUserIcon()
    {
        if( emptyLeafSelectedAndSelectedByUserIcon == null ) {
            emptyLeafSelectedAndSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedAndSelectedByUserIcon.png" );
            }
        return emptyLeafSelectedAndSelectedByUserIcon;
    }
}
