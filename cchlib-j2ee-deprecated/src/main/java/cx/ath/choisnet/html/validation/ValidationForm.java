package cx.ath.choisnet.html.validation;

import cx.ath.choisnet.util.datetime.BasicDate;
import cx.ath.choisnet.util.datetime.BasicDateTimeException;
import cx.ath.choisnet.util.datetime.BasicTime;
import java.text.SimpleDateFormat;
import java.util.Locale;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.document.Form;
import cx.ath.choisnet.html.gadgets.AbstractGadget;
import cx.ath.choisnet.html.gadgets.BGCheckbox;
import cx.ath.choisnet.html.gadgets.BGInputRadio;
import cx.ath.choisnet.html.gadgets.BGInputText;
import cx.ath.choisnet.html.gadgets.advanced.AGDate;
import cx.ath.choisnet.html.gadgets.advanced.AGFormatTime;
import cx.ath.choisnet.html.gadgets.advanced.AGTime;

public class ValidationForm extends Form
{
    protected int gadgetNumber;

    public ValidationForm(String formName)
        throws HTMLDocumentException
    {
        super(formName);

        gadgetNumber = 0;

        try {
            add("<table border=\"2\" cellspacing=\"1\" cellpadding=\"5\" width=\"100%\">");

            addTools_HTMLBuilder_AdvancedGadgets();
            addTools_HTMLBuilder_BasicGadgets();

            add("</table>");
        }
        catch(BasicDateTimeException e) {
            throw new HTMLDocumentException(e);
        }
    }

    protected String getGN()
    {
        return (new StringBuilder()).append("GN").append(gadgetNumber++).toString();
    }

    protected void addGadgets(
            AbstractGadget g, String text
            )
    {
        add("<tr><td>");
        add(g);
        add("</td><td>");
        add(text);
        add("</td></tr>");
    }

    protected void addTools_HTMLBuilder_AdvancedGadgets()
        throws BasicDateTimeException, HTMLDocumentException
    {
        addGadgets(new AGDate(getGN(), 1950, 2050, new BasicDate(), null, null, null), "AGDate( NAME, Locale.FRANCE, 1950, 2050, today, null, null, null )");
        addGadgets(new AGDate(getGN(), 1950, 2050, new BasicDate(), null, null, null), "AGDate( NAME, Locale.ENGLISH, 1950, 2050, today, null, null, null )");
        addGadgets(new AGFormatTime(getGN(), new SimpleDateFormat("hh:mm a", Locale.FRANCE), new BasicTime(7200L), new BasicTime(0x13560L), new BasicTime(), null, null), "AGFormatTime( NAME, Locale.FRANCE, 2h, 22h, now, null, null )");
        addGadgets(new AGFormatTime(getGN(), new SimpleDateFormat("hh:mm a", Locale.ENGLISH), new BasicTime(7200L), new BasicTime(0x13560L), new BasicTime(), null, null), "AGFormatTime( NAME, Locale.ENGLISH, 2h, 22h, now, null, null )");
        addGadgets(new AGTime(getGN(), 2, 22, new BasicTime(), null, null, null), "AGTime( NAME, Locale.FRANCE, 2h, 22h, now, null, null )");
    }

    protected void addTools_HTMLBuilder_BasicGadgets()
        throws HTMLDocumentException
    {
        String[] radioValue = {
            "BGInputRadio 1", "BGInputRadio 2", "BGInputRadio 3"
        };

        addGadgets(new BGInputRadio(getGN(), radioValue, radioValue, 2, null), "BGInputRadio( NAME, radioValue, radioValue, 2, null )");
        addGadgets(new BGInputText(getGN(), new Integer(6), new Integer(8), "BGInputText", null), "BGInputText( NAME, 6, 8, 'defText', null )");
        addGadgets(new BGCheckbox(getGN(), true, null), "BGCheckbox( NAME, true, null )");
    }
}
