package cx.ath.choisnet.html.gadgets.advanced;

import cx.ath.choisnet.util.datetime.BasicTime;
import cx.ath.choisnet.util.datetime.BasicTimeException;
import java.util.Locale;
import javax.servlet.ServletRequest;
import cx.ath.choisnet.html.AbstractFormHTML;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.HTMLFormException;
import cx.ath.choisnet.html.gadgets.AbstractGadget;
import cx.ath.choisnet.html.gadgets.BGSelect;
import cx.ath.choisnet.html.gadgets.advanced.items.SelectDay;
import cx.ath.choisnet.html.gadgets.formaters.SelectMinutesFormater;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class AGTime extends AbstractGadget
    implements AbstractFormHTML
{
    protected SelectDay gadgetSelectSeconds;
    protected BGSelect gadgetSelectAmPm___;
    protected Integer minutesStep;
    protected int minuteToSelect;
    protected AbstractJavascript javascriptMinutes;
    protected int hourToSelect;
    protected int fromHour;
    protected int toHour;
    protected AbstractJavascript javascriptHours;

    public AGTime(
            String              gadgetName, 
            int                 fromHour, 
            int                 toHour, 
            BasicTime           timeToSelect, 
            Integer             minutesStep,
            AbstractJavascript  javascriptHours,
            AbstractJavascript javascriptMinutes
            )
        throws HTMLDocumentException
    {
        super(gadgetName);

        gadgetSelectSeconds = null;
        gadgetSelectAmPm___ = null;

        int hourToSelect = -1;
        int minuteToSelect = -1;
        
        if(timeToSelect != null) {
            int timeToSelectHour = timeToSelect.getHours();

            if(fromHour <= timeToSelectHour && toHour >= timeToSelectHour) {
                hourToSelect = timeToSelect.getHours();
                minuteToSelect = timeToSelect.getMinutes();
            }
        }
        
        this.minutesStep = minutesStep;
        this.minuteToSelect = minuteToSelect;
        this.javascriptMinutes = javascriptMinutes;
        this.hourToSelect = hourToSelect;
        this.fromHour = fromHour;
        this.toHour = toHour;
        this.javascriptHours = javascriptHours;
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        Locale locale = out.getLocale();

        SelectDay gadgetSelectHours   = buildGadgetSelectHours();
        BGSelect  gadgetSelectMinutes = buildGadgetSelectMinutes(locale);

        out.write("<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\"><tr><td>");

        gadgetSelectHours.writeHTML(out);

        out.write("</td><td>:</td><td>");

        gadgetSelectMinutes.writeHTML(out);

        out.write("</td></tr></table>\n");
    }

    public String getHiddenHTMLDatas()
    {
        SelectDay gadgetSelectHours  = buildGadgetSelectHours();
        BGSelect  gadgetSelectMinutes = buildGadgetSelectMinutes(null);

        return (new StringBuilder()).append(gadgetSelectHours.getHiddenHTMLDatas()).append(gadgetSelectMinutes.getHiddenHTMLDatas()).toString();
    }

    protected SelectDay buildGadgetSelectHours()
    {
        return new SelectDay((new StringBuilder()).append("HOURS_").append(gadgetName).toString(), fromHour, toHour, hourToSelect, null, javascriptHours);
    }

    protected BGSelect buildGadgetSelectMinutes(java.util.Locale locale)
    {
        SelectMinutesFormater minsFmt = new SelectMinutesFormater(locale, 0, 59, minutesStep != null ? minutesStep.intValue() : 1, minuteToSelect);
 
        return new BGSelect((new StringBuilder()).append("MINS_").append(gadgetName).toString(), minsFmt, null, javascriptMinutes);
    }

    public BasicTime protected_getBasicTimeValue(ServletRequest request)
        throws HTMLFormException
    {
        SelectDay gadgetSelectHours   = buildGadgetSelectHours();
        BGSelect  gadgetSelectMinutes = buildGadgetSelectMinutes(null);

        try {
            int hours = (int)gadgetSelectHours.getLongValue(request);
            int minutes = (int)gadgetSelectMinutes.getLongValue(request);
            int seconds = gadgetSelectSeconds != null ? (int)gadgetSelectSeconds.getLongValue(request) : 0;

            if(gadgetSelectAmPm___ != null) {
                int amPmFactor = (int)gadgetSelectAmPm___.getLongValue(request);

                if(amPmFactor > 0) {
                    hours += 12;
                }
            }
            
            return new BasicTime(hours, minutes, seconds);
        }
        catch(BasicTimeException e) {
            throw new HTMLFormException(e);
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
