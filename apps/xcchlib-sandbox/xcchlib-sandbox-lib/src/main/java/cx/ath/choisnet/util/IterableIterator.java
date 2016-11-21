/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/IterableIterator.java
** Description   :
**
**  3.02.006 2006.06.06 Claude CHOISNET - Version initiale
*** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.IterableIterator
**
*/
package cx.ath.choisnet.util;

/**
** <P>
** Interface permettant d'avoir des Objets qui soit é la fois compatible avec
** l'interface {@link java.util.Iterator} et l'interface {@link Iterable}.
** <br/>
** <br/>
** Cette classe é pour but de pouvoir bénéficier des écritures simplifiée
** du JDK 1.5 également sur des objets implémentant l'interface {@link java.util.Iterator}
** </P>
**
** @author Claude CHOISNET
** @since   3.02.006
** @version 3.02.006
**
** @see java.lang.Iterable
** @see java.util.Iterator
*/
public interface IterableIterator<T>
            extends
                java.lang.Iterable<T>,
                java.util.Iterator<T>
{

} // class
