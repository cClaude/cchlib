package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.io.File;

public class ExtensionsFileFilter extends AbstractExtensionsFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;

    public ExtensionsFileFilter( String extension,  String...others )
    {
        super( extension, others );
    }

    @Override
    public boolean accept( File file )
    {
        if( file.isFile() ) {
            final String name = file.getName();

            for( String endsWith : getEndsWiths() ) {
                if( name.endsWith( endsWith ) ) {
                     return true;
                    }
                }
            }

        return false;
    }

    @Override
    protected String customiseExtension( String extension )
    {
        return "." + extension;
    }
}
