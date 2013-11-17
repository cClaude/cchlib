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
    private final static Logger LOGGER = Logger.getLogger( SMS.class );

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

    /**
     *
     */
    public SMS()
    {
    }

    /**
     * @return the from
     */
    public String getFrom()
    {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom( String from )
    {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo()
    {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo( String to )
    {
        this.to = to;
    }

    /**
     * @return the body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody( String body )
    {
        this.body = body;
    }

    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime( String time )
    {
        this.time = time;
    }

    /**
     * Returns time in a Date object (unmodified)
     * @return time in a Date object (unmodified)
     */
    public Date getComputedTimeDate()
    {
        if( computedTimeDate == null ) {
            // time should not be null !
            if( time == null ) {
                throw new RuntimeException(
                    "Field 'time' is null"
                    );
            }

            if( time.length() == 0 ) {
                // No way to compute 'time'
                return null;
            }

            // Time should be:
            // "13/07/2010 21:45:16"
            //  01 34 6789 12 45 78 (19 chars)
            if( time.length() == 19 ) {
                Calendar c = Calendar.getInstance();

                int day         = Integer.parseInt( time.substring( 0, 2 ) );
                int month       = Integer.parseInt( time.substring( 3, 5 ) );
                int year        = Integer.parseInt( time.substring( 6, 10 ) );
                int hourOfDay   = Integer.parseInt( time.substring( 11, 13 ) );
                int minute      = Integer.parseInt( time.substring( 14, 16 ) );
                int second      = Integer.parseInt( time.substring( 17, 19 ) );

                c.set( year, month-1, day, hourOfDay, minute, second );

                computedTimeDate = c.getTime();
            }
            else if( "Time".equals( time )){
                //Header!
                computedTimeDate = new Date(0);

                //Remove source
                this.setXtraSource( StringHelper.EMPTY );
            }
            else {
                throw new RuntimeException(
                    "'time' is [" + time + "] len=" + time.length()
                    );
            }
        }

        return computedTimeDate;
    }


    /**
     * @return the storage
     */
    public String getStorage()
    {
        return storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage( String storage )
    {
        this.storage = storage;
    }

    /**
     * @return the pdu
     */
    public String getPdu()
    {
        return pdu;
    }

    /**
     * @param pdu the pdu to set
     */
    public void setPdu( String pdu )
    {
        this.pdu = pdu;
    }

    /**
     * @return the xtraSource
     */
    public String getXtraSource()
    {
        return xtraSource;
    }

    /**
     * @param xtraSource the xtraSource to set
     */
    public void setXtraSource( String xtraSource )
    {
        this.xtraSource = xtraSource;
    }

    /**
     * @return the xtraDate
     */
    public Date getXtraDate()
    {
        return xtraDate;
    }

    /**
     * @param xtraDate the xtraDate to set
     */
    public void setXtraDate( Date xtraDate )
    {
        this.xtraDate = xtraDate;
    }

    /**
     * @param xtraDate the xtraDate to set
     * @throws ParseException
     */
    public void setXtraDate( String xtraDate )
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
        if( computedDate != null ) {
            return computedDate;
            }

        if( xtraDate != null ) {
            // Force to xtraDate
            computedDate = xtraDate;
            return computedDate;
            }

        // Try to use 'time' field
        if( getComputedTimeDate() != null ) {
            //Date cTimeDate = getComputedTimeDate();

            if( SMSConfig.isDateValid( this ) ) {
                computedDate = getComputedTimeDate();
                }
            }

        if( computedDate == null ) {
            LOGGER.warn( "'time' not valid [" + time + "] - generate fake date" );

            xtraDate = computedDate = getFakeDate();
            }

        return computedDate;
    }

    /**
     * Build fake unique date for sorting process
     * @return fake unique date for sorting process
     */
    private synchronized static Date getFakeDate()
    {
        return new Date( fakeDate++ );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "SMS [\"From\";\"To\";\"Body\";\"Time\";\"Storage\";\"PDU\";\"XTime\";\"XSource\"]=[" );

        try {
            appendEntry(builder);
        }
        catch( IOException improbable ) {
            //improbable
            improbable.printStackTrace();
        }

        return builder.toString();
    }

    public void appendEntry( Appendable a ) throws IOException
    {
//        final DateFormat df0 = new SimpleDateFormat( DATE_FORMAT_ISO );
//        //a.append( df0.format( getComputedTimeDate() ) );
//        a.append( "" + getComputedTimeDate() );
//        a.append( ' ' );
//        a.append( df0.format( getComputedDate() ) );
//        a.append( ' ' );

        a.append( '"' );
        a.append( from );
        a.append( "\";\"" );
        a.append( to );
        a.append( "\";\"" );
        a.append( body );
        a.append( "\";\"" );
        a.append( time );
        a.append( "\";\"" );
        a.append( storage );
        a.append( "\";\"" );
        a.append( pdu );

        a.append( "\";\"" );
        final Date d = getXtraDate();

        if( d != null ) {
            final DateFormat df = new SimpleDateFormat( DATE_FORMAT_ISO );

            a.append( df.format( d ) );
        }
        a.append( "\";\"" );
        a.append( xtraSource );
        a.append( '"' );
    }

}
