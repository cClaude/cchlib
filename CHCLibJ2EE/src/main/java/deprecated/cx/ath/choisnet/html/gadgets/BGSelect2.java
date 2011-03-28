// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   BGSelect2.java

package deprecated.cx.ath.choisnet.html.gadgets;

import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

// Referenced classes of package cx.ath.choisnet.html.gadgets:
//            AbstractBGSelect

public class BGSelect2 extends AbstractBGSelect
{

    protected String optionValue[];
    protected String optionDatas[];
    protected int optionSelected;

    public BGSelect2(
            String gadgetName,
            String[] optionValue, 
            String[] optionDatas, 
            int optionSelected,
            Integer size, 
            AbstractJavascript javascript
            )
    {
        super(gadgetName, size, javascript);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:aload           5
    //    3    4:aload           6
    //    4    6:invokespecial   #1   <Method void AbstractBGSelect(String, Integer, cx.ath.choisnet.html.javascript.AbstractJavascript)>
        this.optionValue = optionValue;
    //    5    9:aload_0
    //    6   10:aload_2
    //    7   11:putfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.BGSelect2.optionValue>
        this.optionDatas = optionDatas;
    //    8   14:aload_0
    //    9   15:aload_3
    //   10   16:putfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGSelect2.optionDatas>
        this.optionSelected = optionSelected;
    //   11   19:aload_0
    //   12   20:iload           4
    //   13   22:putfield        #4   <Field int cx.ath.choisnet.html.gadgets.BGSelect2.optionSelected>
    //   14   25:return
    }

    public int getSelectedIndex()
    {
        return optionSelected;
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field int cx.ath.choisnet.html.gadgets.BGSelect2.optionSelected>
    //    2    4:ireturn
    }

    public String[] getOptionValue()
    {
        return optionValue;
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.BGSelect2.optionValue>
    //    2    4:areturn
    }

    public String[] getOptionDatas()
    {
        return optionDatas;
    //    0    0:aload_0
    //    1    1:getfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGSelect2.optionDatas>
    //    2    4:areturn
    }
}
