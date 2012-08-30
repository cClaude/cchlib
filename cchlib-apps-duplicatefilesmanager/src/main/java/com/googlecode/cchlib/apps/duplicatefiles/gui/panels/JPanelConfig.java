package com.googlecode.cchlib.apps.duplicatefiles.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilder;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.Tools;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config.JPanelConfigFilter;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config.JPanelConfigWB;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.menu.LookAndFeelListener;
import cx.ath.choisnet.lang.ToStringBuilder;

/**
 *
 */
public class JPanelConfig
    extends JPanelConfigWB
        implements LookAndFeelListener
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( JPanelConfig.class );
    private DFToolKit dfToolKit;
    private ActionListener actionListener;
    private ConfigMode mode;

    private PathMatcher tryToUseThis; // TODO - http://docs.oracle.com/javase/tutorial/essential/io/find.html
private Scanner s;

    //@I18n( methodSuffixName="I18nTileIncFilesFilter")
    private JPanelConfigFilter jPanelIncFilesFilter;
    @I18nString private String jPanelIncFilesFilterTitle  = "jPanelIncFilesFilterTitle";
    @I18nString private String jPanelIncFilesFilterRegExp = "jPanelIncFilesFilterRegExp";
    //@I18n( methodSuffixName="I18nTileExcFilesFilter")
    private JPanelConfigFilter jPanelExcFilesFilter;
    @I18nString private String jPanelExcFilesFilterTitle = "jPanelExcFilesFilterTitle";
    @I18nString private String jPanelExcFilesFilterRegExp = "jPanelExcFilesFilterRegExp";
    //@I18n( methodSuffixName="I18nTileIncDirsFilter")
    private JPanelConfigFilter jPanelIncDirsFilter;
    @I18nString private String jPanelIncDirsFilterTitle = "jPanelIncDirsFilterTitle";
    @I18nString private String jPanelIncDirsFilterRegExp = "jPanelIncDirsFilterRegExp";
    //@I18n( methodSuffixName="I18nTileExcDirsFilter")
    private JPanelConfigFilter jPanelExcDirsFilter;
    @I18nString private String jPanelExcDirsFilterTitle = "jPanelExcDirsFilterTitle";
    @I18nString private String jPanelExcDirsFilterRegExp = "jPanelExcDirsFilterRegExp";

    @I18nString private String txtDisableFilesFilters = "Disable files filters";
    @I18nString private String txtIncludeFilesFilters = "Include filters";
    @I18nString private String txtExcludeFilesFilters = "Exclude filters";
    //private final static int FILES_FILTER_DISABLED  = 0;
    private final static int FILES_FILTER_INCLUDE   = 1;
    private final static int FILES_FILTER_EXCLUDE   = 2;

    @I18nString private String txtDisableDirsFilters = "Disable dirs filters";
    @I18nString private String txtExcludeDirsFilters = "Exclude filters";
    @I18nString private String txtIncludeDirsFilters = "Include filters";
    //private final static int DIRS_FILTER_DISABLED   = 0;
    private final static int DIRS_FILTER_EXCLUDE    = 2;
    private final static int DIRS_FILTER_INCLUDE    = 1;


    public JPanelConfig( final DFToolKit dfToolKit )
    {
        super();

        this.dfToolKit = dfToolKit;
        //this.rootFrame = dfToolKit.getMainWindow();
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
            this.actionListener = new ActionListener()
            {
                @Override//ActionListener
                public void actionPerformed( ActionEvent e )
                {
                    //logger.debug( "actionPerformed" );
                    Tools.invokeLater( new Runnable() {
                        @Override
                        public void run()
                        {
                            updateDisplay( true );
                        }});
                }
            };
            }
        return this.actionListener;
    }

    /**
     * Must be call to have a
     * @param autoI18n
     */
    public void performeI18n(AutoI18n autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());

        //Properties  prop  = ResourcesLoader.getProperties( "JPanelConfig.properties" );
        Properties  prop  = dfToolKit.getResources().getJPanelConfigProperties();
        Preferences prefs = dfToolKit.getPreferences();
        
        jPanelIncFilesFilter = new JPanelConfigFilter(
                jPanelIncFilesFilterTitle,
                jPanelIncFilesFilterRegExp,
                prop,
                "filetype",
                getActionListener()
                );
        
        for( String exp : prefs.getIncFilesFilterPatternRegExpList() ) {
            jPanelIncFilesFilter.addPatternRegExp( exp );
            }

        jPanelExcFilesFilter = new JPanelConfigFilter(
                jPanelExcFilesFilterTitle,
                jPanelExcFilesFilterRegExp,
                prop,
                "filetype",
                getActionListener()
                );
        jPanelIncDirsFilter = new JPanelConfigFilter( // No values
                jPanelIncDirsFilterTitle,
                jPanelIncDirsFilterRegExp
                );
        jPanelExcDirsFilter = new JPanelConfigFilter(
                jPanelExcDirsFilterTitle,
                jPanelExcDirsFilterRegExp,
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
        this.mode                   = this.dfToolKit.getPreferences().getConfigMode();

        logger.debug( "updateDisplayMode()"
            + prevMode
            + " -> "
            + mode
            );

        final boolean isModeChanged = !this.mode.equals( prevMode );

        if( isModeChanged ) {
            if( mode == ConfigMode.BEGINNER ) {
                getJComboBoxFilesFilters().setModel(
                        new DefaultComboBoxModel<String>(
                            new String[] {
                                txtDisableFilesFilters
                                }
                            )
                        );
                getJComboBoxFilesFilters().setEnabled( false );
                getJComboBoxDirsFilters().setModel(
                        new DefaultComboBoxModel<String>(
                            new String[] {
                                txtDisableDirsFilters
                                }
                            )
                        );
                getJComboBoxDirsFilters().setEnabled( false );
                }
            else if( mode == ConfigMode.ADVANCED ) {
                getJComboBoxFilesFilters().setModel(
                        new DefaultComboBoxModel<String>(
                            new String[] {
                                txtDisableFilesFilters,
                                txtIncludeFilesFilters,
                                txtExcludeFilesFilters
                                }
                            )
                        );
                getJComboBoxFilesFilters().setEnabled( true );
                getJComboBoxDirsFilters().setModel(
                        new DefaultComboBoxModel<String>(
                            new String[] {
                                txtDisableDirsFilters
                                }
                            )
                        );
                getJComboBoxDirsFilters().setEnabled( false );
                }
            else { // if( mode == ConfigMode.EXPERT )
                getJComboBoxFilesFilters().setModel(
                        new DefaultComboBoxModel<String>(
                            new String[] {
                                txtDisableFilesFilters,
                                txtIncludeFilesFilters,
                                txtExcludeFilesFilters
                                }
                            )
                        );
                getJComboBoxFilesFilters().setEnabled( true );
                getJComboBoxDirsFilters().setModel(
                        new DefaultComboBoxModel<String>(
                            new String[] {
                                txtDisableDirsFilters,
                                txtExcludeDirsFilters,
                                txtIncludeDirsFilters
                                }
                            )
                        );
                getJComboBoxDirsFilters().setEnabled( true );
                }
            }

        // private_updateDisplayMode( final boolean doRepaint )
        {
            JPanel jp = getJPanelFilters();

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
                logger.debug( "Display jPanelIncFilesFilter" );
                }
            else if( getJComboBoxFilesFilters().getSelectedIndex() == FILES_FILTER_EXCLUDE ) {
                jp.add( jPanelExcFilesFilter );
                logger.debug( "Display jPanelExcFilesFilter" );
                }
            else { // FILES_FILTER_DISABLED
                logger.debug(
                    "No Display getJComboBoxFilesFilters()="
                        + getJComboBoxFilesFilters().getSelectedIndex()
                    );
                }

            if( getJComboBoxDirsFilters().getSelectedIndex() == DIRS_FILTER_INCLUDE ) {
                jp.add( jPanelIncDirsFilter );
                logger.debug( "Display jPanelIncDirsFilter" );
                }
            else if( getJComboBoxDirsFilters().getSelectedIndex() == DIRS_FILTER_EXCLUDE ) {
                jp.add( jPanelExcDirsFilter );
                logger.debug( "Display jPanelExcDirsFilter" );
                }
            else { // DIRS_FILTER_DISABLED
                logger.debug( "No Display" );
                }

            if( doRepaint ) {
                jp.revalidate();

              //repaint a JFrame jframe in this case
                dfToolKit.getMainWindow().repaint();

                logger.debug( "repaint MainWindow" );
                }
        }
    }

    /**
     * Returns true if Empty Files should be skipped
     * @return true if Empty Files should be skipped
     */
    public boolean IsIgnoreEmptyFiles()
    {
        return jCheckBoxIgnoreEmptyFiles.isSelected();
    }

    private final static void addExtIf(
            Collection<String>                  c,
            FileTypeCheckBox ft
            )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(String s:ft.getData().split( "," )) {
                if(s.length()>0) {
                    c.add( "." + s.toLowerCase() );
                }
            }
        }
    }

    private FileFilterBuilder createIncludeFilesFileFilterBuilder()
    {
        final HashSet<String>   extsList = new HashSet<String>();
        Pattern                 pattern  = null;

        final boolean userIncFilesFilers =
            (getJComboBoxFilesFilters().getSelectedIndex()
                == FILES_FILTER_INCLUDE);

        if( userIncFilesFilers ) {
            for( FileTypeCheckBox ft : jPanelIncFilesFilter ) {
                addExtIf( extsList, ft );
                }

            final boolean useRegExp = jPanelIncFilesFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelIncFilesFilter.getSelectedPattern();
                    }
                catch( Exception ignore ) {
                    logger.error( ignore );
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
        final HashSet<String>   extsList = new HashSet<String>();
        Pattern                 pattern  = null;

        final boolean userExcFilesFilers =
            (getJComboBoxFilesFilters().getSelectedIndex()
                == FILES_FILTER_EXCLUDE);

        if( userExcFilesFilers ) {
            for( FileTypeCheckBox ft : jPanelExcFilesFilter ) {
                addExtIf( extsList, ft );
                }

            final boolean useRegExp = jPanelExcFilesFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelExcFilesFilter.getSelectedPattern();
                    }
                catch( Exception ignore ) {
                    logger.error( ignore );
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

    private final static void addNameIf(
            final Collection<String>    c,
            final FileTypeCheckBox      ft
            )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(String s:ft.getData().split( "," )) {
                if( s.length() > 0 ) {
                    c.add( s.toLowerCase() );
                }
            }
        }
    }

    private FileFilterBuilder createIncludeDirectoriesFileFilterBuilder()
    {
        final HashSet<String>   namesList = new HashSet<String>();
        Pattern                 pattern   = null;

        final boolean useIncDirsFilters  =
            (getJComboBoxDirsFilters().getSelectedIndex()
                    == DIRS_FILTER_INCLUDE);

        if( useIncDirsFilters ) {
            //TODO need to be studies, not really useful like this !
            for( FileTypeCheckBox ft : jPanelIncDirsFilter ) {
                addNameIf( namesList, ft );
                }

            final boolean useRegExp = jPanelIncDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelIncDirsFilter.getSelectedPattern();
                    }
                catch( Exception ignore ) {
                    logger.error( ignore );
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
        final HashSet<String>   namesList = new HashSet<String>();
        Pattern                 pattern   = null;

        final boolean useExcDirsFilters  =
            (getJComboBoxDirsFilters().getSelectedIndex()
                    == DIRS_FILTER_EXCLUDE);

        if( useExcDirsFilters ) {
            for( FileTypeCheckBox ft : jPanelExcDirsFilter ) {
                addNameIf(namesList,ft);
                }

            final boolean useRegExp = jPanelExcDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelExcDirsFilter.getSelectedPattern();
                    }
                catch( Exception ignore ){
                    logger.error( ignore );
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
        final int   FFtype  = getJComboBoxFilesFilters().getSelectedIndex();
        final int   EFtype  = getJComboBoxDirsFilters().getSelectedIndex();

        logger.info( "FFtype = " + FFtype );
        logger.info( "EFtype = " + EFtype);

        // Special cases
        final boolean ignoreEmptyFiles      = jCheckBoxIgnoreEmptyFiles.isSelected();
        final boolean ignoreHiddedFiles     = jCheckBoxFFIgnoreHidden.isSelected();
        final boolean ignoreReadOnlyFiles   = jCheckBoxIgnoreReadOnlyFiles.isSelected();
        final boolean ignoreHiddedDirs      = jCheckBoxFDIgnoreHidden.isSelected();

        logger.info( "ignoreEmptyFiles = " + ignoreEmptyFiles);
        logger.info( "ignoreHiddedFiles = " + ignoreHiddedFiles);
        logger.info( "ignoreReadOnlyFiles = " + ignoreReadOnlyFiles);
        logger.info( "ignoreHiddedDirs = " + ignoreHiddedDirs);

        return new FileFilterBuilders()
        {
            @Override
            public FileFilterBuilder getIncludeDirs()
            {
                if( EFtype == DIRS_FILTER_INCLUDE ) {
                    return createIncludeDirectoriesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public FileFilterBuilder getExcludeDirs()
            {
                if( EFtype == DIRS_FILTER_EXCLUDE ) {
                    return createExcludeDirectoriesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public FileFilterBuilder getIncludeFiles()
            {
                if( FFtype == FILES_FILTER_INCLUDE ) {
                    return createIncludeFilesFileFilterBuilder();
                    }
                else {
                    return null;
                    }
            }
            @Override
            public FileFilterBuilder getExcludeFiles()
            {
                if( FFtype == FILES_FILTER_EXCLUDE ) {
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

