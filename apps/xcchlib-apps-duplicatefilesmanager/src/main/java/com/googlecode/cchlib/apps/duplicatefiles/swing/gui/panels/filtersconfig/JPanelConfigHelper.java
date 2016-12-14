package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.FileTypeCheckBox;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.FileFilterBuilderImpl;

//not public
class JPanelConfigHelper
{
    private static final Logger LOGGER = Logger.getLogger( JPanelConfigHelper.class );

    private JPanelConfigHelper()
    {
        // All static
    }

    static FileFilterBuilderImpl createExcludeFileFilter( //
            final boolean            userExcludeFileFilter,
            final JPanelConfigFilter jPanelConfigFilter
            )
    {
        final Set<String>   namesList = new HashSet<>();
        Pattern             pattern   = null;

        if( userExcludeFileFilter ) {
            for( final FileTypeCheckBox ft : jPanelConfigFilter ) {
                addNameIfItMakeSense(namesList,ft);
                }

            final boolean useRegExp = jPanelConfigFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelConfigFilter.getSelectedPattern();
                    }
                catch( final Exception ignore ){
                    LOGGER.error( ignore );
                    }
                }

            return new FileFilterBuilderImpl( namesList, pattern );
            }

        return null; // No filter
    }

    static FileFilterBuilderImpl createIncludeFileFilter( //
        final boolean            userIncludeFileFilter,
        final JPanelConfigFilter jPanelConfigFilter
        )
    {
        final Set<String>   extsList = new HashSet<>();
        Pattern             pattern  = null;

        if( userIncludeFileFilter ) {
            for( final FileTypeCheckBox ft : jPanelConfigFilter ) {
                addExtIfItMakeSense( extsList, ft );
                }

            final boolean useRegExp = jPanelConfigFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelConfigFilter.getSelectedPattern();
                    }
                catch( final Exception ignore ) {
                    LOGGER.error( ignore );
                    }
                }
            return new FileFilterBuilderImpl( extsList, pattern );
            }

        return null; // No filter
    }

    private static final void addExtIfItMakeSense(
        final Collection<String> c,
        final FileTypeCheckBox   ft
        )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(final String s:ft.getData().split( "," )) {
                if(s.length()>0) {
                    c.add( "." + s.toLowerCase() );
                }
            }
        }
    }

    private static final void addNameIfItMakeSense( //
        final Collection<String>    c, //
        final FileTypeCheckBox      ft //
        )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(final String s:ft.getData().split( "," )) {
                if( s.length() > 0 ) {
                    c.add( s.toLowerCase() );
                }
            }
        }
    }
}
