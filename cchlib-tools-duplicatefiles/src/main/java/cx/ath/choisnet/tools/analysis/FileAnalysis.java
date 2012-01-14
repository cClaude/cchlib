package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import org.apache.log4j.Logger;
import cx.ath.choisnet.tools.analysis.FileCollector.CancelState;

/**
 *
 */
public class FileAnalysis
{
    static final Logger logger = Logger.getLogger( FileAnalysis.class );
    private Writer writerFile;
    private Writer writerDirs;
    private Writer writerIgnore;
    private FileFilter directoryFilter;
    private FileFilter fileFilter;
    private FileCollector fileCollector;

    /**
     *
     * @param commonOutputDirFile
     * @throws IOException
     */
    public FileAnalysis(
        final File commonOutputDirFile
        ) throws IOException
    {
        this(
            new File( commonOutputDirFile, ".FileAnalysis" + ".files" ),
            new File( commonOutputDirFile, ".FileAnalysis" + ".dirs" ),
            new File( commonOutputDirFile, ".FileAnalysis" + ".ignore" )
            );
    }

    /**
     *
     * @param outputFileFiles
     * @param outputFileDirs
     * @param outputFileIgnored
     * @throws IOException
     */
    public FileAnalysis(
        final File outputFileFiles,
        final File outputFileDirs,
        final File outputFileIgnored
        ) throws IOException
    {
        //this.writerFile 	= new BufferedWriter( new FileWriter( outputFileFiles ) );
        FileRoller frFiles 	= new DefaultFileRoller( outputFileFiles );
        this.writerFile 	= new FileRollingWriter( frFiles, 10 * 1024 * 1024 );

        //this.writerDirs		= new BufferedWriter( new FileWriter( outputFileDirs ) );
        FileRoller frDirs	= new DefaultFileRoller( outputFileDirs );
        this.writerDirs		= new FileRollingWriter( frDirs, 1024 * 1024 );

        //this.writerIgnore	= new BufferedWriter( new FileWriter( outputFileIgnored ) );
        FileRoller frIgnore	= new DefaultFileRoller( outputFileIgnored );
        this.writerIgnore	= new FileRollingWriter( frIgnore, 256 * 1024 );

        this.directoryFilter = new DefaultDirectoryFilter( writerIgnore );
        this.fileFilter 	 = new FileFilter()
        {
            @Override
            public boolean accept( File file )
            {
                return true;
            }
        };
    }

    /**
     *
     */
    public void start()
    {
        logger.info( "Start: " + new Date( System.currentTimeMillis()) );

        this.fileCollector = new FileCollector(
                directoryFilter,
                fileFilter,
                File.listRoots()
                );
        FileAnalysisVisitor fce = new FileAnalysisVisitor( writerDirs, writerFile );

        this.fileCollector.walk( fce );

        logger.info( "Done: " + new Date( System.currentTimeMillis()) );
    }

    protected CancelState stop()
    {
        logger.info( "stop() recipe: " + new Date( System.currentTimeMillis()) );

        if( this.fileCollector != null ) {
            this.fileCollector.cancel();
            
            return this.fileCollector.getCancelState();
            }
        
        return null;
    }

    /**
     *
     */
    public void close()
    {
        if( writerIgnore != null ) {
            try {
                writerIgnore.close();
                }
            catch (IOException e) {
                logger.error( "Close fail", e );
                }
            }

        if( writerFile != null ) {
            try {
                writerFile.close();
                }
            catch (IOException e) {
                logger.error( "Close fail", e );
                }
            }

        if( writerDirs != null ) {
            try {
                writerDirs.close();
                }
            catch (IOException e) {
                logger.error( "Close fail", e );
                }
            }
    }

    public static void main( final String[] args )
    {
        File outputDirectory = new File( "." );
//        File outputFile;
//        File outputDirs;
//        File outputIgnore;
//
//        try {
//            outputFile 		= File.createTempFile( "FileCollectorTst",".files");
//            outputDirs 		= File.createTempFile( "FileCollectorTst",".dirs");
//            outputIgnore	= File.createTempFile( "FileCollectorTst",".ignore");
//            }
//        catch( IOException e ) {
//            logger.error( "Init error", e );
//            return;
//            }

        try {
//            FileAnalysis fa = new FileAnalysis(
//                    outputFile,
//                    outputDirs,
//                    outputIgnore
//                    );
            final FileAnalysis fa = new FileAnalysis( outputDirectory );

            fa.start();
            fa.close();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    CancelState cs = fa.stop();
                    fa.close();
                    logger.info( "ShutdownHook CancelState=" + cs );
                    
                    if( cs != null ) {
                        // TODO: store cs !!!
                        
                        try {
                            Thread.sleep( 2 * 1000 );
                            }
                        catch(InterruptedException ignore) {}
                            }

                    logger.info( "ShutdownHook done" + new Date( System.currentTimeMillis()) );
                    }
             });
            }
        catch( IOException e ) {
            logger.error( "I/O error", e );
            }
        catch( Exception e ) {
            logger.error( "Error", e );
            }
    }

}
