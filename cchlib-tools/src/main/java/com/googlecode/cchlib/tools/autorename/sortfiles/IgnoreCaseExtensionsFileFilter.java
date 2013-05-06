package com.googlecode.cchlib.tools.autorename.sortfiles;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class IgnoreCaseExtensionsFileFilter implements FileFilter
{
    private String[] endsWiths;;

    public IgnoreCaseExtensionsFileFilter( String[] extensions )
    {
        this.endsWiths = new String[ extensions.length ];

        for( int i = 0; i<endsWiths.length; i++ ) {
            this.endsWiths[ i ] = '.' + extensions[ i ].toLowerCase();
            }
    }

    @Override
    public boolean accept( File file )
    {
        final String name = file.getName().toLowerCase();
        
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
        builder.append( "IgnoreCaseExtensionsFileFilter [endsWiths=" );
        builder.append( endsWiths != null ? Arrays.asList( endsWiths ).subList( 0, endsWiths.length) : null );
        builder.append( ']' );
        return builder.toString();
    }
}
