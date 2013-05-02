package cx.ath.choisnet.util.datetime;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Simple implementation of Date, this date
 * is base on year, month and day numbers.
 */
public class BasicDate
    implements java.io.Serializable, Cloneable, DateInterface
{
    private static final long serialVersionUID = 2L;
    /** {@value} */
    protected static final String ISO_DATE_FMT = "yyyyMMdd";
    protected int year;
    protected int month;
    protected int day;

    /** For computing only - use {@link #getCalendar()} to access */
    private transient Calendar transient_calendar;

    /** For computing only - use {@link #getIsoDateFormat()} to access */
    private transient DateFormat transient_ISO_DATE_FMT;// = new SimpleDateFormat(ISO_DATE_FMT);

    /** {@value} */
    private static final long MILLISECONDS_BY_DAY = 0x5265c00L;

    /**
     * Create a BasicDate using current date.
     */
    public BasicDate()
    {
        this(new java.util.Date());
    }

    /**
     * Create a BasicDate from a other BasicDate.
     *
     * @param date BasicDate to clone.
     */
    public BasicDate(BasicDate date)
    {
//        year = -1;
//        month = -1;
//        day = -1;
        year  = date.getYear();
        month = date.getMonth();
        day   = date.getDay();
    }

    /**
     * Create a BasicDate from a {@link java.util.Date}.
     *
     * @param javaDate Date
     */
    public BasicDate(java.util.Date javaDate)
    {
//        year = -1;
//        month = -1;
//        day = -1;
        set(javaDate);
    }

    /**
     * Create a BasicDate from a {@link java.sql.Date}.
     *
     * @param sqlDate Date
     */
    public BasicDate(java.sql.Date sqlDate)
    {
//        year = -1;
//        month = -1;
//        day = -1;
        final String strDate = sqlDate.toString();

        year  = Integer.parseInt(strDate.substring(0, 4));
        month = Integer.parseInt(strDate.substring(5, 7));
        day   = Integer.parseInt(strDate.substring(8));
    }

    /**
     * @param date
     * @param fmt
     * @throws ParseException
     */
    public BasicDate(String date, SimpleDateFormat fmt)
        throws ParseException
    {
        this(fmt.parse(date));
    }

    /**
     * @param year
     * @param month
     * @param day
     * @throws BasicDateException
     *
     */
    public BasicDate(int year, int month, int day)
        throws BasicDateException
    {
//        this.year = -1;
//        this.month = -1;
//        this.day = -1;
        set(year, month, day);
    }


    /**
     * Set date.
     * @param year year, range 0 to 9999
     * @param month month, range 1 to 12
     * @param day day, range 1 to 31
     * @throws BasicDateException if one value is not
     * in a valid range
     */
    public void set(int year, int month, int day)
        throws BasicDateException
    {
        setYear(year);
        setMonth(month);

        if(day < 1 || day > 31) {
            throw new BasicDateException(
                (new StringBuilder())
                    .append("BasicDate 'day' invalid = ")
                    .append(day)
                    .toString()
                    );
        }
        else {
            this.day = day;

            check();
        }
    }

    /**
     * Set year.
     * @param year year, range 0 to 9999
     * @throws BasicDateException if value is not
     * in range
     */
    public void setYear(int year)
        throws BasicDateException
    {
        if(year < 0 || year > 9999) {
            throw new BasicDateException(
                (new StringBuilder())
                    .append("BasicDate 'year' invalid = ")
                    .append(year)
                    .toString()
                    );
        }

        this.year = year;
    }

    /**
     * Set Month
     * @param month month, range 1 to 12
     * @throws BasicDateException if value is not
     * in range
     */
    public void setMonth(int month)
        throws BasicDateException
    {
        if(month < 1 || month > 12) {
            throw new BasicDateException(
                (new StringBuilder())
                    .append("BasicDate 'month' invalid = ")
                    .append(month)
                    .toString()
                    );
        }

        this.month = month;
    }

    /**
     * Set day.
     * @param day day, range 1 to 31
     * @throws BasicDateException if value is not
     * in range
     */
    public void setDay(int day)
        throws BasicDateException
    {
        set(-1, -1, day);
    }

    /**
     * @param javaDate
     *
     */
    public void set(java.util.Date javaDate)
    {
        setWithFmtString(getIsoDateFormat().format(javaDate));
    }

    /**
     *
     */
    protected void setWithFmtString(String fmtTime)
    {
        year  = Integer.parseInt(fmtTime.substring(0, 4));
        month = Integer.parseInt(fmtTime.substring(4, 6));
        day   = Integer.parseInt(fmtTime.substring(6));
    }

    /**
     * @return year number
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @return month number
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * @return day number
     */
    public int getDay()
    {
        return day;
    }

    /**
     * @return {@link java.util.Date} view for
     * current BasicDate
     */
    public java.util.Date getJavaDate()
    {
        try {
            return getIsoDateFormat().parse(toString());
            }
        catch(java.text.ParseException bug) {
            throw new RuntimeException("BasicDate.getJavaDate() INTERNAL ERROR (ISO_DATE_FMT)");
            }
    }

    /**
     * @return {@link java.sql.Date} view for
     * current BasicDate
     */
    public java.sql.Date getSQLDate()
    {
        return new java.sql.Date(getJavaDate().getTime());
    }

    @Override
    public boolean equals(Object o)
    {
        if( o instanceof DateInterface ) {
            return compareTo((DateInterface)o) == 0;
        }
        return false;
    }

    @Override
    public boolean equals(DateInterface anotherDate)
    {
        return compareTo(anotherDate) == 0;
    }

    @Override
    public int compareTo(DateInterface anotherDate)
    {
        if(anotherDate instanceof BasicDate) {
            BasicDate aBasicDate = (BasicDate)anotherDate;

            int cmp = year - aBasicDate.year;

            if(cmp == 0) {
                cmp = month - aBasicDate.month;

                if(cmp == 0) {
                    cmp = day - aBasicDate.day;
                }
            }

            return cmp;
        }

        long res = longValue() - anotherDate.longValue();

        if(res > 0L) {
            return 1;
        }

        return res != 0L ? -1 : 0;
    }

    /**
     *
     */
    @Override
    public boolean isBefore(DateInterface anOtherDate)
        throws ClassCastException
    {
        return compareTo(anOtherDate) > 0;
    }

    /**
     *
     */
    @Override
    public boolean isAfter(DateInterface anOtherDate)
        throws ClassCastException
    {
        return compareTo(anOtherDate) < 0;
    }

    /**
     *
     */
    @Override
    public String toString()
    {
        return (new StringBuilder())
            .append(toStringYear())
            .append(toStringMonth())
            .append(toStringDay())
            .toString();
    }

    /**
     * @return year as a String of 4 digits
     */
    public String toStringYear()
    {
        String yearStr = (new StringBuilder()).append("000").append(year).toString();
        return yearStr.substring(yearStr.length() - 4);
    }

    /**
     * @return month as a String of 2 digits
     */
    public String toStringMonth()
    {
        StringBuilder sb = new StringBuilder();

        if( month <= 9 ) {
            sb.append( '0' );
        }
        return sb.append(month).toString();
    }

    /**
     * @return day as a String of 2 digits
     */
    public String toStringDay()
    {
        StringBuilder sb = new StringBuilder();

        if( day <= 9 ) {
            sb.append( '0' );
        }
        return sb.append(day).toString();
    }

    /**
     * @param formatter to use to format date
     * @return formatted String according to formatter
     */
    @Override
    public String toString(Format formatter)
    {
        return formatter.format(getJavaDate());
    }

    /**
     * @return BasicDate as a long
     */
    @Override
    public long longValue()
    {
        return getJavaDate().getTime() / MILLISECONDS_BY_DAY;
    }

    /**
     * Increment Year
     */
    public void incYear()
    {
        int year = getYear() + 1;

        if(year > 9999) {
            year = 0;
            }

        try {
            set(year, getMonth(), getDay());
            }
        catch(BasicDateException ignore) {
            throw new RuntimeException("incMonth() INTERNAL ERROR");
            }
    }

    /**
     * Increment month
     */
    public void incMonth()
    {
        int year  = getYear();
        int month = getMonth() + 1;

        if(month > 12) {
            month = 1;

            if(++year > 9999) {
                year = 0;
                }
            }

        try {
            set(year, month, getDay());
            }
        catch(BasicDateException ignore) {
            throw new RuntimeException("incMonth() INTERNAL ERROR");
            }
    }

    /**
     * Increment day
     */
    public void incDay()
    {
        Calendar c = getCalendar();
        c.set( year, month, day );
        c.add( Calendar.DAY_OF_MONTH, 1 );
        year  = c.get( Calendar.YEAR );
        month = c.get( Calendar.MONTH );
        day   = c.get( Calendar.DAY_OF_MONTH );
    }

    /**
     * @param endOfPeriod
     * @return number of day between two BasicDate
     */
    public int countOfDay(BasicDate endOfPeriod)
    {
        long msBeginOfPeriod  = getJavaDate().getTime();
        long dayBeginOfPeriod = msBeginOfPeriod / MILLISECONDS_BY_DAY;
        long msEndOfPeriod    = endOfPeriod.getJavaDate().getTime();
        long dayEndOfPeriod   = msEndOfPeriod / MILLISECONDS_BY_DAY;
        long countofday       = dayEndOfPeriod - dayBeginOfPeriod;

        return (int)countofday;
    }

    /**
     *
     */
    protected void check() throws BasicDateException
    {
        BasicDate checkDate = new BasicDate(getJavaDate());

        if(day != checkDate.getDay()) {
            throw new BasicDateException(
                (new StringBuilder())
                    .append("BasicDate 'day' invalid = ")
                    .append(toString())
                    .toString()
                    );
            }

        if(month != checkDate.getMonth()) {
            throw new BasicDateException(
                (new StringBuilder())
                    .append("BasicDate 'month' invalid = ")
                    .append(toString())
                    .toString()
                    );
            }

        if(year != checkDate.getYear()) {
            throw new BasicDateException(
                (new StringBuilder())
                    .append("BasicDate 'year' invalid = ")
                    .append(toString())
                    .toString()
                    );
            }
    }

    /**
     * Unsupported Operation
     */
    @Override
    public DateInterface add(DateInterface anotherDate)
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    /**
     * UnsupportedOperationException
     */
    @Override
    public DateInterface sub(DateInterface anotherDate)
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

//    private void writeObject(ObjectOutputStream stream)
//        throws java.io.IOException
//    {
//        stream.defaultWriteObject();
//        stream.writeInt(year);
//        stream.writeInt(month);
//        stream.writeInt(day);
//    }
//
//    private void readObject(ObjectInputStream stream)
//        throws java.io.IOException, ClassNotFoundException
//    {
//        stream.defaultReadObject();
//        year  = stream.readInt();
//        month = stream.readInt();
//        day   = stream.readInt();
//    }

    private Calendar getCalendar()
    {
        if( transient_calendar == null ) {
            transient_calendar = new GregorianCalendar();
        }
        return transient_calendar;
    }

    private DateFormat getIsoDateFormat()
    {
        if( transient_ISO_DATE_FMT == null ) {
            transient_ISO_DATE_FMT = new SimpleDateFormat(ISO_DATE_FMT);
        }
        return transient_ISO_DATE_FMT;
    }

    @Override
    public int hashCode()
    {
        // TODO Customize ?
        return super.hashCode();
    }
}
