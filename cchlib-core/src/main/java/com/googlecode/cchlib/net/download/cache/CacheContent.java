package com.googlecode.cchlib.net.download.cache;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

/** 
 * Workaround for generic warning when restore object 
 * using standard serialization
 * @since 4.1.7
 */
// Not public
final class CacheContent 
    implements Serializable, Iterable<Map.Entry<URL,URLDataCacheEntry>>
{
    private static final long serialVersionUID = 4L;
    private static final Logger logger = Logger.getLogger( CacheContent.class );
    private HashMap<URL,URLDataCacheEntry> dataCache = new HashMap<URL,URLDataCacheEntry>();
    private HashMap<String,URL>            hashcache = new HashMap<String,URL>();

    /**
     * 
     * @param url
     * @param data
     */
    public void put( final URL url, final URLDataCacheEntry data )
    {
        final String hash = data.getContentHashCode();
        
        if( ! hashcache.containsKey( hash ) ) {
            hashcache.put( hash, url );
            dataCache.remove( url );
            dataCache.put( url, data );
            }
        
        debug();
    }
    private void debug()
    {
        final int dataCacheSize = dataCache.size();
        final int hashCacheSize = hashcache.size();
        
        if( dataCacheSize != hashCacheSize ) {
            logger.error( "CacheContent : cache size error * data:" 
                + dataCacheSize 
                + " hash:"
                + hashCacheSize
                );
            }
    }
    public int size()
    {
        return dataCache.size();
    }
    public void clear()
    {
        dataCache.clear();
        hashcache.clear();
    }
    public URLDataCacheEntry get( final URL url )
    {
        return dataCache.get( url );
    }
    public URL get( final String hashcode )
    {
        return hashcache.get( hashcode );
    }
    @Override
    public Iterator<Entry<URL,URLDataCacheEntry>> iterator()
    {
        return dataCache.entrySet().iterator();
    }
//  public void put( final URLFullCacheEntry entry )
//  {
//      put( entry.getURL(), entry );
//  }
//  public Iterable<URLFullCacheEntry> getURLFullCacheEntries()
//  {
//    return new Iterable<URLFullCacheEntry>()
//    {
//          @Override
//          public Iterator<URLFullCacheEntry> iterator()
//          {
//              return new Iterator<URLFullCacheEntry>()
//              {
//                  final Iterator<Map.Entry<URL,URLDataCacheEntry>> parent = cc.entrySet().iterator();
//
//                  @Override
//                  public boolean hasNext()
//                  {
//                      return parent.hasNext();
//                  }
//                  @Override
//                  public URLFullCacheEntry next()
//                  {
//                      final Map.Entry<URL,URLDataCacheEntry> entry = parent.next();
//                      final URL               url   =  entry.getKey();
//                      final URLDataCacheEntry value = entry.getValue();
//
//                      return new URLFullCacheEntry()
//                      {
//                          @Override
//                          public URL getURL()
//                          {
//                              return url;
//                          }
//                          @Override
//                          public Date getDate()
//                          {
//                              return value.getDate();
//                          }
//                          @Override
//                          public String getRelativeFilename()
//                          {
//                              return value.getRelativeFilename();
//                          }
//                          @Override
//                          public String getContentHashCode()
//                          {
//                              return value.getContentHashCode();
//                          }
//                      };
//                  }
//                  @Override
//                  public void remove()
//                  {
//                      throw new UnsupportedOperationException();
//                  }
//              };
//          }
//        
//    };
//  }
    }