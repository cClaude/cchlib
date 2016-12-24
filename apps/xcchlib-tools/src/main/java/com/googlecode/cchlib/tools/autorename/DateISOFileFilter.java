package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class DateISOFileFilter implements FileFilter
{
    public enum Attributes
    {
        FILE_ONLY,
        DIRECTORY_ONLY,
        REMENBER_IGNORED_DIRECTORIES,
        REMENBER_IGNORED_FILES
    }

    protected static final String ISO_PATTERN_STRING =
        "\\d\\d\\d\\d-\\d\\d-\\d\\d" ;
    protected static final Pattern ISO_PATTERN =
        Pattern.compile( ISO_PATTERN_STRING + ".*" );

    private Set<Attributes> attributes;
    private List<File>  ignoredFilesFile;
    private List<File>  ignoredDirsFile;

    public DateISOFileFilter( final Set<Attributes> attributes )
    {
        if( attributes == null ) {
            this.attributes = Collections.unmodifiableSet( EnumSet.noneOf( Attributes.class ) );
        }
        else {
            this.attributes = Collections.unmodifiableSet( EnumSet.copyOf( attributes ) );
            }

        if( this.attributes.contains( Attributes.REMENBER_IGNORED_DIRECTORIES )) {
            this.ignoredDirsFile = new ArrayList<>();
        }
        if( this.attributes.contains( Attributes.REMENBER_IGNORED_FILES )) {
            this.ignoredFilesFile = new ArrayList<>();
        }
    }

    protected Set<Attributes> getAttributes()
    {
        return this.attributes;
    }

    @Override
    public boolean accept( final File file )
    {
        if( this.attributes.contains( Attributes.FILE_ONLY ) && file.isDirectory() ) {
            if( this.attributes.contains( Attributes.REMENBER_IGNORED_DIRECTORIES )) {
                this.ignoredDirsFile.add( file );
            }
            return false;
        }
        if( this.attributes.contains( Attributes.DIRECTORY_ONLY ) && file.isFile() ) {
            if( this.attributes.contains( Attributes.REMENBER_IGNORED_FILES )) {
                this.ignoredFilesFile.add( file );
                }
            return false;
        }

        if( ISO_PATTERN.matcher( file.getName() ).matches() ) {
            return true;
        }
        else if( file.isDirectory() ) {
            if( this.attributes.contains( Attributes.REMENBER_IGNORED_DIRECTORIES )) {
                this.ignoredDirsFile.add( file );
            }
        }
        else if( file.isFile() ) {
            if( this.attributes.contains( Attributes.REMENBER_IGNORED_FILES )) {
                this.ignoredFilesFile.add( file );
            }
        }
        else {
            System.err.println( "Ignore: " + file );
        }

        return false;
    }

    public void clear()
    {
        if( this.ignoredFilesFile != null ) {
            this.ignoredFilesFile.clear();
        }
        if( this.ignoredDirsFile != null ) {
            this.ignoredDirsFile.clear();
        }
    }

    /**
     * @return the ignoredFilesFile
     */
    public List<File> getIgnoredFilesCopy()
    {
        if( this.ignoredFilesFile != null ) {
            return new ArrayList<>( this.ignoredFilesFile );
        }
        return new ArrayList<>();
    }

    /**
     * @return the ignoredDirsFile
     */
    public List<File> getIgnoredDirsCopy()
    {
        if( this.ignoredDirsFile != null ) {
            return new ArrayList<>( this.ignoredDirsFile );
        }
        return new ArrayList<>();
    }
}
