package com.googlecode.cchlib.net.download.cache;

import java.util.Date;

/**
 * NEEDDOC
 */
public class DefaultURICacheEntry implements URIDataCacheEntry
{
    private final String  hashCode;
    private final Date    date;
    private String  filename;

    /**
     * NEEDDOC
     *
     * @param date NEEDDOC
     * @param hashCode NEEDDOC
     * @param filename NEEDDOC
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
        return this.hashCode;
    }

    @Override
    public Date getDate()
    {
        return this.date;
    }

    @Override
    public String getRelativeFilename()
    {
        return this.filename;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31; // $codepro.audit.disable numericLiterals
        int result = 1;
        result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
        result = (prime * result)
                + ((this.filename == null) ? 0 : this.filename.hashCode());
        result = (prime * result)
                + ((this.hashCode == null) ? 0 : this.hashCode.hashCode());
        return result;
    }

    @Override
    public boolean equals( final Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals, cyclomaticComplexity
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
        return "DefaultURICacheEntry [hashCode=" + this.hashCode + ", date=" + this.date
                + ", filename=" + this.filename + "]";
    }
}
