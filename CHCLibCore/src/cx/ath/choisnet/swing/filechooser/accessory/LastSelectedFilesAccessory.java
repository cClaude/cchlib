/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.filechooser.accessory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * TODO: NOT YET IMPLEMENTED !
 * 
 * 
 * @author Claude CHOISNET
 */
public class LastSelectedFilesAccessory 
    extends JPanel
        implements  TabbedAccessoryInterface,
                    ActionListener
{
    private static final long serialVersionUID = 1L;

    private JScrollPane         jScrollPane_LastSelectedFiles;
    private DefaultListModel    listModel_LastSelectedFiles;
    private JButton             jButton_Refresh = new JButton("Refresh");
    private JButton             jButton_RemoveFile = new JButton("Delete");
      
    private JFileChooser    jFileChooser;
    private Configurator    configurator;
    
    public LastSelectedFilesAccessory(
            JFileChooser    jFileChooser,
            Configurator    config
            )
    {
        this.jFileChooser = jFileChooser;
        this.configurator = config;
        
        listModel_LastSelectedFiles = new DefaultListModel();

        for(File f:config.getLastSelectedFiles()) {
            listModel_LastSelectedFiles.addElement( f );
        }

        initComponents();
        initLayout();
        // register(); - Should work event if not visible
        this.jFileChooser.addActionListener( this );
    }
    
    private void initComponents()
    {
        final JList jList_LastSelectedFiles = new JList(listModel_LastSelectedFiles);
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
        return new JButton(
                new ImageIcon(
                        getClass().getResource( "reload.gif" )
                        ) 
                );
    }
    
    /**
     * @return a valid JButton for "Remove File from list" operation
     */
    public JButton getRemoveButton()
    {
        return new JButton(
                new ImageIcon(
                        getClass().getResource( "bookmark-remove.gif" )
                        )
                );
    }
    
    @Override // ActionListener
    public void actionPerformed( ActionEvent e )
    {
        if( e.getActionCommand().equals( JFileChooser.APPROVE_SELECTION ) ) {
            configurator.addLastSelectedFile(
                    jFileChooser.getSelectedFile()
                    );
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
    @Override // TabbedAccessoryInterface
    public void register()
    {
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
    }
    
    /**
     * 
     * 
     * @author Claude CHOISNET
     */
    public interface Configurator extends Serializable
    {
        /**
         * @return collection of last selected File objects 
         */
        public Collection<File> getLastSelectedFiles();
        
        /**
         * @param file File to add to last selected files Collection
         * @return true if File has been added
         */
        public boolean addLastSelectedFile(File file);

        /**
         * @param file File to remove to last selected files Collection
         * @return true if File has been removed
         */
        public boolean removeLastSelectedFile(File file);
    }

}


