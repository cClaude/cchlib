package cx.ath.choisnet.html.gadgets;

import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

// Referenced classes of package cx.ath.choisnet.html.gadgets:
//            BGInputText

public class BGTextArea extends BGInputText
{
    protected final Integer heightChars;

    public BGTextArea(
            String gadgetName,
            Integer lengthChars,
            Integer heightChars,
            String text,
            AbstractJavascript javascript
            )
    {
        super(gadgetName, lengthChars, null, text, javascript);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:aload_2
    //    3    3:aconst_null
    //    4    4:aload           4
    //    5    6:aload           5
    //    6    8:invokespecial   #1   <Method void BGInputText(String, Integer, Integer, String, cx.ath.choisnet.html.javascript.AbstractJavascript)>
        this.heightChars = heightChars;
    //    7   11:aload_0
    //    8   12:aload_3
    //    9   13:putfield        #2   <Field Integer cx.ath.choisnet.html.gadgets.BGTextArea.heightChars>
    //   10   16:return
    }

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        String javascriptString = (javascript == null) ? null : javascript.toInLineJavascript();
    //    0    0:aload_0
    //    1    1:getfield        #3   <Field cx.ath.choisnet.html.javascript.AbstractJavascript cx.ath.choisnet.html.gadgets.BGTextArea.javascript>
    //    2    4:ifnull          17
    //    3    7:aload_0
    //    4    8:getfield        #3   <Field cx.ath.choisnet.html.javascript.AbstractJavascript cx.ath.choisnet.html.gadgets.BGTextArea.javascript>
    //    5   11:invokevirtual   #4   <Method String cx.ath.choisnet.html.javascript.AbstractJavascript.toInLineJavascript()>
    //    6   14:goto            18
    //    7   17:aconst_null
    //    8   18:astore_2
        out.write(BGTextArea.buildTextArea(gadgetName, lengthChars, heightChars, text, javascriptString));
    //    9   19:aload_1
    //   10   20:aload_0
    //   11   21:getfield        #5   <Field String cx.ath.choisnet.html.gadgets.BGTextArea.gadgetName>
    //   12   24:aload_0
    //   13   25:getfield        #6   <Field Integer cx.ath.choisnet.html.gadgets.BGTextArea.lengthChars>
    //   14   28:aload_0
    //   15   29:getfield        #2   <Field Integer cx.ath.choisnet.html.gadgets.BGTextArea.heightChars>
    //   16   32:aload_0
    //   17   33:getfield        #7   <Field String cx.ath.choisnet.html.gadgets.BGTextArea.text>
    //   18   36:aload_2
    //   19   37:invokestatic    #8   <Method String cx.ath.choisnet.html.gadgets.BGTextArea.buildTextArea(String, Integer, Integer, String, String)>
    //   20   40:invokeinterface #9   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //   21   45:return
    }

    protected static String buildTextArea(String gadgetName, Integer lengthChars, Integer heightChars, String text, String javascript)
    {
        StringBuilder s = new StringBuilder((new StringBuilder()).append("<textarea name=\"").append(gadgetName).append('"').toString());
    //    0    0:new             #10  <Class StringBuilder>
    //    1    3:dup
    //    2    4:new             #10  <Class StringBuilder>
    //    3    7:dup
    //    4    8:invokespecial   #11  <Method void StringBuilder()>
    //    5   11:ldc1            #12  <String "<textarea name=\"">
    //    6   13:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //    7   16:aload_0
    //    8   17:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //    9   20:ldc1            #14  <String "\"">
    //   10   22:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   11   25:invokevirtual   #15  <Method String StringBuilder.toString()>
    //   12   28:invokespecial   #16  <Method void StringBuilder(String)>
    //   13   31:astore          5
        if(lengthChars != null)
    //*  14   33:aload_1
    //*  15   34:ifnull          67
        {
            s.append((new StringBuilder()).append(" cols=\"").append(lengthChars).append('"').toString());
    //   16   37:aload           5
    //   17   39:new             #10  <Class StringBuilder>
    //   18   42:dup
    //   19   43:invokespecial   #11  <Method void StringBuilder()>
    //   20   46:ldc1            #17  <String " cols=\"">
    //   21   48:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   22   51:aload_1
    //   23   52:invokevirtual   #18  <Method StringBuilder StringBuilder.append(Object)>
    //   24   55:ldc1            #14  <String "\"">
    //   25   57:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   26   60:invokevirtual   #15  <Method String StringBuilder.toString()>
    //   27   63:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   28   66:pop
        }
        if(heightChars != null)
    //*  29   67:aload_2
    //*  30   68:ifnull          101
        {
            s.append((new StringBuilder()).append(" rows=\"").append(heightChars).append('"').toString());
    //   31   71:aload           5
    //   32   73:new             #10  <Class StringBuilder>
    //   33   76:dup
    //   34   77:invokespecial   #11  <Method void StringBuilder()>
    //   35   80:ldc1            #19  <String " rows=\"">
    //   36   82:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   37   85:aload_2
    //   38   86:invokevirtual   #18  <Method StringBuilder StringBuilder.append(Object)>
    //   39   89:ldc1            #14  <String "\"">
    //   40   91:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   41   94:invokevirtual   #15  <Method String StringBuilder.toString()>
    //   42   97:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   43  100:pop
        }
        if(javascript != null)
    //*  44  101:aload           4
    //*  45  103:ifnull          122
        {
            s.append(' ');
    //   46  106:aload           5
    //   47  108:ldc1            #20  <String " ">
    //   48  110:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   49  113:pop
            s.append(javascript);
    //   50  114:aload           5
    //   51  116:aload           4
    //   52  118:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   53  121:pop
        }
        s.append('>');
    //   54  122:aload           5
    //   55  124:ldc1            #21  <String ">">
    //   56  126:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   57  129:pop
        if(text != null)
    //*  58  130:aload_3
    //*  59  131:ifnull          141
        {
            s.append(text);
    //   60  134:aload           5
    //   61  136:aload_3
    //   62  137:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   63  140:pop
        }
        s.append("</textarea>");
    //   64  141:aload           5
    //   65  143:ldc1            #22  <String "</textarea>">
    //   66  145:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   67  148:pop
        return s.toString();
    //   68  149:aload           5
    //   69  151:invokevirtual   #15  <Method String StringBuilder.toString()>
    //   70  154:areturn
    }
}
