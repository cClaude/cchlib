package oldies.tools.smstools.myphoneexplorer;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * SMS content
 *
 */
public class SMS implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( SMS.class );

    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd.HH-mm-ss";

    /** for entries with no date */
    private static long fakeDate = 1;

    /** Field 'from' @serial */
    private String from;
    /** Field 'to' @serial */
    private String to;
    /** Field 'body' @serial */
    private String body;
    /** Field 'time' (custom format) @serial */
    private String time;
    /** Field 'storage' @serial */
    private String storage;
    /** Field 'pdu' @serial */
    private String pdu;
    /** Extra field that identify source file @serial */
    private String xtraSource;
    /** Extra field that represent a probable date @serial */
    private Date xtraDate;

    /** Not a field, 'time' value in a Date object @serial */
    private Date computedTimeDate;
    /** Not a field, best value between 'time' and 'xtraDate' @serial */
    private Date computedDate;

    public SMS()
    {
        // Empty
    }

    /**
     * @return the from
     */
    public String getFrom()
    {
        return this.from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom( final String from )
    {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo()
    {
        return this.to;
    }

    /**
     * @param to the to to set
     */
    public void setTo( final String to )
    {
        this.to = to;
    }

    /**
     * @return the body
     */
    public String getBody()
    {
        return this.body;
    }

    /**
     * @param body the body to set
     */
    public void setBody( final String body )
    {
        this.body = body;
    }

    /**
     * @return the time
     */
    public String getTime()
    {
        return this.time;
    }

    /**
     * @param time the time to set
     */
    public void setTime( final String time )
    {
        this.time = time;
    }

    /**
     * Returns time in a Date object (unmodified)
     * @return time in a Date object (unmodified)
     */
    public Date getComputedTimeDate()
    {
        if( this.computedTimeDate == null ) {
            // time should not be null !
            if( this.time == null ) {
                throw new RuntimeException(
                    "Field 'time' is null"
                    );
            }

            if( this.time.length() == 0 ) {
                // No way to compute 'time'
                return null;
            }

            // Time should be:
            // "13/07/2010 21:45:16"
            //  01 34 6789 12 45 78 (19 chars)
            if( this.time.length() == 19 ) {
                final Calendar c = Calendar.getInstance();

                final int day         = Integer.parseInt( this.time.substring( 0, 2 ) );
                final int month       = Integer.parseInt( this.time.substring( 3, 5 ) );
                final int year        = Integer.parseInt( this.time.substring( 6, 10 ) );
                final int hourOfDay   = Integer.parseInt( this.time.substring( 11, 13 ) );
                final int minute      = Integer.parseInt( this.time.substring( 14, 16 ) );
                final int second      = Integer.parseInt( this.time.substring( 17, 19 ) );

                c.set( year, month-1, day, hourOfDay, minute, second );

                this.computedTimeDate = c.getTime();
            }
            else if( "Time".equals( this.time )){
                //Header!
                this.computedTimeDate = new Date(0);

                //Remove source
                this.setXtraSource( StringHelper.EMPTY );
            }
            else {
                throw new RuntimeException(
                    "'time' is [" + this.time + "] len=" + this.time.length()
                    );
            }
        }

        return this.computedTimeDate;
    }


    /**
     * @return the storage
     */
    public String getStorage()
    {
        return this.storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage( final String storage )
    {
        this.storage = storage;
    }

    /**
     * @return the pdu
     */
    public String getPdu()
    {
        return this.pdu;
    }

    /**
     * @param pdu the pdu to set
     */
    public void setPdu( final String pdu )
    {
        this.pdu = pdu;
    }

    /**
     * @return the xtraSource
     */
    public String getXtraSource()
    {
        return this.xtraSource;
    }

    /**
     * @param xtraSource the xtraSource to set
     */
    public void setXtraSource( final String xtraSource )
    {
        this.xtraSource = xtraSource;
    }

    /**
     * @return the xtraDate
     */
    public Date getXtraDate()
    {
        return this.xtraDate;
    }

    /**
     * @param xtraDate the xtraDate to set
     */
    public void setXtraDate( final Date xtraDate )
    {
        this.xtraDate = xtraDate;
    }

    /**
     * @param xtraDate the xtraDate to set
     * @throws ParseException if any
     */
    public void setXtraDate( final String xtraDate )
        throws ParseException
    {
        final DateFormat df = new SimpleDateFormat( DATE_FORMAT_ISO );

        this.xtraDate = df.parse( xtraDate );
    }

    /**
     * Just for sorting process
     *
     * @return the computedDate
     */
    public Date getComputedDate()
    {
        if( this.computedDate != null ) {
            return this.computedDate;
            }

        if( this.xtraDate != null ) {
            // Force to xtraDate
            this.computedDate = this.xtraDate;
            return this.computedDate;
            }

        // Try to use 'time' field
        if( getComputedTimeDate() != null ) {

            if( SMSConfig.isDateValid( this ) ) {
                this.computedDate = getComputedTimeDate();
                }
            }

        if( this.computedDate == null ) {
            LOGGER.warn( "'time' not valid [" + this.time + "] - generate fake date" );

            this.xtraDate = this.computedDate = getFakeDate();
            }

        return this.computedDate;
    }

    /**
     * Build fake unique date for sorting process
     * @return fake unique date for sorting process
     */
    private static synchronized Date getFakeDate()
    {
        return new Date( fakeDate++ );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "SMS [\"From\";\"To\";\"Body\";\"Time\";\"Storage\";\"PDU\";\"XTime\";\"XSource\"]=[" );

        try {
            appendEntry(builder);
        }
        catch( final IOException improbable ) {
            // improbable
            LOGGER.warn( "improbable", improbable );
        }

        return builder.toString();
    }

    public void appendEntry( final Appendable a ) throws IOException
    {
        a.append( '"' );
        a.append( this.from );
        a.append( "\";\"" );
        a.append( this.to );
        a.append( "\";\"" );
        a.append( this.body );
        a.append( "\";\"" );
        a.append( this.time );
        a.append( "\";\"" );
        a.append( this.storage );
        a.append( "\";\"" );
        a.append( this.pdu );

        a.append( "\";\"" );
        final Date d = getXtraDate();

        if( d != null ) {
            final DateFormat df = new SimpleDateFormat( DATE_FORMAT_ISO );

            a.append( df.format( d ) );
        }
        a.append( "\";\"" );
        a.append( this.xtraSource );
        a.append( '"' );
    }

}
