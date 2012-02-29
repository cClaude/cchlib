package cx.ath.choisnet.util.datetime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 */
public class BasicTime
    implements Serializable, Cloneable, TimeInterface
{
    private static final long serialVersionUID = 2L;
    /**
     * TODOC
     */
    public static final BasicTime MIN_VALUE = BasicTime.buildBasicTime(0, 0, 0);
    /**
     * TODOC
     */
    public static final BasicTime MAX_VALUE = BasicTime.buildBasicTime(23, 59, 59);
    /** {@value} */
    protected static final String TIME_FMT = "HH:mm:ss";
    //protected static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("HH:mm:ss");
    /** @serial */
    protected int hours;
    /** @serial */
    protected int minutes;
    /** @serial */
    protected int seconds;

    /** For computing only - use {@link #getIsoDateFormat()} to access */
    private transient DateFormat transient_ISO_DATE_FMT;// = new SimpleDateFormat(TIMEFMT);

    /**
     * TODOC
     */
    public BasicTime(BasicTime time)
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        try {
            set(time.getHours(), time.getMinutes(), time.getSeconds());
            }
        catch(BasicTimeException e) {
            throw new RuntimeException("Internal error", e);
            }
    }

    /**
     * TODOC
     */
    public BasicTime()
    {
        this(new java.util.Date());
    }

    /**
     * TODOC
     */
    public BasicTime(java.util.Date javadate)
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(javadate);
    }

    /**
     * TODOC
     */
    public BasicTime(int hours, int minutes, int secondes)
        throws BasicTimeException
    {
        this.hours = -1;
        this.minutes = -1;
        seconds = -1;

        set(hours, minutes, secondes);
    }

    /**
     * TODOC
     */
    public BasicTime(int hours, int minutes)
        throws BasicTimeException
    {
        this.hours = -1;
        this.minutes = -1;
        seconds = -1;

        set(hours, minutes, 0);
    }

    /**
     * TODOC
     */
   public BasicTime(long secondsFormMidnight)
        throws BasicDateTimeNegativeValueException
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(secondsFormMidnight);
    }

   /**
    * TODOC
    */
    public BasicTime(String time, SimpleDateFormat formatter)
        throws java.text.ParseException
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(formatter.parse(time));
    }

    /**
     * TODOC
     */
    public void set(int hours, int minutes, int seconds)
        throws BasicTimeException
    {
        if(hours < 0 || hours > 23) {
            throw new BasicTimeException((new StringBuilder()).append("invalid hours : ").append(hours).toString());
            }

        if(minutes < 0 || minutes > 59) {
            throw new BasicTimeException((new StringBuilder()).append("invalid minutes: ").append(minutes).toString());
            }

        if(seconds < 0 || seconds > 59) {
            throw new BasicTimeException((new StringBuilder()).append("invalid seconds : ").append(seconds).toString());
            }
        else {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
            }
    }

    /**
     * TODOC
     */
    protected void set(long secondsFromMidnight)
        throws BasicDateTimeNegativeValueException
    {
        if(secondsFromMidnight < 0L) {
            throw new BasicDateTimeNegativeValueException();
            }

        long toMuch = secondsFromMidnight / 0x15180L;
        secondsFromMidnight -= toMuch * 0x15180L;

        long hours = secondsFromMidnight / 3600L;
        secondsFromMidnight -= hours * 3600L;

        long mins = secondsFromMidnight / 60L;
        secondsFromMidnight -= mins * 60L;

        try {
            set((int)hours, (int)mins, (int)secondsFromMidnight);
            }
        catch(BasicTimeException bug) {
            throw new RuntimeException((new StringBuilder()).append("BasicTime.set( int secondsFormMidnight ) INTERNAL ERROR : ").append(bug).toString());
            }

    }

    /**
     * TODOC
     */
    protected void setWithFmtString(String fmtTime)
    {
        hours   = Integer.parseInt(fmtTime.substring(0, 2));
        minutes = Integer.parseInt(fmtTime.substring(3, 5));
        seconds = Integer.parseInt(fmtTime.substring(6));
    }

    /**
     * TODOC
     */
    protected void set(java.util.Date javaDate)
    {
        setWithFmtString(getIsoDateFormat().format(javaDate));
    }

    /**
     * TODOC
     */
    public int getHours()
    {
        return hours;
    }

    /**
     * TODOC
     */
    public int getMinutes()
    {
        return minutes;
    }

    /**
     * TODOC
     */
    public int getSeconds()
    {
        return seconds;
    }

    /**
     * TODOC
     */
    public String toString()
    {
        return (new StringBuilder())
            .append(toStringHours())
            .append(':')
            .append(toStringMinutes())
            .append(':')
            .append(toStringSeconds()
                    ).toString();
    }

    /**
     * TODOC
     */
    public String toString(Format formatter)
    {
        return formatter.format(getJavaDate());
    }

    /**
     * TODOC
     */
    public String toStringHours()
    {
        if(hours > 9) {
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
     * TODOC
     */
    public String toStringMinutes()
    {
        return (new StringBuilder()).append(minutes <= 9 ? "0" : "").append(minutes).toString();
    }

    /**
     * TODOC
     */
    public String toStringSeconds()
    {
        return (new StringBuilder()).append(seconds <= 9 ? "0" : "").append(seconds).toString();
    }

    /**
     * TODOC
     */
    public long longValue()
    {
        return (long)(seconds + 60 * (minutes + 60 * hours));
    }

    /**
     * TODOC
     */
    public java.util.Date getJavaDate()
    {
        try {
            return getIsoDateFormat().parse(toString());
            }
        catch(java.text.ParseException e) {
            throw new RuntimeException("BasicTime.getJavaDate() INTERNAL ERROR");
            }
    }

    @Override
    public boolean equals(TimeInterface anotherTime)
    {
        return compareTo(anotherTime) == 0;
    }

    @Override
    public boolean equals(Object o)
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
        if(anotherTime instanceof BasicTime) {
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
     * TODOC
     */
    public boolean isBefore(TimeInterface anotherTime)
    {
        return compareTo(anotherTime) > 0;
    }

    /**
     * TODOC
     */
    public boolean isAfter(TimeInterface anotherTime)
        throws ClassCastException
    {
        return compareTo(anotherTime) < 0;
    }

    /**
     * TODOC
     */
    public TimeInterface add(TimeInterface anotherTime)
        throws BasicDateTimeNegativeValueException
    {
        set(longValue() + anotherTime.longValue());

        return this;
    }

    /**
     * TODOC
     */
    public TimeInterface sub(TimeInterface anotherTime)
        throws BasicDateTimeNegativeValueException
    {
        set(longValue() - anotherTime.longValue());

        return this;
    }

//    private void writeObject(ObjectOutputStream stream)
//        throws java.io.IOException
//    {
//        stream.defaultWriteObject();
//        stream.writeInt(seconds);
//        stream.writeInt(minutes);
//        stream.writeInt(hours);
//    }
//
//    private void readObject(ObjectInputStream stream)
//        throws java.io.IOException, ClassNotFoundException
//    {
//        stream.defaultReadObject();
//        seconds = stream.readInt();
//        minutes = stream.readInt();
//        hours = stream.readInt();
//    }

    /**
     * TODOC
     */
    public static BasicTime subtract(BasicTime basicTime1, BasicTime basicTime2)
        throws BasicDateTimeNegativeValueException
    {
        return new BasicTime(basicTime1.longValue() - basicTime2.longValue());
    }

    /**
     * TODOC
     */
    private static BasicTime buildBasicTime(int hours, int minutes, int seconds)
    {
        try {
            return new BasicTime(hours, minutes, seconds);
            }
        catch(BasicTimeException e) {
            throw new RuntimeException(e);
            }
    }

    /**
     * TODOC
     */
    private DateFormat getIsoDateFormat()
    {
        if( transient_ISO_DATE_FMT == null ) {
            transient_ISO_DATE_FMT = new SimpleDateFormat(TIME_FMT);
        	}
        return transient_ISO_DATE_FMT;
    }

}
