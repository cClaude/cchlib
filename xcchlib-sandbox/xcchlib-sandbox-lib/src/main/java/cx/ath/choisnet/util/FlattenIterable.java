/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/util/FlattenIterable.java
 ** Description   :
 **
 **  3.02.018 2006.06.28 Claude CHOISNET- Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.util.FlattenIterable
 **
 */
package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 ** <P>
 * An {@link Iterator} based on an {@link Iterator}'s of {@link Iterable}
 * </P>
 ** <P>
 * Classe permettant de construire des objets {@link Iterator} sans pour cela avoir besoin de creer de nouvelle
 * collection. Elle permet d'applatir la vue avec de traiter les sous-objets sequentiellement.
 * </P>
 ** <P>
 * Cette classe tente d'optimiser les traitements afin de limiter les parcours et les allocations d'objets.
 * </P>
 **
 ** @author Claude CHOISNET
 ** @since 3.02.018
 ** @version 3.02.018
 **
 ** @see EmptyIterator
 ** @see FlattenIterator
 */
public class FlattenIterable<T> extends ComputableIterator<T> {
    private final Iterator<Iterable<T>> metaIterator;
    private Iterator<T>                 currentIterator = null;

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur iterateurs ({@link Iterator}), d'iterateurs.
     * </p>
     **
     ** @param iterator
     *            Iterateur d'objet Iterable.
     **
     */
    public FlattenIterable( final Iterator<Iterable<T>> iterator )
    {
        this.metaIterator = iterator;
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un collection d'objets {@link Iterable}
     * </p>
     **
     ** @param collection
     *            Collection d'objet Iterable.
     **
     */
    public FlattenIterable( final Collection<Iterable<T>> collection )
    {
        this( collection.iterator() );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un tableau d'objet {@link Iterable}
     * </p>
     **
     ** @param arrayOfIterable
     *            Tableau d'objets {@link Iterable}
     ** @param offset
     *            Index de depart
     ** @param len
     *            Index de fin
     **
     */
    public FlattenIterable( // ------------------------------------------------
            final Iterable<T>[] arrayOfIterable,
            final int offset,
            final int len )
    {
        this.metaIterator = new ArrayIterator<Iterable<T>>( arrayOfIterable, offset, len );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un tableau d'objet {@link Iterable}
     * </p>
     **
     ** @param iterables
     *            Tableau d'objet {@link Iterable}
     **
     */
    @SafeVarargs
    public FlattenIterable( // ------------------------------------------------
            final Iterable<T>... iterables )
    {
        this( iterables, 0, iterables.length );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur d'un objet {@link Iterable} et un element.
     * </p>
     **
     ** @param iterable
     *            Object {@link Iterable}
     ** @param element
     *            Element seul
     **
     **            L'element seul sera traiter APReS les elements de l'iterateur.
     **
     */
    public FlattenIterable( // ------------------------------------------------
            final Iterable<T> iterable,
            final T element )
    {
        this( new ArrayCollection<Iterable<T>>().append( iterable ).append( new SingletonIterator<T>( element ) ) );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un element et d'objet {@link Iterable}
     * </p>
     **
     ** @param element
     *            Element seul
     ** @param iterable
     *            Object {@link Iterable}
     **
     **            L'element seul sera traiter AVANT les elements de l'iterateur.
     **
     */
    public FlattenIterable( // ------------------------------------------------
            final T element,
            final Iterable<T> iterable )
    {
        this( new ArrayCollection<Iterable<T>>().append( new SingletonIterator<T>( element ) ).append( iterable ) );
    }

    @Override
    public T computeNext() throws NoSuchElementException
    {
        if( this.currentIterator == null ) {
            this.currentIterator = this.metaIterator.next().iterator();
        }

        for( ;; ) {
            if( this.currentIterator.hasNext() ) {
                return this.currentIterator.next();
            }

            if( this.metaIterator.hasNext() ) {
                this.currentIterator = this.metaIterator.next().iterator();
            } else {
                //
                // No more
                //
                throw new NoSuchElementException();
            }
        }
    }

} // class