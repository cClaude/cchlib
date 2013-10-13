/*
** $VER: BasicTime.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/BasicTime.java
** Description   :
**
**  1.00.___ 2000.10.29 Claude CHOISNET - Version initiale
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Adapation JDK1.5 et TimeInterface
**  2.01.012 2005.10.07 Claude CHOISNET
**                      Amm�lioration de la s�rialisation
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.BasicTime
**
*/
package cx.ath.choisnet.util.datetime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.Format;

/**
** Cette classe g�re les probl�mes d'heure au sens commun (heure, minutes, secondes)
** <P>
** <P>
** NOTES:<br/>
** - Cette classe est {@link Serializable}<br/>
**
** @author  Claude CHOISNET
** @version 2.01.012
**
** @see BasicDate
*/
public class BasicTime
    implements
        Serializable,
        Cloneable,
        TimeInterface
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Cha�ne de formatage pour la class SimpleDateFormat du
** r�sultat de la m�thode toString()
*/
protected final static String TIMEFMT  = "HH:mm:ss";

/**
** Objet de formatage SimpleDateFormat correspondant au format
** de la m�thode toString()
*/
protected final static SimpleDateFormat TIME_FMT = new SimpleDateFormat( TIMEFMT );

/** int contenant le num�ro de l'heure de 0 � 23 */
protected int hours = -1;

/** int contenant le num�ro des minutes de 0 � 59 */
protected int minutes = -1;

/** int contenant le num�ro des secondes de 0 � 59 */
protected int seconds = -1;

/** Valeur minimum pour BasicTime */
public final static BasicTime MIN_VALUE = buildBasicTime( 0, 0, 0 );

/** Valeur maximum pour BasicTime */
public final static BasicTime MAX_VALUE = buildBasicTime( 23, 59, 59 );

/**
** Construit � partir d'un autre object BasicTime
** <P>
** @param time BasicTime <B>valide</B>
*/
public BasicTime( BasicTime time ) // -----------------------------------
{
 try {
    set( time.getHours(), time.getMinutes(), time.getSeconds() );
    }
 catch( BasicTimeException e ) {
    throw new RuntimeException( "Internal error", e );
    }
}

/**
** Construit avec l'heure courante
*/
public BasicTime() // ----------------------------------------------------
{
 this( new java.util.Date() );
}

/**
** Construit un horaire BasicTime avec une date standard Java
**
** @param javadate Heure au format java.util.Date
*/
public BasicTime( java.util.Date javadate ) // ---------------------------
{
 set( javadate );
}

/**
** Construit une heure BasicTime avec la date sp�cifi�e
**
** @param hours     l'heure interval [0..23]
** @param minutes   les minutes interval [0..59]
** @param secondes  les secondes interval [0..59]
**
** @see #set( int, int, int )
**
** @exception BasicTimeException
*/
public BasicTime( int hours, int minutes, int secondes ) // --------------
    throws BasicTimeException
{
 set( hours, minutes, secondes );
}

/**
** Construit une heure BasicTime avec la date sp�cifi�e
**
** @param hours     l'heure interval [0..23]
** @param minutes   les minutes interval [0..59]
**
** @see #set( int, int, int )
**
** @exception BasicTimeException
*/
public BasicTime( int hours, int minutes ) // ----------------------------
    throws BasicTimeException
{
 set( hours, minutes, 0 );
}

/**
** Construit un horaire BasicTime du nombre de secondes depuis minuit
**
** @param secondsFormMidnight nombre de secondes depuis minuit
*/
public BasicTime( long secondsFormMidnight ) // ---------------------------
    throws BasicDateTimeNegativeValueException
{
 set( secondsFormMidnight );
}

/**
** Construit BasicTime une cha�ne au format sp�cifi�e
**
** @param time          heure sous forme de cha�ne.
** @param formatter     object SimpleDateFormat d�crivant le format de la
**                      cha�ne 'time'
**
** @exception java.text.ParseException
*/
public BasicTime( String time, SimpleDateFormat formatter ) // -----------
    throws java.text.ParseException
{
 set( formatter.parse( time ) );
}

/**
** Initialise l'objet BasicTime � partir de l'ann�e, du mois et du jour.
** <P>
** Cette m�thode lance un exception si les param�tres de la date ne sont pas
** consistant vis-�-vis du calendrier.
**
** @param hours     l'heure interval [0..23]
** @param minutes   les minutes interval [0..59]
** @param seconds   les secondes interval [0..59] (d�pend du mois et de l'ann�e)
**
** @exception BasicTimeException
*/
public void set( int hours, int minutes, int seconds ) // -----------------
    throws BasicTimeException
{
 if( (hours < 0) || (hours > 23) ) {
    throw new BasicTimeException( "invalid hours : " + hours );
    }
 if( (minutes < 0) || (minutes > 59) ) {
    throw new BasicTimeException( "invalid minutes: " + minutes );
    }
 if( (seconds < 0) || (seconds > 59) ) {
    throw new BasicTimeException( "invalid seconds : " + seconds );
    }

 this.hours     = hours;
 this.minutes   = minutes;
 this.seconds   = seconds;
}

