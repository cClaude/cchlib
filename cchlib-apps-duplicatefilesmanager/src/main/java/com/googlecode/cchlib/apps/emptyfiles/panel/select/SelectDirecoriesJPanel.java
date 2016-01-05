// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.emptyfiles.panel.select;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import com.googlecode.cchlib.apps.emptyfiles.RemoveEmptyFilesJPanel;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.swing.dnd.SimpleFileDrop;
import com.googlecode.cchlib.swing.list.NoDuplicateListModel;

@I18nName("emptyfiles.SelectDirecoriesJPanel")
public class SelectDirecoriesJPanel extends JPanel // $codepro.audit.disable largeNumberOfFields
{
    private static final int[] EMPTY_SELECTION = new int[0];

    private static final long serialVersionUID = 1L;

    private final JPanel panel;
    private JList<File> list;
    private final JButton addButton;
    private JButton removeButton;
    private JButton startButton;
    private final DefaultListModel<File> directoriesJListModel = new NoDuplicateListModel<>();
    private final @I18nToolTipText JButton importButton;
    private final JProgressBar progressBar;
    private final JScrollPane scrollPane;

    /**
     * Create the panel.
     */
    public SelectDirecoriesJPanel( final RemoveEmptyFilesJPanel removeEmptyFilesJPanel )
    {
        setLayout(new BorderLayout(0, 0));
        this.panel = new JPanel();
        add(this.panel);

        final GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{300, 100, 0};
        gbl_panel.rowHeights = new int[]{0, 25, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        this.panel.setLayout(gbl_panel);
        {
            this.scrollPane = new JScrollPane();
            final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
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
            this.list.addListSelectionListener( (final ListSelectionEvent event) -> {
                if( list.getSelectedIndices().length > 0 ) {
                    removeButton.setEnabled( true );
                }
                else {
                    removeButton.setEnabled( false );
                }
            });
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
                public void intervalAdded( final ListDataEvent e )
                {
                    onChange();
                }
                @Override
                public void intervalRemoved( final ListDataEvent e )
                {
                    onChange();
                }

                @Override
                public void contentsChanged( final ListDataEvent e )
                {
                    onChange();
                }} );
        }
        {
            this.addButton = new JButton("Add");
            this.addButton.setIcon( removeEmptyFilesJPanel.getResources().getFolderSelectIcon() );

            this.addButton.addActionListener((final ActionEvent e) -> {
                doAdd( removeEmptyFilesJPanel );
            });
            final GridBagConstraints gbc_addButton = new GridBagConstraints();
            gbc_addButton.fill = GridBagConstraints.BOTH;
            gbc_addButton.insets = new Insets(0, 0, 5, 0);
            gbc_addButton.gridx = 1;
            gbc_addButton.gridy = 0;
            this.panel.add(this.addButton, gbc_addButton);
        }
        {
            this.removeButton = new JButton("Remove");
            this.removeButton.setIcon( removeEmptyFilesJPanel.getResources().getFolderRemoveIcon() );
            this.removeButton.addActionListener((final ActionEvent e) -> {
                doRemove();
            });
            this.removeButton.setEnabled(false);

            final GridBagConstraints gbc_removeButton = new GridBagConstraints();
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
            this.importButton.addActionListener((final ActionEvent e) -> {
                doImport( removeEmptyFilesJPanel );
            });

            final GridBagConstraints gbc_importButton = new GridBagConstraints();
            gbc_importButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_importButton.insets = new Insets(0, 0, 5, 0);
            gbc_importButton.gridx = 1;
            gbc_importButton.gridy = 1;
            this.panel.add(this.importButton, gbc_importButton);
        }
        {
            this.startButton = new JButton( "Start" );
            this.startButton.setIcon( removeEmptyFilesJPanel.getResources().getContinueIcon() );
            this.startButton.addActionListener((final ActionEvent e) -> {
                doStart( removeEmptyFilesJPanel );
            });
            {
                this.progressBar = new JProgressBar();
                this.progressBar.setEnabled(false);
                final GridBagConstraints gbc_progressBar = new GridBagConstraints();
                gbc_progressBar.fill = GridBagConstraints.BOTH;
                gbc_progressBar.insets = new Insets(0, 0, 0, 5);
                gbc_progressBar.gridx = 0;
                gbc_progressBar.gridy = 4;
                this.panel.add(this.progressBar, gbc_progressBar);
            }
            this.startButton.setEnabled(false);
            final GridBagConstraints gbc_startButton = new GridBagConstraints();
            gbc_startButton.fill = GridBagConstraints.BOTH;
            gbc_startButton.gridx = 1;
            gbc_startButton.gridy = 4;
            this.panel.add(this.startButton, gbc_startButton);
        }
    }

    private void doStart(
        final RemoveEmptyFilesJPanel removeEmptyFilesJPanel
        )
    {
        final Collection<File> directoriesFiles = new HashSet<>();

        {
            final Enumeration<File> enumeration = directoriesJListModel.elements();

            while( enumeration.hasMoreElements() ) {
                directoriesFiles.add( enumeration.nextElement() );
                }
        }

        list.setEnabled( false );
        addButton.setEnabled( false );
        removeButton.setEnabled( false );
        startButton.setEnabled( false );
        importButton.setEnabled( false );

        new Thread( () -> {
            removeEmptyFilesJPanel.doFindFiles( directoriesFiles, progressBar );
        }, "doStart()" ).start();
    }

    private void doRemove()
    {
        final int[] selecteds = this.list.getSelectedIndices();

        for( int i = selecteds.length - 1; i>=0; i-- ) {
            final int index = selecteds[ i ];

            this.directoriesJListModel.remove( index );
            }
    }

    private void doImport( final RemoveEmptyFilesJPanel removeEmptyFilesJPanel )
    {
        removeEmptyFilesJPanel.doImport( this.directoriesJListModel );
    }

    private void doAdd( final RemoveEmptyFilesJPanel removeEmptyFilesJPanel )
    {
        new Thread( () -> {
            final JFileChooser jfc = removeEmptyFilesJPanel.getJFileChooser();

            jfc.setMultiSelectionEnabled( true );
            jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

            final int returnVal = jfc.showOpenDialog( SelectDirecoriesJPanel.this );

            if( returnVal == JFileChooser.APPROVE_OPTION ) {
                final File[] files = jfc.getSelectedFiles();

                for( final File file : files ) {
                    SelectDirecoriesJPanel.this.directoriesJListModel.addElement( file );
                }
            }
        }, "doAdd()" ).start();
    }

    public void autoSetEnabled()
    {
        this.list.setEnabled( true );
        this.list.setSelectedIndices( EMPTY_SELECTION );

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
