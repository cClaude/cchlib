/*
** SimpleList.java
*/
package jrpdk.util;

import java.util.AbstractSequentialList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
** Liste cha�n�e d'�l�ments simplifi�e. Cette liste est particuli�rement
** efficasse en terme de m�moire et de temps sur les op�rations simples.
*/
public class SimpleList
    extends AbstractSequentialList
        implements
            List,
            Cloneable,
            java.io.Serializable
{
    private static final long serialVersionUID = 1L;
private SimpleListEntry firstItem;
private SimpleListEntry lastItem;
private int             size;
private int             modificationCounter = 0;

/**
**
*/
public SimpleList() // ----------------------------------------------------
{
 this( null );
}

/**
**
*/
public SimpleList( // -----------------------------------------------------
    SimpleListEntry firstEntry
    )
{
 if( firstEntry == null ) {
    this.lastItem = this.firstItem = null;
    this.size = 0;
    }
 else {
    this.firstItem = firstEntry;

    int             count   = 1;
    SimpleListEntry prev    = firstEntry;
    SimpleListEntry node    = firstEntry.next;

    while( node != null ) {
        prev = node;
        node = node.next;
        count++;
        }

    this.lastItem   = prev;
    this.size       = count;
    }
}

/**
** @return the SimpleListEntry at the specified position in this list.
*/
private SimpleListEntry getSimpleListEntry( int index ) // -----------------
{
 if( index < 0 || index >= size ) {
    throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
    }

 SimpleListEntry e = firstItem;

 for( int i = 0; i <= index; i++ ) {
    e = e.next;
    }

 return e;
}

/**
** @return the previous SimpleListEntry that specify.
*/
private SimpleListEntry getPrevSimpleListEntry( // ------------------------
    SimpleListEntry entry
    )
{
 SimpleListEntry prev = null;

 for( SimpleListEntry e = firstItem; e != null; e = e.next ) {

    if( e == entry ) {
        return prev;
        }

    prev = e;
    }

 throw new InternalError( "entry not found." );
}

/**
**
*/
public void add( int index, Object o ) // ---------------------------------
{
 if( index < 0 || index > size ) {
    throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
    }

 if( index == 0 ) { // Ajout en t�te
    addFirst( o );

    return;
    }

 if( index == size ) { // Ajout en queue
    add( o );

    return;
    }

 // Recherche de la position
 SimpleListEntry prev = getSimpleListEntry( index - 1 );

 // Insertion d'une nouvelle entr�e.
 prev.next = new SimpleListEntry( o, prev.next );

 this.size++;
 this.modificationCounter++;
}

/**
** Appends the specified element to the end of this list.
*/
public boolean add( Object o ) // -----------------------------------------
{
 if( this.firstItem == null ) {
    // list vide
    this.lastItem = this.firstItem = new SimpleListEntry( o );
    }
 else {
    // this.firstItem = [NO CHANGE];
    this.lastItem = new SimpleListEntry( o, this.lastItem );
    }

 this.modificationCounter++;
 this.size++;

 return true;
}

/**
** Inserts the given element at the beginning of this list.
**
** @param o the element to be inserted at the beginning of this list.
*/
public void addFirst( Object o ) // ---------------------------------------
{
 if( this.firstItem == null ) {
    // list vide
    this.lastItem = this.firstItem = new SimpleListEntry( o );
    }
 else {
    this.firstItem = new SimpleListEntry( o, this.firstItem );
    // this.lastItem = [NO CHANGE];
    }

 this.modificationCounter++;
 this.size++;
}

// public boolean _addAll( Collection c ){} // ----------------------------------
// public boolean _addAll( int index, Collection c ){} // -----------------------

/**
** Removes all of the elements from this list
*/
public void clear() // ----------------------------------------------------
{
 this.lastItem = this.firstItem = null;
 this.size = 0;
 this.modificationCounter++;
}

/**
** Returns true if this list contains the specified element. More
** formally, returns true if and only if this list contains at least one
** element e such that (o==null ? e==null : o.equals(e)).
**
** @param o element whose presence in this list is to be tested.
**
** @return true if this list contains the specified element.
*/
public boolean contains( Object o ) // ------------------------------------
{
 return indexOf( o ) != -1;
}

// public boolean _containsAll(Collection c)

/**
** Returns the element at the specified position in this list.
**
** @param index index of element to return.
** @return the element at the specified position in this list.
**
** @throws IndexOutOfBoundsException if the specified index is is out of
**      range (<tt>index &lt; 0 || index &gt;= size()</tt>).
*/
public Object get( int index ) // -----------------------------------------
{
 return getSimpleListEntry( index ).element;
}

/**
** Replaces the element at the specified position in this list with the
** specified element.
**
** @param index index of element to replace.
** @param element element to be stored at the specified position.
** @return the element previously at the specified position.
** @throws IndexOutOfBoundsException if the specified index is out of
*        range (<tt>index &lt; 0 || index &gt;= size()</tt>).
*/
public Object set( int index, Object element )
{
 SimpleListEntry    e       = getSimpleListEntry( index );
 Object             oldVal  = e.element;

 e.element = element;

 //
 // Pas de modification de "this.modificationCounter", car la
 // structure de la liste n'a pas chang�e.
 //

 return oldVal;
}

/**
** Returns the index in this list of the first occurrence of the
** specified element, or -1 if the List does not contain this
** element.  More formally, returns the lowest index i such that
** <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt>, or -1 if
** there is no such index.
**
** @param o element to search for.
** @return the index in this list of the first occurrence of the
**         specified element, or -1 if the list does not contain this
**         element.
*/
public int indexOf( Object o ) // -----------------------------------------
{
 int index = 0;

 if( o == null ) {
    for( SimpleListEntry e = firstItem; e != null; e = e.next ) {
        if( e.element == null ) {
            return index;
            }
        index++;
        }
    }
 else {
    for( SimpleListEntry e = firstItem; e != null; e = e.next ) {
        if( o.equals( e.element ) ) {
            return index;
            }
        index++;
        }
    }

 return -1;
}

/**
** @return an iterator over the elements in this list in proper sequence.
*/
public Iterator iterator() // ---------------------------------------------
{
 return new SLIterator();
}

// public int _lastIndexOf(Object o)
// public ListIterator _listIterator()

/**
** Returns a list-iterator of the elements in this list (in proper
** sequence), starting at the specified position in the list.
** Obeys the general contract of <tt>List.listIterator(int)</tt>.<p>
**
** The list-iterator is <i>fail-fast</i>: if the list is structurally
** modified at any time after the Iterator is created, in any way except
** through the list-iterator's own <tt>remove</tt> or <tt>add</tt>
** methods, the list-iterator will throw a
** <tt>ConcurrentModificationException</tt>.  Thus, in the face of
** concurrent modification, the iterator fails quickly and cleanly, rather
** than risking arbitrary, non-deterministic behavior at an undetermined
** time in the future.
**
** @param index index of first element to be returned from the
**          list-iterator (by a call to <tt>next</tt>).
** @return a ListIterator of the elements in this list (in proper
**         sequence), starting at the specified position in the list.
** @throws    IndexOutOfBoundsException if index is out of range
**        (<tt>index &lt; 0 || index &gt; size()</tt>).
** @see List#listIterator(int)
*/
public ListIterator listIterator( final int index ) // --------------------
{
 return new SLListIterator( index );
}

// public Object _remove(int index)

public boolean remove( Object o ) // --------------------------------------
{
System.out.println( "public boolean remove( Object o )" );
 if( size == 0 ) {
    return false; // liste vide
    }

 if( o == null ) {
    if( this.firstItem.element == null ) {
        removeFirst();
        return true;
        }

    if( this.lastItem.element == null ) {
        removeLast();
        return true;
        }

    SimpleListEntry prev = null;

    for( SimpleListEntry e = this.firstItem; e != null; e = e.next ) {

        if( e.element == null ) {
            // il ne s'agit ni du premier, ni du dernier �l�ment.
            prev.next = e.next;
            size--;

            return true;
            }

        prev = e;
        }

    return false;
    }
 else { // o != null

    if( o.equals( this.firstItem.element ) ) {
        removeFirst();
        return true;
        }

    if( o.equals( this.lastItem.element ) ) {
        removeLast();
        return true;
        }

    SimpleListEntry prev = null;

    for( SimpleListEntry e = this.firstItem; e != null; e = e.next ) {
        if( o.equals( e.element ) ) {
            // il ne s'agit ni du premier, ni du dernier �l�ment.
            prev.next = e.next;
            size--;

            return true;
            }

        prev = e;
        }
    return false;
    }
}

private void removeSimpleListEntry( SimpleListEntry entry ) // ------------
{
 if( this.firstItem == entry ) {
    removeFirst();
    return;
    }

 if( this.lastItem.element == entry ) {
    removeLast();
    return;
    }

 SimpleListEntry prev = null;

 for( SimpleListEntry e = this.firstItem; e != null; e = e.next ) {
    if( e == entry ) {
        // il ne s'agit ni du premier, ni du dernier �l�ment.
        prev.next = e.next;
        size--;

        return;
        }

    prev = e;
    }

 throw new NoSuchElementException();
}

// public boolean _removeAll(Collection c)
// public boolean _retainAll(Collection c)

/**
** Returns the number of elements in this list.
**
** @return the number of elements in this list.
*/
public int size() // ------------------------------------------------------
{
 return size;
}

// public List subList( int fromIndex, int toIndex )
// public Object[] _toArray()
// public Object[] _toArray(Object[] a)


/**
** Removes the element at the specified position in this list. Shifts any
** subsequent elements to the left (subtracts one from their indices).
** Returns the element that was removed from the list.
**
** @param index the index of the element to removed.
**
** @return the element previously at the specified position.
**
** @throws IndexOutOfBoundsException if the specified index is out of
**        range (<tt>index &lt; 0 || index &gt;= size()</tt>).
*/
public Object remove( int index ) // --------------------------------------
{
System.out.println( "public boolean remove( int index )" );

 if( (index < 0) || (index >= size) ) {
    throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size );
    }

 if( index == 0 ) {
    return removeFirst();
    }

 if( index-1 == size ) {
    return removeLast();
    }

 SimpleListEntry prev   = null;
 SimpleListEntry e      = firstItem;

 for( int i = 0; i <= index; i++ ) {
    prev    = e;
    e       = e.next;
    }

 // Note: il ne s'agit ni de la t�te, ni de la queue.

 prev.next = e.next;

 this.size--;
 this.modificationCounter++;

 return e;
}

