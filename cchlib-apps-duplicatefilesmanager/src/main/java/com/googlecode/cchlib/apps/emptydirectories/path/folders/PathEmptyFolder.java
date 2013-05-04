package com.googlecode.cchlib.apps.emptydirectories.path.folders;

import java.io.File;
import java.nio.file.Path;
import com.googlecode.cchlib.apps.emptydirectories.AbstractEmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.EmptyFolderType;
import com.googlecode.cchlib.apps.emptydirectories.Folder;

@Deprecated
public class PathEmptyFolder extends AbstractEmptyFolder //PathFolder implements EmptyFolder
{
    private static final long serialVersionUID = 2L;
    private Path path;

    protected PathEmptyFolder( final Path path, final EmptyFolderType type )
    {
        super( type );
        
        this.path = path;
    }

    @Override
    public Path getPath()
    {
        return path;
    }

    @Override
    public File getFile()
    {
        return path.toFile();
    }

    @Override
    public int compareTo( final Folder aEmptyFolder )
    {
        PathFolder other = PathFolder.class.cast( aEmptyFolder );
        
        return this.path.compareTo( other.getPath() );
    }

//    /**
//     * @return true if folder have no entry, false otherwise
//     */
//    @Override
//    public boolean isEmpty()
//    {
//        return type.equals( EmptyFolderType.IS_EMPTY );
//    }
//
//    /**
//     * @return true if folder contain only folder with no entry (rec), false otherwise
//     */
//    public boolean containtOnlyEmptyFolders()
//    {
//        return type.equals( EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS );
//    }
//
//    @Override
//    public String toString()
//    {
//        StringBuilder builder = new StringBuilder();
//        builder.append( "EmptyFolder [path=" );
//        builder.append( getPath() );
//        builder.append( ", type=" );
//        builder.append( type );
//        builder.append( "]" );
//        return builder.toString();
//    }
//
//    public int compareToEmptyFolder( final Folder aFolder )
//    {
//        if( aFolder instanceof EmptyFolder ) {
//            EmptyFolder aEmptyFolder = EmptyFolder.class.cast( aFolder );
//            
//            if( this.isEmpty() == aEmptyFolder.isEmpty() ) {
//                return compareTo( aEmptyFolder ); 
//                }
//            else {
//                return -2;
//                }
//            }
//        else {
//            return -1;
//            }
//    }
//
//    /**
//     * Check current directory and change state if needed, state is not change if
//     * any exception occur.
//     * <p>Child directories are not checked !</p>
//     * 
//     * @throws NotDirectoryException if EmptyFolder is no more a valid directory
//     * @throws DirectoryNotEmptyException if there is at least one file in this directory
//     */
//    @Override
//    public void check() throws NotDirectoryException, DirectoryNotEmptyException
//    {
//        final File file = getPath().toFile();
//        
//        if( ! file.isDirectory() ) {
//            throw new NotDirectoryException( file.getPath() ); 
//            }
//        
//        final File[] files = file.listFiles();
//        
//        for( File f : files ) {
//            if( ! f.isDirectory() ) {
//                throw new DirectoryNotEmptyException( file.getPath() ); 
//                }
//            }
//        
//        if( files.length == 0 ) {
//            this.type = EmptyFolderType.IS_EMPTY;
//            }
//        else {
//            // Child folders are not checked !
//            this.type = EmptyFolderType.CONTAINT_ONLY_EMPTY_FOLDERS;
//            }
//    }
}
