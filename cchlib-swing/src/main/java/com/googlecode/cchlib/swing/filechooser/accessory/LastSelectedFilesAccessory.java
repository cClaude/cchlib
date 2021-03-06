/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package com.googlecode.cchlib.swing.filechooser.accessory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * <p>
 * Store last selected files
 * </p>
 * <p>
 * To unregister this component use {@link #unregisterForce()}
 * </p>
 */
@SuppressWarnings({
    "squid:S00116", // Naming convention for fields
    "squid:S00117"  // Naming convention for local variables
    })
public class LastSelectedFilesAccessory
    extends JPanel
        implements  TabbedAccessoryInterface,
                    ActionListener,
                    PropertyChangeListener
{
    private static final long serialVersionUID = 1L;

    private JScrollPane                   jScrollPane_LastSelectedFiles;
    private final DefaultListModel<File>  listModel_LastSelectedFiles;
    private JButton                       jButton_Refresh    = new JButton("Refresh");
    private JButton                       jButton_RemoveFile = new JButton("Delete");

    /** @serial */
    private final JFileChooser    jFileChooser;
    /** @serial */
    private final LastSelectedFilesAccessoryConfigurator    configurator;
    /** @serial */
    private final ResourcesUtils resourcesUtils;

    public LastSelectedFilesAccessory(
            final JFileChooser    jFileChooser,
            final LastSelectedFilesAccessoryConfigurator    config
            )
    {
        this.jFileChooser = jFileChooser;
        this.configurator = config;
        this.resourcesUtils = new ResourcesUtils();

        this.listModel_LastSelectedFiles = new DefaultListModel<>();

        initComponents();
        initLayout();

        refreshAccessory();

        //register(); - Should work event if not visible
        this.jFileChooser.addActionListener( this );
        this.jFileChooser.addPropertyChangeListener( this );
    }

    private void initComponents()
    {
        final JList<File> jList_LastSelectedFiles = new JList<>( this.listModel_LastSelectedFiles );

        jList_LastSelectedFiles.addMouseListener(
            new MouseAdapter()
            {
                @Override
                public void mouseClicked(final MouseEvent e)
                {
                    if (e.getClickCount() == 2) {
                        final int     index = jList_LastSelectedFiles.locationToIndex(e.getPoint());
                        final Object  o     = LastSelectedFilesAccessory.this.listModel_LastSelectedFiles.get( index );

                         if( o instanceof File ) {
                            LastSelectedFilesAccessory.this.jFileChooser.setSelectedFile(
                                    File.class.cast( o )
                                    );

                            if( LastSelectedFilesAccessory.this.configurator.getAutoApproveSelection() ) {
                                LastSelectedFilesAccessory.this.jFileChooser.approveSelection();
                            }
                        }
                    }
                }
            });

        this.jButton_Refresh = getRefreshButton();
        this.jButton_Refresh.addMouseListener(
            new MouseAdapter()
            {
                @Override
                public void mousePressed(final MouseEvent event)
                {
                    LastSelectedFilesAccessory.this.jFileChooser.rescanCurrentDirectory();
                }
            });

        this.jButton_RemoveFile = getRemoveButton();
        this.jButton_RemoveFile.addMouseListener(
            new MouseAdapter()
            {
                @Override
                public void mousePressed(final MouseEvent event)
                {
                    final int[] selectedIx = jList_LastSelectedFiles.getSelectedIndices();

                    for( int i=selectedIx.length - 1; i>=0; i-- ) {
                        final Object sel = jList_LastSelectedFiles.getModel().getElementAt(selectedIx[i]);

                        if( sel instanceof File) {
                            final File f = File.class.cast( sel );

                            if( LastSelectedFilesAccessory.this.configurator.removeLastSelectedFile( f ) ) {
                                LastSelectedFilesAccessory.this.listModel_LastSelectedFiles.remove( selectedIx[i] );
                            }
                        }
                    }
                }
            });

        this.jScrollPane_LastSelectedFiles = new JScrollPane(jList_LastSelectedFiles);

        setPreferredSize( new Dimension(320, 240) );
    }

    private void initLayout()
    {
        this.setLayout( new BorderLayout() );

        super.add(this.jScrollPane_LastSelectedFiles,BorderLayout.CENTER);

        final JPanel jpanel = new JPanel();
        jpanel.add(this.jButton_Refresh);
        jpanel.add(this.jButton_RemoveFile);

        super.add(jpanel,BorderLayout.SOUTH);
    }

    /**
     * @return a valid JButton for refresh/rescan current directory,
     */
    public JButton getRefreshButton()
    {
        return this.resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_UPDATE );
    }

    /**
     * @return a valid JButton for "Remove File from list" operation
     */
    public JButton getRemoveButton()
    {
        return this.resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_REMOVE );
    }

    /**
     * Refresh display
     */
    public void refreshAccessory()
    {
        this.listModel_LastSelectedFiles.clear();

        for( final File f : this.configurator.getLastSelectedFiles() ) {
            this.listModel_LastSelectedFiles.addElement( f );
            }
    }

    @Override // ActionListener
    public void actionPerformed( final ActionEvent e )
    {
        if( JFileChooser.APPROVE_SELECTION.equals( e.getActionCommand() ) ) {
            this.configurator.addLastSelectedFile(
                    this.jFileChooser.getSelectedFile()
                    );
            //Refresh accessory for next use
            refreshAccessory();
        }
    }
    @Override // PropertyChangeListener
    @SuppressWarnings("squid:S1066") // Collapsible "if"
    public void propertyChange( final PropertyChangeEvent e )
    {
        if( "ancestor".equals( e.getPropertyName() )) {
            if( e.getNewValue() != null ) {
                // This was set to no null value,
                // when dialog is open
                // Warn: found no documentation for that,
                // tested on Win/JDK 6
                refreshAccessory();
            }
        }
    }
    @Override // TabbedAccessoryInterface
    public String getTabName()
    {
        return null;
    }
    @Override // TabbedAccessoryInterface
    public Icon getTabIcon()
    {
        return new ImageIcon(
            getClass().getResource( "lastselectfiles.gif" )
            );
    }
    @Override // TabbedAccessoryInterface
    public Component getComponent()
    {
        return this;
    }
    /**
     * Refresh display
     * <br>
     * To unregister this component use {@link #unregisterForce()}
     */
    @Override
    public void register()
    {
        //This in case of list of last selected files
        //had been updated by an other accessory
        refreshAccessory();
    }
    /**
     * Does nothing, since this Accessory should be active event
     * if not visible.
     * <br>
     * To unregister this component use {@link #unregisterForce()}
     */
    @Override // TabbedAccessoryInterface
    public void unregister()
    {
        // Not use
    }
    /**
     * Force unregister.
     */
    public void unregisterForce()
    {
        this.jFileChooser.removeActionListener( this );
        this.jFileChooser.removePropertyChangeListener( this );
    }

}

