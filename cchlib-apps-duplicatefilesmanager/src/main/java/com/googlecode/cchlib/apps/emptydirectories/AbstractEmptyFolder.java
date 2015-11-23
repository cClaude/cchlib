package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NotDirectoryException;
import javax.annotation.Nonnull;

public abstract class AbstractEmptyFolder implements EmptyFolder
{
    private static final long serialVersionUID = 1L;
    private EmptyFolderType type;

    public AbstractEmptyFolder( final EmptyFolderType type )
    {
        this.type = type;
    }

    /**
     * @return true if folder have no entry, false otherwise
     */
    @Override
    public final boolean isEmpty()
    {
        return this.type.equals( EmptyFolderType.IS_EMPTY );
    }

    /**
     * @return true if folder contain only folder with no entry (rec), false otherwise
     */
    @Override
    public final boolean isContaintOnlyEmptyFolders()
    {
        return this.type.equals( EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS );
    }

    @Override
    public final String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "EmptyFolder [path=" );
        builder.append( getPath() );
        builder.append( ", type=" );
        builder.append( this.type );
        builder.append( ']' );
        return builder.toString();
    }

    public final int compareToEmptyFolder( final Folder aFolder )
    {
        if( aFolder instanceof EmptyFolder ) {
            final EmptyFolder aEmptyFolder = EmptyFolder.class.cast( aFolder );

            if( this.isEmpty() == aEmptyFolder.isEmpty() ) {
                return /*((Folder)this).*/compareTo( aEmptyFolder );
                }
            else {
                return -2; // $codepro.audit.disable numericLiterals
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
    public final void check() throws NotDirectoryException, DirectoryNotEmptyException
    {
        final File file = getFile();

        if( ! file.isDirectory() ) {
            throw new NotDirectoryException( file.getPath() );
            }

        @Nonnull final File[] files = file.listFiles();

        for( final File f : files ) {
            if( ! f.isDirectory() ) {
                throw new DirectoryNotEmptyException( file.getPath() );
                }
            }

        if( files.length == 0 ) {
            this.type = EmptyFolderType.IS_EMPTY;
            }
        else {
            // Child folders are not checked !
            this.type = EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS;
            }
    }
}