/**
** D�finition de l'heure � partir du nombre de secondes
**
*/
protected void set( long secondsFromMidnight ) // -------------------------
    throws BasicDateTimeNegativeValueException
{
 if( secondsFromMidnight < 0 ) {
    throw new BasicDateTimeNegativeValueException();
    }

 long toMuch = secondsFromMidnight / (24 * 60 * 60);
 secondsFromMidnight -= (toMuch * (24 * 60 * 60));

 long hours = secondsFromMidnight / (60 * 60);
 secondsFromMidnight -= (hours * (60 * 60));

 long mins = secondsFromMidnight / 60;
 secondsFromMidnight -= (mins * 60);

 try {
    set( (int)hours, (int)mins, (int)secondsFromMidnight );
    }
 catch( BasicTimeException bug ) {
    throw new RuntimeException( "BasicTime.set( int secondsFormMidnight ) INTERNAL ERROR : " + bug );
    }
}

/**
** Initialise l'objet BasicTime avec une String format�e avec
** le format interne : SIMPLE_TIME_FORMAT
** <P>
*/
protected void setWithFmtString( String fmtTime ) // ----------------------
{
///** @see #getSimpleDateFormat
 // hh:mm:ss
 // 012345678
 this.hours     = Integer.parseInt( fmtTime.substring( 0, 2 ) );
 this.minutes   = Integer.parseInt( fmtTime.substring( 3, 5 ) );
 this.seconds   = Integer.parseInt( fmtTime.substring( 6 ) );
}

/**
** Initialise l'objet BasicTime � partir d'un objet java.util.Date
*/
protected void set( java.util.Date javaDate ) // --------------------------
{
 setWithFmtString( TIME_FMT.format( javaDate ) );
}

/**
** retourne les heures
**
** @return un int correspondant � la valeur des heures [0..23]
*/
public int getHours() // --------------------------------------------------
{
 return this.hours;
}

/**
** retourne les minutes
**
** @return un int correspondant � la valeur des minutes [0..59]
*/
public int getMinutes() // ------------------------------------------------
{
 return this.minutes;
}

/**
** retourne les secondes
**
** @return un int correspondant � la valeur des secondes [0..59]
*/
public int getSeconds() // ------------------------------------------------
{
 return this.seconds;
}

/**
** Surcharge de la m�thode toString() de la classe Object, permet d'utiliser
** la classe comme une cha�ne.
**
** @return l'heure au format hh:mm:ss
*/
public String toString() // -----------------------------------------------
{
 return toStringHours() + ":" + toStringMinutes() + ":" + toStringSeconds();
}

/**
** Renvoie la date associ�e au calendrier conform�ment � l'oject de formatage.
**
** @param formatter     Object SimpleDateFormat contenant les caract�ristiques
**                      du format attendu.
**
** @return l'horaire format�
*/
public String toString( Format formatter ) // -----------------------------
{
 return formatter.format( this.getJavaDate() );
}

/**
** retourne les heures dans une cha�ne sur 2 caract�res
**
** @return une String correspondant � la valeur des heures [00..23]
*/
public String toStringHours() // ------------------------------------------
{
 if( this.hours > 9 ) {
    return Integer.toString( this.hours );
    }
 else {
    return "0" + this.hours;
    }
}

/**
** retourne les minutes dans une cha�ne sur 2 caract�res
**
** @return une String correspondant � la valeur des minutes [00..59]
*/
public String toStringMinutes() // ----------------------------------------
{
 return ((this.minutes>9) ? "" : "0") + this.minutes;
}

/**
** retourne les secondes dans une cha�ne sur 2 caract�res
**
** @return une String correspondant � la valeur des secondes [00..59]
*/
public String toStringSeconds() // ----------------------------------------
{
 return ((this.seconds>9) ? "" : "0") + this.seconds;
}

/**
** retourne le nombre secondes depuis l'heure 00:00:00 correspondant � la
** valeur BasicTime de l'object
**
** @return un long correspondant le nombre secondes depuis l'heure 00:00:00
*/
public long longValue() // ------------------------------------------------
{
 return
    this.seconds +
        ( 60 *
            ( this.minutes +
                ( 60 * this.hours )
            )
        );
}

