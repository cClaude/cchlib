package oldies.tools.smstools.myphoneexplorer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class SMSConfig
{
    private static String[] badDatesString = {
        "2010-07-13.21-45-16", // 13/07/2010 21:45:16
    };
    private static Date[] badDates;

    static {
        badDates = new Date[ badDatesString.length ];

        SimpleDateFormat fmt = new SimpleDateFormat( SMS.DATE_FORMAT_ISO );

        for(int i = 0;i<badDatesString.length; i++) {
            try {
                badDates[ i ] = fmt.parse( badDatesString[ i ] );
            }
            catch( ParseException e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns default value for 'from' field
     * @return default value for 'from' field
     */
    public static String getDefaultFrom()
    {
        return "CC";
    }

    /**
     * Returns default value for 'to' field
     * @return default value for 'to' field
     */
    public static String getDefaultTo()
    {
        return getDefaultFrom();
    }

    public static boolean isDateValid( SMS sms )
    {
        return isDateValid( sms.getComputedTimeDate() );
    }

    private static boolean isDateValid( Date date )
    {
        if( date == null ) {
            return false;
        }

        for(Date d:badDates) {
            if(date.equals( d )) {
                return false;
            }
        }

        return true;
    }
}
