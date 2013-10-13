/*
** $VER: TimePeriod.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/TimePeriod.java
** Description   :
**
**  2.01.012 2005.10.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.TimePeriod
**
*/
package cx.ath.choisnet.util.datetime;

/*
** <p>
** Cette classe gère les problèmes d'écart d'heure au sens commun
** (heure, minutes, secondes)
** </p>
**
** @author  Claude CHOISNET
** @version 2.01.012
**
*/
public class TimePeriod
        implements
            java.io.Serializable,
            Cloneable,
            Comparable<TimePeriod>
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private long time;

/** */
private transient long lastTime;

/** */
private transient int lastDays;

/** */
private transient int lastHours;

/** */
private transient int lastMins;

/** */
private transient int lastSecs;

/** */
private transient int lastMilli;

/**
**
*/
public TimePeriod( long millisecs ) // ------------------------------------
{
 this.time = millisecs;
}

/**
**
*/
protected void validateFields() // ----------------------------------------
{
 if( this.time != this.lastTime ) {
    long value = this.time;

    if( value < 0 ) {
        value = -value;
        }

    this.lastMilli  = (int)( value % 1000 );
    value           = value / 1000;

    this.lastSecs   = (int)( value % 60 );
    value           = value / 60;

    this.lastMins   = (int)( value % 60 );
    value           = value / 60;

    this.lastHours  = (int)( value % 24 );
    value           = value / 24;

    this.lastDays   = (int)( value );
    this.lastTime   = this.time;
    }
}

/**
**
*/
public int compareTo( TimePeriod anOtherTimePeriod ) // -------------------
{
 return (int)( this.time - anOtherTimePeriod.longValue() );
}

/**
**
*/
public boolean equals( Object object ) // ---------------------------------
{
 try {
    return compareTo( (TimePeriod)object ) == 0;
    }
 catch( ClassCastException e ) {
    return false;
    }
}


/**
** retourne le nombre milli-secondes réprésenté par la période courante.
**
** @return un long correspondant au nombre de milli-secondes.
*/
public long longValue() // ------------------------------------------------
{
 return this.time;
}

/**
**
*/
public int[] intArray() // ------------------------------------------------
{
 validateFields();

 int[] array = {
    lastDays, lastHours, lastMins, lastSecs, lastMilli
    };

 return array;
}

/**
**
*/
public Integer[] toIntegerArray() // --------------------------------------
{
 validateFields();

 Integer[] array = {
    new Integer( lastDays ),
    new Integer( lastHours ),
    new Integer( lastMins ),
    new Integer( lastSecs ),
    new Integer( lastMilli )
    };

 return array;
}

/**
**
*/
public String toString() // -----------------------------------------------
{
 validateFields();

 final StringBuilder sb = new StringBuilder();

 if( lastDays > 0 ) {
    sb.append( lastDays );
    sb.append( "d " );
    }

 if( lastHours > 0 ) {
    sb.append( lastHours );
    sb.append( "h " );
    }

 if( lastMins > 0 ) {
    sb.append( lastMins );
    sb.append( "m " );
    }

 if( lastSecs > 0 ) {
    sb.append( lastSecs );
    sb.append( "s " );
    }

 if( lastMilli > 0 ) {
    sb.append( lastMilli );
    sb.append( "ms " );
    }

 return sb.toString();
}

/**
** Ajout de périodes.
**
** @return self reference
*/
public TimePeriod add( TimePeriod addTime ) // ----------------------------
{
 this.time += addTime.longValue();

 return this;
}

/**
** Soustraction de périodes.
**
** @return self reference
*/
public TimePeriod sub( TimePeriod subTime ) // ----------------------------
{
 this.time -= subTime.longValue();

 return this;
}

/**
**
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeLong( this.time );
}

/**
**
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.time = stream.readLong();
}

} // class

