package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 *
 *
 */
class FileAnalysisVisitor implements FileCollectorVisitor
{
    //private static final Logger logger = Logger.getLogger( FileAnalysisVisitor.class );
    private final XLogger logger;
    private Writer outDir;
    private Writer outFile;
    private StringBuilder       sb      = new StringBuilder();
    private FileResultFormater  frf     = new FileResultFormater();

    public FileAnalysisVisitor(
        final Writer 	outDir,
        final Writer 	outFile,
        final XLogger 	logger
        )
    {
        this.outDir 	= outDir;
        this.outFile	= outFile;
        this.logger		= logger;
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
//        sb.setLength( 0 );
//        sb.append( "D|");
//        sb.append( ISO_FORMAT.format( new Date( directoryFile.lastModified() ) ) );
//        sb.append( "|||" );
//        sb.append( directoryFile );
//        sb.append( "\n" );

        try {
            //outDir.write( sb.toString() );
            outDir.write( frf.format( directoryFile, "D" ) );
            }
        catch( IOException e ) {
            throw new RuntimeException( "Error writing to output", e );
            }
    }
    @Override
    public void discoverFile( final File file )
    {
//        sb.setLength( 0 );
//        sb.append( "F|" );
//        sb.append( ISO_FORMAT.format( new Date( file.lastModified() ) ) );
//        sb.append( "|" );
//        sb.append( file.length() );
//        sb.append( "||" ); // space for MD5
//        sb.append( file.getPath() );
//        sb.append( "\n" );

        try {
            //outFile.write( sb.toString() );
            outFile.write( frf.format( file, "F" ) );
            }
        catch( IOException e ) {
            throw new RuntimeException( "Error writing to output", e );
            }
    }
}
