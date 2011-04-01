package deprecated.cx.ath.choisnet.html.gadgets;

import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

public class BGSelect1 extends AbstractBGSelect
{

    protected int fromValue;
    protected int toValue;
    protected int seletedValue;
    protected String optionValue[];
    protected String optionDatas[];
    protected int optionSelected;

    public BGSelect1(
            String gadgetName, 
            int fromValue,
            int toValue, 
            int seletedValue, 
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
        optionValue = null;
    //    5    9:aload_0
    //    6   10:aconst_null
    //    7   11:putfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.BGSelect1.optionValue>
        optionDatas = null;
    //    8   14:aload_0
    //    9   15:aconst_null
    //   10   16:putfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGSelect1.optionDatas>
        optionSelected = -1;
    //   11   19:aload_0
    //   12   20:iconst_m1
    //   13   21:putfield        #4   <Field int cx.ath.choisnet.html.gadgets.BGSelect1.optionSelected>
        this.fromValue = fromValue;
    //   14   24:aload_0
    //   15   25:iload_2
    //   16   26:putfield        #5   <Field int cx.ath.choisnet.html.gadgets.BGSelect1.fromValue>
        this.toValue = toValue;
    //   17   29:aload_0
    //   18   30:iload_3
    //   19   31:putfield        #6   <Field int cx.ath.choisnet.html.gadgets.BGSelect1.toValue>
        this.seletedValue = seletedValue;
    //   20   34:aload_0
    //   21   35:iload           4
    //   22   37:putfield        #7   <Field int cx.ath.choisnet.html.gadgets.BGSelect1.seletedValue>
    //   23   40:return
    }

    private void computeDatas()
    {
        int len = (toValue - fromValue) + 1;

        optionValue = new String[len];
        optionDatas = new String[len];

        for(int i = 0; i < len; i++) {
            Integer tmpInteger = new Integer(i + fromValue);
            String tmpValue = tmpInteger.toString();

            optionValue[i] = tmpValue;
            optionDatas[i] = tmpValue;

            if(i == seletedValue) {
                optionSelected = i;
            }
        }
    }

    public int getSelectedIndex()
    {
        if(optionDatas == null) {
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
