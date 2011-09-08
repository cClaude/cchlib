package cx.ath.choisnet.tools.emptydirectories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;

import junit.framework.Assert;

/**
 *
 *
 */
public class EmptyDirectories
{
	private List<File> emptyDirFiles;
	private File[] rootFiles;

	/**
	 *
	 * @param rootFiles
	 */
	public EmptyDirectories( File...rootFiles )
	{
		this.emptyDirFiles  = new ArrayList<File>();
		this.rootFiles		= rootFiles;
	}

	public List<File> getFolders()
	{
		for( File f:this.rootFiles ) {
			doScan( f );
			}

		return emptyDirFiles;
	}

	private void doScan( File folder )
	{
		if( folder.isDirectory() ) {
			File[] content = folder.listFiles();

			if( content != null ) {
				if( content.length == 0 ) {
					// empty dir
					// add this.
					}
				else {
					for( File f : content ) {

					}
				}
				}
			else {
				// not a true folder : ignore
				}
			}
	}


	private static List<File> getEmptyDirectory( File folder )
	{
		Assert.assertTrue( folder.isDirectory() );

		File[] content = folder.listFiles();

		if( content != null ) {
			if( content.length == 0 ) {
				return Collections.singletonList( folder );
				}
			else {
				List<File> emptyDirectories = new ArrayList<File>();

				for( File f : content ) {
					if( f.isDirectory() ) {
						emptyDirectories.addAll( getEmptyDirectory( f ) );
						}
					}
				return emptyDirectories;
				}
			}
		else {
			return Collections.emptyList();
			}
	}
}
