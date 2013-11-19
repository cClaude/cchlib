package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractExtensionsFileFilter extends AbstractXFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;
    private String[] endsWiths;

    public AbstractExtensionsFileFilter( String extension,  String...others )
    {
        this.endsWiths = createExtensionsArrary( extension, others );
    }

    private String[] createExtensionsArrary( String extension,  String...others )
    {
        final String[] extentions = new String[ 1 + others.length ];

        //extentions[ 0 ] = '.' + extension;
        extentions[ 0 ] = customiseExtension( extension );

        for( int i = 0; i<others.length; i++ ) {
            extentions[ i + 1 ] = customiseExtension( others[ i ] );
            }

        return extentions;
    }

    protected abstract String customiseExtension( final String extension );
    
    public final String[] getEndsWiths()
    {
        return endsWiths;
    }
    
    public final List<String> getEndsWithsList()
    {
        return Arrays.asList( getEndsWiths() );
    }
    
    @Override
    final
    public String toDisplay()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( " [" );
        builder.append( getEndsWithsList() );
        builder.append( ']' );
        return builder.toString();
    }
}
