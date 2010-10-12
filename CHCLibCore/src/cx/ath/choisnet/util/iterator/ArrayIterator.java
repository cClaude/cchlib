package cx.ath.choisnet.util.iterator;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wrap an Iterator from an existing Array, or
 * from giving elements.
 *
 * @author Claude CHOISNET
 * @param <T>
 * @see SingletonIterator
 */
public class ArrayIterator<T>
    implements Iterator<T>,
               Iterable<T>
{
    private final T[]   array;
    private final int   len;
    private int         index;

    /**
     * Wrap an array to have an Iterator
     * 
     * @param array array of element to wrap
     */
    public ArrayIterator(T[] array)
    {
        this.array = array;
        this.index = 0;

        if(array == null) {
            this.len = 0;
        }
        else {
            this.len = this.array.length;
        }
    }

    /**
     * Wrap an array to have an Iterator
     * 
     * @param array  array to wrap
     * @param offset first element
     * @param len    number of element to read
     */
    public ArrayIterator(
            T[] array,
            int offset,
            int len
            )
    {
        this.array = array;
        this.index = offset;
        this.len   = offset + len;
    }
    
    /**
     * Initialize internal array from a class 
     * @param clazz
     * @param capacity
     */
    private ArrayIterator( Class<T> clazz, int capacity)
    {
        //TODO: perhaps something cleaner later?
        @SuppressWarnings("unchecked")
        T[] array = (T[])Array.newInstance(clazz,capacity);
        this.array = array;
        this.index = 0;
        this.len   = capacity;
    }

    /**
     * Build an Iterator on giving objects
     * 
     * @param clazz type of elements
     * @param o1    first element for Iterator
     * @param o2    second element for Iterator
     */
    public ArrayIterator( Class<T> clazz, T o1, T o2 )
    {
        this(clazz,2);
        this.array[0] = o1;
        this.array[1] = o2;
    }
    
    /**
     * Build an Iterator on giving objects
     * 
     * @param clazz type of elements
     * @param o1    first element for Iterator
     * @param o2    second element for Iterator
     * @param o3    third element for Iterator
     */
    public ArrayIterator( Class<T> clazz, T o1, T o2, T o3 )
    {
        this(clazz,3);
        this.array[0] = o1;
        this.array[1] = o2;
        this.array[2] = o3;
    }
    
    /**
     * Returns true if the iteration has more elements.
     * (In other words, returns true if next would return
     * an element rather than throwing an exception.) 
     * @return true if the iteration has more elements.
     */
    @Override
    public boolean hasNext()
    {
        return index < len;
    }

    /** 
     * Returns the next element in the iteration. 
     * @return the next element in the iteration.
     * @throws NoSuchElementException iteration has no more elements.
     */
    @Override
    public T next()
    {
        try {
            return array[index++];
        }
        catch(IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Unsupported Operation
     * 
     * @throws UnsupportedOperationException
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over a set of elements of type T. 
     * @return this Iterator
     */
    @Override
    public Iterator<T> iterator()
    {
        return this;
    }
    
    /**
     * Build a new ArrayIterator. This factory, whose parameter 
     * list uses the varargs feature, may be used to create an
     * ArrayIterator set initially containing an arbitrary number 
     * of elements, but it is likely to run slower than the constructors
     * that do not use varargs. 
     * <BR/>
     * But since Java does not allow to build an array from
     * Genetics, this solution is cleaner.
     *  
     * @param <T>
     * @param entries
     * @return an ArrayIterator
     */
    public static <T> ArrayIterator<T> of(T...entries)
    {
        return new ArrayIterator<T>( entries );
    }
}
