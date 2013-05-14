package cx.ath.choisnet.util.datetime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 */
public class TimePeriod implements Serializable, Cloneable, Comparable<TimePeriod>
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private long time;
    private transient long lastTime;
    private transient int lastDays;
    private transient int lastHours;
    private transient int lastMins;
    private transient int lastSecs;
    private transient int lastMilli;

    public TimePeriod(long millisecs)
    {
        time = millisecs;
    }

    protected void validateFields()
    {
        if(time != lastTime) {
            long value = time;

            if(value < 0L) {
                value = -value;
            }

            lastMilli = (int)(value % 1000L);
            value /= 1000L;
            lastSecs = (int)(value % 60L);
            value /= 60L;
            lastMins = (int)(value % 60L);
            value /= 60L;
            lastHours = (int)(value % 24L);
            value /= 24L;
            lastDays = (int)value;
            lastTime = time;
        }

    }

    @Override
    public int compareTo(TimePeriod anOtherTimePeriod)
    {
        return (int)(time - anOtherTimePeriod.longValue());

    }

    @Override
    public boolean equals(Object object) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
    {
        if( object instanceof TimePeriod ) {
            return compareTo( TimePeriod.class.cast( object )) == 0;
        }
        return false;
    }

    public long longValue()
    {
        return time;
    }

    public int[] intArray()
    {
        validateFields();

        int array[] = { lastDays, lastHours, lastMins, lastSecs, lastMilli };

        return array;
    }

    public Integer[] toIntegerArray()
    {
        validateFields();

        Integer array[] = {
            new Integer(lastDays), new Integer(lastHours), new Integer(lastMins), new Integer(lastSecs), new Integer(lastMilli)
        };

        return array;
    }

    @Override
    public String toString()
    {
        validateFields();

        StringBuilder sb = new StringBuilder();

        if(lastDays > 0) {
            sb.append(lastDays);
            sb.append("d ");
        }

        if(lastHours > 0) {
            sb.append(lastHours);
            sb.append("h ");
        }

        if(lastMins > 0){
            sb.append(lastMins);
            sb.append("m ");
        }

        if(lastSecs > 0) {
            sb.append(lastSecs);
            sb.append("s ");
        }

        if(lastMilli > 0) {
            sb.append(lastMilli);
            sb.append("ms ");
        }

        return sb.toString();
    }

    public TimePeriod add(TimePeriod addTime)
    {
        time += addTime.longValue();

        return this;
    }

    public TimePeriod sub(TimePeriod subTime)
    {
        time -= subTime.longValue();

        return this;
    }

    private void writeObject(ObjectOutputStream stream)
        throws java.io.IOException
    {
        stream.defaultWriteObject();
        stream.writeLong(time);
    }

    private void readObject(ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        time = stream.readLong();
    }

    @Override
    public int hashCode()
    {
        // TODO Customize ?
        return super.hashCode();
    }
}
