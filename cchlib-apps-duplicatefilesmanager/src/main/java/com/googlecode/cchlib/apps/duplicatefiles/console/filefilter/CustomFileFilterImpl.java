package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.apps.duplicatefiles.console.CLIHelper;

class CustomFileFilterImpl implements Serializable, FileFilter
{
    private static final long serialVersionUID = 1L;

    private final Collection<String>  excludeNames;
    private final Collection<String>  excludePaths;
    private final Collection<Pattern> excludeRegexNames;
    private final Collection<Pattern> excludeRegexPaths;
    private final boolean             useIncludeRegexPaths;
    private final Collection<Pattern> includeRegexNames;
    private final boolean             useIncludeRegexNames;
    private final Collection<Pattern> includeRegexPaths;
    private final boolean             verbose;

    CustomFileFilterImpl(
            final Collection<String>  excludeNames,
            final Collection<String>  excludePaths,
            final Collection<Pattern> excludeRegexNames,
            final Collection<Pattern> excludeRegexPaths,
            final Collection<Pattern> includeRegexNames,
            final Collection<Pattern> includeRegexPaths,
            final boolean             verbose
            )
    {
        this.excludeNames         = unmodifiableCollection( excludeNames );
        this.excludePaths         = unmodifiableCollection( excludePaths );
        this.excludeRegexNames    = unmodifiableCollection( excludeRegexNames );
        this.excludeRegexPaths    = unmodifiableCollection( excludeRegexPaths );
        this.includeRegexNames    = unmodifiableCollection( includeRegexNames );
        this.useIncludeRegexNames = this.includeRegexNames.isEmpty();
        this.includeRegexPaths    = unmodifiableCollection( includeRegexPaths );
        this.useIncludeRegexPaths = this.includeRegexNames.isEmpty();
        this.verbose              = verbose;
    }

    private static <T> Collection<T> unmodifiableCollection( final Collection<T> c )
    {
        if( c == null ) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection( c );
    }

    @Override
    public boolean accept( @Nonnull final File file )
    {
        //
        // Exclude filter
        //
        if( this.excludeNames.contains( file.getName() ) ) {
            if( this.verbose ) {
                printMessageFromName( file, " is in excludeNames" );
            }
            return false;
        }

        if( this.excludePaths.contains( file.getPath() ) ) {
            if( this.verbose ) {
                printMessageFromPath( file, " is in excludePaths" );
            }
            return false;
        }

        if( contains( this.excludeRegexNames, file.getName() ) ) {
            if( this.verbose ) {
                printMessageFromName( file, " is in excludeRegexNames" );
            }
            return false;
        }

        if( contains( this.excludeRegexPaths, file.getPath() ) ) {
            if( this.verbose ) {
                printMessageFromPath( file, " is in excludeRegexPaths" );
            }
            return false;
        }

        //
        // Include
        //
        boolean include;

        if( this.useIncludeRegexNames ) {
            include = contains( this.includeRegexNames, file.getName() );

            if( this.verbose && !include ) {
                printMessageFromName( file, " is NOT in includeRegexNames" );
            }
        } else {
            include = true;
        }

        if( include && this.useIncludeRegexPaths ) {
            include = contains( this.includeRegexPaths, file.getPath() );

            if( this.verbose && !include ) {
                printMessageFromPath( file, " is NOT in useIncludeRegexPaths" );
            }
        }

        return include;
    }

    private void printMessageFromPath( final File file, final String message )
    {
        CLIHelper.printMessage(
                String.format(
                    "Ignore %s because %s %s values",
                    file,
                    file.getPath(),
                    message
                    )
                );
    }

    private void printMessageFromName( final File file, final String message )
    {
        CLIHelper.printMessage(
            String.format(
                "Ignore %s because %s %s values",
                file,
                file.getName(),
                message
                )
            );
    }

    private static boolean contains( final Collection<Pattern> patterns, final String file )
    {
        for( final Pattern pattern : patterns ) {
            if( pattern.matcher( file ).matches() ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "CustomFileFilterImpl [excludeNames=" );
        builder.append( this.excludeNames );
        builder.append( ", excludePaths=" );
        builder.append( this.excludePaths );
        builder.append( ", excludeRegexNames=" );
        builder.append( this.excludeRegexNames );
        builder.append( ", excludeRegexPaths=" );
        builder.append( this.excludeRegexPaths );
        builder.append( ", useIncludeRegexPaths=" );
        builder.append( this.useIncludeRegexPaths );
        builder.append( ", includeRegexNames=" );
        builder.append( this.includeRegexNames );
        builder.append( ", useIncludeRegexNames=" );
        builder.append( this.useIncludeRegexNames );
        builder.append( ", includeRegexPaths=" );
        builder.append( this.includeRegexPaths );
        builder.append( ", verbose=" );
        builder.append( this.verbose );
        builder.append( "]" );
        return builder.toString();
    }
}
