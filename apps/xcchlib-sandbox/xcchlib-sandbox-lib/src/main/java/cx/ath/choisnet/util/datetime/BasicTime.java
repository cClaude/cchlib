package cx.ath.choisnet.util.datetime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * {@link BasicTime} handle commons Time : (hours, minutes, seconds)
 *
 * @since 1.00
 *
 * @see BasicDate
 */
public class BasicTime implements Serializable, TimeInterface<BasicTime>
{
    private static final long serialVersionUID = 1L;

    /**
     * Chaene de formatage pour la class SimpleDateFormat du resultat de la methode toString()
     */
    protected static final String           TIMEFMT          = "HH:mm:ss";

    /**
     * Objet de formatage SimpleDateFormat correspondant au format de la methode toString()
     */
    private final SimpleDateFormat timeFormat = new SimpleDateFormat( TIMEFMT );

    private int hours   = -1; // [0...23]
    private int minutes = -1; // [0...59]
    private int seconds = -1; // [0...59]

    /** Valeur minimum pour BasicTime */
    public static final BasicTime           MIN_VALUE        = buildBasicTime( 0, 0, 0 );

    /** Valeur maximum pour BasicTime */
    public static final BasicTime           MAX_VALUE        = buildBasicTime( 23, 59, 59 );

    /**
     * Build a {@link BasicTime} based on a other one
     *
     * @param time
     *            A valid {@link BasicTime}
     */
    public BasicTime( @Nonnull final BasicTime time )
    {
        try {
            set( time.getHours(), time.getMinutes(), time.getSeconds() );
        }
        catch( final BasicTimeException e ) {
            throw new BasicDateTimeRuntimeException( "Internal error", e );
        }
    }

    /**
     ** Construit avec l'heure courante
     */
    public BasicTime()
    {
        this( new java.util.Date() );
    }

    /**
     ** Construit un horaire BasicTime avec une date standard Java
     **
     ** @param javadate
     *            Heure au format java.util.Date
     */
    public BasicTime( final Date javadate )
    {
        set( javadate );
    }

    /**
     ** Construit une heure BasicTime avec la date specifiee
     **
     ** @param hours
     *            l'heure interval [0..23]
     ** @param minutes
     *            les minutes interval [0..59]
     ** @param secondes
     *            les secondes interval [0..59]
     **
     ** @see #set(int, int, int )
     **
     ** @exception BasicTimeException if any
     */
    public BasicTime( final int hours, final int minutes, final int secondes )
        throws BasicTimeException
    {
        set( hours, minutes, secondes );
    }

    /**
     * Construit une heure BasicTime avec la date specifiee
     *
     * @param hours
     *            l'heure interval [0..23]
     * @param minutes
     *            les minutes interval [0..59]
     *
     * @see #set(int, int, int )
     *
     * @exception BasicTimeException if any
     */
    public BasicTime( final int hours, final int minutes ) throws BasicTimeException
    {
        set( hours, minutes, 0 );
    }

    /**
     * Construit un horaire BasicTime du nombre de secondes depuis minuit
     *
     * @param secondsFormMidnight
     *            nombre de secondes depuis minuit
     *
     * @throws BasicDateTimeNegativeValueException if any
     */
    public BasicTime( final long secondsFormMidnight )
        throws BasicDateTimeNegativeValueException
    {
        set( secondsFormMidnight );
    }

    /**
     * Create a {@link BasicTime} based on a String.
     *
     * @param time
     *            Time in String format.
     * @param formatter
     *            {@link SimpleDateFormat} object to decode {@code time}
     *
     * @exception ParseException if {@code time} is no valid
     */
    public BasicTime( final String time, final SimpleDateFormat formatter ) // -----------
        throws ParseException
    {
        set( formatter.parse( time ) );
    }

