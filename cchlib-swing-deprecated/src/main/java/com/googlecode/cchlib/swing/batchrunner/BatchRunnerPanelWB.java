package com.googlecode.cchlib.swing.batchrunner;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException; // $codepro.audit.disable unnecessaryImport
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.lazy.DefaultBatchRunnerJFrame;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.dnd.SimpleFileDropListener;

/**
 * Build JPanel for task runner display
 *
 * @see BatchRunnerPanel
 * @see DefaultBatchRunnerJFrame
 * @since 4.1.7
 */
public abstract class BatchRunnerPanelWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( BatchRunnerPanelWB.class );
    public static final String ACTIONCMD_SELECT_SOURCE_FILES = "ACTIONCMD_SELECT_SOURCE_FILES";
    public static final String ACTIONCMD_SELECT_DESTINATION_FOLDER = "ACTIONCMD_SELECT_DESTINATION_FOLDER";
    public static final String ACTIONCMD_DO_ACTION = "ACTIONCMD_DO_ACTION";

    private JTextField jTextFieldDestination;
    private JTextField jTextFieldMessage;
    private DefaultListModel<File> listModel;
    private JProgressBar jProgressBarGlobal;
    private JButton jButtonSource;
    private JButton jButtonDestination;
    private JButton jButtonDoAction;
    private JButton jButtonClearFileList;

    /**
     * Create the panel.
     */
    protected BatchRunnerPanelWB(
        final BatchRunnerPanelLocaleResources localeResources
        )
    {
        setSize(450, 210);
        setBorder(new EmptyBorder(5, 5, 5, 5));

        {
            GridBagLayout gbl_contentPane = new GridBagLayout();
            gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
            gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
            gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

            this.setLayout(gbl_contentPane);
        }
        {
            jButtonSource = new JButton( localeResources.getTextAddSourceFile() );
            jButtonSource.setActionCommand( ACTIONCMD_SELECT_SOURCE_FILES );
            // $hide>>$
            jButtonSource.addActionListener( getActionListener() );
            // $hide<<$
            GridBagConstraints gbc_jButtonSource = new GridBagConstraints();
            gbc_jButtonSource.fill = GridBagConstraints.BOTH;
            gbc_jButtonSource.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonSource.gridx = 0;
            gbc_jButtonSource.gridy = 0;
            this.add( jButtonSource, gbc_jButtonSource );
        }
        {
            JScrollPane scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.gridheight = 3;
            gbc_scrollPane.gridwidth = 2;
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.gridx = 1;
            gbc_scrollPane.gridy = 0;
            this.add(scrollPane, gbc_scrollPane);

            // $hide>>$ BUG WindowBuilder ???
            JList<File> list = new JList<>();
            listModel = new DefaultListModel<File>();
            list.setModel( listModel );
            scrollPane.setViewportView(list);

            try {
                new SimpleFileDrop( list, new SimpleFileDropListener() {
                        @Override
                        public void filesDropped( List<File> files )
                        {
                            for( File f : files ) {
                                listModel.addElement( f );
                                }
                        }
                    } ).addDropTargetListener();
                }
            catch( HeadlessException | TooManyListenersException e ) {
                logger.error( "No Drag and Drop support", e );
                }
            // $hide<<$
        }
        {
            jButtonDestination = new JButton( localeResources.getTextSetDestinationFolder() );
            jButtonDestination.setActionCommand( ACTIONCMD_SELECT_DESTINATION_FOLDER );
            // $hide>>$
            jButtonDestination.addActionListener( getActionListener() );
            // $hide<<$
            GridBagConstraints gbc_jButtonDestination = new GridBagConstraints();
            gbc_jButtonDestination.fill = GridBagConstraints.BOTH;
            gbc_jButtonDestination.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonDestination.gridx = 0;
            gbc_jButtonDestination.gridy = 3;
            this.add(jButtonDestination, gbc_jButtonDestination);
        }
        {
            jButtonClearFileList = new JButton( localeResources.getTextClearSourceFileList() );
            jButtonClearFileList.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    listModel.clear();
                }} );
            GridBagConstraints gbc_jButtonClearFileList = new GridBagConstraints();
            gbc_jButtonClearFileList.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonClearFileList.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonClearFileList.gridx = 0;
            gbc_jButtonClearFileList.gridy = 2;
            this.add(jButtonClearFileList, gbc_jButtonClearFileList);
        }
        {
            jTextFieldDestination = new JTextField();
            jTextFieldDestination.setEditable(false);
            GridBagConstraints gbc_jTextFieldDestination = new GridBagConstraints();
            gbc_jTextFieldDestination.gridwidth = 2;
            gbc_jTextFieldDestination.insets = new Insets(0, 0, 5, 0);
            gbc_jTextFieldDestination.fill = GridBagConstraints.BOTH;
            gbc_jTextFieldDestination.gridx = 1;
            gbc_jTextFieldDestination.gridy = 3;
            this.add(jTextFieldDestination, gbc_jTextFieldDestination);
        }
        {
            jButtonDoAction = new JButton( localeResources.getTextDoAction() );
            // $hide>>$
            jButtonDoAction.setActionCommand( ACTIONCMD_DO_ACTION );
            // $hide<<$
            jButtonDoAction.addActionListener( getActionListener() );
            GridBagConstraints gbc_jButtonDoAction = new GridBagConstraints();
            gbc_jButtonDoAction.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonDoAction.gridx = 2;
            gbc_jButtonDoAction.gridy = 4;
            this.add(jButtonDoAction, gbc_jButtonDoAction);
        }
        {
            jTextFieldMessage = new JTextField();
            jTextFieldMessage.setEditable(false);
            GridBagConstraints gbc_jTextFieldMessage = new GridBagConstraints();
            gbc_jTextFieldMessage.fill = GridBagConstraints.BOTH;
            gbc_jTextFieldMessage.insets = new Insets(0, 0, 0, 5);
            gbc_jTextFieldMessage.gridx = 1;
            gbc_jTextFieldMessage.gridy = 4;
            this.add(jTextFieldMessage, gbc_jTextFieldMessage);
        }
        {
            jProgressBarGlobal = new JProgressBar();
            jProgressBarGlobal.setStringPainted( true );
            jProgressBarGlobal.setIndeterminate( false );
            GridBagConstraints gbc_jProgressBarGlobal = new GridBagConstraints();
            gbc_jProgressBarGlobal.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarGlobal.insets = new Insets(0, 0, 0, 5);
            gbc_jProgressBarGlobal.gridx = 0;
            gbc_jProgressBarGlobal.gridy = 4;
            add(jProgressBarGlobal, gbc_jProgressBarGlobal);
        }
    }

    /**
     * Returns ActionListener for this panel
     * @return ActionListener for this panel
     */
    // $hide>>$
    protected abstract ActionListener getActionListener();
    // $hide<<$

    /**
     * Add {@link File} to source files list
     * @param file File to add
     */
    protected void addSourceFile( final File file )
    {
        this.listModel.addElement( file );
        this.jProgressBarGlobal.setMaximum( this.listModel.size() );
    }

    /**
     * Set destination folder
     * @param file Destination folder
     */
    protected void setDestinationFolderFile( File file )
    {
        this.jTextFieldDestination.putClientProperty( File.class, file );
        this.jTextFieldDestination.setText( file.getPath() );
    }

    /**
     * Returns destination {@link File} object or null if not defined
     * @return destination {@link File} object or null if not defined
     */
    public File getOutputFolderFile()
    {
        return File.class.cast(
            this.jTextFieldDestination.getClientProperty( File.class )
            );
    }

    /**
     * Returns {@link Enumeration} of sources {@link File}
     * @return Sources files enumeration
     */
    public Enumeration<File> getSourceFileElements()
    {
        return this.listModel.elements();
    }

    /**
     * Returns number of sources {@link File} in list
     * @return Sources files count
     */
    public int getSourceFilesCount()
    {
        return this.listModel.size();
    }

    /**
     * Set current message
     * @param message Message to set
     */
    public void setCurrentMessage( final String message )
    {
        jTextFieldMessage.setText( message );
    }

    /**
     * Set current task number (start from 0)
     * @param value Current task number
     */
    protected void setCurrentTaskNumber( final int value )
    {
        jProgressBarGlobal.setValue( value );
    }

    /**
     * Enable or disable UI buttons
     * @param b True to enable, false to disable
     */
    protected void setEnabledButtons( final boolean b )
    {
        jButtonSource.setEnabled( b );
        jButtonDestination.setEnabled( b );
        jButtonClearFileList.setEnabled( b );
        jButtonDoAction.setEnabled( b );
    }

}
