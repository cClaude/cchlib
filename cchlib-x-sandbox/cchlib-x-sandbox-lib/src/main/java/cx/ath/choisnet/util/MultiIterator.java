/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/MultiIterator.java
** Description   :
**
**  2.00.002 2005.09.14 Claude CHOISNET - Version initiale
**  2.01.015 2005.10.14 Claude CHOISNET
**                      extends ComputableIterator<T>
**  2.01.018 2005.10.14 Claude CHOISNET
**                      Ajout des constructeurs acceptant un �l�ment seul.
**  3.02.018 2006.06.28 Claude CHOISNET
**                      Ajout des constructeurs :
**                          MultiIterator(Iterator<Iterator<T>>)
**                          MultiIterator(Collection<Iterator<T>>)
**  3.02.019 2006.06.29 Claude CHOISNET
**                      @Deprecated
**  3.02.027 2006.07.20 Claude CHOISNET
**                      Documentation
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.MultiIterator
**
*/
package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
** <P>
** An {@link Iterator} based on an {@link Iterator}'s of {@link Iterator}
** </P>
** <P>
** Classe permettant de construire des objets {@link Iterator} sans pour cela
** avoir besoin de cr�er de nouvelle collection.
** </P>
** <P>
** Cette classe tente d'optimiser les traitements afin de limiter les
** parcours et les allocations d'objets.
** </P>
**
** @author Claude CHOISNET
** @since   2.00.001
** @version 3.02.027
**
** @deprecated use {@link FlattenIterator} instead
*/
@Deprecated
public class MultiIterator<T>
    extends ComputableIterator<T>
{
/** */
private final Iterator<Iterator<T>> metaIterator;

/** */
private Iterator<T> currentIterator = null;

/** */
private T nextObject = null;

/**
** <p>
** Construction d'Iterator s'appuyant sur iterateurs ({@link Iterator}),
** d'iterateurs.
** </p>
**
** @since 3.02.018
*/
public MultiIterator( // --------------------------------------------------
    final Iterator<Iterator<T>> iteratorOfIterator
    )
{
 this.metaIterator = iteratorOfIterator;
}

/**
** <p>
** Construction d'Iterator s'appuyant sur un collection d'iterateurs
** ({@link Iterator}),
** </p>
**
** @since 3.02.018
*/
public MultiIterator( // --------------------------------------------------
    final Collection<Iterator<T>> collectionOfIterator
    )
{
 this( collectionOfIterator.iterator() );
}

/**
** Construction d'Iterator s'appuyant sur 2 iterateurs (Iterator).
** <br />
**
*/
public MultiIterator( // --------------------------------------------------
    final Iterator<T>   iter0,
    final Iterator<T>   iter1
    )
{
 final List<Iterator<T>> listOfIterator = new LinkedList<Iterator<T>>();

 listOfIterator.add( iter0 );
 listOfIterator.add( iter1 );

 this.metaIterator = listOfIterator.iterator();
}

/**
** <p>
** Construction d'Iterator s'appuyant sur un iterateur (Iterator) et un
** �l�ment.
** </p>
** L'�l�ment seul sera traiter APR�S les �l�ments de l'iterateur.
**
*/
public MultiIterator( // --------------------------------------------------
    final Iterator<T>   iter,
    final T             element
    )
{
 final List<Iterator<T>> listOfIterator = new LinkedList<Iterator<T>>();

 listOfIterator.add( iter );
 listOfIterator.add( new SingletonIterator<T>( element ) );

 this.metaIterator = listOfIterator.iterator();
}

/**
** <p>
** Construction d'Iterator s'appuyant sur un iterateur (Iterator) et un
** �l�ment.
** </p>
** L'�l�ment seul sera traiter AVANT les �l�ments de l'iterateur.
**
*/
public MultiIterator( // --------------------------------------------------
    final T             element,
    final Iterator<T>   iter
    )
{
 final List<Iterator<T>> listOfIterator = new LinkedList<Iterator<T>>();

 listOfIterator.add( new SingletonIterator<T>( element ) );
 listOfIterator.add( iter );

 this.metaIterator = listOfIterator.iterator();
}

/**
** Construction d'Iterator s'appuyant sur un tableau d'iterateur (Iterator).
** <br />
** <br />
**
*/
public MultiIterator( final Iterator<T>[] arrayOfIterator ) // ------------
{
 this( arrayOfIterator, 0, arrayOfIterator.length );
}

/**
** Construction d'Iterator s'appuyant sur un tableau d'iterateur (Iterator).
** <br />
** <br />
**
*/
public MultiIterator( // --------------------------------------------------
    final Iterator<T>[] arrayOfIterator,
    final int           offset,
    final int           len
    )
{
 this.metaIterator = new ArrayIterator<Iterator<T>>( arrayOfIterator, offset, len );
}

/**
**
*/
@Override
public T computeNext() // -------------------------------------------------
    throws java.util.NoSuchElementException
{
 if( this.currentIterator == null ) {
    this.currentIterator = this.metaIterator.next();
    }

 for(;;) {
    if( this.currentIterator.hasNext() ) {
        return this.currentIterator.next();
        }

    if( this.metaIterator.hasNext() ) {
        this.currentIterator = this.metaIterator.next();
        }
    else {
        //
        // No more
        //
        throw new java.util.NoSuchElementException();
        }
    }
}

/**
**
** java -cp build\classes cx.ath.choisnet.util.MultiIterator
public final static void main( String[] args ) // -------------------------
{
 Integer[] ai1 = { 1,2,3 };
 Integer[] ai2 = { 10,20,30,40 };
 Integer[] ai3 = { 11,22,33,44, 55 };

 // Iterator<Integer>[] array = new Iterator<Integer>[ 3 ];
 Iterator<Integer>[] array = new Iterator[ 3 ];

 array[ 0 ] = new ArrayIterator<Integer>( ai1 );
 array[ 1 ] = new ArrayIterator<Integer>( ai2 );
 array[ 2 ] = new ArrayIterator<Integer>( ai3 );

 MultiIterator<Integer> result = new MultiIterator<Integer>( array );

 for( Integer i : result ) {
    System.out.println( "n = " + i );
    }
}
*/

} // class


/**
**
public boolean hasNext() // -----------------------------------------------
{
 if( nextObject == null ) {
    //
    // L'objet suivant n'est pas pr�t, on le prepare
    //
    try {
        nextObject = computeNext();
        }
    catch( java.util.NoSuchElementException e ) {
        //
        // Il n'existe pas !
        //
        return false;
        }
    }

 //
 // L'object suivant est pr�t et valide !
 //
 return true;
}
*/

/**
**
public T next() // --------------------------------------------------------
    throws java.util.NoSuchElementException
{
 if( nextObject == null ) {
    //
    // L'objet suivant n'est pas pr�t, on le prepare
    //
    //  Note: on sort en NoSuchElementException s'il n'existe pas
    //
    nextObject = computeNext();
    }

 //
 // On sauvegarde l'�l�ment courant
 //
 T returnObject = nextObject;

 //
 // On cacul le prochain
 //
 try {
    nextObject = computeNext();
    }
 catch( java.util.NoSuchElementException e ) {
    nextObject = null; // pas de prochain
    }

 return returnObject;
}
*/

/**
**
public void remove() // ---------------------------------------------------
{
 throw new UnsupportedOperationException();
}
*/

/**
**
public Iterator<T> iterator() // ------------------------------------------
{
 return this;
}
*/