/**
** retourne la date au format java.util.Date.
**
** @return une date au format java.util.Date
**
** @see #BasicTime( java.util.Date )
*/
public java.util.Date getJavaDate() // ------------------------------------
{
 try {
    return TIME_FMT.parse( this.toString() );
    }
 catch( java.text.ParseException e ) {
    throw new RuntimeException( "BasicTime.getJavaDate() INTERNAL ERROR" );
    }
}

/**
** Compare deux BasicTime.
**
** @return true si les 2 horaires correspondent � la seconde pr�s.
*/
public boolean equals( TimeInterface anotherTime ) // ---------------------
{
 return (this.compareTo( anotherTime ) == 0);
}

/**
** @see #equals( TimeInterface )
*/
public boolean equals( Object o ) // --------------------------------------
{
 try {
    return this.equals( (TimeInterface)o );
    }
 catch( ClassCastException e ) {
    return false;
    }
}

/**
** Compare deux BasicTime.
**
** @return  la valeur 0 si les 2 horaires correspondent � la m�me heure, une
**          valeur n�gative si la date de l'object courant est plus vielle que l'horaire
**          pass� en param�tre. une valeur positive si l'horaire de l'object
**          courant est plus r�cent que l'horaire pass� en param�tre.
*/
public int compareTo( TimeInterface anotherTime ) // ----------------------
    throws ClassCastException
{
 if( anotherTime instanceof BasicTime ) {
    BasicTime aBasicTime = (BasicTime)anotherTime;

    int cmp = this.hours - aBasicTime.hours;

    if( cmp == 0 ) {
        cmp = this.minutes - aBasicTime.minutes;

        if( cmp == 0 ) {
            cmp = this.seconds - aBasicTime.seconds;
            }
        }

    return cmp;
    }
 else {
    long res = this.longValue() - anotherTime.longValue();

    if( res > 0 ) {
        return 1;
        }
    else if( res == 0 ) {
        return 0;
        }
    else {
        return -1;
        }
    }
}

/**
** @see #compareTo( TimeInterface )
public int compareTo( Object o ) // ---------------------------------------
    throws ClassCastException
{
 return compareTo( (TimeInterface)o );
}
*/

/**
** Tests if this BasicTime is before the specified time.
**
** @param anotherTime  a BasicTime.
**
** @return  true if and only if the instant of time represented by this
**          BasicTime object is strictly earlier than the instant
**          represented by when; false otherwise.
**
** @exception ClassCastException
*/
public boolean isBefore( TimeInterface anotherTime ) // -------------------
{
 return (this.compareTo( anotherTime ) > 0);
}

/**
** Tests if this BasicTime is after the specified time.
**
** @param anotherTime  a BasicTime.
**
** @return  true if and only if the instant represented by this BasicTime
**          object is strictly later than the instant represented
**          by when; false otherwise.
**
** @exception ClassCastException
*/
public boolean isAfter( TimeInterface anotherTime ) // --------------------
    throws ClassCastException
{
 return (this.compareTo( anotherTime ) < 0);
}

/**
** Addition d'heures
**
** @exception ClassCastException
** @exception BasicDateTimeNegativeValueException
*/
public TimeInterface add( TimeInterface anotherTime ) // ------------------
    throws BasicDateTimeNegativeValueException
{
 set( this.longValue() + anotherTime.longValue() );

 return this;
}

/**
** Soustraction d'heures
**
** @exception ClassCastException
*/
public TimeInterface sub( TimeInterface anotherTime ) // ------------------
    throws BasicDateTimeNegativeValueException
{
 set( this.longValue() - anotherTime.longValue() );

 return this;
}

/**
** interface java.io.Serializable
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeInt( this.seconds );
 stream.writeInt( this.minutes );
 stream.writeInt( this.hours   );
}

/**
** interface java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.seconds   = stream.readInt();
 this.minutes   = stream.readInt();
 this.hours     = stream.readInt();
}

/**
** Soustraction d'horaire.
**
** @param basicTime1    Heure de base
** @param basicTime2    Heure
**
** @return un BasicTime dont la valeur est (basicTime1 - basicTime2).
*/
public static BasicTime subtract( // --------------------------------------
    BasicTime basicTime1,
    BasicTime basicTime2
    )
    throws BasicDateTimeNegativeValueException
{
 return new BasicTime( basicTime1.longValue() - basicTime2.longValue() );
}

/**
** Construit � partir d'un autre object BasicTime
** <P>
*/
private static BasicTime buildBasicTime( // -------------------------------
    int hours,
    int minutes,
    int seconds
    )
{
 try {
    return new BasicTime( hours, minutes, seconds );
    }
 catch( BasicTimeException e ) {
    throw new RuntimeException( e );
    }
}

} // class
