package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import cx.ath.choisnet.util.iterator.Iterators;

/**
 * Private class for building node from {@link File} path
 */
class FileNode implements Serializable, Iterable<File>
{
    private static final long serialVersionUID = 1L;
    private LinkedList<File> nodes = new LinkedList<File>();

    /**
     * Build a FileNode from a file
     * @param file Source {@link File} object
     */
    public FileNode( final File file )
    {
        File f = file;

        while( f != null ) {
            nodes.addFirst( f );
            f = f.getParentFile();
            }
    }

    /**
     * Returns number of node for this {@link File}
     * @return number of node for this {@link File}
     */
    public int size()
    {
        return nodes.size();
    }

    /**
     * Returns File part for this FileNode
     * @param index from root (0) to full path
     * @return {@link File} part for this FileNode
     */
    public File getFile( int index )
    {
        return nodes.get( index );
    }

    /**
     * Returns {@link File} object for this FileNode
     * @return {@link File} object for this FileNode
     */
    public File getFile()
    {
        return nodes.getLast();
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * @param filePart to locate
     * @return the index of the first occurrence of the filePart
     */
    public int indexOf( File filePart )
    {
        return nodes.indexOf( filePart );
    }

    @Override
    public Iterator<File> iterator()
    {
        return Iterators.unmodifiableIterator( nodes.iterator() );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "FileNode [file=" + getFile() + "(" + nodes.size() + ")]";
    }
}
