package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * 
 *
 */
class FileAnalysisVisitor implements FileCollectorVisitor
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
}