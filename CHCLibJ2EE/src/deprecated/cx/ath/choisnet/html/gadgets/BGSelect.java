// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   BGSelect.java

package deprecated.cx.ath.choisnet.html.gadgets;

import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

// Referenced classes of package cx.ath.choisnet.html.gadgets:
//            AbstractBGSelect, BGSelectAbstractCollectionFormater

public class BGSelect extends AbstractBGSelect
{

    protected BGSelectAbstractCollectionFormater formater;
    protected String[] optionValue;
    protected String[] optionDatas;
    protected int      optionSelected;

    public BGSelect(
            String                              gadgetName,
            BGSelectAbstractCollectionFormater  formater, 
            Integer                             size,
            AbstractJavascript                  javascript
            )
    {
        super(gadgetName, size, javascript);

        this.optionValue = null;
        this.optionDatas = null;
        this.optionSelected = -1;
        this.formater = formater;
    }

    private void computeDatas()
    {
        if(formater != null) {
            optionValue = formater.getOptionValue();
            optionDatas = formater.getOptionDatas();
            optionSelected = formater.getOptionSelected();
        }
    }

    public int getSelectedIndex()
    {
        if(optionDatas == null)  {
            computeDatas();
        }
        
        return optionSelected;
    }

    public String[] getOptionValue()
    {
        if(optionDatas == null) {
            computeDatas();
        }
        
        return optionValue;
    }

    public String[] getOptionDatas()
    {
        if(optionDatas == null) {
            computeDatas();
        }
        
        return optionDatas;
    }
}
