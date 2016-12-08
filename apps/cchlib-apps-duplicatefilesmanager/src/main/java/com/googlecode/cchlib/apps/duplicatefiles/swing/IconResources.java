package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.io.Serializable;
import javax.swing.Icon;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.MyResourcesLoader;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.SerializableIcon;

public class IconResources implements Serializable
{
    private static final long serialVersionUID = 3L;

    private static final String DELETED_FILE_ICON_PNG             = "deletedFileIcon.png";
    private static final String DELETE_EMPTY_FILES_PANEL_ICON_PNG = "deleteEmptyFilesPanelIcon.png";

    private static final String DUPLICATE_FILES_PANEL_ICON_PNG = "duplicateFilesPanelIcon.png";

    private static final String EMPTY_ICON_PNG               = "emptyIcon.png";
    private static final String EMPTY_LEAF_ICON_PNG          = "emptyLeafIcon.png";
    private static final String EMPTY_LEAF_SELECTED_ICON_PNG = "emptyLeafSelectedIcon.png";
    private static final String EMPTY_SELECTED_ICON_PNG      = "emptySelectedIcon.png";

    private static final String FILE_ICON_PNG = "fileIcon.png";

    private static final String REMOVE_EMPTY_DIRECTORIES_PANEL_ICON_PNG = "removeEmptyDirectoriesPanelIcon.png";

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


    private MyIcon emptyLeafIcon;
    private MyIcon emptyLeafSelectedIcon;

    private MyIcon deleteEmptyFilesPanelIcon;
    private MyIcon deletedFileIcon;

    private MyIcon duplicateFilesPanelIcon;
    private MyIcon removeEmptyDirectoriesPanelIcon;
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

    public Icon getEmptyIcon()
    {
        if( this.emptyIcon == null ) {
            this.emptyIcon = newSerializableIcon( EMPTY_ICON_PNG );
            }
        return this.emptyIcon.getIcon();
    }

    public Icon getEmptySelectedIcon()
    {
        if( this.emptySelectedIcon == null ) {
            this.emptySelectedIcon = newSerializableIcon( EMPTY_SELECTED_ICON_PNG );
            }
        return this.emptySelectedIcon.getIcon();
    }

    public Icon getEmptyLeafIcon()
    {
        if( this.emptyLeafIcon == null ) {
            this.emptyLeafIcon = newSerializableIcon( EMPTY_LEAF_ICON_PNG );
            }
        return this.emptyLeafIcon.getIcon();
    }

    public Icon getEmptyLeafSelectedIcon()
    {
        if( this.emptyLeafSelectedIcon == null ) {
            this.emptyLeafSelectedIcon = newSerializableIcon(
                    EMPTY_LEAF_SELECTED_ICON_PNG
                    );
            }
        return this.emptyLeafSelectedIcon.getIcon();
    }

    public Icon getDuplicateFilesPanelIcon()
    {
        if( this.duplicateFilesPanelIcon == null ) {
            this.duplicateFilesPanelIcon = newSerializableIcon(
                    DUPLICATE_FILES_PANEL_ICON_PNG
                    );
            }
        return this.duplicateFilesPanelIcon.getIcon();
    }

    public Icon getRemoveEmptyDirectoriesPanelIcon()
    {
        if( this.removeEmptyDirectoriesPanelIcon == null ) {
            this.removeEmptyDirectoriesPanelIcon = newSerializableIcon(
                    REMOVE_EMPTY_DIRECTORIES_PANEL_ICON_PNG
                    );
            }
        return this.removeEmptyDirectoriesPanelIcon.getIcon();
    }

    public Icon getDeleteEmptyFilesPanelIcon()
    {
        if( this.deleteEmptyFilesPanelIcon == null ) {
            this.deleteEmptyFilesPanelIcon = newSerializableIcon(
                    DELETE_EMPTY_FILES_PANEL_ICON_PNG
                    );
            }
        return this.deleteEmptyFilesPanelIcon.getIcon();
    }

    public Icon getDeletedFileIcon()
    {
        if( this.deletedFileIcon == null ) {
            this.deletedFileIcon = newSerializableIcon( DELETED_FILE_ICON_PNG );
            }
        return this.deletedFileIcon.getIcon();
    }

    public Icon getFileIcon()
    {
        if( this.fileIcon == null ) {
            this.fileIcon = newSerializableIcon( FILE_ICON_PNG );
            }
        return this.fileIcon.getIcon();
    }

    public Icon getCurrentEmptySelectedIcon()
    {
        // FIXME under mouse icon should be different
        return getEmptySelectedIcon();
    }

    public Icon getCurrentEmptyIcon()
    {
        // FIXME under mouse icon should be different
        return getEmptyIcon();
    }

    public Icon getCurrentEmptyLeafSelectedIcon()
    {
        // FIXME under mouse icon should be different
        return getEmptyLeafSelectedIcon();
    }

    public Icon getCurrentEmptyLeafIcon()
    {
        // FIXME under mouse icon should be different
        return getEmptyLeafIcon();
    }
}
