package com.googlecode.cchlib.swing.filechooser;

import javax.swing.JFileChooser;

import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.FindAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.ImagePreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.HexPreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

/**
 * TODOC
 * @since 1.4.7
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
    public LasyJFCCustomizer(
        final LastSelectedFilesAccessoryConfigurator configurator
        )
    {
        this( configurator, JFileChooser.FILES_ONLY, false );
    }

    /**
     * TODOC
     * @param configurator
     * @param fileSelectionMode
     * @param isMultiSelectionEnabled
     */
    public LasyJFCCustomizer(
        final LastSelectedFilesAccessoryConfigurator configurator,
        final int fileSelectionMode,
        final boolean isMultiSelectionEnabled
        )
    {
        this.lSFAConf = configurator;
        
        setFileSelectionMode( fileSelectionMode );
        setMultiSelectionEnabled( isMultiSelectionEnabled );
    }

    /**
     *
     */
    @Override
    public void perfomeConfig( JFileChooser jfc )
    {
        super.perfomeConfig( jfc );

        //jfc.setFileSelectionMode( getFileSelectionMode() );
        //jfc.setMultiSelectionEnabled( true );

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
            .addTabbedAccessory( new ImagePreviewAccessory( jfc ) )
            .addTabbedAccessory( new FindAccessory( jfc ) )
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

    /**
     * TODOC
     * @return TODOC
     */
    public LastSelectedFilesAccessoryConfigurator getLastSelectedFilesAccessoryConfigurator()
    {
        return this.lSFAConf;
    }

//    /**
//     * Returns value to set using {@link JFileChooser#setFileSelectionMode(int)}
//     * @return value to set using {@link JFileChooser#setFileSelectionMode(int)}
//     */
//    public int getFileSelectionMode()
//    {
//        return fileSelectionMode;
//    }

//    /**
//     * Returns value to set using {@link JFileChooser#setMultiSelectionEnabled(boolean)}
//     * @return value to set using {@link JFileChooser#setMultiSelectionEnabled(boolean)}
//     */
//    public boolean isMultiSelectionEnabled()
//    {
//        return isMultiSelectionEnabled;
//    }

}

