package cnamts.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.LazyBatchRunnerApp;

/**
 *
 * @author Claude
 */
public class B2TransformApp extends LazyBatchRunnerApp
{
    private static final transient Logger logger = Logger.getLogger( B2TransformApp.class );
    private B2TransformApp()
    {
    }

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        try {
            B2TransformApp instance = new B2TransformApp();

            URL iconURL = B2TransformApp.class.getResource( "cnam_32x32.png" );
            logger.info( "iconURL = " + iconURL );

            instance.start( iconURL );
            }
        catch( Exception e ) {
            DialogHelper.showMessageExceptionDialog( B2TransformApp.class.getName(), e );
            }
    }

    //
    // LazyBatchRunner
    //
    private DelNonAlphaChar batchInstance;
    private long beginTimeMillis;

    @Override//LazyBatchRunner
    public void initializeBath()
    {
        this.batchInstance = new DelNonAlphaChar( DelNonAlphaChar.REPLACEMENT_CHAR, 128 );
        this.beginTimeMillis = System.currentTimeMillis();

        logger.info( "start: " + this.beginTimeMillis );
    }

    @Override//LazyBatchRunner
    public File buildOuputFile( File sourceFile )
            throws BatchRunnerInterruptedException
    {
        final File destinationFolderFile = super.getOutputFolderFile();

        File outputFile = new File(
                destinationFolderFile,
                sourceFile.getName() + ".b2-128"
                );

        if( outputFile.exists() ) {
            String message = String.format(
                    "Le fichier %s existe déjà !\n"
                    + "Souhaitez-vous le remplacer ?",
                    outputFile
                    );
            String title = "Le fichier destination existe déjà.";
            String[] choices = {
                    "oui",
                    "Non, renommer automatiquement",
                    "Non, arrêter tous les traitements"
                    };
            int res = JOptionPane.showOptionDialog(
                    getTopLevelWindow(),
                    message,
                    title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, // icon
                    choices,
                    choices[1]
                    );

            switch( res ) {
                case JOptionPane.YES_OPTION :
                    break;

                case JOptionPane.NO_OPTION :
                    throw new UnsupportedOperationException();
                    //break;

                //case JOptionPane.CANCEL_OPTION :
                default : {
                    BatchRunnerInterruptedException e
                        = new BatchRunnerInterruptedException( "File already exist" );

                    e.setCustomObject( outputFile );

                    throw e;
                    }
                }
            }

        return outputFile;
    }

    @Override//LazyBatchRunner
    public void runTask( InputStream inputStream, OutputStream outputStream )
            throws IOException, BatchRunnerInterruptedException
    {
        this.batchInstance.delNonAlphaChar( inputStream, outputStream );
    }

    @Override//LazyBatchRunner
    public void finalizeBath( boolean isCancelled )
    {
        long endTimeMillis = System.currentTimeMillis();
        long millis = endTimeMillis - beginTimeMillis;
        String msg = String.format( "Conversion terminée en %s ms", millis );

        logger.info( "isCancelled = " + isCancelled );
        logger.info( "end: " + endTimeMillis );
        logger.info( "millis: " + millis );

        super.setCurrentMessage( msg );
    }

    //
    // LazyBatchRunnerLocaleResources
    //

    @Override//LazyBatchRunnerLocaleResources
    public String getTextFrameTitle()
    {
        return "Transformation B2 en fichier text 128 caractères par ligne";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextDoAction()
    {
        return "Convertir";
    }

}
