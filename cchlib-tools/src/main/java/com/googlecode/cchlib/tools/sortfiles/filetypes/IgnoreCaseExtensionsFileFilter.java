package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.io.File;

public class IgnoreCaseExtensionsFileFilter extends AbstractExtensionsFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;

    public IgnoreCaseExtensionsFileFilter( final String extension, final String...others )
    {
        super( extension, others );
    }

    @Override
    public boolean accept( final File file )
    {
        if( file.isFile() ) {
            final String name = file.getName().toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
            
            for( String endsWith : getEndsWiths() ) {
                if( name.endsWith( endsWith ) ) {
                    return true;
                    }
                }
            }

        return false;
    }

    @Override
    protected String customiseExtension( final String extension )
    {
        return "." + extension.toLowerCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
    }
}
