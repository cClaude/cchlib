package cx.ath.choisnet.util.datetime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * Cette classe gère les problèmes d'écart d'heure au
 * sens commun (heure, minutes, secondes)
 *
 * @since 2.01
 */
public class TimePeriod implements Serializable, Comparable<TimePeriod>
{
    private static final long serialVersionUID = 1L;

    private long              time;

    private transient long    lastTime;
    private transient int     lastDays;
    private transient int     lastHours;
    private transient int     lastMins;
    private transient int     lastSecs;
    private transient int     lastMilli;

    public TimePeriod( final long millisecs )
    {
        this.time = millisecs;
    }

    protected void validateFields()
    {
        if( this.time != this.lastTime ) {
            long value = this.time;

            if( value < 0 ) {
                value = -value;
            }

            this.lastMilli = (int)(value % 1000);
            value = value / 1000;

            this.lastSecs = (int)(value % 60);
            value = value / 60;

            this.lastMins = (int)(value % 60);
            value = value / 60;

            this.lastHours = (int)(value % 24);
            value = value / 24;

            this.lastDays = (int)(value);
            this.lastTime = this.time;
        }
    }

    @Override
    public int compareTo( final TimePeriod anOtherTimePeriod )
    {
        return (int)(this.time - anOtherTimePeriod.longValue());
    }

    /**
     * retourne le nombre milli-secondes représenté par la période courante.
     *
     * @return un long correspondant au nombre de milli-secondes.
     */
    public long longValue()
    {
        return this.time;
    }

    int[] intArray()
    {
        validateFields();

        return new int[]{
                this.lastDays,
                this.lastHours,
                this.lastMins,
                this.lastSecs,
                this.lastMilli
                };
    }

    Integer[] toIntegerArray()
    {
        validateFields();

        return new Integer[] {
                Integer.valueOf( this.lastDays ),
                Integer.valueOf( this.lastHours ),
                Integer.valueOf( this.lastMins ),
                Integer.valueOf( this.lastSecs ),
                Integer.valueOf( this.lastMilli )
                };
    }

    @Override
    public String toString() // -----------------------------------------------
    {
        validateFields();

        final StringBuilder sb = new StringBuilder();

        if( this.lastDays > 0 ) {
            sb.append( this.lastDays );
            sb.append( "d " );
        }

        if( this.lastHours > 0 ) {
            sb.append( this.lastHours );
            sb.append( "h " );
        }

        if( this.lastMins > 0 ) {
            sb.append( this.lastMins );
            sb.append( "m " );
        }

        if( this.lastSecs > 0 ) {
            sb.append( this.lastSecs );
            sb.append( "s " );
        }

        if( this.lastMilli > 0 ) {
            sb.append( this.lastMilli );
            sb.append( "ms " );
        }

        return sb.toString();
    }

    /**
     * Add {@code addTime} to current value
     *
     * @param addTime Value to add
     * @return self reference
     */
    public TimePeriod add( final TimePeriod addTime )
    {
        this.time += addTime.longValue();

        return this;
    }

    /**
     * Subtract {@code subTime} to current value
     *
     * @param subTime Value to subtract
     * @return self reference
     */
    public TimePeriod sub( final TimePeriod subTime )
    {
        this.time -= subTime.longValue();

        return this;
    }

    private void writeObject( final ObjectOutputStream stream ) throws IOException
    {
        stream.defaultWriteObject();

        stream.writeLong( this.time );
    }

    private void readObject( final ObjectInputStream stream )
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        this.time = stream.readLong();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (int)(this.time ^ (this.time >>> 32));
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
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final TimePeriod other = (TimePeriod)obj;
        if( this.time != other.time ) {
            return false;
        }
        return true;
    }
} // class

