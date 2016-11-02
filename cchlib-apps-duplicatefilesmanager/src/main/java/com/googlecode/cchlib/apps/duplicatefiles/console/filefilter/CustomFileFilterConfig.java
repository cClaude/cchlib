package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.FileFilter;
import java.io.Serializable;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Configuration to create {@link FileFilter} objects
 */
public class CustomFileFilterConfig implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Collection<String>  excludeNames;
    private Collection<String>  excludePaths;
    private Collection<Pattern> excludeRegexNames;
    private Collection<Pattern> excludeRegexPaths;
    private Collection<Pattern> includeRegexNames;
    private Collection<Pattern> includeRegexPaths;

    public Collection<String> getExcludeNames()
    {
        return this.excludeNames;
    }

    public Collection<String> getExcludePaths()
    {
        return this.excludePaths;
    }

    public Collection<Pattern> getExcludeRegexNames()
    {
        return this.excludeRegexNames;
    }

    public Collection<Pattern> getExcludeRegexPaths()
    {
        return this.excludeRegexPaths;
    }

    public Collection<Pattern> getIncludeRegexNames()
    {
        return this.includeRegexNames;
    }

    public Collection<Pattern> getIncludeRegexPaths()
    {
        return this.includeRegexPaths;
    }

    public void setExcludeNames( final Collection<String> excludeNames )
    {
        this.excludeNames = excludeNames;
    }

    public void setExcludePaths( final Collection<String> excludePaths )
    {
        this.excludePaths = excludePaths;
    }

    public void setExcludeRegexNames( final Collection<Pattern> excludeRegexNames )
    {
        this.excludeRegexNames = excludeRegexNames;
    }

    public void setExcludeRegexPaths( final Collection<Pattern> excludeRegexPaths )
    {
        this.excludeRegexPaths = excludeRegexPaths;
    }

    public void setIncludeRegexNames( final Collection<Pattern> includeRegexNames )
    {
        this.includeRegexNames = includeRegexNames;
    }

    public void setIncludeRegexPaths( final Collection<Pattern> includeRegexPaths )
    {
        this.includeRegexPaths = includeRegexPaths;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "CustomFileFilterConfig [excludeNames=" );
        builder.append( this.excludeNames );
        builder.append( ", excludePaths=" );
        builder.append( this.excludePaths );
        builder.append( ", excludeRegexNames=" );
        builder.append( this.excludeRegexNames );
        builder.append( ", excludeRegexPaths=" );
        builder.append( this.excludeRegexPaths );
        builder.append( ", includeRegexNames=" );
        builder.append( this.includeRegexNames );
        builder.append( ", includeRegexPaths=" );
        builder.append( this.includeRegexPaths );
        builder.append( "]" );

        return builder.toString();
    }
}
