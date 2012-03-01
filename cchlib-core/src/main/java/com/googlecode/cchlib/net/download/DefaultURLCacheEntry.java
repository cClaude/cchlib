package com.googlecode.cchlib.net.download;

import java.net.URL;
import java.util.Date;

/**
 *
 */
// Not public
final class DefaultURLCacheEntry implements URLCacheEntry
{
    private static final long serialVersionUID = 1L;
    private URL     url;
    private Date    date;
    private String  filename;

    public DefaultURLCacheEntry( final URL url, final String filename )
    {
        this( url, null, filename );
    }

    public DefaultURLCacheEntry( final URL url, final Date date, final String filename )
    {
        this.url = url;
        this.date = date == null ? new Date() : date;
        this.filename = filename;
    }

    @Override
    public URL getURL()
    {
        return url;
    }
//    public void setUrl( URL url )
//    {
//        this.url = url;
//    }
    @Override
    public Date getDate()
    {
        return date;
    }
//    public void setDate( Date date )
//    {
//        this.date = date;
//    }
    @Override
    public String getFilename()
    {
        return filename;
    }
//    public void setFilename( String filename )
//    {
//        this.filename = filename;
//    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result
                + ((filename == null) ? 0 : filename.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
            }
        if( obj == null ) {
            return false;
            }
        if( !(obj instanceof DefaultURLCacheEntry) ) {
            return false;
            }
        DefaultURLCacheEntry other = (DefaultURLCacheEntry)obj;
        if( date == null ) {
            if( other.date != null ) {
                return false;
                }
            }
        else if( !date.equals( other.date ) ) {
            return false;
            }
        if( filename == null ) {
            if( other.filename != null ) {
                return false;
                }
            }
        else if( !filename.equals( other.filename ) ) {
            return false;
            }
        if( url == null ) {
            if( other.url != null ) {
                return false;
                }
            }
        else if( !url.equals( other.url ) ) {
            return false;
            }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "EntryImpl [url=" + url + ", date=" + date + ", filename="
                + filename + "]";
    }
}
