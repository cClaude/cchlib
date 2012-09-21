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
public class LastSelectedFilesAccessory
    extends JPanel
        implements  TabbedAccessoryInterface,
                    ActionListener,
                    PropertyChangeListener
{
    private static final long serialVersionUID = 1L;

    /** @serial */
    private JScrollPane         	jScrollPane_LastSelectedFiles;
    /** @serial */
    private DefaultListModel<File>	listModel_LastSelectedFiles;
    /** @serial */
    private JButton             jButton_Refresh = new JButton("Refresh");
    /** @serial */
    private JButton             jButton_RemoveFile = new JButton("Delete");

    /** @serial */
    private JFileChooser    jFileChooser;
    /** @serial */
    private LastSelectedFilesAccessoryConfigurator    configurator;
    /** @serial */
    private ResourcesUtils resourcesUtils;

    public LastSelectedFilesAccessory(
            JFileChooser    jFileChooser,
            LastSelectedFilesAccessoryConfigurator    config
            )
    {
        this.jFileChooser = jFileChooser;
        this.configurator = config;
        this.resourcesUtils = new ResourcesUtils();

        listModel_LastSelectedFiles = new DefaultListModel<File>();

        initComponents();
        initLayout();

        refreshAccessory();

        //register(); - Should work event if not visible
        this.jFileChooser.addActionListener( this );
        this.jFileChooser.addPropertyChangeListener( this );
    }

    private void initComponents()
    {
        final JList<File> jList_LastSelectedFiles = new JList<File>(listModel_LastSelectedFiles);
        jList_LastSelectedFiles.addMouseListener(
            new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 2) {
                        int     index = jList_LastSelectedFiles.locationToIndex(e.getPoint());
                        Object  o     = listModel_LastSelectedFiles.get( index );

                         if( o instanceof File ) {
                            jFileChooser.setSelectedFile(
                                    File.class.cast( o )
                                    );

                            if( configurator.getAutoApproveSelection() ) {
                                jFileChooser.approveSelection();
                            }
                        }
                    }
                }
            });

        jButton_Refresh = getRefreshButton();
        jButton_Refresh.addMouseListener(
            new MouseAdapter()
            {
                public void mousePressed(MouseEvent event)
                {
                    jFileChooser.rescanCurrentDirectory();
                }
            });

        jButton_RemoveFile = getRemoveButton();
        jButton_RemoveFile.addMouseListener(
            new MouseAdapter()
            {
                public void mousePressed(MouseEvent event)
                {
                    int[] selectedIx = jList_LastSelectedFiles.getSelectedIndices();

                    for( int i=selectedIx.length - 1; i>=0; i-- ) {
                        Object sel = jList_LastSelectedFiles.getModel().getElementAt(selectedIx[i]);

                        if( sel instanceof File) {
                            File f = File.class.cast( sel );

                            if( configurator.removeLastSelectedFile( f ) ) {
                                listModel_LastSelectedFiles.remove( selectedIx[i] );
                            }
                        }
                    }
                }
            });

        jScrollPane_LastSelectedFiles = new JScrollPane(jList_LastSelectedFiles);

        Dimension dim = new Dimension(320, 240);
//        setSize(dim.width, dim.height);
//        setMinimumSize(dim);
//        setMaximumSize(dim);
        setPreferredSize(dim);
    }

    private void initLayout()
    {
        this.setLayout( new BorderLayout() );

        super.add(jScrollPane_LastSelectedFiles,BorderLayout.CENTER);

        JPanel jpanel = new JPanel();
        jpanel.add(jButton_Refresh);
        jpanel.add(jButton_RemoveFile);

        super.add(jpanel,BorderLayout.SOUTH);
    }

    /**
     * @return a valid JButton for refresh/rescan current directory,
     */
    public JButton getRefreshButton()
    {
        return resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_UPDATE );
    }

    /**
     * @return a valid JButton for "Remove File from list" operation
     */
    public JButton getRemoveButton()
    {
        return resourcesUtils.getJButton( ResourcesUtils.ID.BOOKMARK_REMOVE );
    }

    /**
     * Refresh display
     */
    public void refreshAccessory()
    {
        listModel_LastSelectedFiles.clear();

        for( File f : configurator.getLastSelectedFiles() ) {
            listModel_LastSelectedFiles.addElement( f );
            }
    }

    @Override // ActionListener
    public void actionPerformed( ActionEvent e )
    {
        if( JFileChooser.APPROVE_SELECTION.equals( e.getActionCommand() ) ) {
            configurator.addLastSelectedFile(
                    jFileChooser.getSelectedFile()
                    );
            //Refresh accessory for next use
            refreshAccessory();
        }
    }
    @Override // PropertyChangeListener
    public void propertyChange( PropertyChangeEvent e )
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

