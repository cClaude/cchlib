/*
** $VER: DateTimeInterface.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/DateTimeInterface.java
** Description   :
**
**  1.53.014 2005.05.20 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.DateTimeInterface
**
*/
package cx.ath.choisnet.util.datetime;

import java.text.Format;

/**
**
** @author Claude CHOISNET
** @since 1.53.014
** @version 1.53.014
*/
public interface DateTimeInterface
{
/**
** Renvoie la date associée au calendrier conformément é l'oject de formatage.
**
** @param formatter     Object de formattage contenant les caractéristiques
**                      du texte attendu.
** @see java.text.Format
** @see java.text.DateFormat
** @see java.text.SimpleDateFormat
*/
public abstract String toString( Format formatter ); // -------------------

/**
** retourne la valeur sous forme de long
*/
public abstract long longValue(); // --------------------------------------

} // class
