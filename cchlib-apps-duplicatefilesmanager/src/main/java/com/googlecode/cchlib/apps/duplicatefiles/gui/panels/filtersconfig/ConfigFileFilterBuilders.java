package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.filtersconfig;

import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilderConfigurator;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import cx.ath.choisnet.lang.ToStringBuilder;

final class ConfigFileFilterBuilders implements FileFilterBuilders {
    private final FileFilterBuilder excludeDirs;
    private final FileFilterBuilder excludeFiles;

    private final FileFilterBuilder includeDirs;
    private final FileFilterBuilder includeFiles;

    private final boolean ignoreEmptyFiles;
    private final boolean ignoreHiddedDirs;
    private final boolean ignoreHiddedFiles;
    private final boolean ignoreReadOnlyFiles;

    ConfigFileFilterBuilders( //
        final FileFilterBuilderConfigurator configurator, //
        final boolean                       ignoreHiddedFiles, //
        final FilterType                    efType, //
        final boolean                       ignoreHiddedDirs, //
        final FilterType                    ffType, //
        final boolean                       ignoreEmptyFiles, //
        final boolean                       ignoreReadOnlyFiles //
        )
    {
        this.ignoreHiddedFiles      = ignoreHiddedFiles;
        this.ignoreHiddedDirs       = ignoreHiddedDirs;
        this.ignoreEmptyFiles       = ignoreEmptyFiles;
        this.ignoreReadOnlyFiles    = ignoreReadOnlyFiles;

        switch( efType ) {
            case EXCLUDE_FILTER:
                this.excludeDirs = configurator.createExcludeDirectoriesFileFilterBuilder();
                this.includeDirs = null;
                break;
            case INCLUDE_FILTER:
                this.includeDirs = configurator.createIncludeDirectoriesFileFilterBuilder();
                this.excludeDirs = null;
                break;
            default:
                throw new IllegalArgumentException( "Unhandle efType value: " + efType );
        }

        switch( ffType ) {
            case EXCLUDE_FILTER:
                this.includeFiles = null;
                this.excludeFiles = configurator.createExcludeFilesFileFilterBuilder();
                break;
            case INCLUDE_FILTER:
                this.includeFiles = configurator.createIncludeFilesFileFilterBuilder();
                this.excludeFiles = null;
                break;
            default:
                throw new IllegalArgumentException( "Unhandle ffType value: " + ffType );
        }
    }

    @Override
    public FileFilterBuilder getExcludeDirs()
    {
        return this.excludeDirs;
    }

    @Override
    public FileFilterBuilder getExcludeFiles()
    {
        return this.excludeFiles;
    }

    @Override
    public FileFilterBuilder getIncludeDirs()
    {
        return this.includeDirs;
    }

    @Override
    public FileFilterBuilder getIncludeFiles()
    {
        return this.includeFiles;
    }

    @Override
    public boolean isIgnoreEmptyFiles()
    {
        return this.ignoreEmptyFiles;
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
    public String toString()
    {
        return ToStringBuilder.toString( this, FileFilterBuilders.class );
    }
}