package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

import java.io.File;
import java.io.Serializable;

/**
 * Convenience version to store couple of value (hash,file)
 */
class HashFile implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String hash;
    private File file;

    /** Default empty constructor */
    public HashFile()
    {
        // Empty
    }

    /**
     * Default constructor
     *
     * @param hash Hash string for {@code file}
     * @param file Related file
     */
    public HashFile( final String hash, final File file )
    {
        this.hash = hash;
        this.file = file;
    }

    @Override
    public String toString()
    {
        return "" + this.hash + "\t" + this.file.getPath();
    }

    public String getHash()
    {
        return this.hash;
    }

    public void setHash( final String hash )
    {
        this.hash = hash;
    }

    public File getFile()
    {
        return this.file;
    }

    public void setFile( final File file )
    {
        this.file = file;
    }
}
