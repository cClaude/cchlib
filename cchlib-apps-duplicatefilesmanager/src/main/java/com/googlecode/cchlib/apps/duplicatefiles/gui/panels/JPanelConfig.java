package com.googlecode.cchlib.apps.duplicatefiles.gui.panels;

import java.awt.event.ActionListener;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config.JPanelConfigFilter;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import com.googlecode.cchlib.swing.menu.LookAndFeelListener;
import cx.ath.choisnet.lang.ToStringBuilder;

@I18nName("duplicatefiles.JPanelConfig")
public class JPanelConfig
    extends JPanelConfigI18n
        implements LookAndFeelListener
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( JPanelConfig.class );

    private ActionListener actionListener;
    private ConfigMode mode;

    private PathMatcher tryToUseThis; // TODO - http://docs.oracle.com/javase/tutorial/essential/io/find.html
private Scanner s;

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
        if( this.actionListener == null ) {
            this.actionListener = event -> SafeSwingUtilities.invokeLater( () -> updateDisplay( true ), "updateDisplay()" );
            }
        return this.actionListener;
    }

    /**
     * Must be call to have a
     * @param autoI18n
     */
    public void performeI18n(final AutoI18nCore autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());

        final Properties           prop  = getAppToolKit().getResources().getJPanelConfigProperties(); // $codepro.audit.disable declareAsInterface
        final PreferencesControler prefs = getAppToolKit().getPreferences();

        this.jPanelIncFilesFilter = new JPanelConfigFilter(
                getjPanelIncFilesFilterTitle(),
                getjPanelIncFilesFilterRegExp(),
                prop,
                "filetype",
                getActionListener()
                );

