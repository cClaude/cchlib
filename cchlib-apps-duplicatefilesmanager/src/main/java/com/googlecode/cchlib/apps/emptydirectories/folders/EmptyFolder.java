package com.googlecode.cchlib.apps.emptydirectories.folders;

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
        return type.equals( EFType.IS_EMPTY );
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
}
