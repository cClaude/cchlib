package cx.ath.choisnet.html.gadgets.special;

import java.util.Date;

public class SGUnicNumber extends SGHidden
{

    public SGUnicNumber()
    {
        this("UNIC");
    }

    public SGUnicNumber(String gadgetName)
    {
        super(gadgetName, String.valueOf((new Date()).getTime()));
    }
}
