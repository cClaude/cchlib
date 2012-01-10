package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.io.BufferedWriter;
import java.io.File;
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
	private static final Logger logger = Logger.getLogger( FileAnalysis.class );

	public static void main( final String[] args )
	{
		logger.info( "Start: " + new Date( System.currentTimeMillis()) );
		
		try {
			File 	outputFile 	= File.createTempFile( "FileCollectorTst",".txt");
			Writer	writer		= new BufferedWriter( new FileWriter( outputFile ) );
			
		    FileCollector 		fc 	= new FileCollector( File.listRoots() );
		    FileAnalysisVisitor fce = new FileAnalysisVisitor( writer );

		    fc.walk( fce );	   
		    writer.close();
			} 
		catch( IOException e ) {
			logger.error( "Fail", e );
			}
		finally {
			logger.info( "Done: " + new Date( System.currentTimeMillis()) );
			}
	}
	
	static class FileAnalysisVisitor implements FileCollectorVisitor
    {
        private Writer out;
        private StringBuilder sb = new StringBuilder();
        
		public FileAnalysisVisitor( final Writer out )
		{
			this.out = out;
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
				out.write( sb.toString() );
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
				out.write( sb.toString() );
				} 
        	catch( IOException e ) {
				throw new RuntimeException( "Error writing to output", e );
        		}
        }
    };
}
