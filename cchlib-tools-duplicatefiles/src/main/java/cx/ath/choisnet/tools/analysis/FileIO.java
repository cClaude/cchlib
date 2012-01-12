package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public class FileIO
{
	private File baseFile;

	/**
	 * 
	 */
	public FileIO( final File baseFile ) 
	{
		this.baseFile = baseFile;
	}

	public File getBaseFile()
	{
		return this.baseFile;
	}
	
	public OutputStream createOutputStream()
	{
		return new OutputStream()
		{
			@Override
			public void write(int arg0) throws IOException 
			{
				// TODO Auto-generated method stub
			}
			
		};
	}
}
