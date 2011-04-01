// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   BGInputText.java

package deprecated.cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;
import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;
import deprecated.cx.ath.choisnet.html.HTMLFormException;
import deprecated.cx.ath.choisnet.html.javascript.AbstractJavascript;

// Referenced classes of package cx.ath.choisnet.html.gadgets:
//            AbstractBG

public class BGInputText extends AbstractBG
{

    protected Integer lengthChars;
    protected Integer maxChars;
    protected String text;
    protected AbstractJavascript javascript;

    public BGInputText(
            String gadgetName, 
            Integer lengthChars,
            Integer maxChars, 
            String text,
            AbstractJavascript javascript
            )
    {
        super(gadgetName);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #1   <Method void AbstractBG(String)>
        this.lengthChars = lengthChars;
    //    3    5:aload_0
    //    4    6:aload_2
    //    5    7:putfield        #2   <Field Integer cx.ath.choisnet.html.gadgets.BGInputText.lengthChars>
        this.maxChars = maxChars;
    //    6   10:aload_0
    //    7   11:aload_3
    //    8   12:putfield        #3   <Field Integer cx.ath.choisnet.html.gadgets.BGInputText.maxChars>
        this.text = text;
    //    9   15:aload_0
    //   10   16:aload           4
    //   11   18:putfield        #4   <Field String cx.ath.choisnet.html.gadgets.BGInputText.text>
        this.javascript = javascript;
    //   12   21:aload_0
    //   13   22:aload           5
    //   14   24:putfield        #5   <Field cx.ath.choisnet.html.javascript.AbstractJavascript cx.ath.choisnet.html.gadgets.BGInputText.javascript>
    //   15   27:return
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        String javascriptString = javascript == null ? null : javascript.toInLineJavascript();
    //    0    0:aload_0
    //    1    1:getfield        #5   <Field cx.ath.choisnet.html.javascript.AbstractJavascript cx.ath.choisnet.html.gadgets.BGInputText.javascript>
    //    2    4:ifnull          17
    //    3    7:aload_0
    //    4    8:getfield        #5   <Field cx.ath.choisnet.html.javascript.AbstractJavascript cx.ath.choisnet.html.gadgets.BGInputText.javascript>
    //    5   11:invokevirtual   #6   <Method String cx.ath.choisnet.html.javascript.AbstractJavascript.toInLineJavascript()>
    //    6   14:goto            18
    //    7   17:aconst_null
    //    8   18:astore_2
        out.write(BGInputText.buildInputText(gadgetName, lengthChars, maxChars, text, javascriptString));
    //    9   19:aload_1
    //   10   20:aload_0
    //   11   21:getfield        #7   <Field String cx.ath.choisnet.html.gadgets.BGInputText.gadgetName>
    //   12   24:aload_0
    //   13   25:getfield        #2   <Field Integer cx.ath.choisnet.html.gadgets.BGInputText.lengthChars>
    //   14   28:aload_0
    //   15   29:getfield        #3   <Field Integer cx.ath.choisnet.html.gadgets.BGInputText.maxChars>
    //   16   32:aload_0
    //   17   33:getfield        #4   <Field String cx.ath.choisnet.html.gadgets.BGInputText.text>
    //   18   36:aload_2
    //   19   37:invokestatic    #8   <Method String cx.ath.choisnet.html.gadgets.BGInputText.buildInputText(String, Integer, Integer, String, String)>
    //   20   40:invokeinterface #9   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //   21   45:return
    }

    public String getHiddenHTMLDatas()
    {
        return BGInputText.buildHIDDEN(gadgetName, text);
    //    0    0:aload_0
    //    1    1:getfield        #7   <Field String cx.ath.choisnet.html.gadgets.BGInputText.gadgetName>
    //    2    4:aload_0
    //    3    5:getfield        #4   <Field String cx.ath.choisnet.html.gadgets.BGInputText.text>
    //    4    8:invokestatic    #10  <Method String cx.ath.choisnet.html.gadgets.BGInputText.buildHIDDEN(String, String)>
    //    5   11:areturn
    }

    public Object getValue(ServletRequest request)
        throws HTMLFormException
    {
        return super.protected_getStringValue(request);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #11  <Method String cx.ath.choisnet.html.gadgets.AbstractBG.protected_getStringValue(javax.servlet.ServletRequest)>
    //    3    5:areturn
    }

    public long getLongValue(ServletRequest request)
        throws HTMLFormException
    {
        return Long.parseLong(super.protected_getStringValue(request));
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #11  <Method String cx.ath.choisnet.html.gadgets.AbstractBG.protected_getStringValue(javax.servlet.ServletRequest)>
    //    3    5:invokestatic    #12  <Method long Long.parseLong(String)>
    //    4    8:lreturn
    }

    public String getStringValue(ServletRequest request)
        throws HTMLFormException
    {
        return super.protected_getStringValue(request);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #11  <Method String cx.ath.choisnet.html.gadgets.AbstractBG.protected_getStringValue(javax.servlet.ServletRequest)>
    //    3    5:areturn
    }

