package cx.ath.choisnet.util.datetime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.googlecode.cchlib.Const;

/**
 *
 * <br>
 * This class is not thread save.
 */
public class BasicTime
    implements Serializable, Cloneable, TimeInterface
{
    private static final long serialVersionUID = 2L;
    /**
     * Min value for {@link BasicTime}: 00:00:00
     */
    public static final BasicTime MIN_VALUE = BasicTime.buildBasicTime(0, 0, 0);
    /**
     * Max value for {@link BasicTime}: 23:59:59
     */
    public static final BasicTime MAX_VALUE = BasicTime.buildBasicTime(23, 59, 59);
    /** {@value} */
    protected static final String TIME_FMT = "HH:mm:ss";
    /** @serial */
    protected int hours;
    /** @serial */
    protected int minutes;
    /** @serial */
    protected int seconds;

    /** For computing only - use {@link #getIsoDateFormat()} to access */
    private transient DateFormat transient_ISO_DATE_FMT;// = new SimpleDateFormat(TIMEFMT);

    /**
     * Create a {@link BasicTime} from a another
     */
    public BasicTime( final BasicTime time )
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        try {
            set( time.getHours(), time.getMinutes(), time.getSeconds() );
            }
        catch( BasicTimeException e ) {
            throw createInternalErrorException( e );
            }
    }

    /**
     * Create a {@link BasicTime} using current time.
     */
    public BasicTime()
    {
        this( new Date() );
    }

    /**
     * Create a {@link BasicTime} using giving {@link Date}.
     *
     * @param javadate {@link Date} to use to get time
     */
    public BasicTime( final Date javadate )
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set( javadate );
    }

    /**
     * Create a {@link BasicTime}
     *
     * @param hours      Hours to set
     * @param minutes    Minutes to set
     * @param seconds    Seconds to set
     * @throws BasicTimeException if any value is out of range
     */
    public BasicTime( int hours, int minutes, int seconds )
        throws BasicTimeException
    {
        this.hours = -1;
        this.minutes = -1;
        this.seconds = -1;

        set(hours, minutes, seconds);
    }

    /**
     * Create a {@link BasicTime}
     *
     * @param hours      Hours to set
     * @param minutes    Minutes to set
     * @throws BasicTimeException if any value is out of range
     */
    public BasicTime(int hours, int minutes)
            throws BasicTimeException
        {
            this.hours = -1;
            this.minutes = -1;
            this.seconds = -1;

            set(hours, minutes, 0);
        }

    /**
     * Create a {@link BasicTime}
     *
     * @param secondsFormMidnight
     * @throws BasicDateTimeNegativeValueException if value is negative
     * @throws BasicTimeException if value is out of range
     */
    public BasicTime(long secondsFormMidnight)
            throws BasicDateTimeNegativeValueException,
                   BasicTimeException
        {
            hours = -1;
            minutes = -1;
            seconds = -1;

            set(secondsFormMidnight);
        }

   /**
     * 
     *
     * @param time
     * @param formatter
     * @throws ParseException
     */
    public BasicTime(
        final String     time,
        final DateFormat formatter
        )
        throws ParseException
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(formatter.parse(time));
    }

    /**
     * Set news values for this {@link BasicTime}
     *
     * @param hours      Hours to set
     * @param minutes    Minutes to set
     * @param seconds    Seconds to set
     * @throws BasicTimeException if any value is out of range
     */
    public void set(int hours, int minutes, int seconds)
        throws BasicTimeException
    {
        if( hours < 0 || hours > 23 ) {
            throw new BasicTimeException( "invalid hours : " + hours );
            }

        if( minutes < 0 || minutes > 59 ) {
            throw new BasicTimeException( "invalid minutes: " + minutes );
            }

        if( seconds < 0 || seconds > 59 ) {
            throw new BasicTimeException( "invalid seconds : " + seconds );
            }
        else {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            }
    }

    /**
     * 
     *
     * @param secondsFromMidnight
     * @throws BasicDateTimeNegativeValueException if value is negative
     * @throws BasicTimeException if value is greater than {@link BasicTime#MAX_VALUE}.
     */
    protected void set( long secondsFromMidnight )
        throws BasicDateTimeNegativeValueException, BasicTimeException
    {
        if( secondsFromMidnight < 0L ) {
            throw new BasicDateTimeNegativeValueException();
            }

        long toMuch = secondsFromMidnight / 0x15180L;
        secondsFromMidnight -= toMuch * 0x15180L;

        long hours = secondsFromMidnight / 3600L;
        secondsFromMidnight -= hours * 3600L;

        long mins = secondsFromMidnight / 60L;
        secondsFromMidnight -= mins * 60L;

        set( (int)hours, (int)mins, (int)secondsFromMidnight );
    }

    /**
     * 
     */
    protected void setWithFmtString( String fmtTime )
    {
        hours   = Integer.parseInt(fmtTime.substring(0, 2));
        minutes = Integer.parseInt(fmtTime.substring(3, 5));
        seconds = Integer.parseInt(fmtTime.substring(6));
    }

    /**
     * 
     */
    protected void set( final Date javaDate )
    {
        setWithFmtString( getIsoDateFormat().format( javaDate ) );
    }

    /**
     * 
     */
    public int getHours()
    {
        return hours;
    }

    /**
     * 
     */
    public int getMinutes()
    {
        return minutes;
    }

    /**
     * 
     */
    public int getSeconds()
    {
        return seconds;
    }

    /**
     * 
     */
    @Override
    public String toString()
    {
        return toStringHours() + ':' + toStringMinutes() + ':' + toStringSeconds();
    }

    /**
     * 
     */
    @Override
    public String toString( final Format formatter )
    {
        return formatter.format( getJavaDate() );
    }

    /**
     * 
     */
    public String toStringHours()
    {
        if( hours > 9 ) {
            return Integer.toString(hours);
            }
        else {
            return (new StringBuilder())
                .append('0')
                .append(hours)
                .toString();
            }
    }

    /**
     * 
     */
    public String toStringMinutes()
    {
        return (new StringBuilder()).append(minutes <= 9 ? "0" : Const.EMPTY_STRING).append(minutes).toString();
    }

    /**
     * 
     */
    public String toStringSeconds()
    {
        return (new StringBuilder()).append(seconds <= 9 ? "0" : Const.EMPTY_STRING).append(seconds).toString();
    }

    /**
     * 
     */
    @Override
    public long longValue()
    {
        return (long)(seconds + 60 * (minutes + 60 * hours));
    }

    /**
     * 
     */
    public Date getJavaDate()
    {
        try {
            return getIsoDateFormat().parse( toString() );
            }
        catch( ParseException e ) {
            throw createInternalErrorException( e );
            }
    }

    @Override
    public boolean equals( TimeInterface anotherTime )
    {
        return compareTo(anotherTime) == 0;
    }

    @Override
    public boolean equals( Object o )
    {
        if( o instanceof TimeInterface ) {
            return compareTo( TimeInterface.class.cast( o ) ) == 0;
            }
        else {
            return false;
            }
    }

    @Override
    public int compareTo(TimeInterface anotherTime)
        throws ClassCastException
    {
        if( anotherTime instanceof BasicTime ) {
            BasicTime aBasicTime = (BasicTime)anotherTime;

            int cmp = hours - aBasicTime.hours;
            if(cmp == 0) {
                cmp = minutes - aBasicTime.minutes;

                if(cmp == 0) {
                    cmp = seconds - aBasicTime.seconds;
                }
            }

            return cmp;
        }

        long res = longValue() - anotherTime.longValue();

        if(res > 0L) {
            return 1;
        }

        return res != 0L ? -1 : 0;
    }

    /**
     * 
     */
    @Override
    public boolean isBefore(TimeInterface anotherTime)
    {
        return compareTo(anotherTime) > 0;
    }

    /**
     * 
     */
    @Override
    public boolean isAfter(TimeInterface anotherTime)
        throws ClassCastException
    {
        return compareTo(anotherTime) < 0;
    }

    /**
     * 
     * @throws BasicTimeException
     */
    public TimeInterface add(TimeInterface anotherTime)
        throws BasicDateTimeNegativeValueException, BasicTimeException
    {
        set(longValue() + anotherTime.longValue());

        return this;
    }

    /**
     * 
     * @throws BasicTimeException
     */
    public TimeInterface sub(TimeInterface anotherTime)
        throws BasicDateTimeNegativeValueException, BasicTimeException
    {
        set(longValue() - anotherTime.longValue());

        return this;
    }

    public static BasicTime subtract(BasicTime basicTime1, BasicTime basicTime2)
        throws BasicDateTimeNegativeValueException, BasicTimeException
    {
        return new BasicTime(basicTime1.longValue() - basicTime2.longValue());
    }

    /**
     * @return {@link DateFormat} according to ISO time standard
     */
    private DateFormat getIsoDateFormat()
    {
        if( transient_ISO_DATE_FMT == null ) {
            transient_ISO_DATE_FMT = new SimpleDateFormat( TIME_FMT );
            }
        return transient_ISO_DATE_FMT;
    }

    @Override
    public int hashCode()
    {
        // TODO customize ?
        return super.hashCode();
    }

    private static BasicTime buildBasicTime(int hours, int minutes, int seconds)
    {
        try {
            return new BasicTime(hours, minutes, seconds);
            }
        catch( BasicTimeException e ) {
            throw createInternalErrorException( e );
            }
    }

    private static RuntimeException createInternalErrorException( Throwable e )
    {
        throw new RuntimeException("INTERNAL ERROR");
    }
}
