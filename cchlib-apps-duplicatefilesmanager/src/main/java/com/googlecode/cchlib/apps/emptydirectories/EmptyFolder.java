package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * TODOC
 */
public class EmptyFolder implements Serializable, Comparable<EmptyFolder>
{
    private static final long serialVersionUID = 1L;

    /**
     * TODOC
     * @param folder
     * @return TODOC
     */
    public static EmptyFolder createEmptyFolder( final Path folder )
    {
        return new EmptyFolder( folder, EFType.IS_EMPTY );
    }

    /**
     * TODOC
     * @param folder
     * @return TODOC
     */
    public static EmptyFolder createCouldBeEmptyFolder( final Path folder )
    {
        return new EmptyFolder( folder, EFType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

    private enum EFType {IS_EMPTY, CONTAINT_ONLY_EMPTY_FOLDERS};
    private Path path;
    private EFType type;

    private EmptyFolder( final Path folder, final EFType type )
    {
        this.path = folder;
        this.type = type;
    }

    public boolean isEmpty()
    {
        return type.equals( EFType.IS_EMPTY );
    }

    public boolean containtOnlyEmptyFolders()
    {
        return type.equals( EFType.IS_EMPTY );
    }
    
    public Path getPath()
    {
        return this.path;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( obj instanceof File ) {
            //return equalsFile( File.class.cast( obj ) );
            throw new IllegalStateException( "Compare with File" );
            }
        if( obj instanceof Path ) {
            //return equalsPath( Path.class.cast( obj ) );
            throw new IllegalStateException( "Compare with Path" );
            }
        
        if( this == obj ) {
            return true;
            }
        if( obj == null ) {
            return false;
            }
        if( !(obj instanceof EmptyFolder) ) {
            return false;
            }
        
        EmptyFolder other = (EmptyFolder)obj;

        if( path == null ) {
            if( other.path != null ) {
                return false;
                }
            }
        else if( !path.equals( other.path ) ) {
            return false;
            }

        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "EmptyFolder [path=" );
        builder.append( path );
        builder.append( ", type=" );
        builder.append( type );
        builder.append( "]" );
        return builder.toString();
    }

    private boolean equalsPath( final Path path )
    {
        return this.path.equals( path );
    }

    private boolean equalsFile( final File file )
    {
        return this.path.equals( file.toPath() );
    }

    @Override
    public int compareTo( EmptyFolder o )
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