    protected static String buildInputText(String gadgetName, Integer lengthChars, Integer maxChars, String text, String javascript)
    {
        StringBuilder sb = new StringBuilder((new StringBuilder()).append("<input type=\"TEXT\" name=\"").append(gadgetName).append("\"").toString());
    //    0    0:new             #13  <Class StringBuilder>
    //    1    3:dup
    //    2    4:new             #13  <Class StringBuilder>
    //    3    7:dup
    //    4    8:invokespecial   #14  <Method void StringBuilder()>
    //    5   11:ldc1            #15  <String "<input type=\"TEXT\" name=\"">
    //    6   13:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //    7   16:aload_0
    //    8   17:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //    9   20:ldc1            #17  <String "\"">
    //   10   22:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   11   25:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   12   28:invokespecial   #19  <Method void StringBuilder(String)>
    //   13   31:astore          5
        if(lengthChars != null)
    //*  14   33:aload_1
    //*  15   34:ifnull          70
        {
            sb.append((new StringBuilder()).append(" size=\"").append(lengthChars.toString()).append("\"").toString());
    //   16   37:aload           5
    //   17   39:new             #13  <Class StringBuilder>
    //   18   42:dup
    //   19   43:invokespecial   #14  <Method void StringBuilder()>
    //   20   46:ldc1            #20  <String " size=\"">
    //   21   48:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   22   51:aload_1
    //   23   52:invokevirtual   #21  <Method String Integer.toString()>
    //   24   55:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   25   58:ldc1            #17  <String "\"">
    //   26   60:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   27   63:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   28   66:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   29   69:pop
        }
        if(maxChars != null)
    //*  30   70:aload_2
    //*  31   71:ifnull          107
        {
            sb.append((new StringBuilder()).append(" maxlength=\"").append(maxChars.toString()).append("\"").toString());
    //   32   74:aload           5
    //   33   76:new             #13  <Class StringBuilder>
    //   34   79:dup
    //   35   80:invokespecial   #14  <Method void StringBuilder()>
    //   36   83:ldc1            #22  <String " maxlength=\"">
    //   37   85:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   38   88:aload_2
    //   39   89:invokevirtual   #21  <Method String Integer.toString()>
    //   40   92:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   41   95:ldc1            #17  <String "\"">
    //   42   97:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   43  100:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   44  103:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   45  106:pop
        }
        if(text != null)
    //*  46  107:aload_3
    //*  47  108:ifnull          141
        {
            sb.append((new StringBuilder()).append(" value=\"").append(text).append("\"").toString());
    //   48  111:aload           5
    //   49  113:new             #13  <Class StringBuilder>
    //   50  116:dup
    //   51  117:invokespecial   #14  <Method void StringBuilder()>
    //   52  120:ldc1            #23  <String " value=\"">
    //   53  122:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   54  125:aload_3
    //   55  126:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   56  129:ldc1            #17  <String "\"">
    //   57  131:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   58  134:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   59  137:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   60  140:pop
        }
        if(javascript != null)
    //*  61  141:aload           4
    //*  62  143:ifnull          172
        {
            sb.append((new StringBuilder()).append(" ").append(javascript).toString());
    //   63  146:aload           5
    //   64  148:new             #13  <Class StringBuilder>
    //   65  151:dup
    //   66  152:invokespecial   #14  <Method void StringBuilder()>
    //   67  155:ldc1            #24  <String " ">
    //   68  157:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   69  160:aload           4
    //   70  162:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   71  165:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   72  168:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   73  171:pop
        }
        sb.append("/>");
    //   74  172:aload           5
    //   75  174:ldc1            #25  <String "/>">
    //   76  176:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   77  179:pop
        return sb.toString();
    //   78  180:aload           5
    //   79  182:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   80  185:areturn
    }

    public static String buildHIDDEN(String gadgetName, String text)
    {
        StringBuilder sb = new StringBuilder((new StringBuilder()).append("<input type=\"HIDDEN\" name=\"").append(gadgetName).append("\"").toString());
    //    0    0:new             #13  <Class StringBuilder>
    //    1    3:dup
    //    2    4:new             #13  <Class StringBuilder>
    //    3    7:dup
    //    4    8:invokespecial   #14  <Method void StringBuilder()>
    //    5   11:ldc1            #26  <String "<input type=\"HIDDEN\" name=\"">
    //    6   13:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //    7   16:aload_0
    //    8   17:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //    9   20:ldc1            #17  <String "\"">
    //   10   22:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   11   25:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   12   28:invokespecial   #19  <Method void StringBuilder(String)>
    //   13   31:astore_2
        if(text != null)
    //*  14   32:aload_1
    //*  15   33:ifnull          65
        {
            sb.append((new StringBuilder()).append(" value=\"").append(text).append("\"").toString());
    //   16   36:aload_2
    //   17   37:new             #13  <Class StringBuilder>
    //   18   40:dup
    //   19   41:invokespecial   #14  <Method void StringBuilder()>
    //   20   44:ldc1            #23  <String " value=\"">
    //   21   46:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   22   49:aload_1
    //   23   50:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   24   53:ldc1            #17  <String "\"">
    //   25   55:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   26   58:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   27   61:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   28   64:pop
        }
        sb.append("/>");
    //   29   65:aload_2
    //   30   66:ldc1            #25  <String "/>">
    //   31   68:invokevirtual   #16  <Method StringBuilder StringBuilder.append(String)>
    //   32   71:pop
        return sb.toString();
    //   33   72:aload_2
    //   34   73:invokevirtual   #18  <Method String StringBuilder.toString()>
    //   35   76:areturn
    }
}
