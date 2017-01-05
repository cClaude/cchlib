package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.swing.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.swing.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.swing.FileFilterBuilderConfigurator;
import com.googlecode.cchlib.apps.duplicatefiles.swing.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config.FiltersConfig;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import com.googlecode.cchlib.swing.menu.LookAndFeelListener;

@I18nName("duplicatefiles.JPanelConfig")
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class JPanelConfig
    extends JPanelConfigI18n
        implements LookAndFeelListener
{
    private final class FileFilterBuilderConfiguratorImpl implements FileFilterBuilderConfigurator {
        @Override
        public FileFilterBuilder createIncludeFilesFileFilterBuilder()
        {
            return JPanelConfigHelper.createIncludeFileFilter(
                getJComboBoxFilesFiltersSelectedItem() == FilterType.INCLUDE_FILTER,
                JPanelConfig.this.jPanelIncFilesFilter
                );
        }

        @Override
        public FileFilterBuilder createExcludeFilesFileFilterBuilder()
        {
            return JPanelConfigHelper.createIncludeFileFilter(
                getJComboBoxFilesFiltersSelectedItem() == FilterType.EXCLUDE_FILTER,
                JPanelConfig.this.jPanelExcFilesFilter
                );
        }

        @Override
        public FileFilterBuilder createIncludeDirectoriesFileFilterBuilder()
        {
            return JPanelConfigHelper.createIncludeFileFilter(
                getJComboBoxDirsFiltersSelectedItem() == FilterType.INCLUDE_FILTER,
                JPanelConfig.this.jPanelIncDirsFilter
                );
        }

        @Override
        public FileFilterBuilder createExcludeDirectoriesFileFilterBuilder()
        {
            return JPanelConfigHelper.createExcludeFileFilter(
                getJComboBoxDirsFiltersSelectedItem() == FilterType.EXCLUDE_FILTER,
                JPanelConfig.this.jPanelExcDirsFilter
                );
        }
    }

    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( JPanelConfig.class );

    private ConfigMode mode;

    private JPanelConfigFilter jPanelIncFilesFilter;
    private JPanelConfigFilter jPanelExcDirsFilter;
    private JPanelConfigFilter jPanelExcFilesFilter;
    private JPanelConfigFilter jPanelIncDirsFilter;

    public JPanelConfig()
    {
        super();
    }

    @Override//LookAndFeelListener
    public void setLookAndFeel( final String lookAndFeelName )
    {
        if( this.jPanelIncFilesFilter != null ) {
            SwingUtilities.updateComponentTreeUI( this.jPanelIncFilesFilter );
            }
        if( this.jPanelExcFilesFilter != null ) {
            SwingUtilities.updateComponentTreeUI( this.jPanelExcFilesFilter );
            }
        if( this.jPanelIncDirsFilter != null ) {
            SwingUtilities.updateComponentTreeUI( this.jPanelIncDirsFilter );
            }
        if( this.jPanelExcDirsFilter != null ) {
            SwingUtilities.updateComponentTreeUI( this.jPanelExcDirsFilter );
            }
    }

    @Override
    protected ActionListener getActionListener()
    {
        return event -> SafeSwingUtilities.invokeLater(
                () -> updateDisplay( true ), "updateDisplay()"
                );
    }

    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );

        final AppToolKit           appToolKit    = getAppToolKit();
        final FiltersConfig        filtersConfig = appToolKit.getResources().getFiltersConfig();
        final PreferencesControler prefs         = appToolKit.getPreferences();

        this.jPanelIncFilesFilter = new JPanelConfigFilter(
                getjPanelIncFilesFilterTitle(),
                getjPanelIncFilesFilterRegExp(),
                filtersConfig.getFileTypes()
                );

        for( final String exp : prefs.getIncFilesFilterPatternRegExpList() ) {
            this.jPanelIncFilesFilter.addPatternRegExp( exp );
            }

        this.jPanelExcFilesFilter = new JPanelConfigFilter(
                getjPanelExcFilesFilterTitle(),
                getjPanelExcFilesFilterRegExp(),
                filtersConfig.getFileTypes()
                );
        this.jPanelIncDirsFilter = new JPanelConfigFilter( // No values
                getjPanelIncDirsFilterTitle(),
                getjPanelIncDirsFilterRegExp()
                );
        this.jPanelExcDirsFilter = new JPanelConfigFilter(
                getjPanelExcDirsFilterTitle(),
                getjPanelExcDirsFilterRegExp(),
                filtersConfig.getDirTypes()
                );

        updateDisplay( false );
    }

    public void updateDisplay(
            final boolean doRepaint
            )
    {
        final ConfigMode prevMode = this.mode;
        this.mode                 = getAppToolKit().getPreferences().getConfigMode();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "updateDisplayMode()"
                    + prevMode
                    + " -> "
                    + this.mode
                    );
        }

        final boolean isModeChanged = !this.mode.equals( prevMode );

        if( isModeChanged ) {
            switch( this.mode ) {
                case BEGINNER:
                    updateDisplayBeginner();
                    break;
                case ADVANCED:
                    updateDisplayAdvanced();
                    break;
                case EXPERT:
                    updateDisplayExpert();
                    break;
                default:
                    throw new IllegalStateException( "Unexpected mode: " + this.mode );
            }
        }

        updateDisplayCommon( doRepaint );
    }

    private void updateDisplayCommon( final boolean doRepaint )
    {
        final JPanel jp = getJPanelFilters();

        if( this.jPanelIncFilesFilter != null ) {
            jp.remove( this.jPanelIncFilesFilter );
            }
        if( this.jPanelExcFilesFilter != null ) {
            jp.remove( this.jPanelExcFilesFilter );
            }
        if( this.jPanelIncDirsFilter != null ) {
            jp.remove( this.jPanelIncDirsFilter );
            }
        if( this.jPanelExcDirsFilter != null ) {
            jp.remove( this.jPanelExcDirsFilter );
            }

        if( getJComboBoxFilesFiltersSelectedItem() == FilterType.INCLUDE_FILTER ) {
            jp.add( this.jPanelIncFilesFilter );
            LOGGER.debug( "Display jPanelIncFilesFilter" );
            }
        else if( getJComboBoxFilesFiltersSelectedItem() == FilterType.EXCLUDE_FILTER ) {
            jp.add( this.jPanelExcFilesFilter );
            LOGGER.debug( "Display jPanelExcFilesFilter" );
            }
        else { // FILES_FILTER_DISABLED
            LOGGER.debug(
                "No Display getJComboBoxFilesFiltersSelectedItem()="
                    + getJComboBoxFilesFiltersSelectedItem()
                );
            }

        if( getJComboBoxDirsFiltersSelectedItem() == FilterType.EXCLUDE_FILTER ) {
            jp.add( this.jPanelIncDirsFilter );
            LOGGER.debug( "Display jPanelIncDirsFilter" );
            }
        else if( getJComboBoxDirsFiltersSelectedItem() == FilterType.INCLUDE_FILTER ) {
            jp.add( this.jPanelExcDirsFilter );
            LOGGER.debug( "Display jPanelExcDirsFilter" );
            }
        else { // DIRS_FILTER_DISABLED
            LOGGER.debug( "No Display" );
            }

        if( doRepaint ) {
            jp.revalidate();

            // repaint a JFrame jframe in this case
            getAppToolKit().getMainFrame().repaint();

            LOGGER.debug( "repaint MainWindow" );
            }
    }

    private void updateDisplayBeginner()
    {
        setJComboBoxFilesFiltersModel( FilterType.DISABLED );
        setJComboBoxFilesFiltersEnabled( false );

        setJComboBoxDirsFiltersModel( FilterType.DISABLED );
        setJComboBoxDirsFiltersEnabled( false );
    }

    private void updateDisplayAdvanced()
    {
        setJComboBoxFilesFiltersModel( FilterType.values() );
        setJComboBoxFilesFiltersEnabled( true );

        setJComboBoxDirsFiltersModel( FilterType.DISABLED );
        setJComboBoxDirsFiltersEnabled( false );
    }

    private void updateDisplayExpert()
    {
        setJComboBoxFilesFiltersModel( FilterType.values() );
        setJComboBoxFilesFiltersEnabled( true );

        setJComboBoxDirsFiltersModel( FilterType.values() );
        setJComboBoxDirsFiltersEnabled( true );
    }

    /**
     * Returns true if Empty Files should be skipped
     * @return true if Empty Files should be skipped
     */
    public boolean isIgnoreEmptyFiles()
    {
        return getjCheckBoxIgnoreEmptyFiles().isSelected();
    }

    public FileFilterBuilders getFileFilterBuilders()
    {
        final FilterType ffType  = getJComboBoxFilesFiltersSelectedItem();
        final FilterType efType  = getJComboBoxDirsFiltersSelectedItem();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "FFtype = " + ffType );
            LOGGER.debug( "EFtype = " + efType);
        }

        // Special cases
        final boolean ignoreEmptyFiles      = getjCheckBoxIgnoreEmptyFiles().isSelected();
        final boolean ignoreHiddedFiles     = getjCheckBoxFFIgnoreHidden().isSelected();
        final boolean ignoreReadOnlyFiles   = getjCheckBoxIgnoreReadOnlyFiles().isSelected();
        final boolean ignoreHiddedDirs      = getjCheckBoxFDIgnoreHidden().isSelected();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "ignoreEmptyFiles    = " + ignoreEmptyFiles);
            LOGGER.debug( "ignoreHiddedFiles   = " + ignoreHiddedFiles);
            LOGGER.debug( "ignoreReadOnlyFiles = " + ignoreReadOnlyFiles);
            LOGGER.debug( "ignoreHiddedDirs    = " + ignoreHiddedDirs);
        }

        final FileFilterBuilderConfigurator configurator = this.toFileFilterBuilderConfigurator();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "X createExcludeDirectoriesFileFilterBuilder() = " + configurator.createExcludeDirectoriesFileFilterBuilder() );
            LOGGER.debug( "X createIncludeDirectoriesFileFilterBuilder() = " + configurator.createIncludeDirectoriesFileFilterBuilder() );
            LOGGER.debug( "X createExcludeFilesFileFilterBuilder() = " + configurator.createExcludeFilesFileFilterBuilder() );
            LOGGER.debug( "X createIncludeFilesFileFilterBuilder() = " + configurator.createIncludeFilesFileFilterBuilder() );
        }

        final ConfigFileFilterBuilders configFileFilterBuilders = new ConfigFileFilterBuilders( configurator, ignoreHiddedFiles, efType, ignoreHiddedDirs, ffType, ignoreEmptyFiles, ignoreReadOnlyFiles );

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "configFileFilterBuilders = " + configFileFilterBuilders);
        }

        return configFileFilterBuilders;
    }

    private FileFilterBuilderConfigurator toFileFilterBuilderConfigurator()
    {
        return new FileFilterBuilderConfiguratorImpl();
    }
}

