package cx.ath.choisnet.tools.analysis;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 *
 */
public class FileRollingOutputStream extends OutputStream
{
	private final static Logger logger = Logger.getLogger( FileRollingOutputStream.class );

	private final File 			baseFile;
	private final List<File>	fileList	= new ArrayList<File>();;
	private final int 			maxLength;

	private int 			currentFileIndex;
	private File 			currentFile;
	private OutputStream 	currentOutputStream;
	private int 			currentLength;
	
	private long 			prevLength;

	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public FileRollingOutputStream(
			final File 	baseFile,
			final int	maxLength
			)
		throws FileNotFoundException
	{
		this.baseFile 	= baseFile;
		this.maxLength 	= maxLength;
		
		this.currentFileIndex 	= 0;
		this.prevLength			= 0;
		
		openCurrentOutputStream();
	}

	private void openCurrentOutputStream() throws FileNotFoundException
	{
		this.currentFile = new File( baseFile.getAbsolutePath() + "." + this.currentFileIndex );
		this.currentFileIndex++;
		this.currentOutputStream = new BufferedOutputStream( 
				new FileOutputStream( this.currentFile )
				);
		
		logger.info( "Open new file: " + this.currentFile );
	}
	
	private void closeCurrentOutputStream() throws IOException
	{
		this.currentOutputStream.flush();
		this.currentOutputStream.close();
		
		logger.info( "Close file: " + this.currentFile.length() );
		
		this.fileList.add( this.currentFile );
		
		this.prevLength += this.currentLength;
		this.currentFile	= null;
		this.currentLength 	= 0; 
	}

	private void checkIfNeedToChangeFile( int len ) 
		throws IOException
	{
		this.currentLength += len;
		
		checkIfNeedToChangeFile();
	}

	private void checkIfNeedToChangeFile() throws IOException
	{
		if( this.currentLength > this.maxLength ) {
			closeCurrentOutputStream();
			openCurrentOutputStream();
			}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException
	{
		currentOutputStream.flush();
		currentOutputStream.close();
		
		closeCurrentOutputStream();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() throws IOException
	{
		currentOutputStream.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write( byte[] b, int off, int len ) throws IOException
	{
		currentOutputStream.write( b, off, len );
		
		checkIfNeedToChangeFile( len );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write( byte[] b ) throws IOException
	{
		currentOutputStream.write( b );
		
		checkIfNeedToChangeFile( b.length );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write( int b ) throws IOException 
	{
		currentOutputStream.write( b );
		
		checkIfNeedToChangeFile( 1 );
	}

	/**
	 * Returns length of all files create by this stream
	 * @return length of all files create by this stream
	 */
	public long length()
	{
		return this.currentLength + this.prevLength;
	}
	
	/**
	 * Returns List of all files create by this stream
	 * @return List of all files create by this stream
	 */
	public List<File> getFileList()
	{
		return Collections.unmodifiableList( this.fileList );
	}
	
	/**
	 * 
	 * @return
	 */
	public File currentFile()
	{
		return this.currentFile;
	}
	
	/**
	 * 
	 * @return
	 */
	public int currentLength()
	{
		return this.currentLength;
	}
}
