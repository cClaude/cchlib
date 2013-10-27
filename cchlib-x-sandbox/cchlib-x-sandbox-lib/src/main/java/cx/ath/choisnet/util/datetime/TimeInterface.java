/*
** $VER: TimeInterface.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/TimeInterface.java
** Description   :
**
**  1.53.014 2005.05.20 Claude CHOISNET - Version initiale
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Modification de l'h�ritable JDK1.5
**                          Comparable -> Comparable<TimeInterface>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.TimeInterface
**
*/
package cx.ath.choisnet.util.datetime;

/**
**
** @author Claude CHOISNET
** @since   1.53.014
** @version 2.00.003
*/
public interface TimeInterface
    extends
        DateTimeInterface,
        Comparable<TimeInterface>
{

/**
**
*/
public abstract boolean isBefore( TimeInterface anotherTime ); // ---------

/**
**
*/
public abstract boolean isAfter( TimeInterface anotherTime ); // ----------

/**
** Addition d'horaire.
*/
public abstract TimeInterface add( TimeInterface anotherTime ) // ---------
    throws BasicDateTimeException;

/**
** Soustraction d'horaire.
*/
public abstract TimeInterface sub( TimeInterface anotherTime ) // ---------
    throws BasicDateTimeException;

/**
** Compare deux object horaire.
**
** @return true si les 2 horaires correspondent.
*/
public abstract boolean equals( TimeInterface anotherTime ); // -----------

/*
** Compare deux object horaire.
**
** @return  la valeur 0 si les 2 horaires correspondent, une valeur n�gative
**          si l'object courant est plus ancien que l'horaire donn� en
**          param�tre. une valeur positive si l'horaire de l'object
**          courant est plus r�cent que l'horaire pass� en param�tre.
*/
@Override
public abstract int compareTo( TimeInterface anotherTime ); // ------------

} // class

/**
** Renvoie la date associ�e au calendrier conform�ment � l'oject de formatage.
**
** @param formatter     Object de formattage contenant les caract�ristiques
**                      du texte attendu.
** @see java.text.Format
** @see java.text.DateFormat
** @see java.text.SimpleDateFormat
public abstract String toString( Format formatter ); // -------------------
*/

/**
** retourne la valeur sous forme de long
public abstract long longValue(); // --------------------------------------
*/
