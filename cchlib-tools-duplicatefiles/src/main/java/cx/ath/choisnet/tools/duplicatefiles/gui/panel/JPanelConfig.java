package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.ResourcesLoader;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.I18nSwingHelper;
import cx.ath.choisnet.lang.ToStringBuilder;
import cx.ath.choisnet.tools.duplicatefiles.ConfigMode;
import cx.ath.choisnet.tools.duplicatefiles.DFToolKit;
import cx.ath.choisnet.tools.duplicatefiles.FileFilterBuilder;
import cx.ath.choisnet.tools.duplicatefiles.FileFilterBuilders;

/**
 *
 */
public class JPanelConfig
    extends JPanelConfigWB
        implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( JPanelConfig.class );
    private ConfigMode mode;
    private Window rootFrame;

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


    public JPanelConfig()
    {
        // No initialization here
    }

//    public void setI18nTileIncFilesFilter(String localText)
//    {
//        I18nSwingHelper.setTitledBorderTitle( jPanelIncFilesFilter, localText );
//    }
//
//    public String getI18nTileIncFilesFilter()
//    {
//        return I18nSwingHelper.getTitledBorderTitle( jPanelIncFilesFilter );
//    }

//    public void setI18nTileExcFilesFilter(String localText)
//    {
//         I18nSwingHelper.setTitledBorderTitle( jPanelExcFilesFilter, localText );
//    }
//
//    public String getI18nTileExcFilesFilter()
//    {
//        return I18nSwingHelper.getTitledBorderTitle( jPanelExcFilesFilter );
//    }

//    public void setI18nTileIncDirsFilter(String localText)
//    {
//        I18nSwingHelper.setTitledBorderTitle( jPanelIncFilesFilter, localText );
//    }
//
//    public String getI18nTileIncDirsFilter()
//    {
//        return I18nSwingHelper.getTitledBorderTitle( jPanelIncFilesFilter );
//    }

//    public void setI18nTileExcDirsFilter(String localText)
//    {
//        I18nSwingHelper.setTitledBorderTitle( jPanelExcFilesFilter, localText );
//    }
//
//    public String getI18nTileExcDirsFilter()
//    {
//        return I18nSwingHelper.getTitledBorderTitle( jPanelExcFilesFilter );
//    }

    public void setI18nTileUseFilesFilters(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle( getJPanelFilesFilers(), localText );
    }

    public String getI18nTileUseFilesFilters()
    {
        return I18nSwingHelper.getTitledBorderTitle( getJPanelFilesFilers() );
    }

    public void setI18nTileUseDirsFilters(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle( getJPanelDirectoryFilters(), localText );
    }

    public String getI18nTileUseDirsFilters()
    {
        return I18nSwingHelper.getTitledBorderTitle( getJPanelDirectoryFilters() );
    }

    public void setI18nTileIgnore(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle( getJPanelIgnore(), localText );
    }

    public String getI18nTileIgnore()
    {
        return I18nSwingHelper.getTitledBorderTitle( getJPanelIgnore() );
    }

    @Override//ActionListener
    public void actionPerformed( ActionEvent e )
    {
        logger.debug( "actionPerformed" );
        updateDisplayMode( true );
    }

    public void initFixComponents(
            final DFToolKit dfToolKit
            )
    {
        this.rootFrame = dfToolKit.getMainWindow();

        jCheckBoxFFIgnoreHidden.setSelected( true );
        jCheckBoxFDIgnoreHidden.setSelected( true );
        jCheckBoxIgnoreReadOnlyFiles.setSelected( true );
        jCheckBoxIgnoreEmptyFiles.setSelected( true );

        getJComboBoxFilesFilters().addActionListener( this );
        getJComboBoxDirsFilters().addActionListener( this );

    }

    /**
     * Must be call to have a
     * @param autoI18n
     */
    public void performeI18n(AutoI18n autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());
        //autoI18n.performeI18n(jPanelIncFilesFilter,jPanelIncFilesFilter.getClass());
        //autoI18n.performeI18n(jPanelExcFilesFilter,jPanelExcFilesFilter.getClass());
        //autoI18n.performeI18n(jPanelIncDirsFilter,jPanelIncDirsFilter.getClass());
        //autoI18n.performeI18n(jPanelExcDirsFilter,jPanelExcDirsFilter.getClass());

        Properties  prop = ResourcesLoader.getProperties( "JPanelConfig.properties" );

        jPanelIncFilesFilter = new JPanelConfigFilter(
                jPanelIncFilesFilterTitle,
                jPanelIncFilesFilterRegExp,
                prop,
                "filetype",
                this
                );
        jPanelIncFilesFilter.getXComboBoxPatternRegExp().addItem(
                "(.*?)\\.(jpg|jpeg|png|gif)" // TODO: remove this sample
                );
        jPanelIncFilesFilter.getXComboBoxPatternRegExp().addItem(
                "(.*?)\\.(reg)" // TODO: remove this sample
                );

        jPanelExcFilesFilter = new JPanelConfigFilter(
                jPanelExcFilesFilterTitle,
                jPanelExcFilesFilterRegExp,
                prop,
                "filetype",
                this
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
                this
                );

        updateDisplayMode( ConfigMode.EXPERT, false ); // FIXME use begginner !
    }

    public void updateDisplayMode(
            final ConfigMode    newMode,
            final boolean       doRepaint
            )
    {
        final ConfigMode prevMode   = this.mode;
        this.mode                   = newMode;

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

        updateDisplayMode( doRepaint );
    }

    private void updateDisplayMode( final boolean doRepaint )
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

            rootFrame.repaint();//repaint a JFrame jframe in this case

            logger.debug( "repaint: " + rootFrame );
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
                    pattern = jPanelIncFilesFilter.getXComboBoxPatternRegExp().getSelectedPattern();
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
                    pattern = jPanelExcFilesFilter.getXComboBoxPatternRegExp().getSelectedPattern();
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
                addNameIf(namesList,ft);
                  }

            final boolean useRegExp = jPanelIncDirsFilter.getJCheckBoxRegExp().isSelected();

            if( useRegExp ) {
                try {
                    pattern = jPanelIncDirsFilter.getXComboBoxPatternRegExp().getSelectedPattern();
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
                    pattern = jPanelExcDirsFilter.getXComboBoxPatternRegExp().getSelectedPattern();
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

