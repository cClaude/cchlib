package com.googlecode.cchlib.swing.filechooser;

import javax.swing.JFileChooser;

import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.HexPreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

/**
 * TODOC
 *
 */
public class LasyJFCCustomizer extends DefaultJFCCustomizer
{
    private static final long serialVersionUID = 2L;

    private LastSelectedFilesAccessoryConfigurator lSFAConf;

    /**
     * TODOC
     */
    public LasyJFCCustomizer()
    {
        this( new LastSelectedFilesAccessoryDefaultConfigurator() );
    }

    /**
     * TODOC
     * @param configurator
     */
    public LasyJFCCustomizer(LastSelectedFilesAccessoryConfigurator configurator)
    {
        this.lSFAConf = configurator;
    }

    /**
     *
     */
    public void perfomeConfig( JFileChooser jfc )
    {
        super.perfomeConfig( jfc );

        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setMultiSelectionEnabled( true );

        HexPreviewAccessory hexAcc = new HexPreviewAccessory( jfc );
        TabbedAccessory     tabAcc = new TabbedAccessory()
            .addTabbedAccessory(
                new BookmarksAccessory(
                    jfc,
                    new DefaultBookmarksAccessoryConfigurator()
                    )
                )
            .addTabbedAccessory(
                new LastSelectedFilesAccessory(
                    jfc,
                    getLastSelectedFilesAccessoryConfigurator()
                    )
                )
            .addTabbedAccessory( hexAcc );

        tabAcc.setPreferredSize( hexAcc.getMinimumSize() );
        jfc.setAccessory( tabAcc );
//        jfc.setAccessory( new TabbedAccessory()
//            .addTabbedAccessory( new BookmarksAccessory(
//                jfc,
//                new DefaultBookmarksAccessoryConfigurator()
//                )
//            )
//        );
    }

    public LastSelectedFilesAccessoryConfigurator getLastSelectedFilesAccessoryConfigurator()
    {
        return this.lSFAConf;
    }
}

