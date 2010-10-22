package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessory;
import cx.ath.choisnet.swing.filechooser.accessory.BookmarksAccessoryDefaultConfigurator;
import cx.ath.choisnet.swing.filechooser.accessory.TabbedAccessory;
import cx.ath.choisnet.swing.helpers.LookAndFeelHelpers;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 *
 * @author Claude CHOISNET
 */
final public class DuplicateFilesFrame
    extends DuplicateFilesFrameVS4E
        implements MyToolKit
{
    private static final long        serialVersionUID                         = 1L;
    private static final Logger      slogger                                  = Logger.getLogger( DuplicateFilesFrame.class );
    /* @serial */
    private JFileChooserInitializer  jFileChooserInitializer;

    private HashMapSet<String,KeyFileState> duplicateFiles = new HashMapSet<String,KeyFileState>();

    private int                      state;
    private final static int         STATE_SELECT_DIRS                        = 0;
    private final static int         STATE_SEARCH_CONFIG                      = 1;
    private final static int         STATE_SEARCHING                          = 2;
    private final static int         STATE_RESULTS                            = 3;
    private final static int         STATE_CONFIRM                            = 4;
    
    private AutoI18n autoI18n = I18nBundle.getAutoI18n();
    @I18nString private String txtContinue = "Continue";
    @I18nString private String txtRestart = "Restart";
    @I18nString private String txtRemove = "Remove (does not delete yet)";
    @I18nString private String txtDeleteNow = "Delete now";
    @I18nString private String txtBack = "Back";
    
    public DuplicateFilesFrame()
    {
        super();

        initFixComponents();

        // Apply i18n !
        performeI18n(autoI18n);

        slogger.info( "DuplicateFilesFrame() done." );
    }

    public void performeI18n(AutoI18n autoI18n)
    {
        autoI18n.performeI18n(this,this.getClass());
        autoI18n.performeI18n(jPanel0SelectFolders,jPanel0SelectFolders.getClass());
        autoI18n.performeI18n(jPanel1Config,jPanel1Config.getClass());
        autoI18n.performeI18n(jPanel2Searching,jPanel2Searching.getClass());
        autoI18n.performeI18n(jPanel3Result,jPanel3Result.getClass());
        autoI18n.performeI18n(jPanel4Confirm,jPanel4Confirm.getClass());
    }

    private void initFixComponents()
    {
        setIconImage(
            Toolkit.getDefaultToolkit().getImage(
                getClass().getResource( "icon.png" )
                )
            );
        this.state = STATE_SELECT_DIRS;

        jTabbedPaneMain.setEnabled( false );

        jPanel0SelectFolders.initFixComponents( this );
        jPanel2Searching.initFixComponents();
        jPanel3Result.initFixComponents( duplicateFiles, this );

        updateDisplayAccordState();

        // build menu (VS4E does not support Box!)
        jMenuBarMain.add( Box.createHorizontalGlue() );
        jMenuBarMain.add( getJMenuLookAndFeel() );

        // initDynComponents
        LookAndFeelHelpers.buildLookAndFeelMenu( this, jMenuLookAndFeel );
    }

    protected void updateDisplayAccordState()
    {
        slogger.info( "updateDisplayAccordState: " + state );

        jTabbedPaneMain.setSelectedIndex( state );
        jButtonNextStep.setText( txtContinue );
        jButtonRestart.setText( txtRestart );

        if( state == STATE_SELECT_DIRS ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( true );
        }
        else if( state == STATE_SEARCH_CONFIG ) {
            jButtonRestart.setEnabled( true );
            jButtonNextStep.setEnabled( true );
        }
        else if( state == STATE_SEARCHING ) {
            jButtonRestart.setEnabled( false );
            jButtonNextStep.setEnabled( false );
            jMenuLookAndFeel.setEnabled( false );

            try {
                jPanel2Searching.prepareScan(
                        new MessageDigestFile( "MD5" ),
                        jPanel1Config.IsIgnoreEmptyFiles()
                        );
            }
            catch( NoSuchAlgorithmException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    jPanel2Searching.doScan(
                            jPanel0SelectFolders,
                            jPanel1Config.getDirectoriesFileFilterBuilder(),
                            jPanel1Config.getFilesFileFilterBuilder(),
                            duplicateFiles
                            );

                    jButtonNextStep.setEnabled( true );
                    jMenuLookAndFeel.setEnabled( true );
                    jButtonRestart.setEnabled( true );
                }
            } ).start();
        }
        else if( state == STATE_RESULTS ) {
            jButtonRestart.setEnabled( true );
            jButtonNextStep.setText( txtRemove );

            jPanel3Result.initDisplay();
        }
        else if( state == STATE_CONFIRM ) {
            jButtonRestart.setEnabled( true );
            jButtonNextStep.setText( txtDeleteNow  );
            jButtonRestart.setText( txtBack  );

            jPanel4Confirm.initComponents( duplicateFiles );
            //TODO
        }
    }


    private JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer(
                new JFileChooserInitializer.DefaultConfigurator() {
                    private static final long serialVersionUID = 1L;

                    public void perfomeConfig( JFileChooser jfc )
                    {
                        super.perfomeConfig( jfc );

                        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                        jfc.setAccessory( new TabbedAccessory()
                                .addTabbedAccessory( new BookmarksAccessory(
                                        jfc,
                                        new BookmarksAccessoryDefaultConfigurator() ) ) );
                    }
                } );
        }
        return jFileChooserInitializer;
    }

    @Override // MyToolKit
    public JFileChooser getJFileChooser()
    {
        return getJFileChooserInitializer().getJFileChooser();
    }

    @Override // MyToolKit
    public void beep()
    {
        Toolkit.getDefaultToolkit().beep();
    }
    
    @Override // MyToolKit
    public void openDesktop( File file )
    {
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        try {
            slogger.info( "trying to open: " + file );
            desktop.open( file );
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        slogger.info( "starting..." );

        try { 
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                    ); 
            } 
        catch(Exception e ) {
            slogger.warn( e );
            } 

        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                DuplicateFilesFrame frame = new DuplicateFilesFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( "Duplicate Files Manager (ALPHA)" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
                frame.getJFileChooserInitializer();
            }
        } );
    }

    @Override
    protected void jButtonRestartMouseMousePressed( MouseEvent event )
    {
        if( jButtonRestart.isEnabled() ) {

            if( state == STATE_CONFIRM ) {
                state = STATE_RESULTS;
            }
            else {
                state = STATE_SELECT_DIRS;
                 jPanel2Searching.clear();
            }

            updateDisplayAccordState();
        }
    }

    @Override
    protected void jButtonNextStepMouseMousePressed( MouseEvent event )
    {
        slogger.info( "Next: " + state );

        if( state == STATE_SELECT_DIRS ) {
            if( jPanel0SelectFolders.directoriesSize() > 0 ) {
                state = STATE_SEARCH_CONFIG;
            }
            else {
                beep();
            }
        }
        else if( state == STATE_SEARCH_CONFIG ) {
            state = STATE_SEARCHING;
        }
        else if( state == STATE_SEARCHING ) {
            state = STATE_RESULTS;
        }
        else if( state == STATE_RESULTS ) {
            state = STATE_CONFIRM;
        }
        else if( state == STATE_CONFIRM ) {
            doDelete();
            state = STATE_RESULTS;
        }

        updateDisplayAccordState();
    }

    @Override
    protected void jButtonExtraMouseMousePressed(MouseEvent event)
    {
        //TODO
        throw new UnsupportedOperationException();
    }

    private void doDelete()
    {
        Iterator<KeyFileState> iter = duplicateFiles.iterator();
        int                     deleteCount = 0;
        
        while( iter.hasNext() ) {
            KeyFileState f = iter.next();
            
            if( f.isSelectedToDelete() ) {
                slogger.info("Delete: " + f);
                iter.remove();
                deleteCount++;
            }
        }

        int keepCount = duplicateFiles.valuesSize();

        slogger.info( "deleteCount= " + deleteCount  );
        slogger.info( "keepCount= " + keepCount );

        duplicateFiles.purge(2);

        int newDupCount = duplicateFiles.valuesSize();

        slogger.info( "newDupCount= " + newDupCount );
    }
}
