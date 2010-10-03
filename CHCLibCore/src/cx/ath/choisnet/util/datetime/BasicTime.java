package cx.ath.choisnet.util.datetime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class BasicTime
    implements java.io.Serializable, Cloneable, TimeInterface
{
    private static final long serialVersionUID = 1L;
    protected static final String TIMEFMT = "HH:mm:ss";
    protected static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("HH:mm:ss");
    /** @serial */
    protected int hours;
    /** @serial */
    protected int minutes;
    /** @serial */
    protected int seconds;
    public static final BasicTime MIN_VALUE = BasicTime.buildBasicTime(0, 0, 0);
    public static final BasicTime MAX_VALUE = BasicTime.buildBasicTime(23, 59, 59);

    public BasicTime(BasicTime time)
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        try {
            set(time.getHours(), time.getMinutes(), time.getSeconds());
        }
        catch(cx.ath.choisnet.util.datetime.BasicTimeException e) {
            throw new RuntimeException("Internal error", e);
        }
    }

    public BasicTime()
    {
        this(new Date());
    }

    public BasicTime(Date javadate)
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(javadate);
    }

    public BasicTime(int hours, int minutes, int secondes)
        throws cx.ath.choisnet.util.datetime.BasicTimeException
    {
        this.hours = -1;
        this.minutes = -1;
        seconds = -1;

        set(hours, minutes, secondes);
    }

    public BasicTime(int hours, int minutes)
        throws cx.ath.choisnet.util.datetime.BasicTimeException
    {
        this.hours = -1;
        this.minutes = -1;
        seconds = -1;

        set(hours, minutes, 0);
    }

    public BasicTime(long secondsFormMidnight)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(secondsFormMidnight);
    }

    public BasicTime(String time, SimpleDateFormat formatter)
        throws java.text.ParseException
    {
        hours = -1;
        minutes = -1;
        seconds = -1;

        set(formatter.parse(time));
    }

    public void set(int hours, int minutes, int seconds)
        throws cx.ath.choisnet.util.datetime.BasicTimeException
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

    protected void set(long secondsFromMidnight)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException
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
        catch(cx.ath.choisnet.util.datetime.BasicTimeException bug) {
            throw new RuntimeException((new StringBuilder()).append("BasicTime.set( int secondsFormMidnight ) INTERNAL ERROR : ").append(bug).toString());
        }

    }

    protected void setWithFmtString(String fmtTime)
    {
        hours = Integer.parseInt(fmtTime.substring(0, 2));
        minutes = Integer.parseInt(fmtTime.substring(3, 5));
        seconds = Integer.parseInt(fmtTime.substring(6));
    }

    protected void set(Date javaDate)
    {
        setWithFmtString(TIME_FMT.format(javaDate));
    }

    public int getHours()
    {
        return hours;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public String toString()
    {
        return (new StringBuilder()).append(toStringHours()).append(":").append(toStringMinutes()).append(":").append(toStringSeconds()).toString();
    }

    public String toString(java.text.Format formatter)
    {
        return formatter.format(getJavaDate());

    }

    public String toStringHours()
    {
        if(hours > 9) {
            return Integer.toString(hours);
        }
        else {
            return (new StringBuilder()).append("0").append(hours).toString();
        }
    }

    public String toStringMinutes()
    {
        return (new StringBuilder()).append(minutes <= 9 ? "0" : "").append(minutes).toString();
    }

    public String toStringSeconds()
    {
        return (new StringBuilder()).append(seconds <= 9 ? "0" : "").append(seconds).toString();
    }

    public long longValue()
    {
        return (long)(seconds + 60 * (minutes + 60 * hours));
    }

    public java.util.Date getJavaDate()
    {
        try {
            return TIME_FMT.parse(toString());
        }
        catch(java.text.ParseException e) {
            throw new RuntimeException("BasicTime.getJavaDate() INTERNAL ERROR");
        }
    }

    public boolean equals(TimeInterface anotherTime)
    {
        return compareTo(anotherTime) == 0;
    }

    public boolean equals(Object o)
    {
        try {
            return equals((TimeInterface)o);
        }
        catch(ClassCastException e) {
            return false;
        }
    }

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

    public boolean isBefore(TimeInterface anotherTime)
    {
        return compareTo(anotherTime) > 0;
    }

    public boolean isAfter(TimeInterface anotherTime)
        throws ClassCastException
    {
        return compareTo(anotherTime) < 0;
    }

    public TimeInterface add(TimeInterface anotherTime)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException
    {
        set(longValue() + anotherTime.longValue());

        return this;
    }

    public TimeInterface sub(TimeInterface anotherTime)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException
    {
        set(longValue() - anotherTime.longValue());

        return this;
    }

    private void writeObject(ObjectOutputStream stream)
        throws java.io.IOException
    {
        stream.defaultWriteObject();
        stream.writeInt(seconds);
        stream.writeInt(minutes);
        stream.writeInt(hours);
    }

    private void readObject(ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        seconds = stream.readInt();
        minutes = stream.readInt();
        hours = stream.readInt();
    }

    public static BasicTime subtract(BasicTime basicTime1, BasicTime basicTime2)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException
    {
        return new BasicTime(basicTime1.longValue() - basicTime2.longValue());
    }

    private static BasicTime buildBasicTime(int hours, int minutes, int seconds)
    {
        try {
            return new BasicTime(hours, minutes, seconds);

        }
        catch(cx.ath.choisnet.util.datetime.BasicTimeException e) {
            throw new RuntimeException(e);
        }
    }
}
