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
        implements VeryLazyBatchRunnerLocaleResources
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
     * Build TASK
     *
     * @return a reusable task for each file
     */
    public abstract TASK buildTask();

    /**
     *
     */
    public abstract File buildBasicOuputFile(
            final File destinationFolderFile,
            final File sourceFile
            );

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
    public File buildOuputFile( final File sourceFile )
            throws BatchRunnerInterruptedException
    {
        final File  destinationFolderFile   = super.getOutputFolderFile();
        File        outputFile              = buildBasicOuputFile( destinationFolderFile, sourceFile );

        if( outputFile.exists() ) {
            String message = String.format(
                    getTextFileExistShouldReplaceIt_FMT(),
                    outputFile
                    );
            String   title   = getTextFileExistTitle();
            String[] choices = getTextFileExistChoices();
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
                    outputFile = buildNotUsedOuputFile( outputFile );
                    break;

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

    /**
     * Returns a non existing {@link File} based on giving sourceFile
     * @param sourceFile {@link File} to use to build new {@link File}
     * @return a non existing {@link File} based on giving sourceFile
     */
    public File buildNotUsedOuputFile(
           final File sourceFile
           )
   {
       File     dir         = sourceFile.getParentFile();
       String   filename    = sourceFile.getName();

       for( int i = 1;; i++ ) {
           File newFile = new File( dir, filename + "(" + i + ")" );

           if( ! newFile.exists() ) {
               return  newFile;
               }
           }
   }

    @Override//LazyBatchRunner
    public void runTask(
        final InputStream   inputStream,
        final OutputStream  outputStream
        )
        throws IOException, BatchRunnerInterruptedException
    {
        this.batchInstance.runTask( inputStream, outputStream );
    }

    @Override//LazyBatchRunner
    public void finalizeBath( final boolean isCancelled )
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

    //
    // BEGIN: VeryLazyBatchRunnerLocaleResources
    //

    @Override//VeryLazyBatchRunnerLocaleResources
    public String getTextFileExistShouldReplaceIt_FMT()
    {
        return resourceBundle.getString( "VeryLazyBatchRunnerLocaleResources.TextFileExistShouldReplaceIt_FMT" );
    }

    @Override//VeryLazyBatchRunnerLocaleResources
    public String getTextFileExistTitle()
    {
        return resourceBundle.getString( "VeryLazyBatchRunnerLocaleResources.TextFileExistTitle" );
    }

    @Override//VeryLazyBatchRunnerLocaleResources
    public String[] getTextFileExistChoices()
    {
        final String    keyPrefix = "VeryLazyBatchRunnerLocaleResources.TextFileExistChoices.";
        final String[]  choices = {
            resourceBundle.getString( keyPrefix + "YesReplace" ),
            resourceBundle.getString( keyPrefix + "NoRename" ),
            resourceBundle.getString( keyPrefix + "NoCancel" ),
            };

        return choices;
    }

    //
    // END: VeryLazyBatchRunnerLocaleResources
    //
}
