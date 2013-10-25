/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/IteratorBuilder.java
** Description   :
**
**  1.30.010 2005.05.15 Claude CHOISNET - Version initiale
**  1.53.002 2005.08.08 Claude CHOISNET
**                      Ajout de: toString( Iterator, String )
**  1.53.010 2005.08.18 Claude CHOISNET
**                      Evolution de la méthode :
**                          toString( Iterator, String )
**  2.01.006 2005.10.03 Claude CHOISNET
**                      Correction d'un bug sur
**                          toIterator( T[], int, int )
**                      et  toIterator( T[] ) qui ne retournais pas un
**                      Iterator<T>.
**  2.01.017 2005.10.14 Claude CHOISNET
**                      Nettoyage de certaine méthodes disponibles sous
**                      forme de classe autonome.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.IteratorBuilder
**
*/
package cx.ath.choisnet.util;

import java.util.Iterator;

/**
** <P>
** Classe permettant de construire des objets Iterator sans pour cela
** avoir besoin de créer de nouvelle collection.
** </P>
** <P>
** Cette classe tente d'optimiser les traitements afin de limiter les
** parcours et les allocations d'objets.
** </P>
**
** @author Claude CHOISNET
** @since   1.30
** @version 2.01.017
**
** @see ArrayIterator
** @see BiIterator
** @see EmptyIterator
** @see EnumerationIterator
** @see Iterator
** @see IteratorWrapper
** @see FlattenIterator
** @see FlattenIterable
*/
public class IteratorBuilder
{

/**
** @return un Iterator basé sur le tableau de File et sur le filtre donné.
**
** @see cx.ath.choisnet.io.FileFilterHelper#and(FileFilter,FileFilter)
** @see cx.ath.choisnet.io.FileFilterHelper#directoryFileFilter
** @see cx.ath.choisnet.io.FileFilterHelper#nameMatchesFileFilter
** @see cx.ath.choisnet.io.FileFilterHelper#not(FileFilter)
** @see cx.ath.choisnet.io.FileFilterHelper#or(FileFilter,FileFilter)
*/
public static Iterator<java.io.File> toIterator( // -----------------------
    final java.io.File[]        files,
    final java.io.FileFilter    fileFilter
    )
{
 return new IteratorFilter<java.io.File>(
                new ArrayIterator<java.io.File>( files ),
                IteratorFilter.wrappe( fileFilter )
                );
}

/**
** Construction d'Iterator s'appuyant sur un tableau d'iterateur (Iterator).
** <br />
** <br />
**
** @return an Iterator based on an Iterator's of Iterator
** @since 1.53.010
**
** @see FlattenIterator
** @see FlattenIterable
*/
public static <T> Iterator<T> toIterator( // ------------------------------
    final Iterator<T>[] iteratorArray
    )
{
 return toIterator( iteratorArray, 0, iteratorArray.length );
}

/**
** Construction d'Iterator s'appuyant sur un tableau d'iterateur (Iterator).
** <br />
** <br />
**
** @return an Iterator based on an Iterator's of Iterator
** @since 1.53.010
**
** @see FlattenIterator
** @see FlattenIterable
*/
public static <T> Iterator<T> toIterator( // ------------------------------
    final Iterator<T>[] array,
    final int           offset,
    final int           len
    )
{
 return new Iterator<T>()
 {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        int index       = offset;
        T   nextObject  = null;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            if( nextObject == null ) {

                //
                // L'objet suivant n'est pas prêt, on le prepare
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
            // L'object suivant est prêt et valide !
            //
            return true;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            if( nextObject == null ) {
                //
                // L'objet suivant n'est pas prêt, on le prepare
                //
                //  Note: on sort en NoSuchElementException s'il n'existe pas
                //
                nextObject = computeNext();
                }

            //
            // On sauvegarde l'élément courant
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
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private T computeNext() //- - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {

            while( index < len ) {

                if( array[ index ].hasNext() ) {
                    return array[ index ].next();
                    }

                index++;
                }

            //
            // Not found
            //
            throw new java.util.NoSuchElementException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 };
}

/**
** @param iterator  Iterator Object to use
** @param separator a String
**
** @return un String correspondant à la concaténation des valeurs toString()
**         de l'iterateur donnée. Chaque valeur étant séparée par le contenu
**         de la chaîne 'separator'
**
** @since 1.53.002
**
** @see IteratorWrapper#toString
*/
public static String toString( Iterator iterator, String separator ) // ---
{
 final StringBuilder sb = new StringBuilder();

 if( iterator.hasNext() ) {
    sb.append( iterator.next().toString() );
    }

 while( iterator.hasNext() ) {
    sb.append( separator );
    sb.append( iterator.next().toString() );
    }

 return sb.toString();
}

} // class

/**
** Construction d'un objet Iterator à partir d'un objet Enumeration
**
** @return  un Iterator equivalent à l'Enumeration donné
**
** @see EnumerationIterator
public static <T> Iterator<T> toIterator( // ------------------------------
    final Enumeration<T> enumeration
    )
{
 return new Iterator<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            return enumeration.hasMoreElements();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
        {
            return enumeration.nextElement();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}
*/

/**
** @return un Iterator basé sur un Iterator de base et sur le filtre donné.
**
** @see IteratorWrapper
public static <T> Iterator<T> toIterator( // ------------------------------
    final Iterator<T>           iter0,
    final IteratorFiltrable<T> filter
    )
{
 return new Iterator<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        T nextObject = null;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        private T computeNext() //- - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            while( iter0.hasNext() ) {
                T currentObject = iter0.next();

                if( filter.isSelected( currentObject ) ) {
                    //
                    // Trouvé ! On sort avec le prochain object valide
                    //
                    return currentObject;
                    }
                }

            //
            // Not found
            //
            throw new java.util.NoSuchElementException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            if( nextObject == null ) {

                //
                // L'objet suivant n'est pas prêt, on le prepare
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
            // L'object suivant est prêt et valide !
            //
            return true;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
            throws java.util.NoSuchElementException
        {
            if( nextObject == null ) {
                //
                // L'objet suivant n'est pas prêt, on le prepare
                //
                //  Note: on sort en NoSuchElementException s'il n'existe pas
                //
                nextObject = computeNext();
                }

            //
            // On sauvegarde l'élément courant
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
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}
*/

/**
** Construction d'un objet Iterator vide, mais valide.
**
** @return  un Iterator ne contenant aucun object
**
** @see EmptyIterator
public static <T> Iterator<T> toIterator() // -----------------------------
{
 return new Iterator<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            return false;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
        {
            throw new java.util.NoSuchElementException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}
*/

/**
** <P>
** Construction d'objet Iterator parcourant l'ensemble des objects du tableau.
** </P>
** <br />
** Cette méthode accepte la valeur null pour le paramètre 'array', dans ce
** cas elle retourne un objet Iterator vide, mais valide.
** <br />
**
** @return un Iterator sur l'ensemble des objects du tableau
**
** @see ArrayIterator
public static <T> Iterator<T> toIterator( final T[] array ) // ------------
{
 if( array == null ) {
    return toIterator();
    }
 else {
    return toIterator( array, 0, array.length );
    }
}
*/

/**
** <P>
** Construction d'objet Iterator parcourant les objects du tableau à partir
** de l'indice offset et jusqu'à la position len - 1
** </P>
** <br />
** Cette méthode <b>n'accepte pas</b> la valeur null pour le paramètre 'array'
** <br />
**
** @return  un Iterator sur les objets du tableau de offset et jusqu'à la
**          position len - 1
**
** @see ArrayIterator
public static <T> Iterator<T> toIterator( // ------------------------------
    final T[]   array,
    final int   offset,
    final int   len
    )
{
 return new Iterator<T>()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        int index = offset;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean hasNext() // - - - - - - - - - - - - - - - - - - - -
        {
            return index < len;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public T next() //- - - - - - - - - - - - - - - - - - - - - - - - -
        {
            try {
                return array[ index++ ];
                }
            catch( IndexOutOfBoundsException e ) {
                throw new java.util.NoSuchElementException();
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void remove() // - - - - - - - - - - - - - - - - - - - - - -
        {
            throw new UnsupportedOperationException();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}
*/