/**
** Removes and returns the first element from this list.
**
** @return the first element from this list.
**
** @throws    NoSuchElementException if this list is empty.
*/
public Object removeFirst() // --------------------------------------------
{
System.out.println( "public Object removeFirst()" );

 SimpleListEntry first = firstItem;

 if( first == null ) {
    throw new NoSuchElementException();
    }

 if( size == 1 ) {
    lastItem = firstItem = null;
    this.size = 0;
    }
 else {
    firstItem = first.next;

    this.size--;
    }

 this.modificationCounter++;

 return first;
}


/**
** Removes and returns the last element from this list.
**
** @return the last element from this list.
** @throws    NoSuchElementException if this list is empty.
*/
public Object removeLast() // ---------------------------------------------
{
System.out.println( "public Object removeLast()" );

 SimpleListEntry last = lastItem;

 if( last == null ) {
    throw new NoSuchElementException();
    }

 if( size == 1 ) {
    // il n'y a qu'un element dans la liste
    lastItem = firstItem = null;
    size = 0;
    }
 else if( size == 2 ) {
    // il n'y a que deux elements dans la liste
    lastItem = firstItem;
    size     = 1;
    }
 else {
    lastItem = getSimpleListEntry( size - 2 );

    this.size--;
    }

 this.modificationCounter++;

 return last;
}

