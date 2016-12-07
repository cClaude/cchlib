package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.io.Serializable;
import javax.swing.Icon;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.MyResourcesLoader;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.SerializableIcon;

public class IconResources implements Serializable
{
    private static final long serialVersionUID = 2L;

    private static class MyIcon implements SerializableIcon
    {
        private static final long serialVersionUID = 1L;

        private final String resourceName;
        private transient Icon icon;

        MyIcon( final String resourceName )
        {
            this.resourceName = resourceName;
        }

        @Override
        public Icon getIcon()
        {
            if( this.icon == null ) {
                this.icon = MyResourcesLoader .getImageIcon( this.resourceName );
            }
            return this.icon;
        }

    }
    private static volatile IconResources instance;

    private MyIcon emptyIcon;
    private MyIcon emptySelectedIcon;
    @Deprecated private Icon emptySelectedByUserIcon;
    @Deprecated private Icon emptySelectedAndSelectedByUserIcon;

    private MyIcon emptyLeafIcon;
    private MyIcon emptyLeafSelectedIcon;
    @Deprecated private Icon emptyLeafSelectedByUserIcon;
    @Deprecated private Icon emptyLeafSelectedAndSelectedByUserIcon;

    private MyIcon duplicateFilesPanelIcon;
    private MyIcon removeEmptyDirectoriesPanelIcon;
    private MyIcon deleteEmptyFilesPanelIcon;
    private MyIcon deletedFileIcon;
    private MyIcon fileIcon;

    private IconResources()
    {
        // Static
    }

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

    private MyIcon newSerializableIcon( final String resourceName )
    {
        return new MyIcon( resourceName );
    }

    private Icon newImageIcon( final String resourceName )
    {
        return MyResourcesLoader.getImageIcon( resourceName );
    }

    public Icon getEmptyIcon()
    {
        if( this.emptyIcon == null ) {
            this.emptyIcon = newSerializableIcon( "emptyIcon.png" );
            }
        return this.emptyIcon.getIcon();
    }

    public Icon getEmptySelectedIcon()
    {
        if( this.emptySelectedIcon == null ) {
            this.emptySelectedIcon = newSerializableIcon( "emptySelectedIcon.png" );
            }
        return this.emptySelectedIcon.getIcon();
    }

    public Icon getEmptyLeafIcon()
    {
        if( this.emptyLeafIcon == null ) {
            this.emptyLeafIcon = newSerializableIcon( "emptyLeafIcon.png" );
            }
        return this.emptyLeafIcon.getIcon();
    }

    public Icon getEmptyLeafSelectedIcon()
    {
        if( this.emptyLeafSelectedIcon == null ) {
            this.emptyLeafSelectedIcon = newSerializableIcon( "emptyLeafSelectedIcon.png" );
            }
        return this.emptyLeafSelectedIcon.getIcon();
    }

    public Icon getDuplicateFilesPanelIcon()
    {
        if( this.duplicateFilesPanelIcon == null ) {
            this.duplicateFilesPanelIcon = newSerializableIcon( "duplicateFilesPanelIcon.png" );
            }
        return this.duplicateFilesPanelIcon.getIcon();
    }

    public Icon getRemoveEmptyDirectoriesPanelIcon()
    {
        if( this.removeEmptyDirectoriesPanelIcon == null ) {
            this.removeEmptyDirectoriesPanelIcon = newSerializableIcon( "removeEmptyDirectoriesPanelIcon.png" );
            }
        return this.removeEmptyDirectoriesPanelIcon.getIcon();
    }

    public Icon getDeleteEmptyFilesPanelIcon()
    {
        if( this.deleteEmptyFilesPanelIcon == null ) {
            this.deleteEmptyFilesPanelIcon = newSerializableIcon( "deleteEmptyFilesPanelIcon.png" );
            }
        return this.deleteEmptyFilesPanelIcon.getIcon();
    }

    public Icon getDeletedFileIcon()
    {
        if( this.deletedFileIcon == null ) {
            this.deletedFileIcon = newSerializableIcon( "deletedFileIcon.png" );
            }
        return this.deletedFileIcon.getIcon();
    }

    public Icon getFileIcon()
    {
        if( this.fileIcon == null ) {
            this.fileIcon = newSerializableIcon( "fileIcon.png" );
            }
        return this.fileIcon.getIcon();
    }

    @Deprecated
    public Icon getEmptySelectedByUserIcon()
    {
        if( this.emptySelectedByUserIcon == null ) {
            this.emptySelectedByUserIcon = newImageIcon( "emptySelectedByUserIcon.png" );
            }
        return this.emptySelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptySelectedAndSelectedByUserIcon()
    {
        if( this.emptySelectedAndSelectedByUserIcon == null ) {
            this.emptySelectedAndSelectedByUserIcon = newImageIcon( "emptySelectedAndSelectedByUserIcon.png" );
            }
        return this.emptySelectedAndSelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptyLeafSelectedByUserIcon()
    {
        if( this.emptyLeafSelectedByUserIcon == null ) {
            this.emptyLeafSelectedByUserIcon = newImageIcon( "emptyLeafSelectedByUserIcon.png" );
            }
        return this.emptyLeafSelectedByUserIcon;
    }

    @Deprecated
    public Icon getEmptyLeafSelectedAndSelectedByUserIcon()
    {
        if( this.emptyLeafSelectedAndSelectedByUserIcon == null ) {
            this.emptyLeafSelectedAndSelectedByUserIcon = newImageIcon( "emptyLeafSelectedAndSelectedByUserIcon.png" );
            }
        return this.emptyLeafSelectedAndSelectedByUserIcon;
    }
}
