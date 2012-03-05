package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.io.Serializable;

/**
 *
 */
final public class KeyFileState
    implements  Serializable,
                Comparable<KeyFileState>
{
    private static final long serialVersionUID = 1L;
    private String  key;
    private File    file;
    private String  path;
    private boolean selectedToDelete;

    /**
     * File object
     *
     * @param key
     * @param file
     */
    public KeyFileState(String key, File file)
    {
        this.key  = key;
        this.file = file;
        this.selectedToDelete = false;
     }

    @Override
    public String toString()
    {
        return this.file.getPath();
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return the file
     */
    public File getFile()
    {
        return file;
    }


    public boolean isInDirectory( String dirPath )
    {
        if( path == null ) {
            path = file.getPath();
            }

        return path.startsWith( dirPath );
    }

    /**
     * @return the selectedToDelete
     */
    public final boolean isSelectedToDelete()
    {
        return selectedToDelete;
    }

    /**
     * @param selectedToDelete the selectedToDelete to set
     */
    public final void setSelectedToDelete( boolean selectedToDelete )
    {
        this.selectedToDelete = selectedToDelete;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
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
        if( !(obj instanceof KeyFileState) ) {
            return false;
        }
        KeyFileState other = (KeyFileState)obj;
        if( file == null ) {
            if( other.file != null ) {
                return false;
            }
        } else if( !file.equals( other.file ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo( KeyFileState kfs )
    {
        return file.compareTo( kfs.file );
    }
}
