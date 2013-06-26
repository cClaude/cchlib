package com.googlecode.cchlib.apps.emptyfiles.panel.select;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.list.NoDuplicateListModel;
import java.awt.BorderLayout;
import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class SelectDirecoriesJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JList<File> list;
    private JButton addButton;
    private JButton removeButton;
    private JButton startButton;
    private DefaultListModel<File> directoriesJListModel = new NoDuplicateListModel<>();
    private @I18nToolTipText JButton importButton;
    private JProgressBar progressBar;
    private JScrollPane scrollPane;

    /**
     * Create the panel.
     */
    public SelectDirecoriesJPanel( final RemoveEmptyFilesJPanel removeEmptyFilesJPanel )
    {
        setLayout(new BorderLayout(0, 0));
        this.panel = new JPanel();
        add(this.panel);

        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{300, 100, 0};
        gbl_panel.rowHeights = new int[]{0, 25, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        this.panel.setLayout(gbl_panel);
        {
            this.scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridheight = 4;
            gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 0;
            this.panel.add(this.scrollPane, gbc_scrollPane);
            this.list = new JList<>();
            this.scrollPane.setViewportView(this.list);
            this.list.setModel( directoriesJListModel );

            SimpleFileDrop.createSimpleFileDrop(
                this.list,
                this.directoriesJListModel,
                SimpleFileDrop.SelectionFilter.DIRECTORIES_ONLY
                );

            // Handle selection change
            this.list.addListSelectionListener( new ListSelectionListener() {
                @Override
                public void valueChanged( ListSelectionEvent event )
                {
                    if( list.getSelectedIndices().length > 0 ) {
                        removeButton.setEnabled( true );
                        }
                    else {
                        removeButton.setEnabled( false );
                        }
                }} );
        }

        {

            this.directoriesJListModel.addListDataListener( new ListDataListener() {
                private void onChange()
                {
                    if( directoriesJListModel.size() > 0 ) {
                        startButton.setEnabled( true );
                    } else {
                        startButton.setEnabled( false );
                    }
                }
                @Override
                public void intervalAdded( ListDataEvent e )
                {
                    onChange();
                }
                @Override
                public void intervalRemoved( ListDataEvent e )
                {
                    onChange();
                }

                @Override
                public void contentsChanged( ListDataEvent e )
                {
                    onChange();
                }} );
        }
        {
            this.addButton = new JButton("Add");
            this.addButton.setIcon( removeEmptyFilesJPanel.getResources().getFolderSelectIcon() );

            this.addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doAdd( removeEmptyFilesJPanel );
                }
            });
            GridBagConstraints gbc_addButton = new GridBagConstraints();
            gbc_addButton.fill = GridBagConstraints.BOTH;
            gbc_addButton.insets = new Insets(0, 0, 5, 0);
            gbc_addButton.gridx = 1;
            gbc_addButton.gridy = 0;
            this.panel.add(this.addButton, gbc_addButton);
        }
        {
            this.removeButton = new JButton("Remove");
            this.removeButton.setIcon( removeEmptyFilesJPanel.getResources().getFolderRemoveIcon() );
            this.removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doRemove();
                }
            });
            this.removeButton.setEnabled(false);
            
            GridBagConstraints gbc_removeButton = new GridBagConstraints();
            gbc_removeButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_removeButton.insets = new Insets(0, 0, 5, 0);
            gbc_removeButton.gridx = 1;
            gbc_removeButton.gridy = 2;
            this.panel.add(this.removeButton, gbc_removeButton);
        }
        {
            this.importButton = new JButton("Import");
            this.importButton.setToolTipText("Import directories from duplicate tab");
            this.importButton.setIcon( removeEmptyFilesJPanel.getResources().getFolderImportIcon() );
            this.importButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doImport( removeEmptyFilesJPanel );
                }
            });
            
            GridBagConstraints gbc_importButton = new GridBagConstraints();
            gbc_importButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_importButton.insets = new Insets(0, 0, 5, 0);
            gbc_importButton.gridx = 1;
            gbc_importButton.gridy = 1;
            this.panel.add(this.importButton, gbc_importButton);
        }
        {
            this.startButton = new JButton( "Start" );
            this.startButton.setIcon( removeEmptyFilesJPanel.getResources().getContinueIcon() );
            this.startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    final Collection<File> directoriesFiles = new HashSet<>();

                    {
                        Enumeration<File> enumeration = directoriesJListModel.elements();

                        while( enumeration.hasMoreElements() ) {
                            directoriesFiles.add( enumeration.nextElement() );
                            }
                    }

                    list.setEnabled( false );
                    addButton.setEnabled( false );
                    removeButton.setEnabled( false );
                    startButton.setEnabled( false );
                    importButton.setEnabled( false );

                    new Thread( new Runnable() {
                        @Override
                        public void run()
                        {
                            removeEmptyFilesJPanel.doFindFiles( directoriesFiles, progressBar );
                        }} ).start();
                }
            });
            {
                this.progressBar = new JProgressBar();
                this.progressBar.setEnabled(false);
                GridBagConstraints gbc_progressBar = new GridBagConstraints();
                gbc_progressBar.fill = GridBagConstraints.BOTH;
                gbc_progressBar.insets = new Insets(0, 0, 0, 5);
                gbc_progressBar.gridx = 0;
                gbc_progressBar.gridy = 4;
                this.panel.add(this.progressBar, gbc_progressBar);
            }
            this.startButton.setEnabled(false);
            GridBagConstraints gbc_startButton = new GridBagConstraints();
            gbc_startButton.fill = GridBagConstraints.BOTH;
            gbc_startButton.gridx = 1;
            gbc_startButton.gridy = 4;
            this.panel.add(this.startButton, gbc_startButton);
        }
    }

    protected void doRemove()
    {
        int[] selecteds = this.list.getSelectedIndices();

        for( int i = selecteds.length - 1; i>=0; i-- ) {
            int index = selecteds[ i ];

            this.directoriesJListModel.remove( index );
            }
    }

    protected void doImport( RemoveEmptyFilesJPanel removeEmptyFilesJPanel )
    {
        removeEmptyFilesJPanel.doImport( this.directoriesJListModel );
    }

    protected void doAdd( final RemoveEmptyFilesJPanel removeEmptyFilesJPanel )
    {
        new Thread( new Runnable() {
            @Override
            public void run()
            {
                JFileChooser jfc = removeEmptyFilesJPanel.getJFileChooser();

                jfc.setMultiSelectionEnabled( true );
                jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

                int returnVal = jfc.showOpenDialog( SelectDirecoriesJPanel.this );

                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    File[] files = jfc.getSelectedFiles();

                    for( File file : files ) {
                        SelectDirecoriesJPanel.this.directoriesJListModel.addElement( file );
                        }
                    }
            }} ).start();
    }

    public void autoSetEnabled()
    {
        this.list.setEnabled( true );
        this.list.setSelectedIndices( new int[0] );

        this.addButton.setEnabled( true );
        this.removeButton.setEnabled( false );
        this.importButton.setEnabled( true );

        if( this.directoriesJListModel.size() > 0 ) {
            this.startButton.setEnabled( true );
        } else {
            this.startButton.setEnabled( false );
        }
    }
}
