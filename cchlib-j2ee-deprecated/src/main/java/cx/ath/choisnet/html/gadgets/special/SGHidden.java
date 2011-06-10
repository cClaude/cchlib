package cx.ath.choisnet.html.gadgets.special;

import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.gadgets.BGInputText;

public class SGHidden extends BGInputText
{

    public SGHidden(String gadgetName, String value)
    {
        super(gadgetName, null, null, value, null);
    }

    public SGHidden(String gadgetName, int value)
    {
        super(gadgetName, null, null, String.valueOf(value), null);
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        out.write(getHiddenHTMLDatas());
    }
}
