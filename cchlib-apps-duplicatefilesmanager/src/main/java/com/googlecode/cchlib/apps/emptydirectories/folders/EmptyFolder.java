package com.googlecode.cchlib.apps.emptydirectories.folders;

import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

/**
 * TODOC
 */
public class EmptyFolder extends Folder
{
    private static final long serialVersionUID = 2L;
    protected enum EFType {IS_EMPTY, CONTAINT_ONLY_EMPTY_FOLDERS};
    private EFType type;

    protected EmptyFolder( final Path path, final EFType type )
    {
        super( path );
        
        this.type = type;
    }

    public boolean isEmpty()
    {
        return type.equals( EFType.IS_EMPTY );
    }

    public boolean containtOnlyEmptyFolders()
    {
        return type.equals( EFType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "EmptyFolder [path=" );
        builder.append( getPath() );
        builder.append( ", type=" );
        builder.append( type );
        builder.append( "]" );
        return builder.toString();
    }

    public int compareToEmptyFolder( final Folder aFolder )
    {
        if( aFolder instanceof EmptyFolder ) {
            EmptyFolder aEmptyFolder = EmptyFolder.class.cast( aFolder );
            
            if( this.isEmpty() == aEmptyFolder.isEmpty() ) {
                return compareTo( aEmptyFolder ); 
                }
            else {
                return -2;
                }
            }
        else {
            return -1;
            }
    }

    /**
     * Check current directory and change state if needed, state is not change if
     * any exception occur.
     * <p>Child directories are not checked !</p>
     * 
     * @throws NotDirectoryException if EmptyFolder is no more a valid directory
     * @throws DirectoryNotEmptyException if there is at least one file in this directory
     */
    public void check() throws NotDirectoryException, DirectoryNotEmptyException
    {
        final File file = getPath().toFile();
        
        if( ! file.isDirectory() ) {
            throw new NotDirectoryException( file.getPath() ); 
            }
        
        final File[] files = file.listFiles();
        
        for( File f : files ) {
            if( ! f.isDirectory() ) {
                throw new DirectoryNotEmptyException( file.getPath() ); 
                }
            }
        
        if( files.length == 0 ) {
            this.type = EFType.IS_EMPTY;
            }
        else {
            // Child folders are not checked !
            this.type = EFType.CONTAINT_ONLY_EMPTY_FOLDERS;
            }
    }
}