    /**
     * Initialise l'objet BasicTime e partir de l'annee, du mois et du jour.
     * <p>
     * Cette methode lance un exception si les parametres de la date ne sont
     * pas consistant vis-e-vis du calendrier.
     *
     * @param hours
     *            l'heure interval [0..23]
     * @param minutes
     *            les minutes interval [0..59]
     * @param seconds
     *            les secondes interval [0..59] (depend du mois et de l'annee)
     *
     * @exception BasicTimeException if any
     */
    protected void set( final int hours, final int minutes, final int seconds )
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

        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /*
     * Definition de l'heure e partir du nombre de secondes
     */
    protected void set( @Nonnegative final long secondsFromMidnight )
        throws BasicDateTimeNegativeValueException
    {
        if( secondsFromMidnight < 0 ) {
            throw new BasicDateTimeNegativeValueException(
                "secondsFromMidnight = " + secondsFromMidnight
                );
        }
        long secondsToSet = secondsFromMidnight;

        final long toMuch = secondsToSet / (24 * 60 * 60);
        secondsToSet -= (toMuch * (24 * 60 * 60));

        final long hoursToSet = secondsToSet / (60 * 60);
        secondsToSet -= (hoursToSet * (60 * 60));

        final long minutesToSet = secondsToSet / 60;
        secondsToSet -= (minutesToSet * 60);

        try {
            set( (int)hoursToSet, (int)minutesToSet, (int)secondsToSet );
        }
        catch( final BasicTimeException bug ) {
            throw new BasicDateTimeRuntimeException(
                    "BasicTime.set( int secondsFormMidnight ) INTERNAL ERROR",
                    bug
                    );
        }
    }

    /*
     * Set {@link BasicTime} with a formated String
     * <p>
     * Format is "hh?mm?ss". ie: "hh:mm:ss", "hh-mm-ss", "hh.mm.ss", ...
     */
    protected void setWithFmtString( final String fmtTime ) // ----------------------
    {
        // /** @see #getSimpleDateFormat
        // hh:mm:ss
        // 012345678
        this.hours   = Integer.parseInt( fmtTime.substring( 0, 2 ) );
        this.minutes = Integer.parseInt( fmtTime.substring( 3, 5 ) );
        this.seconds = Integer.parseInt( fmtTime.substring( 6 ) );
    }

    /*
     * Initialise l'objet BasicTime e partir d'un objet {@link java.util.Date}
     *
     * @param javaDate {@link Date} to set
     */
    protected void set( final Date javaDate )
    {
        setWithFmtString( this.timeFormat.format( javaDate ) );
    }

    /**
     * Returns hours
     *
     * @return hours as an int [0..23]
     */
    public int getHours()
    {
        return this.hours;
    }

    /**
     * Returns minutes
     *
     * @return minutes as an int [0..59]
     */
    public int getMinutes()
    {
        return this.minutes;
    }

    /**
     * Returns secondes
     *
     * @return secondes as an int [0..59]
     */
    public int getSeconds()
    {
        return this.seconds;
    }

    /**
     * @return Time as a formatted String "hh:mm:ss"
     */
    @Override
    public String toString()
    {
        return toStringHours() + ":" + toStringMinutes() + ":" + toStringSeconds();
    }

    /**
     ** Renvoie la date associee au calendrier conformement e l'oject de formatage.
     **
     ** @param formatter
     *            Object SimpleDateFormat contenant les caracteristiques du format attendu.
     **
     ** @return l'horaire formate
     */
    @Override
    public String toString( final Format formatter )
    {
        return formatter.format( this.getJavaDate() );
    }

    /**
     * retourne les heures dans une chaene sur 2 caracteres
     *
     * @return une String correspondant e la valeur des heures [00..23]
     */
    public String toStringHours()
    {
        if( this.hours > 9 ) {
            return Integer.toString( this.hours );
        } else {
            return "0" + this.hours;
        }
    }

    /**
     * retourne les minutes dans une chaene sur 2 caracteres
     *
     * @return une String correspondant e la valeur des minutes [00..59]
     */
    public String toStringMinutes()
    {
        return ((this.minutes > 9) ? "" : "0") + this.minutes;
    }

    /**
     * retourne les secondes dans une chaene sur 2 caracteres
     *
     * @return une String correspondant e la valeur des secondes [00..59]
     */
    public String toStringSeconds()
    {
        return ((this.seconds > 9) ? "" : "0") + this.seconds;
    }

    /**
     * retourne le nombre secondes depuis l'heure 00:00:00 correspondant e la valeur BasicTime de l'object
     *
     * @return un long correspondant le nombre secondes depuis l'heure 00:00:00
     */
    @Override
    public long longValue()
    {
        return this.seconds + (60L * (this.minutes + (60L * this.hours)));
    }

