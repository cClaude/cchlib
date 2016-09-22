package com.googlecode.cchlib.net.download.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *
 * @since 4.1.7
 */
//NOT public
interface URICachePersistenceManager
{
    void store( File cacheFile, CacheContent cache )
        throws IOException;

    void load( File cacheFile, CacheContent cache )
        throws FileNotFoundException, IOException, PersistenceFileBadVersionException;
}
