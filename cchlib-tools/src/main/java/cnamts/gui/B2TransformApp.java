package cnamts.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.log4j.Logger;
import cnamts.DelNonAlphaChar;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.LazyBatchRunnerApp;

/**
 *
 * @author Claude
 *
 */
public class B2TransformApp extends LazyBatchRunnerApp
{
    private static final transient Logger logger = Logger.getLogger( B2TransformApp.class );
    private B2TransformApp()
    {
        super();
//        super( new NeverFailResourceBundle(
//            ResourceBundle.getBundle(
//                B2TransformApp.class.getPackage().getName()
//                    + ".DefaultLazyBatchRunnerAppResourceBundle"
//                ) )
//            );
    }

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        B2TransformApp instance = new B2TransformApp();

        URL iconURL = B2TransformApp.class.getResource( "cnam_32x32.png" );
        logger.info( "iconURL = " + iconURL );

        instance.start( iconURL );
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
    public File buildDestinationFile( File sourceFile )
    {
        final File destinationFolderFile = super.getDestinationFolderFile();

        return new File(
                destinationFolderFile,
                sourceFile.getName() + ".128"
                );
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
        return "Convert";
    }

/*
    //
    // LazyBatchRunnerLocaleResources
    //

    @Override//LazyBatchRunnerLocaleResources
    public String getTextAddSourceFile()
    {
        return "Ajouter des fichiers source";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextSetDestinationFolder()
    {
        return "Dossier de destination";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextClearSourceFileList()
    {
        return "Effacer la liste";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextDoAction()
    {
        return "Convert";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextJFileChooserInitializerTitle()
    {
        return "Patientez...";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextJFileChooserInitializerMessage()
    {
        return "Analyze de vos disques";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextNoSourceFile()
    {
        return  "<<< Pas de fichiers source >>>";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextNoDestinationFolder()
    {
        return "<<< Pas de dossier destination >>>";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextWorkingIn()
    {
        return "Traitement de : %s";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextEndOfBatch()
    {
        return "Traitement terminée";
    }
    @Override//LazyBatchRunnerLocaleResources
    public String getTextFrameTitle()
    {
        return "Transformation B2 en fichier text 128 caractères par ligne";
    }
*/
}
