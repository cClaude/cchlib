package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import org.apache.log4j.Logger;

/**
 *
 */
public final class KeyFileState
    implements  Serializable,
                Comparable<KeyFileState>
{
    private static final Logger LOGGER = Logger.getLogger( KeyFileState.class );
    private static final long serialVersionUID = 1L;

    private Integer             depth;
    private final File          file;
    private final String        key;
    private final long          length;
    private final String        name;
    private final String        path;
    private boolean             selectedToDelete;

    /**
     * File object
     *
     * @param key
     * @param file
     */
    public KeyFileState(final String key, final File file)
    {
        this.key              = key;
        this.file             = file;
        this.path             = file.getPath();
        this.name             = file.getName();
        this.length           = file.length();
        this.selectedToDelete = false;
     }

    @Override
    public int compareTo( final KeyFileState kfs )
    {
        return this.file.compareTo( kfs.file );
    }

    public boolean delete()
    {
        if( this.file.length() != this.length ) {
            LOGGER.info( "File has change, can not delete :" + this.path );
            return false;
        }
        else {
            return this.file.delete();
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        else if( obj == null ) {
            return false;
        }
        else if( !(obj instanceof KeyFileState) ) {
            return false;
        }
        final KeyFileState other = (KeyFileState)obj;
        if( this.file == null ) {
            if( other.file != null ) {
                return false;
            }
        } else if( !this.file.equals( other.file ) ) {
            return false;
        }
        return true;
    }

    /**
     * Returns depth of file
     * @return depth of file
     */
    public int getDepth()
    {
        if( this.depth == null ) {
            int  d = 0;
            File f = this.file.getParentFile();

            while( f != null ) {
                f = f.getParentFile();
                d++;
                }
            this.depth = Integer.valueOf( d );
            }
        return this.depth.intValue();
    }

    /**
     * @return the file
     */
    public File getFile()
    {
        return this.file;
    }

    public String getFileNameWithExtention()
    {
        return this.file.getName();
    }

    public String getFileNameWithoutExtention()
    {
        final String filename = getFileNameWithExtention();

        if( filename.indexOf( 0 ) == '.' ) {
            // File start with DOT, so it's a hidden file (or system file) - no extention
            return filename;
            }

        final int index = filename.lastIndexOf( '.' );

        if( index < 0 ) {
            // No extention
            return filename;
            }

        return filename.substring( 0, index );
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return this.key;
    }

    public long getLastModified()
    {
        return this.file.lastModified();
    }

    public long getLength()
    {
        return this.length;
    }

    public String getName()
    {
        return this.name;
    }

    public File getParentFile()
    {
        return this.file.getParentFile();
    }

    /**
     * @return the file path
     */
    public String getPath()
    {
        return this.path;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31; // $codepro.audit.disable numericLiterals
        int result = 1;
        result = (prime * result) + ((this.file == null) ? 0 : this.file.hashCode());
        return result;
    }

    public boolean isFileExists()
    {
        return this.file.exists();
    }

    public boolean isInDirectory( final String dirPath )
    {
        return this.path.startsWith( dirPath );
    }

    /**
     * @return the selectedToDelete
     */
    public final boolean isSelectedToDelete()
    {
        return this.selectedToDelete;
    }

    /**
     * @param selectedToDelete the selectedToDelete to set
     */
    public final void setSelectedToDelete( final boolean selectedToDelete )
    {
        this.selectedToDelete = selectedToDelete;
    }

    public Path toPath()
    {
        return this.file.toPath();
    }

    @Override
    public String toString()
    {
        return this.path;
    }

    /**
     * Returns current {@link File} object associate to this object.
     * @return current {@link File} object
     */
    public File getCurrentFile()
    {
        return this.file;
    }
}
