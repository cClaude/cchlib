package deprecated.cx.ath.choisnet.html.gadgets.advanced;

import cx.ath.choisnet.util.datetime.BasicDate;
import cx.ath.choisnet.util.datetime.BasicDateException;
import java.util.Locale;
import javax.servlet.ServletRequest;
import deprecated.cx.ath.choisnet.html.AbstractFormHTML;
import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;
import deprecated.cx.ath.choisnet.html.HTMLFormException;
import deprecated.cx.ath.choisnet.html.gadgets.AbstractGadget;
import deprecated.cx.ath.choisnet.html.gadgets.advanced.items.SelectDay;
import deprecated.cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth;
import deprecated.cx.ath.choisnet.html.gadgets.advanced.items.SelectYear;
import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

public class AGDate extends AbstractGadget
    implements AbstractFormHTML
{

    protected SelectYear gadgetSelectYear_;
    protected SelectMonth gadgetSelectMonth;
    protected SelectDay gadgetSelectDay__;

    public AGDate(
            String gadgetName, 
            int fromYear,
            int toYear,
            BasicDate dateToSelect, 
            AbstractJavascript javascriptYear,
            AbstractJavascript javascriptMonth,
            AbstractJavascript javascriptDay
            )
        throws HTMLDocumentException
    {
        super(gadgetName);

        gadgetSelectYear_ = null;
        gadgetSelectMonth = null;
        gadgetSelectDay__ = null;
        int yearToSelect = -1;
        int monthToSelect = -1;
        int dayToSelect = -1;

        if(dateToSelect != null) {
            int dateToSelectYear = dateToSelect.getYear();

            if(fromYear <= dateToSelectYear && toYear >= dateToSelectYear) {
                yearToSelect = dateToSelect.getYear();
                monthToSelect = dateToSelect.getMonth();
                dayToSelect = dateToSelect.getDay();
            }
        }
        
        gadgetSelectYear_ = new SelectYear((new StringBuilder()).append("YEAR_").append(gadgetName).toString(), fromYear, toYear, yearToSelect, null, javascriptYear);
        gadgetSelectMonth = new SelectMonth((new StringBuilder()).append("MONTH_").append(gadgetName).toString(), monthToSelect, null, javascriptMonth);
        gadgetSelectDay__ = new SelectDay((new StringBuilder()).append("DAY_").append(gadgetName).toString(), 1, 31, dayToSelect, null, javascriptDay);
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        out.write("<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\"><tr><td>");

        if(out.getLocale().getDisplayLanguage().equals(Locale.FRENCH.getDisplayLanguage())) {
            gadgetSelectDay__.writeHTML(out);

            out.write("</td><td>");
            gadgetSelectMonth.writeHTML(out);
            out.write("</td><td>");

            gadgetSelectYear_.writeHTML(out);
        }
        else {
            gadgetSelectMonth.writeHTML(out);

            out.write("</td><td>");

            gadgetSelectDay__.writeHTML(out);

            out.write("</td><td>");

            gadgetSelectYear_.writeHTML(out);
        }

        out.write("</td></tr></table>\n");
    }

    public String getHiddenHTMLDatas()
    {
        StringBuilder b = new StringBuilder();

        b.append(gadgetSelectYear_.getHiddenHTMLDatas());
        b.append(gadgetSelectMonth.getHiddenHTMLDatas());
        b.append(gadgetSelectDay__.getHiddenHTMLDatas());

        return b.toString();
    }

    public BasicDate getBasicDateValue(ServletRequest request)
        throws HTMLFormException, BasicDateException
    {
        long year = gadgetSelectYear_.getLongValue(request);
        long month = gadgetSelectMonth.getLongValue(request);
        long day = gadgetSelectDay__.getLongValue(request);

        return new BasicDate((int)year, (int)month, (int)day);
    }

    public BasicDate getBasicDateValue(javax.servlet.ServletRequest request, cx.ath.choisnet.util.datetime.BasicDate defaultValue)
        throws BasicDateException
    {
        try {
            return getBasicDateValue(request);
        }
        catch(HTMLFormException e) {
            return new BasicDate(defaultValue);
        }
    }

    protected BasicDate protected_getBasicDateValue(javax.servlet.ServletRequest request)
        throws HTMLFormException
    {
        try {
            return getBasicDateValue(request);
        }
        catch(BasicDateException e) {
            throw new HTMLFormException("BasicDateException", e);
        }
    }

    public Object getValue(ServletRequest request)
        throws HTMLFormException
    {
        return protected_getBasicDateValue(request);
    }

    public long getLongValue(ServletRequest request)
        throws HTMLFormException
    {
        return protected_getBasicDateValue(request).longValue();
    }

    public String getStringValue(ServletRequest request)
        throws HTMLFormException
    {
        return protected_getBasicDateValue(request).toString();
    }
}
