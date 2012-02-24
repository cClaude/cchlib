package com.googlecode.cchlib.swing.batchrunner;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;
import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.WaitingJFileChooserInitializer;
import com.googlecode.cchlib.swing.filechooser.accessory.BookmarksAccessory;
import com.googlecode.cchlib.swing.filechooser.accessory.DefaultBookmarksAccessoryConfigurator;
import com.googlecode.cchlib.swing.filechooser.accessory.TabbedAccessory;

public class BatchRunnerPanel extends BatchRunnerPanelWB
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( BatchRunnerPanel.class );

    private WaitingJFileChooserInitializer jFileChooserInitializer;
    private ActionListener myActionListener;

    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";

    public BatchRunnerPanel(
            final String txtAddSourceFile, // "Ajouter des fichiers source"
            final String txtSetDestinationFolder, // "Dossier de destination"
            final String txtClearSourceFileList, //"Effacer la liste"
            final String txtDoActionButton // "Convert"
            )
    {
        super( txtAddSourceFile, txtSetDestinationFolder, txtClearSourceFileList, txtDoActionButton );

        // Init JFileChooser
        getJFileChooserInitializer();
    }

    @Override
    protected ActionListener getActionListener()
    {
        if( myActionListener == null ) {
            myActionListener = new ActionListener()
            {
                @Override
                public void actionPerformed( final ActionEvent event )
                {
                    final String c = event.getActionCommand();

                    new Thread( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            switch( c ) {
                                case ACTIONCMD_SELECT_SOURCE :
                                    selectFiles();
                                    break;

                                case ACTIONCMD_DO_ACTION :
                                    convert();
                                    break;

                                case ACTIONCMD_SELECT_DESTINATION_FOLDER:
                                    selectDestinationFolder();
                                    break;

                                default : // t
                                    logger.warn( "Unknown event action command: " + c );
                                    logger.warn( "Event : " + event );
                                    break;
                                }
                        }
                    }).start();
                }
            };
            }
        return myActionListener;
    }

    protected void selectFiles()
    {
        JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.setMultiSelectionEnabled( true );

        if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
            File[] files = jfc.getSelectedFiles();

            for( File f:files ) {
                logger.info( "selected file:" + f );
                this.addSourceFile( f );
                }
            }
    }

    protected void selectDestinationFolder()
    {
        JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        jfc.setMultiSelectionEnabled( false );

        if( jfc.showOpenDialog( getTopLevelAncestor() ) == JFileChooser.APPROVE_OPTION ) {
            File file = jfc.getSelectedFile();

            logger.info( "selected folder:" + file );
            this.setDestinationFolderFile( file );
            }
    }

    protected void convert()
    {
        if( this.getSourceFilesCount() == 0 ) {
            // TODO: display alert
            setCurrentState( "<<< Pas de fichiers source >>>" );
            return;
            }
        final File destinationFolderFile = this.getDestinationFolderFile();
        if( destinationFolderFile == null ) {
            // TODO: display alert
            setCurrentState( "<<< Pas de dossier destination >>>" );
            return;
            }

        final DelNonAlphaChar instance = new DelNonAlphaChar( DelNonAlphaChar.REPLACEMENT_CHAR, 128 );
        final Enumeration<File> enumFile = getSourceFileElements();

        setCurrentState( "Conversion en cours" );
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                while( enumFile.hasMoreElements() ) {
                    final File sourceFile = enumFile.nextElement();

                    logger.info( "Convert " + sourceFile );
                    setCurrentState( "Conversion de : " + sourceFile.getPath() );

                    File destinationFile = new File(
                            destinationFolderFile,
                            sourceFile.getName() + ".128"
                            );

                    try {
                        instance.delNonAlphaChar(sourceFile, destinationFile);
                        setCurrentState( "Conversion terminée" );
                        logger.info( "Convert done" );
                        }
                    catch( IOException e ) {
                        final String title = "Erreur de conversion";

                        logger.error( title, e );
                        setCurrentState( title );

                        Container topContainer = getTopLevelAncestor();

                        DialogHelper.showMessageExceptionDialog(
                            Window.class.cast( topContainer ),
                            title,
                            e
                            );
                        }
                    }
            }
        }).start();
    }

    public JFileChooserInitializer getJFileChooserInitializer()
    {
        if( jFileChooserInitializer == null ) {
            final JFileChooserInitializer.DefaultConfigurator configurator = new JFileChooserInitializer.DefaultConfigurator()
            {
                private static final long serialVersionUID = 1L;

                public void perfomeConfig( JFileChooser jfc )
                {
                    super.perfomeConfig( jfc );
                    jfc.setAccessory( new TabbedAccessory()
                            .addTabbedAccessory( new BookmarksAccessory(
                                    jfc,
                                    new DefaultBookmarksAccessoryConfigurator() ) ) );
                }
            };

            Container topContainer = getTopLevelAncestor();
            jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    Window.class.cast( topContainer ),
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );
        }

        return jFileChooserInitializer;
    }
}
