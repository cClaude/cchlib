package com.googlecode.cchlib.net.download;

import java.util.Date;

/**
 *
 */
// Not public
final class DefaultURLCacheEntry implements URLDataCacheEntry //URLCacheEntry
{
    //private static final long serialVersionUID = 1L;
    private String  hashCode;
    //private URL     url;
    private Date    date;
    private String  filename;

    public DefaultURLCacheEntry( final String hashCode/*, final URL url*/, final String filename )
    {
        this( hashCode, /*url,*/ null, filename );
    }

    public DefaultURLCacheEntry( final String hashCode/*, final URL url*/, final Date date, final String filename )
    {
        this.hashCode = hashCode;
        //this.url = url;
        this.date = date == null ? new Date() : date;
        this.filename = filename;
    }

    @Override
    public String getContentHashCode()
    {
        return hashCode;
    }

    @Override
    public Date getDate()
    {
        return date;
    }

    @Override
    public String getRelativeFilename()
    {
        return filename;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result
                + ((filename == null) ? 0 : filename.hashCode());
        result = prime * result
                + ((hashCode == null) ? 0 : hashCode.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        DefaultURLCacheEntry other = (DefaultURLCacheEntry)obj;
        if( date == null ) {
            if( other.date != null ) {
                return false;
            }
        } else if( !date.equals( other.date ) ) {
            return false;
        }
        if( filename == null ) {
            if( other.filename != null ) {
                return false;
            }
        } else if( !filename.equals( other.filename ) ) {
            return false;
        }
        if( hashCode == null ) {
            if( other.hashCode != null ) {
                return false;
            }
        } else if( !hashCode.equals( other.hashCode ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DefaultURLCacheEntry [hashCode=" + hashCode + ", date=" + date
                + ", filename=" + filename + "]";
    }


}
