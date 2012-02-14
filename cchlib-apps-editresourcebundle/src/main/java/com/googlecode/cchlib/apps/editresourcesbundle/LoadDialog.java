package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;

import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.util.FormattedProperties;

/**
 *
 * @author Claude CHOISNET
 */
class LoadDialog
    extends LoadDialogWB
        implements I18nAutoUpdatable
{
    private static final long   serialVersionUID        = 1L;
    private final static Logger slogger = Logger.getLogger( LoadDialog.class );
    private JFileChooserInitializer jFileChooserInitializer = new JFileChooserInitializer();
    private FilesConfig filesConfig;

    private final static int PROPERTIES = 1;
    private final static int FORMATTED_PROPERTIES = 2;
    private final static String ACTION_FT_Properties = "Properties";
    private final static String ACTION_FT_FormattedProperties = "FormattedProperties";
    private final static String ACTION_FT_ini = "ini";
    private final static String ACTION_Change_isUseLeftHasDefault = "isUseLeftHasDefault";
    private final static String ACTION_Change_ShowLineNumbers = "showLineNumbers";
    private final static String ACTION_Change_CUT_LINE_AFTER_HTML_BR = "CUT_LINE_AFTER_HTML_BR";
    private final static String ACTION_Change_CUT_LINE_AFTER_HTML_END_P = "CUT_LINE_AFTER_HTML_END_P";
    private final static String ACTION_Change_CUT_LINE_AFTER_NEW_LINE = "CUT_LINE_AFTER_NEW_LINE";
    private final static String ACTION_Change_CUT_LINE_BEFORE_HTML_BEGIN_P = "CUT_LINE_BEFORE_HTML_BEGIN_P";
    private final static String ACTION_Change_CUT_LINE_BEFORE_HTML_BR = "CUT_LINE_BEFORE_HTML_BR";

    @I18nString private String strMsgTitle = "Load...";
    @I18nString private String strMsg_ErrorWhileLoading = "Error while loading files";

    private ActionListener thisActionListener;
    private Preferences preferences;

    public LoadDialog(
        final CompareResourcesBundleFrame   parentFrame,
        final FilesConfig                   filesConfig
        )
    {
        super( parentFrame );
        this.preferences = parentFrame.getPreferences();
        this.jFileChooserInitializer = parentFrame.getJFileChooserInitializer();
        this.filesConfig = filesConfig;
        initFixComponents();

        setDefaultCloseOperation( LoadDialog.DISPOSE_ON_CLOSE );
        setTitle( strMsgTitle );
        setLocationRelativeTo( parentFrame );
        getContentPane().setPreferredSize( this.getSize() );
        pack();
    }

    @Override // I18nAutoUpdatable
    public void performeI18n(AutoI18n autoI18n)
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    public void initFixComponents()
    {
        // Load tab
        if( filesConfig.getLeftFileObject() != null ) {
            jCheckBox_LeftReadOnly.setSelected(
                    filesConfig.getLeftFileObject().isReadOnly()
                    );
            }

        initCheckBox(
                jCheckBox_ShowLineNumbers,
                ACTION_Change_ShowLineNumbers,
                filesConfig.isShowLineNumbers()
                );

        // Load tab - FileType
        initCheckBox(
                jCheckBox_Properties,
                ACTION_FT_Properties,
                FilesConfig.FileType.PROPERTIES == filesConfig.getFileType()
                );
        initCheckBox(
                jCheckBox_FormattedProperties,
                ACTION_FT_FormattedProperties,
                FilesConfig.FileType.FORMATTED_PROPERTIES == filesConfig.getFileType()
                );
        initCheckBox(
                jCheckBox_ini,
                ACTION_FT_ini,
                false // TODO add in config
                );

        // Properties tab
        initCheckBox(
                jCheckBox_RightUseLeftHasDefaults,
                ACTION_Change_isUseLeftHasDefault,
                filesConfig.isUseLeftHasDefault()
                );

        // FormattedProperties tab
        initCheckBox(
                jCheckBox_CUT_LINE_AFTER_HTML_BR,
                ACTION_Change_CUT_LINE_AFTER_HTML_BR,
                filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_AFTER_HTML_BR
                        )
                );
        initCheckBox(
                jCheckBox_CUT_LINE_AFTER_HTML_END_P,
                ACTION_Change_CUT_LINE_AFTER_HTML_END_P,
                filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_AFTER_HTML_END_P
                        )
                );
        initCheckBox(
                jCheckBox_CUT_LINE_AFTER_NEW_LINE,
                ACTION_Change_CUT_LINE_AFTER_NEW_LINE,
                filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_AFTER_NEW_LINE
                        )
                );
        initCheckBox(
                jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P,
                ACTION_Change_CUT_LINE_BEFORE_HTML_BEGIN_P,
                filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BEGIN_P
                        )
                );
        initCheckBox(
                jCheckBox_CUT_LINE_BEFORE_HTML_BR,
                ACTION_Change_CUT_LINE_BEFORE_HTML_BR,
                filesConfig.getFormattedPropertiesStore().contains(
                        FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BR
                        )
                );

        udpateFilesDisplay();
        udpateTabFileTypeDisplay();
    }

    private void initCheckBox(
            JCheckBox   jCheckBox,
            String      actionCommand,
            boolean     value
            )
    {
        jCheckBox.setActionCommand(actionCommand);
        jCheckBox.addActionListener( getActionListener() );
        jCheckBox.setSelected( value );
    }

    protected void udpateFilesDisplay()
    {
        if( this.filesConfig.getLeftFileObject()!=null ) {
            getJTextField_Left().setText(
                this.filesConfig.getLeftFileObject().getFile().getPath()
                );
            }
        else {
            getJTextField_Left().setText("");
            }
        if( this.filesConfig.getRightFileObject()!=null ) {
            getJTextField_Right().setText(
                this.filesConfig.getRightFileObject().getFile().getPath()
                );
            }
        else {
            getJTextField_Right().setText("");
            }
    }

    protected void udpateTabFileTypeDisplay()
    {
        if( jCheckBox_Properties.isSelected() ) {
            getJTabbedPaneRoot().setEnabledAt( PROPERTIES, true );
            getJTabbedPaneRoot().setEnabledAt( FORMATTED_PROPERTIES, false );
            }
        else if( jCheckBox_FormattedProperties.isSelected() ) {
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
        return jFileChooserInitializer.getJFileChooser();
    }

    private FileObject getLoadFile(
            FileObject previousFile,
            boolean    readOnly
            )
    {
        final JFileChooser fc = getJFileChooser();

        if( previousFile != null ) {
            if( previousFile.getFile() != null ) {
                fc.setSelectedFile( previousFile.getFile() );
                }
            }

        int returnVal = fc.showOpenDialog( this );

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return new FileObject(fc.getSelectedFile(), readOnly);
            }

        return null;
    }

    //@Override
    private void jButton_LeftMouseMousePressed(/*MouseEvent event*/)
    {
        FileObject fo = getLoadFile(
                        filesConfig.getLeftFileObject(),
                        jCheckBox_LeftReadOnly.isSelected()
                        );
        if( fo != null ) {
            this.filesConfig.setLeftFileObject( fo );
            slogger.info( "Left File:" + fo);
            udpateFilesDisplay();
            }
    }

    //@Override
    protected void jButton_RightMouseMousePressed(/*MouseEvent event*/)
    {
        FileObject fo = getLoadFile(
                        filesConfig.getRightFileObject(),
                        false
                        );
        if( fo != null ) {
            this.filesConfig.setRightFileObject( fo );
            slogger.info( "Right File:" + fo);
            udpateFilesDisplay();
            }
    }

    //@Override
    protected void jButton_CancelMouseMousePressed(/*MouseEvent event*/)
    {
        this.filesConfig.clear();
        dispose();
    }

    //@Override
    protected void jButton_OkMouseMousePressed(/*MouseEvent event*/)
    {
        if( !filesConfig.isFilesExists() ) {
            Toolkit.getDefaultToolkit().beep();
            //TODO: display alert !
            return;
            }

        // Update left file is ReadOnly has change
        FileObject foLeft = new FileObject(
                filesConfig.getLeftFileObject().getFile(),
                jCheckBox_LeftReadOnly.isSelected()
                );
        filesConfig.setLeftFileObject( foLeft );

        try {
            filesConfig.load();
            }
        catch( IOException e ) {
            DialogHelper.showMessageExceptionDialog( this, strMsg_ErrorWhileLoading, e );
            }

        preferences.setLastDirectory( foLeft.getFile().getParentFile() );
        dispose();
    }


    private void updateStoreOptions(JCheckBox jCheckBox,FormattedProperties.Store storeOption)
    {
        if( jCheckBox.isSelected() ) {
            filesConfig.add( storeOption );
            }
        else {
            filesConfig.remove( storeOption );
            }
    }

    @Override
    protected ActionListener getActionListener()
    {
        if( this.thisActionListener == null ) {
            this.thisActionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    final String c = e.getActionCommand();

                    if( ACTIONCMD_SELECT_LEFT.equals( e.getActionCommand() ) ) {
                        jButton_LeftMouseMousePressed();
                        }
                    else if( ACTIONCMD_SELECT_RIGHT.equals( e.getActionCommand() ) ) {
                        jButton_RightMouseMousePressed();
                        }
                    else if( ACTIONCMD_OK_BUTTON.equals( e.getActionCommand() ) ) {
                        jButton_OkMouseMousePressed();
                        }
                    else if( ACTIONCMD_CANCEL_BUTTON.equals( e.getActionCommand() ) ) {
                        jButton_CancelMouseMousePressed();
                        }
                    else if( ACTION_FT_Properties.equals( c )) {
                        udpateTabFileTypeDisplay();
                        filesConfig.setFileType( FilesConfig.FileType.PROPERTIES );
                        }
                    else if( ACTION_FT_FormattedProperties.equals( c )) {
                        udpateTabFileTypeDisplay();
                        filesConfig.setFileType( FilesConfig.FileType.FORMATTED_PROPERTIES );
                        }
                    else if( ACTION_FT_ini.equals( c )) {
                        udpateTabFileTypeDisplay();
                        //TODO:  !
                        }
                    else if( ACTION_Change_isUseLeftHasDefault.equals( c )) {
                        filesConfig.setUseLeftHasDefault(
                                jCheckBox_RightUseLeftHasDefaults.isSelected()
                                );
                        }
                    else if( ACTION_Change_CUT_LINE_AFTER_HTML_BR.equals( c )) {
                        updateStoreOptions(
                                jCheckBox_CUT_LINE_AFTER_HTML_BR,
                                FormattedProperties.Store.CUT_LINE_AFTER_HTML_BR
                                );
                        }
                    else if( ACTION_Change_CUT_LINE_AFTER_HTML_END_P.equals( c )) {
                        updateStoreOptions(
                                jCheckBox_CUT_LINE_AFTER_HTML_END_P,
                                FormattedProperties.Store.CUT_LINE_AFTER_HTML_END_P
                                );
                        }
                    else if( ACTION_Change_CUT_LINE_AFTER_NEW_LINE.equals( c )) {
                        updateStoreOptions(
                                jCheckBox_CUT_LINE_AFTER_NEW_LINE,
                                FormattedProperties.Store.CUT_LINE_AFTER_NEW_LINE
                                );
                        }
                    else if( ACTION_Change_CUT_LINE_BEFORE_HTML_BEGIN_P.equals( c )) {
                        updateStoreOptions(
                                jCheckBox_CUT_LINE_BEFORE_HTML_BEGIN_P,
                                FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BEGIN_P
                                );
                        }
                    else if( ACTION_Change_CUT_LINE_BEFORE_HTML_BR.equals( c )) {
                        updateStoreOptions(
                                jCheckBox_CUT_LINE_BEFORE_HTML_BR,
                                FormattedProperties.Store.CUT_LINE_BEFORE_HTML_BR
                                );
                        }
                    else if( ACTION_Change_ShowLineNumbers.equals( c )) {
                        filesConfig.setShowLineNumbers(
                                jCheckBox_ShowLineNumbers.isSelected()
                                );
                        }
                    else {
                        slogger.warn( "Unknown ActionCommand: " + c );
                        }
                }
            };
            }
        return this.thisActionListener;
    }
}
