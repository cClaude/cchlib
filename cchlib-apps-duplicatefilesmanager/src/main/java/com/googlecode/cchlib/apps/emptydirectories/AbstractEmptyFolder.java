package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NotDirectoryException;

public abstract class AbstractEmptyFolder implements EmptyFolder
{
    private static final long serialVersionUID = 1L;
    private EmptyFolderType type;
    
    public AbstractEmptyFolder( EmptyFolderType type )
    {
        this.type = type;
    }

    /**
     * @return true if folder have no entry, false otherwise
     */
    @Override
    final // TODO: remove this
    public boolean isEmpty()
    {
        return type.equals( EmptyFolderType.IS_EMPTY );
    }

    /**
     * @return true if folder contain only folder with no entry (rec), false otherwise
     */
    @Override
    final // TODO: remove this
    public boolean containtOnlyEmptyFolders()
    {
        return type.equals( EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

    @Override
    final // TODO: remove this
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

    final // TODO: remove this
    public int compareToEmptyFolder( final Folder aFolder )
    {
        if( aFolder instanceof EmptyFolder ) {
            EmptyFolder aEmptyFolder = EmptyFolder.class.cast( aFolder );
            
            if( this.isEmpty() == aEmptyFolder.isEmpty() ) {
                return /*((Folder)this).*/compareTo( aEmptyFolder ); 
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
    @Override
    final // TODO: remove this
    public void check() throws NotDirectoryException, DirectoryNotEmptyException
    {
        final File file = getFile();
        
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
            type = EmptyFolderType.IS_EMPTY;
            }
        else {
            // Child folders are not checked !
            type = EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS;
            }
    }
}