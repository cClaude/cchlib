// $codepro.audit.disable staticFieldNamingConvention
package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.io.Serializable;
import javax.swing.Icon;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.MyResourcesLoader;

public class IconResources implements Serializable
{
    private static final long serialVersionUID = 2L;

    private static volatile IconResources instance;

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

    public static IconResources getInstance()
    {
        if( instance == null ) {
            synchronized( IconResources.class ) {
                if( instance == null ) {
                    instance = new IconResources();
                }
            }
        }
        return instance;
    }

    public Icon getEmptyIcon()
    {
        if( this.emptyIcon == null ) {
            this.emptyIcon = MyResourcesLoader.getImageIcon( "emptyIcon.png" );
            }
        return this.emptyIcon;
    }

    public Icon getEmptySelectedIcon()
    {
        if( this.emptySelectedIcon == null ) {
            this.emptySelectedIcon = MyResourcesLoader.getImageIcon( "emptySelectedIcon.png" );
            }
        return this.emptySelectedIcon;
    }

    public Icon getEmptyLeafIcon()
    {
        if( this.emptyLeafIcon == null ) {
            this.emptyLeafIcon = MyResourcesLoader.getImageIcon( "emptyLeafIcon.png" );
            }
        return this.emptyLeafIcon;
    }

    public Icon getEmptyLeafSelectedIcon()
    {
        if( this.emptyLeafSelectedIcon == null ) {
            this.emptyLeafSelectedIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedIcon.png" );
            }
        return this.emptyLeafSelectedIcon;
    }

    public Icon getDuplicateFilesPanelIcon()
    {
        if( this.duplicateFilesPanelIcon == null ) {
            this.duplicateFilesPanelIcon = MyResourcesLoader.getImageIcon( "duplicateFilesPanelIcon.png" );
            }
        return this.duplicateFilesPanelIcon;
    }

    public Icon getRemoveEmptyDirectoriesPanelIcon()
    {
        if( this.removeEmptyDirectoriesPanelIcon == null ) {
            this.removeEmptyDirectoriesPanelIcon = MyResourcesLoader.getImageIcon( "removeEmptyDirectoriesPanelIcon.png" );
            }
        return this.removeEmptyDirectoriesPanelIcon;
    }

    public Icon getDeleteEmptyFilesPanelIcon()
    {
        if( this.deleteEmptyFilesPanelIcon == null ) {
            this.deleteEmptyFilesPanelIcon = MyResourcesLoader.getImageIcon( "deleteEmptyFilesPanelIcon.png" );
            }
        return this.deleteEmptyFilesPanelIcon;
    }

    public Icon getDeletedFileIcon()
    {
        if( this.deletedFileIcon == null ) {
            this.deletedFileIcon = MyResourcesLoader.getImageIcon( "deletedFileIcon.png" );
            }
        return this.deletedFileIcon;
    }

    public Icon getFileIcon()
    {
        if( this.fileIcon == null ) {
            this.fileIcon = MyResourcesLoader.getImageIcon( "fileIcon.png" );
            }
        return this.fileIcon;
    }

    @Deprecated
    public Icon getEmptySelectedByUserIcon()
    {
        if( this.emptySelectedByUserIcon == null ) {
            this.emptySelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptySelectedByUserIcon.png" );
            }
        return this.emptySelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptySelectedAndSelectedByUserIcon()
    {
        if( this.emptySelectedAndSelectedByUserIcon == null ) {
            this.emptySelectedAndSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptySelectedAndSelectedByUserIcon.png" );
            }
        return this.emptySelectedAndSelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptyLeafSelectedByUserIcon()
    {
        if( this.emptyLeafSelectedByUserIcon == null ) {
            this.emptyLeafSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedByUserIcon.png" );
            }
        return this.emptyLeafSelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptyLeafSelectedAndSelectedByUserIcon()
    {
        if( this.emptyLeafSelectedAndSelectedByUserIcon == null ) {
            this.emptyLeafSelectedAndSelectedByUserIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedAndSelectedByUserIcon.png" );
            }
        return this.emptyLeafSelectedAndSelectedByUserIcon;
    }
}