//        for( String exp : prefs.getIncFilesFilterPatternRegExpList() ) {
//            jPanelIncFilesFilter.addPatternRegExp( exp );
//            }
        prefs.getIncFilesFilterPatternRegExpList().stream().forEach( exp -> this.jPanelIncFilesFilter.addPatternRegExp( exp ) );

        this.jPanelExcFilesFilter = new JPanelConfigFilter(
                getjPanelExcFilesFilterTitle(),
                getjPanelExcFilesFilterRegExp(),
                prop,
                "filetype",
                getActionListener()
                );
        this.jPanelIncDirsFilter = new JPanelConfigFilter( // No values
                getjPanelIncDirsFilterTitle(),
                getjPanelIncDirsFilterRegExp()
                );
        this.jPanelExcDirsFilter = new JPanelConfigFilter(
                getjPanelExcDirsFilterTitle(),
                getjPanelExcDirsFilterRegExp(),
                prop,
                "dirtype",
                getActionListener()
                );

        updateDisplay( false );
    }

    public void updateDisplay(
            final boolean doRepaint
            )
    {
        final ConfigMode prevMode   = this.mode;
        this.mode                   = getAppToolKit().getPreferences().getConfigMode();

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
            }
        }

        // private_updateDisplayMode( final boolean doRepaint )
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

            //jsp.revalidate();
            //this.repaint();//repaint a JFrame jframe in this case

            if( getJComboBoxFilesFilters().getSelectedIndex() == FilterType.INCLUDE_FILTER.ordinal() ) {
                jp.add( this.jPanelIncFilesFilter );
                LOGGER.debug( "Display jPanelIncFilesFilter" );
                }
            else if( getJComboBoxFilesFilters().getSelectedIndex() == FilterType.EXCLUDE_FILTER.ordinal() ) {
                jp.add( this.jPanelExcFilesFilter );
                LOGGER.debug( "Display jPanelExcFilesFilter" );
                }
            else { // FILES_FILTER_DISABLED
                LOGGER.debug(
                    "No Display getJComboBoxFilesFilters()="
                        + getJComboBoxFilesFilters().getSelectedIndex()
                    );
                }

            if( getJComboBoxDirsFilters().getSelectedIndex() == FilterType.EXCLUDE_FILTER.ordinal() ) {
                jp.add( this.jPanelIncDirsFilter );
                LOGGER.debug( "Display jPanelIncDirsFilter" );
                }
            else if( getJComboBoxDirsFilters().getSelectedIndex() == FilterType.INCLUDE_FILTER.ordinal() ) {
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
    }

    private void updateDisplayBeginner()
    {
        getJComboBoxFilesFilters().setModel(
                new DefaultComboBoxModel<>(
                    new String[] {
                        getTxtDisableFilesFilters()
                        }
                    )
                );
        getJComboBoxFilesFilters().setEnabled( false );
        getJComboBoxDirsFilters().setModel(
                new DefaultComboBoxModel<>(
                    new String[] {
                        getTxtDisableDirsFilters()
                        }
                    )
                );
        getJComboBoxDirsFilters().setEnabled( false );
    }

    private void updateDisplayAdvanced()
    {
        getJComboBoxFilesFilters().setModel(
                new DefaultComboBoxModel<>(
                    new String[] {
                        getTxtDisableFilesFilters(),
                        getTxtIncludeFilesFilters(),
                        getTxtExcludeFilesFilters()
                        }
                    )
                );
        getJComboBoxFilesFilters().setEnabled( true );
        getJComboBoxDirsFilters().setModel(
                new DefaultComboBoxModel<>(
                    new String[] {
                        getTxtDisableDirsFilters()
                        }
                    )
                );
        getJComboBoxDirsFilters().setEnabled( false );
    }

    private void updateDisplayExpert()
    {
        getJComboBoxFilesFilters().setModel(
                new DefaultComboBoxModel<>(
                    new String[] {
                        getTxtDisableFilesFilters(),
                        getTxtIncludeFilesFilters(),
                        getTxtExcludeFilesFilters()
                        }
                    )
                );
        getJComboBoxFilesFilters().setEnabled( true );
        getJComboBoxDirsFilters().setModel(
                new DefaultComboBoxModel<>(
                    new String[] {
                        getTxtDisableDirsFilters(),
                        getTxtExcludeDirsFilters(),
                        getTxtIncludeDirsFilters()
                        }
                    )
                );
        getJComboBoxDirsFilters().setEnabled( true );
    }

    /**
     * Returns true if Empty Files should be skipped
     * @return true if Empty Files should be skipped
     */
    public boolean isIgnoreEmptyFiles()
    {
        return getjCheckBoxIgnoreEmptyFiles().isSelected();
    }

    private static final void addExtIf(
        final Collection<String> c,
        final FileTypeCheckBox   ft
        )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(final String s:ft.getData().split( "," )) {
                if(s.length()>0) {
                    c.add( "." + s.toLowerCase() ); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
                }
            }
        }
    }

    FileFilterBuilder createIncludeFilesFileFilterBuilder()
    {
        final Set<String>   extsList = new HashSet<>();
        Pattern             pattern  = null;

        final boolean userIncFilesFilers =
            (getJComboBoxFilesFilters().getSelectedIndex()
                == FilterType.INCLUDE_FILTER.ordinal());

        if( userIncFilesFilers ) {
            for( final FileTypeCheckBox ft : this.jPanelIncFilesFilter ) {
                addExtIf( extsList, ft );
                }

            final boolean useRegExp = this.jPanelIncFilesFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = this.jPanelIncFilesFilter.getSelectedPattern();
                    }
                catch( final Exception ignore ) {
                    LOGGER.error( ignore );
                    }
                }
            }
        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return extsList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }

    FileFilterBuilder createExcludeFilesFileFilterBuilder()
    {
        final Set<String>   extsList = new HashSet<>();
        Pattern             pattern  = null;

        final boolean userExcFilesFilers =
            (getJComboBoxFilesFilters().getSelectedIndex()
                == FilterType.EXCLUDE_FILTER.ordinal());

        if( userExcFilesFilers ) {
            for( final FileTypeCheckBox ft : this.jPanelExcFilesFilter ) {
                addExtIf( extsList, ft );
                }

            final boolean useRegExp = this.jPanelExcFilesFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = this.jPanelExcFilesFilter.getSelectedPattern();
                    }
                catch( final Exception ignore ) {
                    LOGGER.error( ignore );
                    }
                }
        }
        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return extsList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }

    private static final void addNameIf(
            final Collection<String>    c,
            final FileTypeCheckBox      ft
            )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(final String s:ft.getData().split( "," )) {
                if( s.length() > 0 ) {
                    c.add( s.toLowerCase() ); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
                }
            }
        }
    }

    FileFilterBuilder createIncludeDirectoriesFileFilterBuilder()
    {
        final Set<String>   namesList = new HashSet<>();
        Pattern             pattern   = null;

        final boolean useIncDirsFilters  =
            (getJComboBoxDirsFilters().getSelectedIndex()
                    == FilterType.EXCLUDE_FILTER.ordinal());

        if( useIncDirsFilters ) {
            //TODO need to be studies, not really useful like this !
            for( final FileTypeCheckBox ft : this.jPanelIncDirsFilter ) {
                addNameIf( namesList, ft );
                }

            final boolean useRegExp = this.jPanelIncDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = this.jPanelIncDirsFilter.getSelectedPattern();
                    }
                catch( final Exception ignore ) {
                    LOGGER.error( ignore );
                    }
                }
            }

        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return namesList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }

    FileFilterBuilder createExcludeDirectoriesFileFilterBuilder()
    {
        final Set<String>   namesList = new HashSet<>();
        Pattern             pattern   = null;

        final boolean useExcDirsFilters  =
            (getJComboBoxDirsFilters().getSelectedIndex()
                    == FilterType.INCLUDE_FILTER.ordinal());

        if( useExcDirsFilters ) {
            for( final FileTypeCheckBox ft : this.jPanelExcDirsFilter ) {
                addNameIf(namesList,ft);
                }

            final boolean useRegExp = this.jPanelExcDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = this.jPanelExcDirsFilter.getSelectedPattern();
                    }
                catch( final Exception ignore ){
                    LOGGER.error( ignore );
                    }
                }
            }

        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return namesList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }

    public FileFilterBuilders getFileFilterBuilders()
    {
        final FilterType ffType  = FilterType.buildFromOrdinal( getJComboBoxFilesFilters().getSelectedIndex() );
        final FilterType efType  = FilterType.buildFromOrdinal( getJComboBoxDirsFilters().getSelectedIndex() );

        LOGGER.info( "FFtype = " + ffType );
        LOGGER.info( "EFtype = " + efType);

        // Special cases
        final boolean ignoreEmptyFiles      = getjCheckBoxIgnoreEmptyFiles().isSelected();
        final boolean ignoreHiddedFiles     = getjCheckBoxFFIgnoreHidden().isSelected();
        final boolean ignoreReadOnlyFiles   = getjCheckBoxIgnoreReadOnlyFiles().isSelected();
        final boolean ignoreHiddedDirs      = getjCheckBoxFDIgnoreHidden().isSelected();

        LOGGER.info( "ignoreEmptyFiles = " + ignoreEmptyFiles);
        LOGGER.info( "ignoreHiddedFiles = " + ignoreHiddedFiles);
        LOGGER.info( "ignoreReadOnlyFiles = " + ignoreReadOnlyFiles);
        LOGGER.info( "ignoreHiddedDirs = " + ignoreHiddedDirs);

        return new ConfigFileFilterBuilders( this, ignoreHiddedFiles, efType, ignoreHiddedDirs, ffType, ignoreEmptyFiles, ignoreReadOnlyFiles );
    }
}

