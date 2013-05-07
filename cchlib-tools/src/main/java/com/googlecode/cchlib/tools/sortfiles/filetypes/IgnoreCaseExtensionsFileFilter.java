package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.io.File;
import java.util.Arrays;

public class IgnoreCaseExtensionsFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;
    private String[] endsWiths;;

    public IgnoreCaseExtensionsFileFilter( String extension, String...others )
    {
        this.endsWiths = ExtensionsFileFilter.createExtensionsArrary( extension, others );

        for( int i = 0; i<endsWiths.length; i++ ) {
            this.endsWiths[ i ] = this.endsWiths[ i ].toLowerCase();
            }
    }

    @Override
    public boolean accept( File file )
    {
        if( file.isFile() ) {
            final String name = file.getName().toLowerCase();
            
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
        builder.append( "IgnoreCaseExtensionsFileFilter [" );
        builder.append( Arrays.asList( endsWiths ).subList( 0, endsWiths.length) );
        builder.append( ']' );
        return builder.toString();
    }
}
