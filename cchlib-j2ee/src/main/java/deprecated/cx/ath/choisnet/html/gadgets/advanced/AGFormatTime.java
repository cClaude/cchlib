package deprecated.cx.ath.choisnet.html.gadgets.advanced;

import cx.ath.choisnet.util.datetime.BasicDateTimeException;
import cx.ath.choisnet.util.datetime.BasicTime;
import java.text.SimpleDateFormat;
import javax.servlet.ServletRequest;
import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLFormException;
import deprecated.cx.ath.choisnet.html.gadgets.BGSelect;
import deprecated.cx.ath.choisnet.html.gadgets.formaters.AGFormatTimeFormater;
import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

public class AGFormatTime extends BGSelect
{
    public AGFormatTime(
            String              gadgetName, 
            SimpleDateFormat    formatter, 
            BasicTime           from,
            BasicTime           to, 
            BasicTime           timeToSelect, 
            Integer             minutesStep,
            AbstractJavascript javascript
            )
        throws HTMLDocumentException
    {
        super(
                gadgetName,
                new AGFormatTimeFormater(formatter, from, to, timeToSelect, minutesStep),
                null, 
                javascript
                );
    }

    protected BasicTime protected_getBasicTimeValue(ServletRequest request)
        throws HTMLFormException
    {
        long secondsFromMidnight = super.getLongValue(request);

        try {
            return new BasicTime(secondsFromMidnight);
        }
        catch(BasicDateTimeException e) {
            throw new HTMLFormException((new StringBuilder()).append("secondsFromMidnight = ").append(secondsFromMidnight).toString(), e);
        }
    }

    public Object getValue(ServletRequest request)
        throws HTMLFormException
    {
        return protected_getBasicTimeValue(request);
    }

    public long getLongValue(ServletRequest request)
        throws HTMLFormException
    {
        return protected_getBasicTimeValue(request).longValue();
    }

    public String getStringValue(ServletRequest request)
        throws HTMLFormException
    {
        return protected_getBasicTimeValue(request).toString();
    }
}
