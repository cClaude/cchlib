/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/util/FlattenIterator.java
 ** Description   :
 **
 **  3.02.019 2006.06.29 Claude CHOISNET - Version initiale
 **                      Adaptation de MultiIterator
 **  3.02.028 2006.07.20 Claude CHOISNET - Version initiale
 **                      Ajout de: FlattenIterator(Iterator<T>,Iterator<T>)
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.util.FlattenIterator
 **
 */
package cx.ath.choisnet.util;

import java.util.Collection;
import java.util.Iterator;

/**
 ** <P>
 * An {@link Iterator} based on an {@link Iterator}'s of {@link Iterator}
 * </P>
 ** <P>
 * Classe permettant de construire des objets {@link Iterator} sans pour cela avoir besoin de creer de nouvelle
 * collection.
 * </P>
 ** <P>
 * Cette classe tente d'optimiser les traitements afin de limiter les parcours et les allocations d'objets.
 * </P>
 **
 ** @author Claude CHOISNET
 ** @since 2.00.001
 **
 ** @see EmptyIterator
 ** @see FlattenIterable
 */
public class FlattenIterator<T> extends ComputableIterator<T> {
    private final Iterator<Iterator<T>> metaIterator;
    private Iterator<T>                 currentIterator = null;

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur iterateurs ({@link Iterator}), d'iterateurs.
     * </p>
     **
     ** @param iteratorOfIterator
     *            Iterateur de reference
     */
    public FlattenIterator( // ------------------------------------------------
            final Iterator<Iterator<T>> iteratorOfIterator )
    {
        this.metaIterator = iteratorOfIterator;
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un collection d'iterateurs ({@link Iterator}),
     * </p>
     **
     ** @param collectionOfIterator
     *            Collection de reference
     */
    public FlattenIterator( // ------------------------------------------------
            final Collection<Iterator<T>> collectionOfIterator )
    {
        this( collectionOfIterator.iterator() );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un tableau d'iterateur (Iterator).
     * </p>
     **
     ** @param arrayOfIterator
     *            Tableau d'iterateur
     ** @param offset
     *            Index de depart
     ** @param len
     *            Index de fin
     **
     */
    public FlattenIterator( // ------------------------------------------------
            final Iterator<T>[] arrayOfIterator,
            final int offset,
            final int len )
    {
        this.metaIterator = new ArrayIterator<Iterator<T>>( arrayOfIterator, offset, len );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un tableau d'iterateur (Iterator).
     * </p>
     **
     ** @param arrayOfIterator
     *            Tableau d'iterateur
     **
     ** @see ArrayCollection
     */
    public FlattenIterator( // ------------------------------------------------
            final Iterator<T>[] arrayOfIterator )
    {
        this( arrayOfIterator, 0, arrayOfIterator.length );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur deux iterateur ({@link Iterator}).
     * </p>
     ** Ce constructeur s'appuis sur {@link ArrayCollection}.
     **
     ** @param iter1
     *            Premier iterateur
     ** @param iter2
     *            Second iterateur
     **
     ** @see ArrayCollection
     **
     ** @since 3.02.028
     */
    public FlattenIterator( // ------------------------------------------------
            final Iterator<T> iter1,
            final Iterator<T> iter2 )
    {
        this( new ArrayCollection<Iterator<T>>().append( iter1 ).append( iter2 ) );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un iterateur (Iterator) et un element.
     * </p>
     ** Ce constructeur s'appuis sur {@link ArrayCollection}.
     **
     ** @param iter
     *            Object {@link Iterator}
     ** @param element
     *            Element seul
     **
     **            L'element seul sera traiter APReS les elements de l'iterateur.
     **
     */
    public FlattenIterator( // ------------------------------------------------
            final Iterator<T> iter,
            final T element )
    {
        this( new ArrayCollection<Iterator<T>>().append( iter ).append( new SingletonIterator<T>( element ) ) );
    }

    /**
     ** <p>
     * Construction d'Iterator s'appuyant sur un iterateur (Iterator) et un element.
     * </p>
     ** Ce constructeur s'appuis sur {@link ArrayCollection}.
     **
     ** @param element
     *            Element seul
     ** @param iter
     *            Object {@link Iterator}
     **
     **            L'element seul sera traiter AVANT les elements de l'iterateur.
     **
     */
    public FlattenIterator( // ------------------------------------------------
            final T element,
            final Iterator<T> iter )
    {
        this( new ArrayCollection<Iterator<T>>().append( new SingletonIterator<T>( element ) ).append( iter ) );
    }

    @Override
    public T computeNext() // -------------------------------------------------
            throws java.util.NoSuchElementException
    {
        if( this.currentIterator == null ) {
            this.currentIterator = this.metaIterator.next();
        }

        for( ;; ) {
            if( this.currentIterator.hasNext() ) {
                return this.currentIterator.next();
            }

            if( this.metaIterator.hasNext() ) {
                this.currentIterator = this.metaIterator.next();
            } else {
                //
                // No more
                //
                throw new java.util.NoSuchElementException();
            }
        }
    }

}