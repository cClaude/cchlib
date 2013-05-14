package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.HTMLFormException;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class BGInputRadio extends AbstractBG
{
    private String[] optionValue;
    private String[] optionDatas;
    private int optionSelected;
    private boolean lineLayout;
    private AbstractJavascript[] javascript;

    public BGInputRadio(
            String                  gadgetName, 
            String[]                optionValue, 
            String[]                optionDatas, 
            int                     optionSelected, 
            AbstractJavascript[]    javascript
            )
        throws HTMLDocumentException
    {
        this(gadgetName, optionValue, optionDatas, optionSelected, false, javascript);
    }

    public BGInputRadio(
            String      gadgetName, 
            String[]    optionValue, 
            String[]    optionDatas,
            int         optionSelected,
            boolean     lineLayout, 
            AbstractJavascript[] javascript
            )
        throws HTMLDocumentException
    {
        super(gadgetName);

        this.optionValue = null;
        this.optionDatas = null;
        this.optionSelected = -1;
        this.lineLayout = false;
    //   12   20:aload_0
    //   13   21:iconst_0
    //   14   22:putfield        #6   <Field boolean cx.ath.choisnet.html.gadgets.BGInputRadio.lineLayout>
        this.javascript = null;
    //   15   25:aload_0
    //   16   26:aconst_null
    //   17   27:putfield        #7   <Field cx.ath.choisnet.html.javascript.AbstractJavascript[] cx.ath.choisnet.html.gadgets.BGInputRadio.javascript>
        this.optionValue = optionValue;
    //   18   30:aload_0
    //   19   31:aload_2
    //   20   32:putfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionValue>
        this.optionDatas = optionDatas;
    //   21   35:aload_0
    //   22   36:aload_3
    //   23   37:putfield        #4   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionDatas>
        this.optionSelected = optionSelected;
    //   24   40:aload_0
    //   25   41:iload           4
    //   26   43:putfield        #5   <Field int cx.ath.choisnet.html.gadgets.BGInputRadio.optionSelected>
        this.lineLayout = lineLayout;
    //   27   46:aload_0
    //   28   47:iload           5
    //   29   49:putfield        #6   <Field boolean cx.ath.choisnet.html.gadgets.BGInputRadio.lineLayout>
        this.javascript = javascript;
    //   30   52:aload_0
    //   31   53:aload           6
    //   32   55:putfield        #7   <Field cx.ath.choisnet.html.javascript.AbstractJavascript[] cx.ath.choisnet.html.gadgets.BGInputRadio.javascript>
        if(optionValue != null && optionValue.length != optionDatas.length)
    //*  33   58:aload_2
    //*  34   59:ifnull          79
    //*  35   62:aload_2
    //*  36   63:arraylength
    //*  37   64:aload_3
    //*  38   65:arraylength
    //*  39   66:icmpeq          79
        {
            throw new HTMLDocumentException("optionValue.length != optionDatas.length");
    //   40   69:new             #8   <Class cx.ath.choisnet.html.HTMLDocumentException>
    //   41   72:dup
    //   42   73:ldc1            #9   <String "optionValue.length != optionDatas.length">
    //   43   75:invokespecial   #10  <Method void HTMLDocumentException(String)>
    //   44   78:athrow
        }
        if(javascript != null && javascript.length != optionDatas.length)
    //*  45   79:aload           6
    //*  46   81:ifnull          102
    //*  47   84:aload           6
    //*  48   86:arraylength
    //*  49   87:aload_3
    //*  50   88:arraylength
    //*  51   89:icmpeq          102
        {
            throw new HTMLDocumentException("javascript.length != optionDatas.length");
    //   52   92:new             #8   <Class cx.ath.choisnet.html.HTMLDocumentException>
    //   53   95:dup
    //   54   96:ldc1            #11  <String "javascript.length != optionDatas.length">
    //   55   98:invokespecial   #10  <Method void HTMLDocumentException(String)>
    //   56  101:athrow
        } else
        {
            return;
    //   57  102:return
        }
    }

    public String[] getRawHTML()
    {
        String rawHTML[] = new String[optionDatas.length];
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionDatas>
    //    2    4:arraylength
    //    3    5:anewarray       String[]
    //    4    8:astore_1
        for(int i = 0; i < optionDatas.length; i++)
    //*   5    9:iconst_0
    //*   6   10:istore_2
    //*   7   11:iload_2
    //*   8   12:aload_0
    //*   9   13:getfield        #4   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionDatas>
    //*  10   16:arraylength
    //*  11   17:icmpge          105
        {
            String javascriptString;
            if(javascript != null && javascript[i] != null)
    //*  12   20:aload_0
    //*  13   21:getfield        #7   <Field cx.ath.choisnet.html.javascript.AbstractJavascript[] cx.ath.choisnet.html.gadgets.BGInputRadio.javascript>
    //*  14   24:ifnull          49
    //*  15   27:aload_0
    //*  16   28:getfield        #7   <Field cx.ath.choisnet.html.javascript.AbstractJavascript[] cx.ath.choisnet.html.gadgets.BGInputRadio.javascript>
    //*  17   31:iload_2
    //*  18   32:aaload
    //*  19   33:ifnull          49
            {
                javascriptString = javascript[i].toInLineJavascript();
    //   20   36:aload_0
    //   21   37:getfield        #7   <Field cx.ath.choisnet.html.javascript.AbstractJavascript[] cx.ath.choisnet.html.gadgets.BGInputRadio.javascript>
    //   22   40:iload_2
    //   23   41:aaload
    //   24   42:invokevirtual   #13  <Method String cx.ath.choisnet.html.javascript.AbstractJavascript.toInLineJavascript()>
    //   25   45:astore_3
            } else
    //*  26   46:goto            51
            {
                javascriptString = null;
    //   27   49:aconst_null
    //   28   50:astore_3
            }
            rawHTML[i] = BGInputRadio.build(gadgetName, i, optionValue == null ? null : optionValue[i], optionDatas[i], optionSelected == i, javascriptString);
    //   29   51:aload_1
    //   30   52:iload_2
    //   31   53:aload_0
    //   32   54:getfield        #14  <Field String cx.ath.choisnet.html.gadgets.BGInputRadio.gadgetName>
    //   33   57:iload_2
    //   34   58:aload_0
    //   35   59:getfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionValue>
    //   36   62:ifnull          74
    //   37   65:aload_0
    //   38   66:getfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionValue>
    //   39   69:iload_2
    //   40   70:aaload
    //   41   71:goto            75
    //   42   74:aconst_null
    //   43   75:aload_0
    //   44   76:getfield        #4   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionDatas>
    //   45   79:iload_2
    //   46   80:aaload
    //   47   81:aload_0
    //   48   82:getfield        #5   <Field int cx.ath.choisnet.html.gadgets.BGInputRadio.optionSelected>
    //   49   85:iload_2
    //   50   86:icmpne          93
    //   51   89:iconst_1
    //   52   90:goto            94
    //   53   93:iconst_0
    //   54   94:aload_3
    //   55   95:invokestatic    #15  <Method String cx.ath.choisnet.html.gadgets.BGInputRadio.build(String, int, String, String, boolean, String)>
    //   56   98:aastore
        }

    //   57   99:iinc            2  1
    //*  58  102:goto            11
        return rawHTML;
    //   59  105:aload_1
    //   60  106:areturn
    }

    @Override
    public String getHiddenHTMLDatas()
    {
        String valueTxt;
        if(optionValue != null)
    //*   0    0:aload_0
    //*   1    1:getfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionValue>
    //*   2    4:ifnull          31
        {
            valueTxt = optionValue[optionSelected <= 0 ? 0 : optionSelected];
    //    3    7:aload_0
    //    4    8:getfield        #3   <Field String[] cx.ath.choisnet.html.gadgets.BGInputRadio.optionValue>
    //    5   11:aload_0
    //    6   12:getfield        #5   <Field int cx.ath.choisnet.html.gadgets.BGInputRadio.optionSelected>
    //    7   15:ifle            25
    //    8   18:aload_0
    //    9   19:getfield        #5   <Field int cx.ath.choisnet.html.gadgets.BGInputRadio.optionSelected>
    //   10   22:goto            26
    //   11   25:iconst_0
    //   12   26:aaload
    //   13   27:astore_1
        } else
    //*  14   28:goto            39
        {
            valueTxt = String.valueOf(optionSelected);
    //   15   31:aload_0
    //   16   32:getfield        #5   <Field int cx.ath.choisnet.html.gadgets.BGInputRadio.optionSelected>
    //   17   35:invokestatic    #16  <Method String String.valueOf(int)>
    //   18   38:astore_1
        }
        return BGInputText.buildHIDDEN(gadgetName, valueTxt);
    //   19   39:aload_0
    //   20   40:getfield        #14  <Field String cx.ath.choisnet.html.gadgets.BGInputRadio.gadgetName>
    //   21   43:aload_1
    //   22   44:invokestatic    #17  <Method String cx.ath.choisnet.html.gadgets.BGInputText.buildHIDDEN(String, String)>
    //   23   47:areturn
    }

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        String rawHTML[] = getRawHTML();
    //    0    0:aload_0
    //    1    1:invokevirtual   #18  <Method String[] cx.ath.choisnet.html.gadgets.BGInputRadio.getRawHTML()>
    //    2    4:astore_2
        out.write("<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\">");
    //    3    5:aload_1
    //    4    6:ldc1            #19  <String "<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\">">
    //    5    8:invokeinterface #20  <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        if(!lineLayout)
    //*   6   13:aload_0
    //*   7   14:getfield        #6   <Field boolean cx.ath.choisnet.html.gadgets.BGInputRadio.lineLayout>
    //*   8   17:ifne            69
        {
            for(int i = 0; i < rawHTML.length; i++)
    //*   9   20:iconst_0
    //*  10   21:istore_3
    //*  11   22:iload_3
    //*  12   23:aload_2
    //*  13   24:arraylength
    //*  14   25:icmpge          66
            {
                out.write((new StringBuilder()).append("<tr><td>").append(rawHTML[i]).append("</td></tr>").toString());
    //   15   28:aload_1
    //   16   29:new             #21  <Class StringBuilder>
    //   17   32:dup
    //   18   33:invokespecial   #22  <Method void StringBuilder()>
    //   19   36:ldc1            #23  <String "<tr><td>">
    //   20   38:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   21   41:aload_2
    //   22   42:iload_3
    //   23   43:aaload
    //   24   44:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   25   47:ldc1            #25  <String "</td></tr>">
    //   26   49:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   27   52:invokevirtual   #26  <Method String StringBuilder.toString()>
    //   28   55:invokeinterface #20  <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
            }

    //   29   60:iinc            3  1
        } else
    //*  30   63:goto            22
    //*  31   66:goto            131
        {
            out.write("<tr>");
    //   32   69:aload_1
    //   33   70:ldc1            #27  <String "<tr>">
    //   34   72:invokeinterface #20  <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
            for(int i = 0; i < rawHTML.length; i++)
    //*  35   77:iconst_0
    //*  36   78:istore_3
    //*  37   79:iload_3
    //*  38   80:aload_2
    //*  39   81:arraylength
    //*  40   82:icmpge          123
            {
                out.write((new StringBuilder()).append("<td>").append(rawHTML[i]).append("</td>").toString());
    //   41   85:aload_1
    //   42   86:new             #21  <Class StringBuilder>
    //   43   89:dup
    //   44   90:invokespecial   #22  <Method void StringBuilder()>
    //   45   93:ldc1            #28  <String "<td>">
    //   46   95:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   47   98:aload_2
    //   48   99:iload_3
    //   49  100:aaload
    //   50  101:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   51  104:ldc1            #29  <String "</td>">
    //   52  106:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   53  109:invokevirtual   #26  <Method String StringBuilder.toString()>
    //   54  112:invokeinterface #20  <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
            }

    //   55  117:iinc            3  1
    //*  56  120:goto            79
            out.write("</tr>");
    //   57  123:aload_1
    //   58  124:ldc1            #30  <String "</tr>">
    //   59  126:invokeinterface #20  <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        }
        out.write("</table>");
    //   60  131:aload_1
    //   61  132:ldc1            #31  <String "</table>">
    //   62  134:invokeinterface #20  <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //   63  139:return
    }

    @Override
    public Object getValue(ServletRequest request)
        throws HTMLFormException
    {
        return super.protected_getStringValue(request);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #32  <Method String cx.ath.choisnet.html.gadgets.AbstractBG.protected_getStringValue(javax.servlet.ServletRequest)>
    //    3    5:areturn
    }

    @Override
    public long getLongValue(ServletRequest request)
        throws HTMLFormException
    {
        return Long.parseLong(super.protected_getStringValue(request));
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #32  <Method String cx.ath.choisnet.html.gadgets.AbstractBG.protected_getStringValue(javax.servlet.ServletRequest)>
    //    3    5:invokestatic    #33  <Method long Long.parseLong(String)>
    //    4    8:lreturn
    }

    @Override
    public String getStringValue(ServletRequest request)
        throws HTMLFormException
    {
        return super.protected_getStringValue(request);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #32  <Method String cx.ath.choisnet.html.gadgets.AbstractBG.protected_getStringValue(javax.servlet.ServletRequest)>
    //    3    5:areturn
    }

    protected static String build(String gadgetName, int index, String value, String datas, boolean isSelect, String javascript)
    {
        StringBuilder sb = new StringBuilder((new StringBuilder()).append("<input type=\"RADIO\" name=\"").append(gadgetName).append("\" value=\"").toString());
    //    0    0:new             #21  <Class StringBuilder>
    //    1    3:dup
    //    2    4:new             #21  <Class StringBuilder>
    //    3    7:dup
    //    4    8:invokespecial   #22  <Method void StringBuilder()>
    //    5   11:ldc1            #34  <String "<input type=\"RADIO\" name=\"">
    //    6   13:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //    7   16:aload_0
    //    8   17:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //    9   20:ldc1            #35  <String "\" value=\"">
    //   10   22:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   11   25:invokevirtual   #26  <Method String StringBuilder.toString()>
    //   12   28:invokespecial   #36  <Method void StringBuilder(String)>
    //   13   31:astore          6
        if(value != null)
    //*  14   33:aload_2
    //*  15   34:ifnull          47
        {
            sb.append(value);
    //   16   37:aload           6
    //   17   39:aload_2
    //   18   40:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   19   43:pop
        } else
    //*  20   44:goto            54
        {
            sb.append(index);
    //   21   47:aload           6
    //   22   49:iload_1
    //   23   50:invokevirtual   #37  <Method StringBuilder StringBuilder.append(int)>
    //   24   53:pop
        }
        sb.append('"');
    //   25   54:aload           6
    //   26   56:bipush          34
    //   27   58:invokevirtual   #38  <Method StringBuilder StringBuilder.append(char)>
    //   28   61:pop
        if(isSelect)
    //*  29   62:iload           4
    //*  30   64:ifeq            75
        {
            sb.append(" checked");
    //   31   67:aload           6
    //   32   69:ldc1            #39  <String " checked">
    //   33   71:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   34   74:pop
        }
        if(javascript != null)
    //*  35   75:aload           5
    //*  36   77:ifnull          106
        {
            sb.append((new StringBuilder()).append(' ').append(javascript).toString());
    //   37   80:aload           6
    //   38   82:new             #21  <Class StringBuilder>
    //   39   85:dup
    //   40   86:invokespecial   #22  <Method void StringBuilder()>
    //   41   89:ldc1            #40  <String " ">
    //   42   91:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   43   94:aload           5
    //   44   96:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   45   99:invokevirtual   #26  <Method String StringBuilder.toString()>
    //   46  102:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   47  105:pop
        }
        sb.append("/>");
    //   48  106:aload           6
    //   49  108:ldc1            #41  <String "/>">
    //   50  110:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   51  113:pop
        if(datas != null)
    //*  52  114:aload_3
    //*  53  115:ifnull          125
        {
            sb.append(datas);
    //   54  118:aload           6
    //   55  120:aload_3
    //   56  121:invokevirtual   #24  <Method StringBuilder StringBuilder.append(String)>
    //   57  124:pop
        }
        return sb.toString();
    //   58  125:aload           6
    //   59  127:invokevirtual   #26  <Method String StringBuilder.toString()>
    //   60  130:areturn
    }
}
