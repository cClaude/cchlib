// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   SelectDay.java

package deprecated.cx.ath.choisnet.html.gadgets.advanced.items;

import deprecated.cx.ath.choisnet.html.gadgets.BGSelect1;
import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

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
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:iload_2
    //    3    3:iload_3
    //    4    4:iload           4
    //    5    6:iconst_1
    //    6    7:isub
    //    7    8:aload           5
    //    8   10:aload           6
    //    9   12:invokespecial   #1   <Method void BGSelect1(String, int, int, int, Integer, cx.ath.choisnet.html.javascript.AbstractJavascript)>
    //   10   15:return
    }
}
