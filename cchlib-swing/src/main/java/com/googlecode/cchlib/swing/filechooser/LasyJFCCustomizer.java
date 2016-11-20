package com.googlecode.cchlib.swing.filechooser;

import javax.swing.JFileChooser;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.FindAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.HexPreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.ImagePreviewAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.LastSelectedFilesAccessoryDefaultConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

/**
 * NEEDDOC
 * @since 1.4.7
 */
public class LasyJFCCustomizer extends DefaultJFCCustomizer
{
    private static final long serialVersionUID = 2L;
    private final LastSelectedFilesAccessoryConfigurator lSFAConf;

    /**
     * NEEDDOC
     */
    public LasyJFCCustomizer()
    {
        this( new LastSelectedFilesAccessoryDefaultConfigurator() );
    }

    /**
     * NEEDDOC
     * @param configurator
     */
    public LasyJFCCustomizer(
        final LastSelectedFilesAccessoryConfigurator configurator
        )
    {
        this( configurator, JFileChooser.FILES_ONLY, false );
    }

    /**
     * NEEDDOC
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
    public void perfomeConfig( final JFileChooser jfc )
    {
        super.perfomeConfig( jfc );

        final HexPreviewAccessory hexAcc = new HexPreviewAccessory( jfc );
        final TabbedAccessory     tabAcc = new TabbedAccessory()
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
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public LastSelectedFilesAccessoryConfigurator getLastSelectedFilesAccessoryConfigurator()
    {
        return this.lSFAConf;
    }
}

