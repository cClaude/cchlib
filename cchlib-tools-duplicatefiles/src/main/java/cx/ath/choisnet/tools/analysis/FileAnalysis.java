package cx.ath.choisnet.tools.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import org.apache.log4j.Logger;

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
        this.writerFile 	= new BufferedWriter( new FileWriter( outputFileFiles ) );
        this.writerDirs		= new BufferedWriter( new FileWriter( outputFileDirs ) );
        this.writerIgnore	= new BufferedWriter( new FileWriter( outputFileIgnored ) );

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

	protected void stop() 
	{
        logger.info( "stop(): " + new Date( System.currentTimeMillis()) );
		
        if( this.fileCollector != null ) {
			this.fileCollector.cancel();
			
			try {
				Thread.sleep( 2 * 1000 );
				}
			catch(InterruptedException ignore) {}
			}
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
                	fa.stop();
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
