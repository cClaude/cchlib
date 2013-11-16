package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import cx.ath.choisnet.tools.analysis.view.XLogger;

/**
 *
 *
 */
class FileAnalysisVisitor implements FileCollectorVisitor
{
    private final XLogger logger;
    private Writer outDir;
    private Writer outFile;
    private StringBuilder       sb      = new StringBuilder();
    private FileResultFormater  frf     = new FileResultFormater();

    public FileAnalysisVisitor(
        final Writer     outDir,
        final Writer     outFile,
        final XLogger    logger
        )
    {
        this.outDir     = outDir;
        this.outFile    = outFile;
        this.logger     = logger;
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
        try {
            outDir.write( frf.format( directoryFile, "D" ) );
            }
        catch( IOException e ) {
            throw new RuntimeException( "Error writing to output", e );
            }
    }
    
    @Override
    public void discoverFile( final File file )
    {
        try {
            outFile.write( frf.format( file, "F" ) );
            }
        catch( IOException e ) {
            throw new RuntimeException( "Error writing to output", e );
            }
    }
}
