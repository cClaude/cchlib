package com.googlecode.cchlib.tools.autorename.sortfiles;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class ExtensionsFileFilter implements FileFilter
{
    private String[] endsWiths;;

    public ExtensionsFileFilter( String[] extensions )
    {
        this.endsWiths = new String[ extensions.length ];

        for( int i = 0; i<endsWiths.length; i++ ) {
            this.endsWiths[ i ] = '.' + extensions[ i ];
            }
    }

    @Override
    public boolean accept( File file )
    {
        final String name = file.getName();

        for( String endsWith : this.endsWiths ) {
            if( name.endsWith( endsWith ) ) {
                //System.err.println( endsWith + " - " + file );
                 return true;
                }
            }
        
        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "ExtensionsFileFilter [endsWiths=" );
        builder.append( endsWiths != null ? Arrays.asList( endsWiths ).subList( 0, endsWiths.length) : null );
        builder.append( ']' );
        return builder.toString();
    }
}