/**
** Returns a shallow copy of this <tt>SimpleList</tt>. (The elements
** themselves are not cloned.)
**
** @return a shallow copy of this <tt>SimpleList</tt> instance.
*/
public Object clone() // --------------------------------------------------
{
 SimpleList clone = null;

 try {
    clone = (SimpleList)super.clone();
    }
 catch( CloneNotSupportedException e ) {
    throw new InternalError();
    }

 // Initialize clone
 clone.firstItem             = null;
 clone.lastItem              = null;
 clone.size                  = 0;
 clone.modificationCounter   = 0;

 // Read in all elements in the proper order.
 for( SimpleListEntry e = firstItem.next; e != null; e = e.next ) {
    clone.add( e.element );
    }

 return clone;
}

/**
** Save the state of this <tt>SimpleList</tt> instance to a stream (that
** is, serialize it).
**
** @serialData The size of the list (the number of elements it
**         contains) is emitted (int), followed by all of its
**          elements (each an Object) in the proper order.
*/
private synchronized void writeObject( java.io.ObjectOutputStream s ) // --
    throws java.io.IOException
{
 // Write out any hidden serialization magic
 s.defaultWriteObject();

 // Write out size
 s.writeInt( size );

 // Write out all elements in the proper order.
 for( SimpleListEntry e = firstItem.next; e != null; e = e.next ) {
    s.writeObject( e.element );
    }
}

