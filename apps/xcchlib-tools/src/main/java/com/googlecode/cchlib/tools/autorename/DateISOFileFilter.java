/**
 *
 */
package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;
import java.io.FileFilter;

/**
 * @author CC
 *
 */
public class DateISOFileFilter implements FileFilter
{
    public enum Attrib{
        FILE_ONLY,
        DIRECTORY_ONLY,
        REMENBER_IGNORED_DIRECTORIES,
        REMENBER_IGNORED_FILES
        };
    protected static final String ISO_PATTERN_STRING =
        "\\d\\d\\d\\d-\\d\\d-\\d\\d" ;
    protected static final Pattern ISO_PATTERN =
        Pattern.compile( ISO_PATTERN_STRING + ".*" );

    private EnumSet<Attrib> attribSet;
    private List<File> ignoredFilesFile;
    private List<File> ignoredDirsFile;

    /**
     * @param attributes
     *
     */
    public DateISOFileFilter( EnumSet<Attrib> attributes )
    {
        if( attributes == null ) {
            this.attribSet = EnumSet.noneOf( Attrib.class );
        }
        else {
            this.attribSet = attributes;
            }

        if( attribSet.contains( Attrib.REMENBER_IGNORED_DIRECTORIES )) {
            ignoredDirsFile = new ArrayList<File>();
        }
        if( attribSet.contains( Attrib.REMENBER_IGNORED_FILES )) {
            ignoredFilesFile = new ArrayList<File>();
        }

    }

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept( File file )
    {
        if( attribSet.contains( Attrib.FILE_ONLY ) && file.isDirectory() ) {
            if( attribSet.contains( Attrib.REMENBER_IGNORED_DIRECTORIES )) {
                ignoredDirsFile.add( file );
            }
            return false;
        }
        if( attribSet.contains( Attrib.DIRECTORY_ONLY ) && file.isFile() ) {
            if( attribSet.contains( Attrib.REMENBER_IGNORED_FILES )) {
                ignoredFilesFile.add( file );
                }
            return false;
        }

        if( ISO_PATTERN.matcher( file.getName() ).matches() ) {
            return true;
        }
        else if( file.isDirectory() ) {
            if( attribSet.contains( Attrib.REMENBER_IGNORED_DIRECTORIES )) {
                ignoredDirsFile.add( file );
            }
        }
        else if( file.isFile() ) {
            if( attribSet.contains( Attrib.REMENBER_IGNORED_FILES )) {
                ignoredFilesFile.add( file );
            }
        }
        else {
            System.err.println( "Ignore: " + file );
        }

        return false;
    }

    public void clear()
    {
        if( ignoredFilesFile != null ) {
            ignoredFilesFile.clear();
        }
        if( ignoredDirsFile != null ) {
            ignoredDirsFile.clear();
        }
    }

    /**
     * @return the ignoredFilesFile
     */
    public List<File> getIgnoredFilesCopy()
    {
        if( ignoredFilesFile != null ) {
            return new ArrayList<File>( ignoredFilesFile );
        }
        return new ArrayList<File>();
    }

    /**
     * @return the ignoredDirsFile
     */
    public List<File> getIgnoredDirsCopy()
    {
        if( ignoredDirsFile != null ) {
            return new ArrayList<File>( ignoredDirsFile );
        }
        return new ArrayList<File>();
    }
//
//    /**
//     * @param ignoredFilesFile the ignoredFilesFile to set
//     */
//    public void addIgnoredFile( List<File> ignoredFilesFile )
//    {
//        this.ignoredFilesFile = ignoredFilesFile;
//    }
//
//    /**
//     * @param ignoredDirsFile the ignoredDirsFile to set
//     */
//    public void setIgnoredDirsFile( List<File> ignoredDirsFile )
//    {
//        this.ignoredDirsFile = ignoredDirsFile;
//    }

}
