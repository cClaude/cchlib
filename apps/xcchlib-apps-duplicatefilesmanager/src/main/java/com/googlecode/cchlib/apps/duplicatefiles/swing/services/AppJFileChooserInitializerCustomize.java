package com.googlecode.cchlib.apps.duplicatefiles.swing.services;

import javax.swing.JFileChooser;
import com.googlecode.cchlib.swing.filechooser.DefaultJFCCustomizer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

//Not pubic
final class AppJFileChooserInitializerCustomize extends DefaultJFCCustomizer
{
    private static final long serialVersionUID = 1L;

    @Override
    public void perfomeConfig( final JFileChooser jfc )
    {
        super.perfomeConfig( jfc );

        jfc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        jfc.setMultiSelectionEnabled( true );
        jfc.setAccessory( new TabbedAccessory()
            .addTabbedAccessory( new BookmarksAccessory(
                        jfc,
                        new DefaultBookmarksAccessoryConfigurator() ) ) );
    }
}
