package com.googlecode.cchlib.swing.batchrunner.verylazy;

import java.io.File;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerApp;
import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerCustomJPanelFactory;

/**
 * TODOC
 *
 * @since 1.4.7
 */
public abstract class VeryLazyBatchRunnerApp<TASK extends VeryLazyBatchTask>
    extends LazyBatchRunnerApp
        implements  VeryLazyBatchRunnerLocaleResources,
                    VeryLazyBatchRunner
{
    private static final Logger logger = Logger.getLogger( VeryLazyBatchRunnerApp.class );

    /**
     * Create a VeryLazyBatchRunnerApp, using default ResourceBundle
     * and no custom panel.
     */
    public VeryLazyBatchRunnerApp()
    {
        super( null, null );
     }

    /**
     * Create a VeryLazyBatchRunnerApp, using giving ResourceBundle
     * and giving custom panel.
     *
     * @param customJPanelFactory Custom JPanel to add on UI,
     *        could be null for no custom panel.
     * @param resourceBundle Custom {@link ResourceBundle} to use for
     *        localization, could be null to use internal {@link ResourceBundle}.
     */
    public VeryLazyBatchRunnerApp(
        final LazyBatchRunnerCustomJPanelFactory    customJPanelFactory,
        final ResourceBundle                        resourceBundle
        )
    {
        super( customJPanelFactory, resourceBundle );
    }

    /**
     * Build TASK
     *
     * @return a reusable task for each file
     */
    public abstract TASK buildTask();

    /**
     * Implementation must build a valid destination file for
     * giving sourceFile, output File must be store under
     * output folder select by user
     * (see {@link LazyBatchRunnerApp#getOutputFolderFile()}).
     *
     * @param destinationFolderFile Destination directory {@link File} object
     * @param sourceFile Source {@link File} object
     * @return output {@link File} object for giving sourceFile
     */
    public abstract File buildBasicOuputFile(
            final File destinationFolderFile,
            final File sourceFile
            );

    //
    // BEGIN: LazyBatchRunner
    //
    @SuppressWarnings("unused")
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

        return buildOuputFile(
            sourceFile,
            destinationFolderFile,
            buildBasicOuputFile( destinationFolderFile, sourceFile ),
            this,
            this
            );
    }

    /**
     * Implementation must build a valid destination file for
     * giving sourceFile, output File must be store under
     * output folder select by user
     *
     * @param sourceFile Source {@link File}
     * @param destinationFolderFile Destination directory {@link File} object
     * @param outputFileFirstTry Expected destination {@link File} object, could
     *        be modify by user if already exist.
     * @param local Reference to a {@link VeryLazyBatchRunnerLocaleResources} object
     * @param tools Reference to a {@link VeryLazyBatchRunner} object
     * @return output {@link File} object for giving sourceFile
     * @throws BatchRunnerInterruptedException if output {@link File}
     *         can not be created
     */
    public static File buildOuputFile(
        final File  sourceFile,
        final File  destinationFolderFile,
        final File  outputFileFirstTry,
        final VeryLazyBatchRunnerLocaleResources local,
        final VeryLazyBatchRunner                tools
        )
            throws BatchRunnerInterruptedException
    {
        //final File  destinationFolderFile   = super.getOutputFolderFile();
        File        outputFile              = outputFileFirstTry;//buildBasicOuputFile( destinationFolderFile, sourceFile );

        if( outputFile.exists() ) {
            String message = String.format(
                    local.getTextFileExistShouldReplaceIt_FMT(),
                    outputFile
                    );
            String   title   = local.getTextFileExistTitle();
            String[] choices = local.getTextFileExistChoices();
            int res = JOptionPane.showOptionDialog(
                    tools.getTopLevelWindow(),
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
                    outputFile = tools.buildNotUsedOuputFile( outputFile );
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
    @Override//VeryLazyBatchRunner
    public File buildNotUsedOuputFile(
           final File sourceFile
           )
    {
        return buildNotUsedOuputFile( sourceFile, true );
    }

    /**
     * Returns a non existing {@link File} based on giving sourceFile
     *
     * @param sourceFile {@link File} to use to build new {@link File}
     * @param keepExtension if extension should be preserve
     * @return a non existing {@link File} based on giving sourceFile
     */
    public static File buildNotUsedOuputFile(
        final File      sourceFile,
        final boolean   keepExtension
        )
    {
        final File   dir         = sourceFile.getParentFile();
        final String rawFilename = sourceFile.getName();
        final String filename;
        final String extension;
        final String format;

        if( keepExtension ) {
            format = "%s(%d)%s";
            int p = rawFilename.lastIndexOf( '.' );
            filename  = rawFilename.substring( 0, p );
            extension = rawFilename.substring( p );
            }
        else {
            format = "%s(%d)";
            filename = rawFilename;
            extension = null;
            }

        for( int i = 1;; i++ ) {
            File newFile = new File(
                        dir,
                        //filename + "(" + i + ")"
                        String.format(format, filename, i, extension )
                        );

            if( ! newFile.exists() ) {
                return  newFile;
                }
            }
    }

//    @Override//LazyBatchRunner
//    public void runTask(
//        final InputStream   inputStream,
//        final OutputStream  outputStream
//        )
//        throws IOException, BatchRunnerInterruptedException
//    {
//        this.batchInstance.runTask( inputStream, outputStream );
//    }

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
