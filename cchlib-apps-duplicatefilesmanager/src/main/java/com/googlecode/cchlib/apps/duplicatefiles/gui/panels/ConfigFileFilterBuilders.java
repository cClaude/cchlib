package com.googlecode.cchlib.apps.duplicatefiles.gui.panels;

import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import cx.ath.choisnet.lang.ToStringBuilder;

final class ConfigFileFilterBuilders implements FileFilterBuilders {
    private final JPanelConfig  jPanelConfig;
    private final boolean       ignoreHiddedFiles;
    private final FilterType    efType;
    private final boolean       ignoreHiddedDirs;
    private final FilterType    ffType;
    private final boolean       ignoreEmptyFiles;
    private final boolean       ignoreReadOnlyFiles;

    ConfigFileFilterBuilders( //
        final JPanelConfig  jPanelConfig, //
        final boolean       ignoreHiddedFiles, //
        final FilterType    efType, //
        final boolean       ignoreHiddedDirs, //
        final FilterType    ffType, //
        final boolean       ignoreEmptyFiles, //
        final boolean       ignoreReadOnlyFiles //
        )
    {
        this.jPanelConfig           = jPanelConfig;
        this.ignoreHiddedFiles      = ignoreHiddedFiles;
        this.efType                 = efType;
        this.ignoreHiddedDirs       = ignoreHiddedDirs;
        this.ffType                 = ffType;
        this.ignoreEmptyFiles       = ignoreEmptyFiles;
        this.ignoreReadOnlyFiles    = ignoreReadOnlyFiles;
    }

    @Override
    public FileFilterBuilder getIncludeDirs()
    {
        if( this.efType == FilterType.INCLUDE_FILTER ) {
            return this.jPanelConfig.createIncludeDirectoriesFileFilterBuilder();
            }
        else {
            return null;
            }
    }

    @Override
    public FileFilterBuilder getExcludeDirs()
    {
        if( this.efType == FilterType.EXCLUDE_FILTER ) {
            return this.jPanelConfig.createExcludeDirectoriesFileFilterBuilder();
            }
        else {
            return null;
            }
    }

    @Override
    public FileFilterBuilder getIncludeFiles()
    {
        if( this.ffType == FilterType.INCLUDE_FILTER ) {
            return this.jPanelConfig.createIncludeFilesFileFilterBuilder();
            }
        else {
            return null;
            }
    }

    @Override
    public FileFilterBuilder getExcludeFiles()
    {
        if( this.ffType == FilterType.EXCLUDE_FILTER ) {
            return this.jPanelConfig.createExcludeFilesFileFilterBuilder();
            }
        else {
            return null;
            }
    }

    @Override
    public boolean isIgnoreHiddenDirs()
    {
        return this.ignoreHiddedDirs;
    }

    @Override
    public boolean isIgnoreHiddenFiles()
    {
        return this.ignoreHiddedFiles;
    }

    @Override
    public boolean isIgnoreReadOnlyFiles()
    {
        return this.ignoreReadOnlyFiles;
    }

    @Override
    public boolean isIgnoreEmptyFiles()
    {
        return this.ignoreEmptyFiles;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.toString( this, FileFilterBuilders.class );
    }
}