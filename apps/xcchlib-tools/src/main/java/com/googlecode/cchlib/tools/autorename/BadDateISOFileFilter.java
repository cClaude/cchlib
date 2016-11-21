package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 */
public class BadDateISOFileFilter extends DateISOFileFilter
{
    public final static String PARTIAL_ISO_PATTERN_STRING =
        "....-..-.." ;
    public final static Pattern PARTIAL_ISO_PATTERN =
        Pattern.compile( PARTIAL_ISO_PATTERN_STRING + ".*" );

    private EnumSet<Attrib> attribSet;
    private List<File> ignoredFilesFile;
    private List<File> ignoredDirsFile;
    private List<File> ignoredISOFile;

    /**
     * @param attributes
     *
     */
    public BadDateISOFileFilter( EnumSet<Attrib> attributes )
    {
        super( buildSuperAttrib(attributes) );
//        this.dirname = dirname;      directories
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
        ignoredISOFile = new ArrayList<File>();
    }

    private static EnumSet<Attrib> buildSuperAttrib( EnumSet<Attrib> attrib )
    {
        Set<Attrib> superAttr = new HashSet<Attrib>();

        if( attrib.contains( Attrib.FILE_ONLY ) ) {
            superAttr.add( Attrib.FILE_ONLY );
        }
        if( attrib.contains( Attrib.DIRECTORY_ONLY ) ) {
            superAttr.add( Attrib.DIRECTORY_ONLY );
        }

        return EnumSet.copyOf( superAttr );
    }

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept( File file )
    {
        boolean isISO = super.accept( file );

        if( isISO ) {
            ignoredISOFile.add( file );
        }
        else {
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

            if( PARTIAL_ISO_PATTERN.matcher( file.getName() ).matches() ) {
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
        }
        return false;
    }

    @Override
    public void clear()
    {
        if( ignoredFilesFile != null ) {
            ignoredFilesFile.clear();
        }
        if( ignoredDirsFile != null ) {
            ignoredDirsFile.clear();
        }
        if( ignoredISOFile != null ) {
            ignoredISOFile.clear();
        }
    }

    /**
     * @return the ignoredFilesFile
     */
    @Override
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
    @Override
    public List<File> getIgnoredDirsCopy()
    {
        if( ignoredDirsFile != null ) {
            return new ArrayList<File>( ignoredDirsFile );
        }
        return new ArrayList<File>();
    }

    public List<File> getIgnoredGoodISOCopy()
    {
        return new ArrayList<File>( ignoredISOFile );
    }

    public File normalize( final File file )
    {
        String        oldName = file.getName();
        StringBuilder newName = new StringBuilder();

        // yyyy-mm-dd
        for( int i = 0; i<oldName.length(); i++ ) {
            char c = oldName.charAt( i );

            if( i == 4 || i == 7 ) {
                newName.append( c );
            }
            else if( i < 10 ) {
                if( Character.isDigit( c ) ) {
                    newName.append( c );
                }
                else {
                    newName.append( '0' );
                }
            }
            else {
                newName.append( c );
            }
        }

        return new File( file.getParentFile(), newName.toString() );
    }
}