    /**
     * retourne la date au format java.util.Date.
     *
     * @return une date au format java.util.Date
     *
     * @see #BasicTime(java.util.Date )
     */
    public java.util.Date getJavaDate()
    {
        try {
            return this.timeFormat.parse( this.toString() );
        }
        catch( final ParseException e ) {
            throw new BasicDateTimeRuntimeException( "BasicTime.getJavaDate() INTERNAL ERROR", e );
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return true if both {@link BasicTime} are equals
     */
    @Override
    public boolean isEqualTo( final BasicTime anotherTime )
    {
        return this.compareTo( anotherTime ) == 0;
    }

    /**
     * Compare deux BasicTime.
     *
     * @return la valeur 0 si les 2 horaires correspondent e la meme heure, une valeur negative si la date de l'object
     *         courant est plus vielle que l'horaire passe en parametre. une valeur positive si l'horaire de l'object
     *         courant est plus recent que l'horaire passe en parametre.
     */
    @Override
    public int compareTo( final BasicTime anotherTime )
    {
        final BasicTime aBasicTime = anotherTime;

        int cmp = this.hours - aBasicTime.hours;

        if( cmp == 0 ) {
            cmp = this.minutes - aBasicTime.minutes;

            if( cmp == 0 ) {
                cmp = this.seconds - aBasicTime.seconds;
            }
        }

        return cmp;
    }

    /**
     * Tests if this BasicTime is before the specified time.
     *
     * @param anotherTime
     *            a BasicTime.
     *
     * @return true if and only if the instant of time represented by this BasicTime object is strictly earlier than the
     *         instant represented by when; false otherwise.
     *
     */
    @Override
    public boolean isBefore( final BasicTime anotherTime )
    {
        return this.compareTo( anotherTime ) > 0;
    }

    /**
     * Tests if this BasicTime is after the specified time.
     *
     * @param anotherTime
     *            a BasicTime.
     *
     * @return true if and only if the instant represented by this BasicTime object
     *         is strictly later than the instant represented by when; false otherwise.
     */
    @Override
    public boolean isAfter( final BasicTime anotherTime )
    {
        return this.compareTo( anotherTime ) < 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicTime add( final BasicTime anotherTime ) throws BasicDateTimeException
    {
        set( this.longValue() + anotherTime.longValue() );

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicTime sub( final BasicTime anotherTime ) throws BasicDateTimeException
    {
        set( this.longValue() - anotherTime.longValue() );

        return this;
    }

    /*
     * interface java.io.Serializable
     */
    private void writeObject( final ObjectOutputStream stream ) throws IOException
    {
        stream.defaultWriteObject();

        stream.writeInt( this.seconds );
        stream.writeInt( this.minutes );
        stream.writeInt( this.hours );
    }

    /*
     * interface java.io.Serializable
     */
    private void readObject( final ObjectInputStream stream )
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        this.seconds = stream.readInt();
        this.minutes = stream.readInt();
        this.hours = stream.readInt();
    }

    /**
     ** Soustraction d'horaire.
     **
     ** @param basicTime1
     *            Heure de base
     ** @param basicTime2
     *            Heure
     *
     * @return un BasicTime dont la valeur est (basicTime1 - basicTime2).
     * @throws BasicDateTimeNegativeValueException if {@code basicTime1} &lt; {@code basicTime2}
     */
    public static BasicTime subtract(
        final BasicTime basicTime1,
        final BasicTime basicTime2
        ) throws BasicDateTimeNegativeValueException
    {
        return new BasicTime( basicTime1.longValue() - basicTime2.longValue() );
    }

    private static BasicTime buildBasicTime(
        final int hours,
        final int minutes,
        final int seconds
        )
    {
        try {
            return new BasicTime( hours, minutes, seconds );
        }
        catch( final BasicTimeException e ) {
            throw new BasicDateTimeRuntimeException( e );
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.hours;
        result = (prime * result) + this.minutes;
        result = (prime * result) + this.seconds;
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( !(obj instanceof BasicTime) ) {
            return false;
        }
        final BasicTime other = (BasicTime)obj;
        if( this.hours != other.hours ) {
            return false;
        }
        if( this.minutes != other.minutes ) {
            return false;
        }
        if( this.seconds != other.seconds ) {
            return false;
        }
        return true;
    }
}
