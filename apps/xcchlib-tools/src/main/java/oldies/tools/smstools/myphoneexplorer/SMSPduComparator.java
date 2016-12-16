package oldies.tools.smstools.myphoneexplorer;

import java.util.Comparator;

public class SMSPduComparator implements Comparator<SMS>
{
    @Override
    public int compare( final SMS sms0, final SMS sms1 )
    {
        if( sms0 == null ) {
            if( sms1 == null ) {
                return 0;
            }
            return Integer.MIN_VALUE;
        }
        if( sms1 == null ) {
            return Integer.MAX_VALUE;
        }

        if( sms0.getPdu() == null ) {
            if( sms1.getPdu() == null ) {
                return 0;
            }
            return Integer.MIN_VALUE;
        }
        if( sms1.getPdu() == null ) {
            return Integer.MAX_VALUE;
        }

        return sms0.getPdu().compareTo(
                    sms1.getPdu()
                    );
    }
}
