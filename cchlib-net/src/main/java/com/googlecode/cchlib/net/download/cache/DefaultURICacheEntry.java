package com.googlecode.cchlib.net.download.cache;

import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Default implementation for {@link URIDataCacheEntry}
 */
public class DefaultURICacheEntry implements URIDataCacheEntry
{
    private final String           hashCode;
    private final Date             date;
    @Nullable private final String filename;

    /**
     * Create a {@link URIDataCacheEntry}
     *
     * @param date
     *            {@link Date} for this entry. If null use current date.
     * @param hashCode
     *            Hash code for this entry
     * @param filename
     *            Local file name for this entry
     */
    public DefaultURICacheEntry(
        @Nullable final Date   date,
        @Nonnull final  String hashCode,
        @Nullable final String filename
        )
    {
        if( hashCode == null ) {
            throw new IllegalArgumentException( "hashCode is null" );
        }

        this.date     = (date == null) ? new Date() : date;
        this.hashCode = hashCode;

        if( filename == null ) {
            this.filename = null;
        } else {
            if( filename.isEmpty() ) {
                this.filename = null;
            } else {
                this.filename = filename;
            }
        }
    }

    @Override
    public String getContentHashCode()
    {
        return this.hashCode;
    }

    @Override
    public Date getDate()
    {
        return this.date;
    }

    @Override
    @Nullable
    public String getRelativeFilename()
    {
        return this.filename;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;

        int result = 1;
        result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
        result = (prime * result)
                + ((this.filename == null) ? 0 : this.filename.hashCode());
        result = (prime * result)
                + ((this.hashCode == null) ? 0 : this.hashCode.hashCode());

        return result;
    }

    @Override
    public boolean equals( final Object obj )
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
        final DefaultURICacheEntry other = (DefaultURICacheEntry)obj;
        if( this.date == null ) {
            if( other.date != null ) {
                return false;
            }
        } else if( !this.date.equals( other.date ) ) {
            return false;
        }
        if( this.filename == null ) {
            if( other.filename != null ) {
                return false;
            }
        } else if( !this.filename.equals( other.filename ) ) {
            return false;
        }
        if( this.hashCode == null ) {
            if( other.hashCode != null ) {
                return false;
            }
        } else if( !this.hashCode.equals( other.hashCode ) ) {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "DefaultURICacheEntry [hashCode=" );
        builder.append( this.hashCode );
        builder.append( ", date=" );
        builder.append( this.date );
        builder.append( ", filename=" );
        builder.append( this.filename );
        builder.append( "]" );

        return builder.toString();
    }
}
