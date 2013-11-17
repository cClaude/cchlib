package com.googlecode.cchlib.net.download.cache;

import java.util.Date;

public class DefaultURICacheEntry implements URIDataCacheEntry
{
    private String  hashCode;
    private Date    date;
    private String  filename;

    /**
     *
     * @param date
     * @param hashCode
     * @param filename
     */
    public DefaultURICacheEntry(
        final Date   date,
        final String hashCode,
        final String filename
        )
    {
        this.date     = (date == null) ? new Date() : date;
        this.hashCode = hashCode;
        if( filename == null ) {
            this.filename = null;
            }
        else {
            if( filename.isEmpty() ) {
                this.filename = null;
                }
            else {
                this.filename = filename;
                }
            }
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
        final int prime = 31; // $codepro.audit.disable numericLiterals
        int result = 1;
        result = (prime * result) + ((date == null) ? 0 : date.hashCode());
        result = (prime * result)
                + ((filename == null) ? 0 : filename.hashCode());
        result = (prime * result)
                + ((hashCode == null) ? 0 : hashCode.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals, cyclomaticComplexity
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) { // $codepro.audit.disable useEquals
            return false;
        }
        DefaultURICacheEntry other = (DefaultURICacheEntry)obj;
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
        return "DefaultURICacheEntry [hashCode=" + hashCode + ", date=" + date
                + ", filename=" + filename + "]";
    }
}
