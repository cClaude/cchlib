// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   StringBufferHTMLDocumentWriter.java

package deprecated.cx.ath.choisnet.html;

import java.util.Locale;

// Referenced classes of package cx.ath.choisnet.html:
//            HTMLDocumentWriter, HTMLDocumentException

public class StringBufferHTMLDocumentWriter
    implements HTMLDocumentWriter
{

    private final StringBuilder sb = new StringBuilder();
    private final Locale locale;

    public StringBufferHTMLDocumentWriter(Locale locale)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        this.locale = locale;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #2   <Field java.util.Locale cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.locale>
    //    5    9:aload_0
    //    6   10:new             #3   <Class StringBuilder>
    //    7   13:dup
    //    8   14:invokespecial   #4   <Method void StringBuilder()>
    //    9   17:putfield        #5   <Field StringBuilder cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.sb>
    //   10   20:return
    }

    public void reset()
    {
        sb.setLength(0);
    //    0    0:aload_0
    //    1    1:getfield        #5   <Field StringBuilder cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.sb>
    //    2    4:iconst_0
    //    3    5:invokevirtual   #6   <Method void StringBuilder.setLength(int)>
    //    4    8:return
    }

    public void write(String htmlContent)
        throws HTMLDocumentException
    {
        sb.append(htmlContent);
    //    0    0:aload_0
    //    1    1:getfield        #5   <Field StringBuilder cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.sb>
    //    2    4:aload_1
    //    3    5:invokevirtual   #7   <Method StringBuilder StringBuilder.append(String)>
    //    4    8:pop
    //    5    9:return
    }

    public Locale getLocale()
    {
        return locale;
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field java.util.Locale cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.locale>
    //    2    4:areturn
    }

    public String toHTML()
    {
        return sb.toString();
    //    0    0:aload_0
    //    1    1:getfield        #5   <Field StringBuilder cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.sb>
    //    2    4:invokevirtual   #8   <Method String StringBuilder.toString()>
    //    3    7:areturn
    }

    public String toString()
    {
        return sb.toString();
    //    0    0:aload_0
    //    1    1:getfield        #5   <Field StringBuilder cx.ath.choisnet.html.StringBufferHTMLDocumentWriter.sb>
    //    2    4:invokevirtual   #8   <Method String StringBuilder.toString()>
    //    3    7:areturn
    }
}
