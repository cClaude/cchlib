package com.googlecode.cchlib.swing.batchrunner.verylazy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerApp;

/**
 *
 */
public abstract class VeryLazyBatchRunnerApp<TASK extends VeryLazyBatchTask>
    extends LazyBatchRunnerApp
{
    private static final transient Logger logger = Logger.getLogger( VeryLazyBatchRunnerApp.class );

    /**
     *
     */
    public VeryLazyBatchRunnerApp()
    {
        super();
    }

    /**
     * @param resourceBundle
     */
    public VeryLazyBatchRunnerApp( ResourceBundle resourceBundle )
    {
        super( resourceBundle );
    }

    /**
     *
     * @return
     */
    public abstract TASK buildTask();

    //
    // BEGIN: LazyBatchRunner
    //
    private TASK batchInstance;
    private long beginTimeMillis;

    @Override//LazyBatchRunner
    public void initializeBath()
    {
        //this.batchInstance = new DelNonAlphaChar( DelNonAlphaChar.REPLACEMENT_CHAR, 128 );
        this.batchInstance   = buildTask();
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
        this.batchInstance.runTask( inputStream, outputStream );
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
    // END: LazyBatchRunner
    //
}