/**
** Reconstitute this <tt>SimpleList</tt> instance from a stream
** (that is deserialize it).
*/
private synchronized void readObject( java.io.ObjectInputStream s ) // ----
    throws java.io.IOException, ClassNotFoundException
{
 // Read in any hidden serialization magic
 s.defaultReadObject();

 // Read in size
 int size = s.readInt();

 // Initialize
 this.firstItem             = null;
 this.lastItem              = null;
 this.size                  = 0;
 this.modificationCounter   = 0;

 // Read in all elements in the proper order.
 for( int i=0; i<size; i++ ) {
    add( s.readObject() );
    }
}

    /**
    **
    */
    private class SLIterator implements Iterator
    {
    protected SimpleListEntry   currentEntry;
    protected int               currentEntryIndex;
    protected int               iteratorMC;
    protected SimpleListEntry   lastReturn;

    public SLIterator() // ------------------------------------------------
    {
     currentEntry       = firstItem;
     currentEntryIndex  = 0;
     iteratorMC         = modificationCounter;
     lastReturn         = null;
    }

    public boolean hasNext() // -------------------------------------------
    {
     return currentEntry != null;
    }

    public Object next() // -----------------------------------------------
    {
     SimpleListEntry result = currentEntry;

     if( iteratorMC != modificationCounter ) {
        throw new ConcurrentModificationException();
        }

     try {
        currentEntry = currentEntry.next;
        }
     catch( NullPointerException e ) {
        throw new NoSuchElementException();
        }

     lastReturn = result;
     currentEntryIndex++;

     return result.element;
    }

    public void remove() // -----------------------------------------------
    {
     if( lastReturn == null ) {
        throw new IllegalStateException();
        }

     if( iteratorMC != modificationCounter ) {
        throw new ConcurrentModificationException();
        }

     SimpleList.this.removeSimpleListEntry( lastReturn );

     --currentEntryIndex; // La position courante a boug�e d'un element

     lastReturn = null;
     iteratorMC = modificationCounter;
    }

    } // inner-class SLIterator

    /**
    **
    */
    private class SLListIterator extends SLIterator implements ListIterator
    {
    private int             currentIndex;
    private int             iteratorMC;
    private SimpleListEntry lastReturned;
    private SimpleListEntry lastReturnedNext;

    public SLListIterator( int index ) // ---------------------------------
    {
     currentIndex       = index;
     iteratorMC         = modificationCounter;
     lastReturned       = null;
     lastReturnedNext   = null;
    }

    public void add( Object o ) // ----------------------------------------
    {
         synchronized( SimpleList.this ) {
            if( iteratorMC != modificationCounter ) {
                throw new ConcurrentModificationException();
                }

            SimpleList.this.add( currentIndex, o );

            currentIndex++; // on a ajout� avant le curseur
            iteratorMC = modificationCounter;
            }

         lastReturned = lastReturnedNext = null;
        }

        public boolean hasNext() // ---------------------------------------
        {
         return currentIndex < size;
        }

        public boolean hasPrevious() // -----------------------------------
        {
         return currentIndex > 0;
        }

        public Object next() // -------------------------------------------
        {
         if( iteratorMC != modificationCounter ) {
            throw new ConcurrentModificationException();
            }

         SimpleListEntry    entry = getSimpleListEntry( currentIndex++ );
         Object             value;

         try {
            value = entry.element;
            }
         catch( NullPointerException e ) {
            throw new NoSuchElementException();
            }

         lastReturned = entry;
         lastReturnedNext = entry.next;

         return value;
        }

        public int nextIndex() // -----------------------------------------
        {
         return currentIndex;
        }

        public Object previous() // ---------------------------------------
        {
         if( iteratorMC != modificationCounter ) {
            throw new ConcurrentModificationException();
            }

         if( currentIndex == 0 ) {
            throw new NoSuchElementException();
            }

         SimpleListEntry    entry = getSimpleListEntry( --currentIndex );
         Object             value = entry.element;

         lastReturnedNext = lastReturned = entry;

         return value;
        }

        public int previousIndex() // -------------------------------------
        {
         return currentIndex - 1;
        }

        public void remove() // -------------------------------------------
        {
         synchronized( SimpleList.this ) {
            if( iteratorMC != modificationCounter ) {
                throw new ConcurrentModificationException();
                }

            if( lastReturned == null ) {
                throw new IllegalStateException();
                }

            if( lastReturnedNext != lastReturned ) {
                --currentIndex;
                }

            SimpleList.this.remove( lastReturned );

            iteratorMC = modificationCounter;
            }

         lastReturned = lastReturnedNext = null;
        }

        public void set( Object o ) // ------------------------------------
        {
         if( iteratorMC != modificationCounter ) {
            throw new ConcurrentModificationException();
            }

         if( lastReturned == null ) {
            throw new IllegalStateException();
            }

        lastReturned.element = o;
        lastReturned = null;
        }
    }

