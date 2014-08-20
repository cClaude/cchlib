/*
** $VER: DateInterface.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/DateInterface.java
** Description   :
**
**  1.53.014 2005.05.20 Claude CHOISNET - Version initiale
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Modification de l'heritable JDK1.5
**                          Comparable -> Comparable<DateInterface>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.DateInterface
**
*/
package cx.ath.choisnet.util.datetime;

/**
**
** @author Claude CHOISNET
** @since   1.53.014
** @version 2.00.003
*/
public interface DateInterface
    extends
        DateTimeInterface,
        Comparable<DateInterface>
{
/**
**
*/
public abstract boolean isBefore( DateInterface anotherDate ); // ---------

/**
**
*/
public abstract boolean isAfter( DateInterface anotherDate ); // ----------

/**
** Addition d'objet DateInterface.
*/
public abstract DateInterface add( DateInterface anotherDate ) // ---------
    throws BasicDateTimeException;

/**
** Soustraction d'objet DateInterface.
*/
public abstract DateInterface sub( DateInterface anotherDate ) // ---------
    throws BasicDateTimeException;


/*
** Compare deux object horaire.
**
** @return  la valeur 0 si les 2 horaires correspondent, une valeur negative
**          si l'object courant est plus ancien que l'horaire donne en
**          parametre. une valeur positive si l'horaire de l'object
**          courant est plus recent que l'horaire passe en parametre.
*/
@Override
public abstract int compareTo( DateInterface anotherDate ); // ------------

/**
** Compare deux object horaire.
**
** @return true si les 2 horaires correspondent.
*/
public abstract boolean equals( DateInterface anotherDate ); // -----------

} // class
