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

    //private final static int FILES_FILTER_DISABLED  = 0;
    private static final int FILES_FILTER_INCLUDE   = 1;
    private static final int FILES_FILTER_EXCLUDE   = 2;

    //private final static int DIRS_FILTER_DISABLED   = 0;
    private static final int DIRS_FILTER_EXCLUDE    = 1;
    private static final int DIRS_FILTER_INCLUDE    = 2;

    public JPanelConfig()
    {
        super();
    }

    @Override//LookAndFeelListener
    public void setLookAndFeel( final String lookAndFeelName )
    {
        if( jPanelIncFilesFilter != null ) {
            SwingUtilities.updateComponentTreeUI( jPanelIncFilesFilter );
            }
        if( jPanelExcFilesFilter != null ) {
            SwingUtilities.updateComponentTreeUI( jPanelExcFilesFilter );
            }
        if( jPanelIncDirsFilter != null ) {
            SwingUtilities.updateComponentTreeUI( jPanelIncDirsFilter );
            }
        if( jPanelExcDirsFilter != null ) {
            SwingUtilities.updateComponentTreeUI( jPanelExcDirsFilter );
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

        jPanelIncFilesFilter = new JPanelConfigFilter(
                getjPanelIncFilesFilterTitle(),
                getjPanelIncFilesFilterRegExp(),
                prop,
                "filetype",
                getActionListener()
                );

//        for( String exp : prefs.getIncFilesFilterPatternRegExpList() ) {
//            jPanelIncFilesFilter.addPatternRegExp( exp );
//            }
        prefs.getIncFilesFilterPatternRegExpList().stream().forEach( exp -> jPanelIncFilesFilter.addPatternRegExp( exp ) );

        jPanelExcFilesFilter = new JPanelConfigFilter(
                getjPanelExcFilesFilterTitle(),
                getjPanelExcFilesFilterRegExp(),
                prop,
                "filetype",
                getActionListener()
                );
        jPanelIncDirsFilter = new JPanelConfigFilter( // No values
                getjPanelIncDirsFilterTitle(),
                getjPanelIncDirsFilterRegExp()
                );
        jPanelExcDirsFilter = new JPanelConfigFilter(
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
                    + mode
                    );
        }

        final boolean isModeChanged = !this.mode.equals( prevMode );

        if( isModeChanged ) {
            switch( mode ) {
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

            if( jPanelIncFilesFilter != null ) {
                jp.remove( jPanelIncFilesFilter );
                }
            if( jPanelExcFilesFilter != null ) {
                jp.remove( jPanelExcFilesFilter );
                }
            if( jPanelIncDirsFilter != null ) {
                jp.remove( jPanelIncDirsFilter );
                }
            if( jPanelExcDirsFilter != null ) {
                jp.remove( jPanelExcDirsFilter );
                }

            //jsp.revalidate();
            //this.repaint();//repaint a JFrame jframe in this case

            if( getJComboBoxFilesFilters().getSelectedIndex() == FILES_FILTER_INCLUDE ) {
                jp.add( jPanelIncFilesFilter );
                LOGGER.debug( "Display jPanelIncFilesFilter" );
                }
            else if( getJComboBoxFilesFilters().getSelectedIndex() == FILES_FILTER_EXCLUDE ) {
                jp.add( jPanelExcFilesFilter );
                LOGGER.debug( "Display jPanelExcFilesFilter" );
                }
            else { // FILES_FILTER_DISABLED
                LOGGER.debug(
                    "No Display getJComboBoxFilesFilters()="
                        + getJComboBoxFilesFilters().getSelectedIndex()
                    );
                }

            if( getJComboBoxDirsFilters().getSelectedIndex() == DIRS_FILTER_INCLUDE ) {
                jp.add( jPanelIncDirsFilter );
                LOGGER.debug( "Display jPanelIncDirsFilter" );
                }
            else if( getJComboBoxDirsFilters().getSelectedIndex() == DIRS_FILTER_EXCLUDE ) {
                jp.add( jPanelExcDirsFilter );
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

    private FileFilterBuilder createIncludeFilesFileFilterBuilder()
    {
        final Set<String>   extsList = new HashSet<>();
        Pattern             pattern  = null;

        final boolean userIncFilesFilers =
            (getJComboBoxFilesFilters().getSelectedIndex()
                == FILES_FILTER_INCLUDE);

        if( userIncFilesFilers ) {
            for( final FileTypeCheckBox ft : jPanelIncFilesFilter ) {
                addExtIf( extsList, ft );
                }

            final boolean useRegExp = jPanelIncFilesFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelIncFilesFilter.getSelectedPattern();
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

    private FileFilterBuilder createExcludeFilesFileFilterBuilder()
    {
        final Set<String>   extsList = new HashSet<>();
        Pattern             pattern  = null;

        final boolean userExcFilesFilers =
            (getJComboBoxFilesFilters().getSelectedIndex()
                == FILES_FILTER_EXCLUDE);

        if( userExcFilesFilers ) {
            for( final FileTypeCheckBox ft : jPanelExcFilesFilter ) {
                addExtIf( extsList, ft );
                }

            final boolean useRegExp = jPanelExcFilesFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelExcFilesFilter.getSelectedPattern();
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

    private FileFilterBuilder createIncludeDirectoriesFileFilterBuilder()
    {
        final Set<String>   namesList = new HashSet<>();
        Pattern             pattern   = null;

        final boolean useIncDirsFilters  =
            (getJComboBoxDirsFilters().getSelectedIndex()
                    == DIRS_FILTER_INCLUDE);

        if( useIncDirsFilters ) {
            //TODO need to be studies, not really useful like this !
            for( final FileTypeCheckBox ft : jPanelIncDirsFilter ) {
                addNameIf( namesList, ft );
                }

            final boolean useRegExp = jPanelIncDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelIncDirsFilter.getSelectedPattern();
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

    private FileFilterBuilder createExcludeDirectoriesFileFilterBuilder()
    {
        final Set<String>   namesList = new HashSet<>();
        Pattern             pattern   = null;

        final boolean useExcDirsFilters  =
            (getJComboBoxDirsFilters().getSelectedIndex()
                    == DIRS_FILTER_EXCLUDE);

        if( useExcDirsFilters ) {
            for( final FileTypeCheckBox ft : jPanelExcDirsFilter ) {
                addNameIf(namesList,ft);
                }

            final boolean useRegExp = jPanelExcDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelExcDirsFilter.getSelectedPattern();
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
        final int   ffType  = getJComboBoxFilesFilters().getSelectedIndex();
        final int   efType  = getJComboBoxDirsFilters().getSelectedIndex();

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

        return new FileFilterBuilders()
        {
            @Override
            public FileFilterBuilder getIncludeDirs()
            {
                if( efType == DIRS_FILTER_INCLUDE ) {
                    return createIncludeDirectoriesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public FileFilterBuilder getExcludeDirs()
            {
                if( efType == DIRS_FILTER_EXCLUDE ) {
                    return createExcludeDirectoriesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public FileFilterBuilder getIncludeFiles()
            {
                if( ffType == FILES_FILTER_INCLUDE ) {
                    return createIncludeFilesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public FileFilterBuilder getExcludeFiles()
            {
                if( ffType == FILES_FILTER_EXCLUDE ) {
                    return createExcludeFilesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public boolean isIgnoreHiddenDirs()
            {
                return ignoreHiddedDirs;
            }
            @Override
            public boolean isIgnoreHiddenFiles()
            {
                return ignoreHiddedFiles;
            }
            @Override
            public boolean isIgnoreReadOnlyFiles()
            {
                return ignoreReadOnlyFiles;
            }
            @Override
            public boolean isIgnoreEmptyFiles()
            {
                return ignoreEmptyFiles;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilders.class );
            }
        };
    }
}

