package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class FiltersConfig implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Collection<FilterEntry>        fileTypes            = new ArrayList<>();
    private Collection<FilterEntry>        dirTypes             = new ArrayList<>();
    private Collection<ProtectedDirectory> protectedDirectories = new ArrayList<>();

    public Collection<FilterEntry> getFileTypes()
    {
        return this.fileTypes;
    }

    public void setFileTypes( final Collection<FilterEntry> fileTypes )
    {
        this.fileTypes = fileTypes;
    }

    public Collection<FilterEntry> getDirTypes()
    {
        return this.dirTypes;
    }

    public void setDirTypes( final Collection<FilterEntry> dirTypes )
    {
        this.dirTypes = dirTypes;
    }

    public Collection<ProtectedDirectory> getProtectedDirectories()
    {
        return this.protectedDirectories;
    }

    public void setProtectedDirectories( final Collection<ProtectedDirectory> protectedDirectories )
    {
        this.protectedDirectories = protectedDirectories;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "FiltersConfig [fileTypes=" );
        builder.append( this.fileTypes );
        builder.append( ", dirTypes=" );
        builder.append( this.dirTypes );
        builder.append( ", protectedDirectories=" );
        builder.append( this.protectedDirectories );
        builder.append( "]" );

        return builder.toString();
    }
}