public static void main( String[] args ) // -------------------------------
{
 SimpleList instance
    = new SimpleList(
        new SimpleListEntry( "1",
            new SimpleListEntry( "2",
                new SimpleListEntry( "3",
                    new SimpleListEntry( "4",
                        new SimpleListEntry( "5" )
                        )
                    )
                )
            )
        );

 System.out.print( "LIST( " );
 int skip = 3;

 for( Iterator iter = instance.iterator(); iter.hasNext(); ) {
    System.out.print( " " + iter.next() + "," );

    if( skip == 0 ) {
        iter.remove();
        }
    else {
        skip--;
        }
    }

 System.out.println( " END )" + instance.size() );

 System.out.print( "LIST( " );

 for( Iterator iter = instance.iterator(); iter.hasNext(); ) {
    System.out.print( " " + iter.next() + "," );
    }

 System.out.println( " END )" + instance.size() );
/*
 System.out.print( "LIST( " );

 for( ListIterator iter = instance.listIterator( 0 ); iter.hasNext(); ) {
    System.out.print( " " + iter.next() + "," );
    }
 System.out.println( " END )" );
/*

 System.out.print( "LIST( " );
 for( ListIterator iter = instance.listIterator( 0 ); iter.hasPrevious(); ) {
    System.out.print( " " + iter.previous() + "," );
    }
 System.out.println( " END )" );
*/
}

} // class

