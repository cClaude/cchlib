package com.googlecode.cchlib.apps.editresourcesbundle.load;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.FilesConfig;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FileObject;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.Windows;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.util.FormattedProperties;

/**
 *
 */
@SuppressWarnings({
    "squid:S00100", "squid:S00116", // naming convention
    "squid:MaximumInheritanceDepth" // Swing
    })
@I18nName("LoadDialog")
public class LoadDialog
    extends LoadDialogWB
        implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = Logger.getLogger( LoadDialog.class );

    private JFileChooserInitializer jFileChooserInitializer = new JFileChooserInitializer();
    private final FilesConfig filesConfig;

    private static final int PROPERTIES = 1;
    private static final int FORMATTED_PROPERTIES = 2;

    private static final int LOAD_DIALOG_MINIMUM_WIDTH  = 400;
    private static final int LOAD_DIALOG_MINIMUM_HEIGHT = 250;

    private final Preferences preferences;

    public LoadDialog(
        final CompareResourcesBundleFrame   parentFrame,
        final Preferences                   preferences
        )
    {
        this( parentFrame, new FilesConfig( preferences ) );
    }

    public LoadDialog(
        final CompareResourcesBundleFrame   parentFrame,
        final FilesConfig                   filesConfig
        )
    {
        super( parentFrame, filesConfig.getNumberOfFiles() );

        this.preferences             = parentFrame.getPreferences();
        this.filesConfig             = filesConfig;
        this.jFileChooserInitializer = parentFrame.getJFileChooserInitializer();

        init( parentFrame );
   }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );

        final FilesPanel selectJPanel = getSelectJPanel();

        for( int index = 0; index < selectJPanel.getEntryCount(); index++ ) {
            final FileEntryPanel panel = selectJPanel.getPanelFile( index );

            autoI18n.performeI18n( panel, panel.getClass() );
            }
    }


    private void init(final CompareResourcesBundleFrame parentFrame)
    {
        initFixComponents();

        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setTitle( this.msgTitle );
        setLocationRelativeTo( parentFrame );
        getContentPane().setPreferredSize( this.getSize() );
        pack();

        Windows.handleMinimumSize( this, LOAD_DIALOG_MINIMUM_WIDTH, LOAD_DIALOG_MINIMUM_HEIGHT );
    }

    public void initFixComponents()
    {
        // Load tab
        if( this.filesConfig.getLeftFileObject() != null ) {
            getCheckBox_LeftReadOnly().setSelected(
                this.filesConfig.getLeftFileObject().isReadOnly()
                );
            }

        initCheckBox(
                getCheckBox_ShowLineNumbers(),
                LoadDialogAction.ACTION_Change_ShowLineNumbers,
                this.filesConfig.isShowLineNumbers()
                );

        // Load tab - FileType
        initCheckBox(
                getCheckBox_Properties(),
                LoadDialogAction.ACTION_FT_Properties,
                FilesConfig.FileType.PROPERTIES == this.filesConfig.getFileType()
                );
        initCheckBox(
                getCheckBox_FormattedProperties(),
                LoadDialogAction.ACTION_FT_FormattedProperties,
                FilesConfig.FileType.FORMATTED_PROPERTIES == this.filesConfig.getFileType()
                );
        initCheckBox(
                getCheckBox_ini(),
                LoadDialogAction.ACTION_FT_ini,
                false // TODO later (*.ini files)
                );

        // Properties tab
        initCheckBox(
                getCheckBox_RightUseLeftHasDefaults(),
                LoadDialogAction.ACTION_Change_isUseLeftHasDefault,
                this.filesConfig.isUseLeftHasDefault()
                );

        // FormattedProperties tab
        initCheckBox(
                getCheckBox_CUT_LINE_AFTER_HTML_BR(),
                LoadDialogAction.ACTION_Change_CUT_LINE_AFTER_HTML_BR,
                this.filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_AFTER_HTML_BR
                        )
                );
        initCheckBox(
                getCheckBox_CUT_LINE_AFTER_HTML_END_P(),
                LoadDialogAction.ACTION_Change_CUT_LINE_AFTER_HTML_END_P,
                this.filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_AFTER_HTML_END_P
                        )
                );
        initCheckBox(
                getCheckBox_CUT_LINE_AFTER_NEW_LINE(),
                LoadDialogAction.ACTION_Change_CUT_LINE_AFTER_NEW_LINE,
                this.filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_AFTER_NEW_LINE
                        )
                );
        initCheckBox(
                getCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P(),
                LoadDialogAction.ACTION_Change_CUT_LINE_BEFORE_HTML_BEGIN_P,
                this.filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BEGIN_P
                        )
                );
        initCheckBox(
                getCheckBox_CUT_LINE_BEFORE_HTML_BR(),
                LoadDialogAction.ACTION_Change_CUT_LINE_BEFORE_HTML_BR,
                this.filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BR
                        )
                );

        udpateFilesDisplay();
        udpateTabFileTypeDisplay();
    }

    private void initCheckBox(
        final JCheckBox        jCheckBox,
        final LoadDialogAction action,
        final boolean          value
        )
    {
        jCheckBox.setActionCommand( action.name() );
        jCheckBox.addActionListener( getActionListener() );
        jCheckBox.setSelected( value );
    }

    protected void udpateFilesDisplay()
    {
        for( int index = 0; index < this.filesConfig.getNumberOfFiles(); index++ ) {
            final FileObject fo = this.filesConfig.getFileObject( index );

            if( fo != null ) {
              getJTextField( index ).setText( fo.getFile().getPath() );
              }
            else {
                getJTextField( index ).setText( StringHelper.EMPTY );
                }
            }
    }

    protected void udpateTabFileTypeDisplay()
    {
        if( getCheckBox_Properties().isSelected() ) {
            getJTabbedPaneRoot().setEnabledAt( PROPERTIES, true );
            getJTabbedPaneRoot().setEnabledAt( FORMATTED_PROPERTIES, false );
            }
        else if( getCheckBox_FormattedProperties().isSelected() ) {
            getJTabbedPaneRoot().setEnabledAt( PROPERTIES, true );
            getJTabbedPaneRoot().setEnabledAt( FORMATTED_PROPERTIES, true );
            }
        else {
            getJTabbedPaneRoot().setEnabledAt( PROPERTIES, false );
            getJTabbedPaneRoot().setEnabledAt( FORMATTED_PROPERTIES, false );
            }
    }

    protected JFileChooser getJFileChooser()
    {
        return this.jFileChooserInitializer.getJFileChooser();
    }

    @SuppressWarnings("squid:S1066")
    private FileObject getLoadFile(
            final FileObject          previousFile,
            final boolean             readOnly
            )
    {
        final JFileChooser fc = getJFileChooser();

        if( previousFile != null ) {
            if( previousFile.getFile() != null ) {
                fc.setSelectedFile( previousFile.getFile() );
                }
            }

        final int returnVal = fc.showOpenDialog( this );

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return new FileObject( fc.getSelectedFile(), readOnly );
            }

        return null;
    }

    //@Override
    private void jButton_LeftMouseMousePressed()
    {
        final FileObject fo = getLoadFile(
                        this.filesConfig.getLeftFileObject(),
                        getCheckBox_LeftReadOnly().isSelected()
                        );
        if( fo != null ) {
            this.filesConfig.setFileObject( fo, 0 );
            LOGGER.info( "Left File:" + fo);
            udpateFilesDisplay();
            }
    }

    //@Override
    protected void jButton_RightMouseMousePressed( final int index )
    {
        final FileObject fo = getLoadFile(
                        this.filesConfig.getFileObject( index ),
                        false
                        );
        if( fo != null ) {
            this.filesConfig.setFileObject( fo, index );
            LOGGER.info( "Right[" + index + "] File:" + fo );
            udpateFilesDisplay();
            }
    }

    //@Override
    protected void jButton_CancelMouseMousePressed()
    {
        this.filesConfig.clear();
        dispose();
    }

    //@Override
    protected void jButton_OkMouseMousePressed()
    {
        if( !this.filesConfig.isFilesExists() ) {
            Toolkit.getDefaultToolkit().beep();
            //TODO: display alert !
            return;
            }

        // Update left file is ReadOnly has change
        final FileObject foLeft = new FileObject(
                this.filesConfig.getLeftFileObject().getFile(),
                getCheckBox_LeftReadOnly().isSelected()
                );
        this.filesConfig.setFileObject( foLeft, 0 );

        try {
            this.filesConfig.load();
            }
        catch( final IOException e ) {
            DialogHelper.showMessageExceptionDialog( this, this.msgErrorWhileLoading, e );
            }

        this.preferences.setLastDirectory( foLeft.getFile().getParentFile() );
        dispose();
    }


    private void updateStoreOptions(final JCheckBox jCheckBox,final FormattedProperties.Store storeOption)
    {
        if( jCheckBox.isSelected() ) {
            this.filesConfig.add( storeOption );
            }
        else {
            this.filesConfig.remove( storeOption );
            }
    }

    @Override
    protected ActionListener getActionListener()
    {
        return e -> actionPerform( e.getActionCommand() );
    }

    private void jButtonSelectedPressed( final int index )
    {
        if( index == 0 ) {
            jButton_LeftMouseMousePressed();
            }
        else {
            jButton_RightMouseMousePressed( index );
            }
    }

    private void actionPerform( final String actionCommandString )
    {
        final Integer index = LoadDialogAction.ACTIONCMD_SELECT_PREFIX.getIndex( actionCommandString );

        if( index != null ) {
            jButtonSelectedPressed( index.intValue() );
        } else {
            final LoadDialogAction action = LoadDialogAction.valueOf( actionCommandString );

            try {
                action.perform( this );
            }
            catch( final Exception cause ) {
                // Unexpected error
                LOGGER.error(
                        "Unexpected error on: " + actionCommandString + " - " + action,
                        cause
                        );
            }
        }
    }

    void actionSelectPrefix()
    {
        // Should never occur
        throw new IllegalStateException();
    }

    void actionOkButton()
    {
        jButton_OkMouseMousePressed();
    }

    void actionCancelButton()
    {
        jButton_CancelMouseMousePressed();
    }

    void actionFtIni()
    {
        udpateTabFileTypeDisplay();
        //TODO: later (*.ini files)
    }

    void actionFtProperties()
    {
        udpateTabFileTypeDisplay();
        this.filesConfig.setFileType( FilesConfig.FileType.PROPERTIES );
    }

    void actionFtFormattedproperties()
    {
        udpateTabFileTypeDisplay();
        this.filesConfig.setFileType( FilesConfig.FileType.FORMATTED_PROPERTIES );
    }

    void actionChangeIsuselefthasdefault()
    {
        this.filesConfig.setUseLeftHasDefault(
                getCheckBox_RightUseLeftHasDefaults().isSelected()
                );
    }

    void actionChangeShowlinenumbers()
    {
        this.filesConfig.setShowLineNumbers(
                getCheckBox_ShowLineNumbers().isSelected()
                );
    }

    void actionChangeCutLineBeforeHtmlBr()
    {
        updateStoreOptions(
                getCheckBox_CUT_LINE_BEFORE_HTML_BR(),
                FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BR
                );
    }

    void actionChangeCutLineBeforeHtmlBeginP()
    {
        updateStoreOptions(
                getCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P(),
                FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BEGIN_P
                );
    }

    void actionChangeCutLineAfterNewLine()
    {
        updateStoreOptions(
                getCheckBox_CUT_LINE_AFTER_NEW_LINE(),
                FormattedProperties.Store.CUT_LINE_AFTER_NEW_LINE
                );
    }

    void actionChangeCutLineAfterHtmlEndP()
    {
        updateStoreOptions(
                getCheckBox_CUT_LINE_AFTER_HTML_END_P(),
                FormattedProperties.Store.CUT_LINE_AFTER_HTML_END_P
                );
    }

    void actionChangeCutLineAfterHtmlBr()
    {
        updateStoreOptions(
                getCheckBox_CUT_LINE_AFTER_HTML_BR(),
                FormattedProperties.Store.CUT_LINE_AFTER_HTML_BR
                );
    }
}
