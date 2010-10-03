package cx.ath.choisnet.util.iterator;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import cx.ath.choisnet.util.Selectable;

/**
 * Apply a filter on an Iterator
 * <BR/>
 * Note: This Iterator extends also {@link Iterable Iterable} interface
 *
 * @author Claude CHOISNET
 * @param <T>
 */
public class IteratorFilter<T>
    extends ComputableIterator<T>
        implements Iterable<T>
{
    private Iterator<T>   iterator;
    private Selectable<T> filter;

    public IteratorFilter(
            Iterator<T>     iterator, 
            Selectable<T>   filter
            )
    {
        this.iterator = iterator;
        this.filter   = filter;
    }

    public T computeNext()
        throws java.util.NoSuchElementException
    {
        while(iterator.hasNext()) {
            T currentObject = iterator.next();

            if( filter.isSelected( currentObject ) ) {
                return currentObject;
            }
        }

        throw new NoSuchElementException();
    }

    public Iterator<T> iterator()
    {
        return this;
    }

    public static Selectable<File> wrappe(final FileFilter fileFilter)
    {
        return new Selectable<File>() {
            public boolean isSelected(File file)
            {
                return fileFilter.accept(file);
            }
        };
    }

//    public static <T> String toString( Iterator<T> iterator, String separator)
//    {
//        StringBuilder sb = new StringBuilder();
//
//        if(iterator.hasNext()) {
//            sb.append(iterator.next().toString());
//        }
//
//        for(; iterator.hasNext(); sb.append(iterator.next().toString())) {
//            sb.append(separator);
//        }
//
//        return sb.toString();
//    }
}
