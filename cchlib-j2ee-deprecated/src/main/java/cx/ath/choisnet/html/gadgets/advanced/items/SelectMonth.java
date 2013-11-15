package cx.ath.choisnet.html.gadgets.advanced.items;

import cx.ath.choisnet.util.datetime.BasicDate;
import java.text.SimpleDateFormat;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.gadgets.AbstractBGSelect;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class SelectMonth extends AbstractBGSelect
{
    protected String[] optionValue;
    protected int optionSelected;
    protected java.util.Locale locale;

    public SelectMonth(
            String gadgetName,
            int monthToSelect,
            Integer size,
            AbstractJavascript javascript
            )
        throws HTMLDocumentException
    {
        super(gadgetName, size, javascript);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:aload_3
    //    3    3:aload           4
    //    4    5:invokespecial   #1   <Method void AbstractBGSelect(String, Integer, cx.ath.choisnet.html.javascript.AbstractJavascript)>
        optionValue = null;
    //    5    8:aload_0
    //    6    9:aconst_null
    //    7   10:putfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionValue>
        locale = null;
    //    8   13:aload_0
    //    9   14:aconst_null
    //   10   15:putfield        #3   <Field java.util.Locale cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.locale>
        optionSelected = monthToSelect != -1 ? monthToSelect - 1 : -1;
    //   11   18:aload_0
    //   12   19:iload_2
    //   13   20:iconst_m1
    //   14   21:icmpne          28
    //   15   24:iconst_m1
    //   16   25:goto            31
    //   17   28:iload_2
    //   18   29:iconst_1
    //   19   30:isub
    //   20   31:putfield        #4   <Field int cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionSelected>
    //   21   34:return
    }

    private void computeDatas()
    {
        optionValue = new String[12];
    //    0    0:aload_0
    //    1    1:bipush          12
    //    2    3:anewarray       String[]
    //    3    6:putfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionValue>
        for(int m = 1; m < 13; m++)
    //*   4    9:iconst_1
    //*   5   10:istore_1
    //*   6   11:iload_1
    //*   7   12:bipush          13
    //*   8   14:icmpge          44
        {
            int i = m - 1;
    //    9   17:iload_1
    //   10   18:iconst_1
    //   11   19:isub
    //   12   20:istore_2
            optionValue[i] = Integer.toString(m);
    //   13   21:aload_0
    //   14   22:getfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionValue>
    //   15   25:iload_2
    //   16   26:new             #6   <Class Integer>
    //   17   29:dup
    //   18   30:iload_1
    //   19   31:invokespecial   #7   <Method void Integer(int)>
    //   20   34:invokevirtual   #8   <Method String Integer.toString()>
    //   21   37:aastore
        }

    //   22   38:iinc            1  1
    //*  23   41:goto            11
    //   24   44:return
    }

    @Override
    public int getSelectedIndex()
    {
        if(optionValue == null)
    //*   0    0:aload_0
    //*   1    1:getfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionValue>
    //*   2    4:ifnonnull       11
        {
            computeDatas();
    //    3    7:aload_0
    //    4    8:invokespecial   #9   <Method void cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.computeDatas()>
        }
        return optionSelected;
    //    5   11:aload_0
    //    6   12:getfield        #4   <Field int cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionSelected>
    //    7   15:ireturn
    }

    @Override
    public String[] getOptionValue()
    {
        if(optionValue == null)
    //*   0    0:aload_0
    //*   1    1:getfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionValue>
    //*   2    4:ifnonnull       11
        {
            computeDatas();
    //    3    7:aload_0
    //    4    8:invokespecial   #9   <Method void cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.computeDatas()>
        }
        return optionValue;
    //    5   11:aload_0
    //    6   12:getfield        #2   <Field String[] cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.optionValue>
    //    7   15:areturn
    }

    @Override
    public String[] getOptionDatas()
    {
        String[] optionDatas = new String[12];
    //    0    0:bipush          12
    //    1    2:anewarray       String[]
    //    2    5:astore_1
        java.text.SimpleDateFormat fmt = new SimpleDateFormat("MMMM", locale);
    //    3    6:new             #10  <Class java.text.SimpleDateFormat>
    //    4    9:dup
    //    5   10:ldc1            #11  <String "MMMM">
    //    6   12:aload_0
    //    7   13:getfield        #3   <Field java.util.Locale cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.locale>
    //    8   16:invokespecial   #12  <Method void SimpleDateFormat(String, java.util.Locale)>
    //    9   19:astore_2
        try
        {
            cx.ath.choisnet.util.datetime.BasicDate currentDate = new BasicDate();
    //   10   20:new             #13  <Class cx.ath.choisnet.util.datetime.BasicDate>
    //   11   23:dup
    //   12   24:invokespecial   #14  <Method void BasicDate()>
    //   13   27:astore_3
            for(int m = 1; m < 13; m++)
    //*  14   28:iconst_1
    //*  15   29:istore          4
    //*  16   31:iload           4
    //*  17   33:bipush          13
    //*  18   35:icmpge          70
            {
                int i = m - 1;
    //   19   38:iload           4
    //   20   40:iconst_1
    //   21   41:isub
    //   22   42:istore          5
                currentDate.set(currentDate.getYear(), m, 1);
    //   23   44:aload_3
    //   24   45:aload_3
    //   25   46:invokevirtual   #15  <Method int cx.ath.choisnet.util.datetime.BasicDate.getYear()>
    //   26   49:iload           4
    //   27   51:iconst_1
    //   28   52:invokevirtual   #16  <Method void cx.ath.choisnet.util.datetime.BasicDate.set(int, int, int)>
                optionDatas[i] = currentDate.toString(fmt);
    //   29   55:aload_1
    //   30   56:iload           5
    //   31   58:aload_3
    //   32   59:aload_2
    //   33   60:invokevirtual   #17  <Method String cx.ath.choisnet.util.datetime.BasicDate.toString(java.text.Format)>
    //   34   63:aastore
            }

    //   35   64:iinc            4  1
        }
    //*  36   67:goto            31
    //*  37   70:goto            85
        catch(cx.ath.choisnet.util.datetime.BasicDateException e)
    //*  38   73:astore_3
        {
            throw new RuntimeException("Internal error", e);
    //   39   74:new             #19  <Class RuntimeException>
    //   40   77:dup
    //   41   78:ldc1            #20  <String "Internal error">
    //   42   80:aload_3
    //   43   81:invokespecial   #21  <Method void RuntimeException(String, Throwable)>
    //   44   84:athrow
        }
        return optionDatas;
    //   45   85:aload_1
    //   46   86:areturn
    }

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        locale = out.getLocale();
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokeinterface #22  <Method java.util.Locale cx.ath.choisnet.html.HTMLDocumentWriter.getLocale()>
    //    3    7:putfield        #3   <Field java.util.Locale cx.ath.choisnet.html.gadgets.advanced.items.SelectMonth.locale>
        super.writeHTML(out);
    //    4   10:aload_0
    //    5   11:aload_1
    //    6   12:invokespecial   #23  <Method void cx.ath.choisnet.html.gadgets.AbstractBGSelect.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
    //    7   15:return
    }
}
