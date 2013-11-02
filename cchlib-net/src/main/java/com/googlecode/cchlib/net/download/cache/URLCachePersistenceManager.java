package com.googlecode.cchlib.net.download.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *
 * @since 4.1.7
 */
//NOT public
interface URLCachePersistenceManager 
{
	/**
	 * 
	 * @param cacheFile
	 * @param cache
	 * @throws IOException 
	 */
	public void store( File cacheFile, CacheContent cache )
        throws IOException;

	/**
	 * 
	 * @param backupFile
	 * @param tmpCache
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws PersistenceFileBadVersionException 
	 */
    public void load( File cacheFile, CacheContent cache )
        throws FileNotFoundException, IOException, PersistenceFileBadVersionException;

}
