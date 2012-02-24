package com.googlecode.cchlib.swing.batchrunner;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;

/**
 *
 */
public abstract class BatchRunnerPanelWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    public static final String ACTIONCMD_SELECT_SOURCE = "ACTIONCMD_SELECT_SOURCE";
    public static final String ACTIONCMD_SELECT_DESTINATION_FOLDER = "ACTIONCMD_SELECT_DESTINATION_FOLDER";
    public static final String ACTIONCMD_DO_ACTION = "ACTIONCMD_DO_ACTION";

    private JLabel jLabelDestination;
    private JLabel jLabelCurrentState;
    private JScrollPane scrollPane;
    private DefaultListModel<File> listModel;
    private JButton jButtonClearFileList;

    /**
     * Create the frame.
     * @param txtDesinationFolder
     * @param txtClearSourceFileList
     * @param txtDoActionButton, // "Convert"
     */
    public BatchRunnerPanelWB(
       final String txtAddSourceFile, // "Ajouter des fichiers source"
       final String txtSetDestinationFolder, // "Dossier de destination"
       final String txtClearSourceFileList, //"Effacer la liste"
       final String txtDoActionButton // "Convert"
       )
    {
        setSize(450, 210);

        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};

        this.setLayout(gbl_contentPane);

        JButton jButtonSource = new JButton( txtAddSourceFile );
        jButtonSource.setActionCommand( ACTIONCMD_SELECT_SOURCE );
        jButtonSource.addActionListener( getActionListener() );
        GridBagConstraints gbc_jButtonSource = new GridBagConstraints();
        gbc_jButtonSource.fill = GridBagConstraints.BOTH;
        gbc_jButtonSource.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonSource.gridx = 0;
        gbc_jButtonSource.gridy = 0;
        this.add( jButtonSource, gbc_jButtonSource );

        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 3;
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 0;
        this.add(scrollPane, gbc_scrollPane);

        JList<File> list = new JList<>();
        listModel = new DefaultListModel<File>();
        list.setModel( listModel);
        scrollPane.setViewportView(list);

        JButton jButtonDestination = new JButton( txtSetDestinationFolder );
        jButtonDestination.setActionCommand( ACTIONCMD_SELECT_DESTINATION_FOLDER );
        jButtonDestination.addActionListener( getActionListener() );

        jButtonClearFileList = new JButton( txtClearSourceFileList );
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
        GridBagConstraints gbc_jButtonDestination = new GridBagConstraints();
        gbc_jButtonDestination.fill = GridBagConstraints.BOTH;
        gbc_jButtonDestination.insets = new Insets(0, 0, 5, 5);
        gbc_jButtonDestination.gridx = 0;
        gbc_jButtonDestination.gridy = 3;
        this.add(jButtonDestination, gbc_jButtonDestination);

        jLabelDestination = new JLabel();
        GridBagConstraints gbc_jLabelDestination = new GridBagConstraints();
        gbc_jLabelDestination.gridwidth = 2;
        gbc_jLabelDestination.insets = new Insets(0, 0, 5, 0);
        gbc_jLabelDestination.anchor = GridBagConstraints.EAST;
        gbc_jLabelDestination.fill = GridBagConstraints.VERTICAL;
        gbc_jLabelDestination.gridx = 2;
        gbc_jLabelDestination.gridy = 3;
        this.add(jLabelDestination, gbc_jLabelDestination);

        JButton jButtonConvert = new JButton( txtDoActionButton );
        jButtonConvert.setActionCommand( ACTIONCMD_DO_ACTION );
        jButtonConvert.addActionListener( getActionListener() );

        jLabelCurrentState = new JLabel();
        GridBagConstraints gbc_jLabelCurrentState = new GridBagConstraints();
        gbc_jLabelCurrentState.fill = GridBagConstraints.HORIZONTAL;
        gbc_jLabelCurrentState.gridwidth = 3;
        gbc_jLabelCurrentState.insets = new Insets(0, 0, 0, 5);
        gbc_jLabelCurrentState.gridx = 0;
        gbc_jLabelCurrentState.gridy = 4;
        this.add(jLabelCurrentState, gbc_jLabelCurrentState);
        GridBagConstraints gbc_jButtonConvert = new GridBagConstraints();
        gbc_jButtonConvert.gridx = 3;
        gbc_jButtonConvert.gridy = 4;
        this.add(jButtonConvert, gbc_jButtonConvert);
    }

    protected abstract ActionListener getActionListener();

    protected void addSourceFile( final File file )
    {
        this.listModel.addElement( file );
    }

    protected void setDestinationFolderFile( File file )
    {
        this.jLabelDestination.putClientProperty( File.class, file );
        this.jLabelDestination.setText( file.getPath() );
    }

    public File getDestinationFolderFile()
    {
        return File.class.cast(
            this.jLabelDestination.getClientProperty( File.class )
            );
    }

    public Enumeration<File> getSourceFileElements()
    {
        return this.listModel.elements();
    }

    public int getSourceFilesCount()
    {
        return this.listModel.size();
    }

    public void setCurrentState( final String message )
    {
        jLabelCurrentState.setText( message );
    }
}
