package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;


import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.util.FormattedProperties;

/**
 *
 * @author Claude CHOISNET
 */
class LoadDialog
    extends LoadDialogWB
        implements ActionListener
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

    public LoadDialog(
        Frame                   parent,
        JFileChooserInitializer jFileChooserInitializer,
        FilesConfig             filesConfig
        )
    {
        super( parent );
        this.jFileChooserInitializer = jFileChooserInitializer;
        this.filesConfig = filesConfig;
        initFixComponents();

        setDefaultCloseOperation( LoadDialog.DISPOSE_ON_CLOSE );
        setTitle( "Load..." ); // TODO i18n
        setLocationRelativeTo( parent );
        getContentPane().setPreferredSize( this.getSize() );
        pack();
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
        jCheckBox.addActionListener( this );
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
/*
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                FilesConfig fc = new FilesConfig();
                CompareResourcesBundleFrame frame = new CompareResourcesBundleFrame();
                LoadDialog dialog = new LoadDialog(
                        frame,
                        frame.getJFileChooserInitializer(),
                        fc
                        );
                dialog.setDefaultCloseOperation( LoadDialog.DISPOSE_ON_CLOSE );
                dialog.setTitle( "Load Dialog (Debug)" );
                dialog.setLocationRelativeTo( null );
                dialog.getContentPane().setPreferredSize( dialog.getSize() );
                dialog.pack();
                dialog.setVisible( true );
            }
        } );
    }
*/
    @Override
    protected void jButton_LeftMouseMousePressed(MouseEvent event)
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

    @Override
    protected void jButton_RightMouseMousePressed(MouseEvent event)
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

    @Override
    protected void jButton_CancelMouseMousePressed(MouseEvent event)
    {
        this.filesConfig.clear();
        dispose();
    }

    @Override
    protected void jButton_OkMouseMousePressed(MouseEvent event)
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
        catch( FileNotFoundException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dispose();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        final String c = e.getActionCommand();

        if( ACTION_FT_Properties.equals( c )) {
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

    private void updateStoreOptions(JCheckBox jCheckBox,FormattedProperties.Store storeOption)
    {
        if( jCheckBox.isSelected() ) {
            filesConfig.add( storeOption );
        }
        else {
            filesConfig.remove( storeOption );
        }
    }

}
