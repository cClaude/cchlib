package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class BadDateISOFileFilter extends DateISOFileFilter
{
    public static final String PARTIAL_ISO_PATTERN_STRING =
        "....-..-.." ;
    public static final Pattern PARTIAL_ISO_PATTERN =
        Pattern.compile( PARTIAL_ISO_PATTERN_STRING + ".*" );

    private List<File> ignoredFilesFile;
    private List<File> ignoredDirsFile;
    private final List<File> ignoredISOFile;

    public BadDateISOFileFilter( final Set<Attributes> attributes )
    {
        super( buildSuperAttrib( attributes ) );

        if( getAttributes().contains( Attributes.REMENBER_IGNORED_DIRECTORIES )) {
            this.ignoredDirsFile = new ArrayList<>();
        }

        if( getAttributes().contains( Attributes.REMENBER_IGNORED_FILES )) {
            this.ignoredFilesFile = new ArrayList<>();
        }

        this.ignoredISOFile = new ArrayList<>();
    }

    private static EnumSet<Attributes> buildSuperAttrib( final Set<Attributes> attrib )
    {
        final Set<Attributes> superAttr = new HashSet<>();

        if( attrib.contains( Attributes.FILE_ONLY ) ) {
            superAttr.add( Attributes.FILE_ONLY );
        }
        if( attrib.contains( Attributes.DIRECTORY_ONLY ) ) {
            superAttr.add( Attributes.DIRECTORY_ONLY );
        }

        return EnumSet.copyOf( superAttr );
    }

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept( final File file )
    {
        final boolean isISO = super.accept( file );

        if( isISO ) {
            this.ignoredISOFile.add( file );
        }
        else {
            if( getAttributes().contains( Attributes.FILE_ONLY ) && file.isDirectory() ) {
                if( getAttributes().contains( Attributes.REMENBER_IGNORED_DIRECTORIES )) {
                    this.ignoredDirsFile.add( file );
                }
                return false;
            }
            if( getAttributes().contains( Attributes.DIRECTORY_ONLY ) && file.isFile() ) {
                if( getAttributes().contains( Attributes.REMENBER_IGNORED_FILES )) {
                    this.ignoredFilesFile.add( file );
                    }
                return false;
            }

            if( PARTIAL_ISO_PATTERN.matcher( file.getName() ).matches() ) {
                return true;
            }
            else if( file.isDirectory() ) {
                if( getAttributes().contains( Attributes.REMENBER_IGNORED_DIRECTORIES )) {
                    this.ignoredDirsFile.add( file );
                }
            }
            else if( file.isFile() ) {
                if( getAttributes().contains( Attributes.REMENBER_IGNORED_FILES )) {
                    this.ignoredFilesFile.add( file );
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
        if( this.ignoredFilesFile != null ) {
            this.ignoredFilesFile.clear();
        }
        if( this.ignoredDirsFile != null ) {
            this.ignoredDirsFile.clear();
        }
        if( this.ignoredISOFile != null ) {
            this.ignoredISOFile.clear();
        }
    }

    /**
     * @return the ignoredFilesFile
     */
    @Override
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
    @Override
    public List<File> getIgnoredDirsCopy()
    {
        if( this.ignoredDirsFile != null ) {
            return new ArrayList<>( this.ignoredDirsFile );
        }
        return new ArrayList<>();
    }

    public List<File> getIgnoredGoodISOCopy()
    {
        return new ArrayList<>( this.ignoredISOFile );
    }

    public File normalize( final File file )
    {
        final String        oldName = file.getName();
        final StringBuilder newName = new StringBuilder();

        // yyyy-mm-dd
        for( int i = 0; i<oldName.length(); i++ ) {
            final char c = oldName.charAt( i );

            if( (i == 4) || (i == 7) ) {
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
