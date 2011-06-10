package cx.ath.choisnet.html.gadgets.advanced.items;

import cx.ath.choisnet.html.gadgets.BGSelect1;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class SelectYear extends BGSelect1
{
    public SelectYear(
            String name, 
            int fromYear,
            int toYear, 
            int seletedYear, 
            Integer size, 
            AbstractJavascript javascript
            )
    {
        super(name, fromYear, toYear, seletedYear, size, javascript);
    }
}
