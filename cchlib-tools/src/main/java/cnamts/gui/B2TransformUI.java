package cnamts.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
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

public class B2TransformUI extends B2TransformWB
{
    private static final long serialVersionUID = 1L;
    private static final transient Logger logger = Logger.getLogger( B2TransformUI.class );

    private WaitingJFileChooserInitializer jFileChooserInitializer;
    private ActionListener myActionListener;

    @I18nString private String jFileChooserInitializerTitle     = "Waiting...";
    @I18nString private String jFileChooserInitializerMessage   = "Analyze disk structure";

    public B2TransformUI()
    {
        super();

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

                    switch( c ) {
                        case ACTIONCMD_SELECT_SOURCE :
                            new Thread( new Runnable()
                            {
                                JComponent jc = JComponent.class.cast( event.getSource() );
                                @Override
                                public void run()
                                {
                                    selectFile( jc );
                                }
                            }).start();
                            break;

                        case ACTIONCMD_CONVERT_FILE :
                            new Thread( new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    convert();
                                }
                            }).start();
                            break;
                    }
                }
            };
        }
        return myActionListener;
    }

    protected void selectFile( JComponent jComponent )
    {
        JFileChooser jfc = getJFileChooserInitializer().getJFileChooser();

        int returnVal = jfc.showOpenDialog( B2TransformUI.this );

        if( returnVal == JFileChooser.APPROVE_OPTION ) {
//            File[] files = jfc.getSelectedFiles();
//
//            for(File f:files) {
//                logger.info( "selected file:" + f );
//                //addEntry( f, false );
//                }
            File file = jfc.getSelectedFile();
            logger.info( "selected file:" + file );

            setSourceFile( file );
            }
    }

    protected void convert()
    {
        final File sourceFile = getSourceFile();
        final File destinationFile = new File( sourceFile.getPath() + ".128" );

        final DelNonAlphaChar instance = new DelNonAlphaChar( DelNonAlphaChar.REPLACEMENT_CHAR, 128 );

        logger.info( "Convert " + sourceFile + " to " + destinationFile );

        setCurrentState( "Conversion en cours" );
        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    instance.delNonAlphaChar(sourceFile, destinationFile);
                    setCurrentState( "Conversion terminée" );
                    logger.info( "Convert done" );
                    }
                catch( IOException e ) {
                    final String title = "Erreur de conversion";

                    logger.error( title, e );
                    setCurrentState( title );

                    DialogHelper.showMessageExceptionDialog( B2TransformUI.this, title, e );
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

                    jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
                    jfc.setMultiSelectionEnabled( true );
                    jfc.setAccessory( new TabbedAccessory()
                            .addTabbedAccessory( new BookmarksAccessory(
                                    jfc,
                                    new DefaultBookmarksAccessoryConfigurator() ) ) );
                }
            };

            jFileChooserInitializer = new WaitingJFileChooserInitializer(
                    configurator,
                    this,
                    jFileChooserInitializerTitle,
                    jFileChooserInitializerMessage
                    );
        }

        return jFileChooserInitializer;
    }

}
