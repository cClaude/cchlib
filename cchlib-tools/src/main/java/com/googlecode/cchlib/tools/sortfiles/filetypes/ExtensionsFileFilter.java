package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.io.File;
import java.util.Arrays;

public class ExtensionsFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;
    private String[] endsWiths;;

    public ExtensionsFileFilter( String extension,  String...others )
    {
        this.endsWiths = createExtensionsArrary( extension, others );
    }

    protected static String[] createExtensionsArrary( String extension,  String...others )
    {
        final String[] extentions = new String[ 1 + others.length ];

        extentions[ 0 ] = '.' + extension;

        for( int i = 0; i<others.length; i++ ) {
            extentions[ i + 1 ] = '.' + others[ i ];
            }

        return extentions;
    }

    @Override
    public boolean accept( File file )
    {
        if( file.isFile() ) {
            final String name = file.getName();

            for( String endsWith : this.endsWiths ) {
                if( name.endsWith( endsWith ) ) {
                     return true;
                    }
                }
            }

        return false;
    }

    @Override
    public String toDisplay()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "ExtensionsFileFilter [" );
        builder.append( Arrays.asList( endsWiths ).subList( 0, endsWiths.length) );
        builder.append( ']' );
        return builder.toString();
    }
}
