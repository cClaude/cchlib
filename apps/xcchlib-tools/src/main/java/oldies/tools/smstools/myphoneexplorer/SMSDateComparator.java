package oldies.tools.smstools.myphoneexplorer;

import java.util.Comparator;

public class SMSDateComparator implements Comparator<SMS>
{
    @Override
    public int compare( final SMS sms0, final SMS sms1 )
    {
        return sms0.getComputedDate().compareTo(
                sms1.getComputedDate()
                );
    }
}
