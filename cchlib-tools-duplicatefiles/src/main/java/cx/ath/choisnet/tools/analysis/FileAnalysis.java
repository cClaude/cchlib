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

    public void start()
    {
        logger.info( "Start: " + new Date( System.currentTimeMillis()) );

        FileCollector fc 	= new FileCollector(
                directoryFilter,
                fileFilter,
                File.listRoots()
                );
        FileAnalysisVisitor fce = new FileAnalysisVisitor( writerDirs, writerFile );

        fc.walk( fce );

        logger.info( "Done: " + new Date( System.currentTimeMillis()) );
    }

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
        File outputFile;
        File outputDirs;
        File outputIgnore;

        try {
            outputFile 		= File.createTempFile( "FileCollectorTst",".files");
            outputDirs 		= File.createTempFile( "FileCollectorTst",".dirs");
            outputIgnore	= File.createTempFile( "FileCollectorTst",".ignore");
            }
        catch( IOException e ) {
            logger.error( "Init error", e );
            return;
            }

        try {
            FileAnalysis fa = new FileAnalysis(
                    outputFile,
                    outputDirs,
                    outputIgnore
                    );

            fa.start();
            fa.close();
            }
        catch( IOException e ) {
            logger.error( "I/O error", e );
            }
        catch( Exception e ) {
            logger.error( "Error", e );
            }
    }

    static class FileAnalysisVisitor implements FileCollectorVisitor
    {
        private static final Logger logger = Logger.getLogger( FileAnalysisVisitor.class );
        private Writer outDir;
        private Writer outFile;
        private StringBuilder sb = new StringBuilder();

        public FileAnalysisVisitor(
            final Writer outDir,
            final Writer outFile
            )
        {
            this.outDir 	= outDir;
            this.outFile	= outFile;
        }
        @Override
        public void openRootDirectory( final File rootDirectoryFile )
        {
            sb.setLength( 0 );
            sb.append( "Exploring: " );
            sb.append( rootDirectoryFile );
            logger.info( sb.toString() );
        }
        @Override
        public void openDirectory( final File directoryFile )
        {
            sb.setLength( 0 );
            sb.append( "D||||" );
            sb.append( directoryFile );
            sb.append( "\n" );

            try {
                outDir.write( sb.toString() );
                }
            catch( IOException e ) {
                throw new RuntimeException( "Error writing to output", e );
                }
        }
        @Override
        public void discoverFile( final File file )
        {
            sb.setLength( 0 );
            sb.append( "F|" );
            sb.append( file.length() );
            sb.append( "|" );
            sb.append( file.lastModified() );
            sb.append( "||" ); // space for MD5
            sb.append( file.getPath() );
            sb.append( "\n" );

            try {
                outFile.write( sb.toString() );
                }
            catch( IOException e ) {
                throw new RuntimeException( "Error writing to output", e );
                }
        }
    };
}
