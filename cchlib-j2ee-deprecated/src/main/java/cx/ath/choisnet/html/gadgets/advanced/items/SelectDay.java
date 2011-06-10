package cx.ath.choisnet.html.gadgets.advanced.items;

import cx.ath.choisnet.html.gadgets.BGSelect1;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class SelectDay extends BGSelect1
{
    public SelectDay(
            String gadgetName,
            int beginDay,
            int endDay, 
            int dayToSelect,
            Integer size, 
            AbstractJavascript javascript
            )
    {
        super(gadgetName, beginDay, endDay, dayToSelect - 1, size, javascript);
    }
}
