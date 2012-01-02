package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 *
 */
public class FilePath implements Iterable<File>
{
    private File[] path;

    /**
     *
     * @param file
     */
    public FilePath( File file )
    {
        ArrayList<File> pathList    = new ArrayList<File>();
        File            f           = file;

        while( f != null ) {
            pathList.add( f );
            f = f.getParentFile();
            }

        this.path = pathList.toArray( new File[ pathList.size() ] );
    }

    /**
     *
     * @param index
     * @return
     * @throws  NoSuchElementException if index is out of bounds
     */
    public File getFilePart( final int index )
        throws  NoSuchElementException
    {
        try {
            return path[ index ];
            }
        catch( ArrayIndexOutOfBoundsException e ) {
            NoSuchElementException ne = new NoSuchElementException();
            
            ne.initCause( e );
            
            throw ne;
            }
    }

    /**
     *
     * @return
     */
    public File getFile()
    {
        return path[ 0 ];
    }

    /**
     *
     * @return
     */
    public File getRootFile()
    {
        return path[ path.length - 1 ];
    }

    /**
     *
     * @return
     */
    public int size()
    {
        return path.length;
    }

    @Override
    public String toString()
    {
        return super.getClass().getSimpleName() + "[" + this.getFile().getPath() + "]";
    }

    /**
     *
     * @param firstElementIndex
     * @return
     */
    public Iterable<File> startFrom( final int firstElementIndex )
    {
        return new Iterable<File>()
        {
            @Override
            public Iterator<File> iterator()
            {
                return new Iterator<File>()
                {
                    int index = firstElementIndex;
                    @Override
                    public boolean hasNext()
                    {
                        return index >= 0;
                    }
                    @Override
                    public File next()
                    {
                        return getFilePart( index -- );
                    }
                    @Override
                    public void remove()
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    /**
     *
     */
    @Override
    public Iterator<File> iterator()
    {
        return startFrom( path.length - 1 ).iterator(); // index of root
    }
}
