// $codepro.audit.disable
package cx.ath.choisnet.html.gadgets.formaters;

import java.util.Locale;
import cx.ath.choisnet.html.gadgets.BGSelectAbstractCollectionFormater;

public class SelectMinutesFormater extends BGSelectAbstractCollectionFormater
{

    protected int selectedItem;

    public SelectMinutesFormater(
            Locale locale,
            int from,
            int to,
            int step,
            int selectedValue
            )
    {
        super(to - from);
    //    0    0:aload_0
    //    1    1:iload_3
    //    2    2:iload_2
    //    3    3:isub
    //    4    4:invokespecial   #1   <Method void BGSelectAbstractCollectionFormater(int)>
        selectedItem = -1;
    //    5    7:aload_0
    //    6    8:iconst_m1
    //    7    9:putfield        #2   <Field int cx.ath.choisnet.html.gadgets.formaters.SelectMinutesFormater.selectedItem>
        int max = to - from;
    //    8   12:iload_3
    //    9   13:iload_2
    //   10   14:isub
    //   11   15:istore          6
        for(int i = 0; i < max; i += step)
    //*  12   17:iconst_0
    //*  13   18:istore          7
    //*  14   20:iload           7
    //*  15   22:iload           6
    //*  16   24:icmpge          111
        {
            int value = from + i;
    //   17   27:iload_2
    //   18   28:iload           7
    //   19   30:iadd
    //   20   31:istore          8
            String strValue = String.valueOf(value);
    //   21   33:iload           8
    //   22   35:invokestatic    #3   <Method String String.valueOf(int)>
    //   23   38:astore          9
            super.optionDatas[i] = (value <= 9) ? (new StringBuilder()).append('0').append(strValue).toString() : strValue;
    //   24   40:aload_0
    //   25   41:getfield        #4   <Field String[] cx.ath.choisnet.html.gadgets.BGSelectAbstractCollectionFormater.optionDatas>
    //   26   44:iload           7
    //   27   46:iload           8
    //   28   48:bipush          9
    //   29   50:icmple          58
    //   30   53:aload           9
    //   31   55:goto            78
    //   32   58:new             #5   <Class StringBuilder>
    //   33   61:dup
    //   34   62:invokespecial   #6   <Method void StringBuilder()>
    //   35   65:ldc1            #7   <String "0">
    //   36   67:invokevirtual   #8   <Method StringBuilder StringBuilder.append(String)>
    //   37   70:aload           9
    //   38   72:invokevirtual   #8   <Method StringBuilder StringBuilder.append(String)>
    //   39   75:invokevirtual   #9   <Method String StringBuilder.toString()>
    //   40   78:aastore
            super.optionValue[i] = strValue;
    //   41   79:aload_0
    //   42   80:getfield        #10  <Field String[] cx.ath.choisnet.html.gadgets.BGSelectAbstractCollectionFormater.optionValue>
    //   43   83:iload           7
    //   44   85:aload           9
    //   45   87:aastore
            if(selectedValue == value)
    //*  46   88:iload           5
    //*  47   90:iload           8
    //*  48   92:icmpne          101
            {
                selectedItem = selectedValue;
    //   49   95:aload_0
    //   50   96:iload           5
    //   51   98:putfield        #2   <Field int cx.ath.choisnet.html.gadgets.formaters.SelectMinutesFormater.selectedItem>
            }
        }

    //   52  101:iload           7
    //   53  103:iload           4
    //   54  105:iadd
    //   55  106:istore          7
    //*  56  108:goto            20
    //   57  111:return
    }

    @Override
    public int getOptionSelected()
    {
        return selectedItem;
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field int cx.ath.choisnet.html.gadgets.formaters.SelectMinutesFormater.selectedItem>
    //    2    4:ireturn
    }
}
