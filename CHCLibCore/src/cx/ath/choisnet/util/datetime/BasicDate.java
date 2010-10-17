package cx.ath.choisnet.util.datetime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Claude CHOISNET
 */
public class BasicDate
    implements java.io.Serializable, Cloneable, DateInterface
{
    private static final long serialVersionUID = 1L;
    /** {@value} */
    protected static final String DATEFMT = "yyyyMMdd";
    protected static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyyMMdd");
    protected transient int year;
    protected transient int month;
    protected transient int day;
    /** {@value} */
    private static final long MILLISECONDS_BY_DAY = 0x5265c00L;

    /**
     * 
     */
    public BasicDate()
    {
        this(new java.util.Date());
    }

    /**
     * @param javaDate 
     * 
     */
    public BasicDate(java.util.Date javaDate)
    {
        year = -1;
        month = -1;
        day = -1;

        set(javaDate);
    }

    /**
     * @param date 
     * 
     */
    public BasicDate(BasicDate date)
    {
        year = -1;
        month = -1;
        day = -1;

        year = date.getYear();
        month = date.getMonth();
        day = date.getDay();
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
        this.year = -1;
        this.month = -1;
        this.day = -1;

        set(year, month, day);
    }

    /**
     * @param sqlDate 
     * 
     */
    public BasicDate(java.sql.Date sqlDate)
    {
        year = -1;
        month = -1;
        day = -1;

        String strDate = sqlDate.toString();

        year = Integer.parseInt(strDate.substring(0, 4));
        month = Integer.parseInt(strDate.substring(5, 7));
        day = Integer.parseInt(strDate.substring(8));
    }

    /**
     * @param year 
     * @param month 
     * @param day 
     * @throws BasicDateException 
     * 
     */
    public void set(int year, int month, int day)
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

        if(month < 1 || month > 12) {
            throw new BasicDateException((new StringBuilder()).append("BasicDate 'month' invalid = ").append(month).toString());
        }

        this.month = month;

        if(day < 1 || day > 31) {
            throw new BasicDateException((new StringBuilder()).append("BasicDate 'day' invalid = ").append(day).toString());
        }
        else {
            this.day = day;

            check();
            return;
        }
    }

    /**
     * @param year 
     * @throws BasicDateException 
     * 
     */
    public void setYear(int year)
        throws BasicDateException
    {
        set(year, -1, -1);
    }

    /**
     * @param month 
     * @throws BasicDateException 
     * 
     */
    public void setMonth(int month)
        throws BasicDateException
    {
        set(-1, month, -1);
    }

    /**
     * @param day 
     * @throws BasicDateException 
     * 
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
        setWithFmtString(DATE_FMT.format(javaDate));
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
     * @return 
     */
    public int getDay()
    {
        return day;
    }

    /**
     * @return 
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * @return 
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @return 
     */
    public java.util.Date getJavaDate()
    {
        try {
            return DATE_FMT.parse(toString());
        }
        catch(java.text.ParseException bug) {
            throw new RuntimeException("BasicDate.getJavaDate() INTERNAL ERROR");
        }
    }

    /**
     * @return 
     */
    public java.sql.Date getSQLDate()
    {
        return new java.sql.Date(getJavaDate().getTime());
    }

    @Override
    public boolean equals(Object o)
    {
        try {
            return equals((DateInterface)o);

        }
        catch(ClassCastException e) {
            return false;
        }
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
    public boolean isBefore(DateInterface anOtherDate)
        throws ClassCastException
    {
        return compareTo(anOtherDate) > 0;
    }

    /**
     * 
     */
    public boolean isAfter(DateInterface anOtherDate)
        throws ClassCastException
    {
        return compareTo(anOtherDate) < 0;
    }

    /**
     * 
     */
    public String toString()
    {
        return (new StringBuilder())
            .append(toStringYear())
            .append(toStringMonth())
            .append(toStringDay())
            .toString();
    }
    
    /**
     * @return 
     */
    public String toStringYear()
    {
        String yearStr = (new StringBuilder()).append("000").append(year).toString();
        return yearStr.substring(yearStr.length() - 4);
    }

    /**
     * @return 
     */
    public String toStringMonth()
    {
        return (new StringBuilder()).append(month <= 9 ? "0" : "").append(month).toString();
    }

    /**
     * @return 
     */
    public String toStringDay()
    {
        return (new StringBuilder()).append(day <= 9 ? "0" : "").append(day).toString();
    }

    /**
     * @return 
     */
    public String toString(Format formatter)
    {
        return formatter.format(getJavaDate());
    }

    /**
     * @return 
     */
    public long longValue()
    {
        return getJavaDate().getTime() / MILLISECONDS_BY_DAY;
    }

    /**
     * 
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
     * 
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
     * @param endOfPeriod 
     * @return 
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
    public DateInterface add(DateInterface anotherDate)
        throws UnsupportedOperationException
    {
        //throw new RuntimeException("$$$$ NOT YET IMPLEMENTED $$$$");
        throw new UnsupportedOperationException();
    }

    /**
     * UnsupportedOperationException
     */
    public DateInterface sub(DateInterface anotherDate)
        throws UnsupportedOperationException
    {
        //throw new RuntimeException("$$$$ NOT YET IMPLEMENTED $$$$");
        throw new UnsupportedOperationException();
    }

    private void writeObject(ObjectOutputStream stream)
        throws java.io.IOException
    {
        stream.defaultWriteObject();
        stream.writeInt(year);
        stream.writeInt(month);
        stream.writeInt(day);
    }

    private void readObject(ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        year = stream.readInt();
        month = stream.readInt();
        day = stream.readInt();
    }
}
