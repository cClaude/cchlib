package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import com.googlecode.cchlib.swing.batchrunner.BREnableListener;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;


/**
 * Build JPanel for task runner display
 *
 * @see BRFrame
 * @since 4.1.8
 */
public class BRPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger( BRPanel.class );

    public static final String ACTIONCMD_SELECT_SOURCE_FILES       = "ACTIONCMD_SELECT_SOURCE_FILES";
    public static final String ACTIONCMD_SELECT_DESTINATION_FOLDER = "ACTIONCMD_SELECT_DESTINATION_FOLDER";
    public static final String ACTIONCMD_DO_ACTION                 = "ACTIONCMD_DO_ACTION";

    private final BRActionListener       actionListener;
    private final BRPanelLocaleResources localeResources;

    private JTextField            jTextFieldDestination;
    private JTextField             jTextFieldMessage;
    private DefaultListModel<File> listModel;
    private JProgressBar           jProgressBarGlobal;
    private JButton                jButtonSource;
    private JButton                jButtonDestination;
    private JButton                jButtonDoAction;
    private JButton                jButtonClearFileList;

    /**
     * Create the panel.
     *
     * @param actionListener  a valid {@link BRActionListener}
     * @param localeResources a valid {@link BRPanelLocaleResources}
     */
    @SuppressWarnings({"squid:S00117","squid:S1199"}) // generated code
    protected BRPanel(
        final BRActionListener       actionListener,
        final BRPanelLocaleResources localeResources
        )
    {
        this.actionListener  = actionListener;
        this.localeResources = localeResources;

        setSize(450, 210);
        setBorder(new EmptyBorder(5, 5, 5, 5));

        {
            final GridBagLayout gbl_contentPane = new GridBagLayout();
            gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
            gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
            gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

            this.setLayout(gbl_contentPane);
        }
        {
            this.jButtonSource = new JButton( localeResources.getTextAddSourceFile() );
            this.jButtonSource.setActionCommand( ACTIONCMD_SELECT_SOURCE_FILES );
            // $hide>>$
            this.jButtonSource.addActionListener( actionListener );
            // $hide<<$
            final GridBagConstraints gbc_jButtonSource = new GridBagConstraints();
            gbc_jButtonSource.fill = GridBagConstraints.BOTH;
            gbc_jButtonSource.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonSource.gridx = 0;
            gbc_jButtonSource.gridy = 0;
            this.add( this.jButtonSource, gbc_jButtonSource );
        }
        {
            final JScrollPane scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.gridheight = 3;
            gbc_scrollPane.gridwidth = 2;
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
            gbc_scrollPane.gridx = 1;
            gbc_scrollPane.gridy = 0;
            this.add(scrollPane, gbc_scrollPane);

            final JList<File> list = new JList<>();

            this.listModel = new DefaultListModel<>();

            list.setModel( this.listModel );
            scrollPane.setViewportView( list );

            addDropTargetListener( list );
        }
        {
            this.jButtonDestination = new JButton( localeResources.getTextSetDestinationFolder() );
            this.jButtonDestination.setActionCommand( ACTIONCMD_SELECT_DESTINATION_FOLDER );
            // $hide>>$
            this.jButtonDestination.addActionListener( actionListener );
            // $hide<<$
            final GridBagConstraints gbc_jButtonDestination = new GridBagConstraints();
            gbc_jButtonDestination.fill = GridBagConstraints.BOTH;
            gbc_jButtonDestination.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonDestination.gridx = 0;
            gbc_jButtonDestination.gridy = 3;
            this.add(this.jButtonDestination, gbc_jButtonDestination);
        }
        {
            this.jButtonClearFileList = new JButton( localeResources.getTextClearSourceFileList() );
            this.jButtonClearFileList.addActionListener( e -> BRPanel.this.listModel.clear() );
            final GridBagConstraints gbc_jButtonClearFileList = new GridBagConstraints();
            gbc_jButtonClearFileList.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonClearFileList.insets = new Insets(0, 0, 5, 5);
            gbc_jButtonClearFileList.gridx = 0;
            gbc_jButtonClearFileList.gridy = 2;
            this.add(this.jButtonClearFileList, gbc_jButtonClearFileList);
        }
        {
            this.jTextFieldDestination = new JTextField();
            this.jTextFieldDestination.setEditable(false);
            final GridBagConstraints gbc_jTextFieldDestination = new GridBagConstraints();
            gbc_jTextFieldDestination.gridwidth = 2;
            gbc_jTextFieldDestination.insets = new Insets(0, 0, 5, 0);
            gbc_jTextFieldDestination.fill = GridBagConstraints.BOTH;
            gbc_jTextFieldDestination.gridx = 1;
            gbc_jTextFieldDestination.gridy = 3;
            this.add(this.jTextFieldDestination, gbc_jTextFieldDestination);
        }
        {
            this.jButtonDoAction = new JButton( localeResources.getTextDoAction() );
            // $hide>>$
            this.jButtonDoAction.setActionCommand( ACTIONCMD_DO_ACTION );
            // $hide<<$
            this.jButtonDoAction.addActionListener( actionListener );
            final GridBagConstraints gbc_jButtonDoAction = new GridBagConstraints();
            gbc_jButtonDoAction.fill = GridBagConstraints.HORIZONTAL;
            gbc_jButtonDoAction.gridx = 2;
            gbc_jButtonDoAction.gridy = 4;
            this.add(this.jButtonDoAction, gbc_jButtonDoAction);
        }
        {
            this.jTextFieldMessage = new JTextField();
            this.jTextFieldMessage.setEditable(false);
            final GridBagConstraints gbc_jTextFieldMessage = new GridBagConstraints();
            gbc_jTextFieldMessage.fill = GridBagConstraints.BOTH;
            gbc_jTextFieldMessage.insets = new Insets(0, 0, 0, 5);
            gbc_jTextFieldMessage.gridx = 1;
            gbc_jTextFieldMessage.gridy = 4;
            this.add(this.jTextFieldMessage, gbc_jTextFieldMessage);
        }
        {
            this.jProgressBarGlobal = new JProgressBar();
            this.jProgressBarGlobal.setStringPainted( true );
            this.jProgressBarGlobal.setIndeterminate( false );
            final GridBagConstraints gbc_jProgressBarGlobal = new GridBagConstraints();
            gbc_jProgressBarGlobal.fill = GridBagConstraints.HORIZONTAL;
            gbc_jProgressBarGlobal.insets = new Insets(0, 0, 0, 5);
            gbc_jProgressBarGlobal.gridx = 0;
            gbc_jProgressBarGlobal.gridy = 4;
            add(this.jProgressBarGlobal, gbc_jProgressBarGlobal);
        }
    }

    private void addDropTargetListener( final JList<File> list )
    {
        try {
            new SimpleFileDrop(
                    list,
                    this::filesDroppedSimpleFileDropListener
                    ).addDropTargetListener();
            }
        catch( /*TooManyListeners*/final Exception e ) {
            LOGGER.error( "No Drag and Drop support", e );
            }
    }

    private void filesDroppedSimpleFileDropListener( final List<File> files )
    {
        for( final File f : files ) {
            BRPanel.this.listModel.addElement( f );
            }
    }

    public BRPanelLocaleResources getSBRLocaleResources()
    {
        return this.localeResources;
    }

    /**
     * Add {@link File} to source files list if <code>file<code> is allowed according to
     * {@link BRPanelConfig#getSourceFileFilter()}
     *
     * @param file File to add
     */
    protected void addSourceFile( final File file )
    {
        if( ! this.actionListener.getSBRConfig().getSourceFileFilter().accept( file ) ) {
            LOGGER.info( "ignore addSourceFile( " + file + " );" );
            return;
            }

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "addSourceFile( " + file + " );" );
            }

        SafeSwingUtilities.invokeLater( ( ) -> {
            BRPanel.this.listModel.addElement( file );
            BRPanel.this.jProgressBarGlobal.setMaximum( BRPanel.this.listModel.size() );
        }, "SBRPanel.addSourceFile" );
    }

    /**
     * Set destination folder
     * @param file Destination folder
     */
    protected void setDestinationFolderFile( final File file )
    {
        SafeSwingUtilities.invokeLater( ( ) -> {
            BRPanel.this.jTextFieldDestination.putClientProperty( File.class, file );
            BRPanel.this.jTextFieldDestination.setText( file.getPath() );
        }, "SBRPanel.setDestinationFolderFile" );
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
        this.jTextFieldMessage.setText( message );
    }

    /**
     * Set current task number (start from 0)
     * @param value Current task number
     */
    protected void setCurrentTaskNumber( final int value )
    {
        this.jProgressBarGlobal.setValue( value );
    }

    /**
     * Enable or disable UI buttons
     * @param b True to enable, false to disable
     */
    protected void setEnabledButtons( final boolean b )
    {
        this.jButtonSource.setEnabled( b );
        this.jButtonDestination.setEnabled( b );
        this.jButtonClearFileList.setEnabled( b );
        this.jButtonDoAction.setEnabled( b );
    }

    /**
     * Adds an EnableListener to the listener list.
     * The listener is registered for invocation of
     * {@link #fireStateChanged(boolean)}.
     *
     * @param listener the listener to be added
     */
    public void addEnableListener( final BREnableListener listener )
    {
        this.listenerList.add( BREnableListener.class, listener );
    }

    /**
     * Remove an EnableListener to the listener list.
     *
     * @param listener the listener to be removed
     */
    public void removeEnableListener( final BREnableListener listener )
    {
        this.listenerList.remove( BREnableListener.class, listener );
    }

    /**
     * Runs each <code>EnableListener</code>'s <code>stateChanged</code>
     * method.
     */
    protected void fireStateChanged( final boolean enable )
    {
        setEnabledButtons( enable );

        final Object[] listeners = this.listenerList.getListenerList();

        for( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if (listeners[i] == BREnableListener.class) {
                ((BREnableListener) listeners[i + 1]).setEnabled( enable );
                }
            }
    }

    /**
     * @return current {@link BRActionListener}.
     */
    protected BRActionListener getSBRActionListener()
    {
        return this.actionListener;
    }
}
