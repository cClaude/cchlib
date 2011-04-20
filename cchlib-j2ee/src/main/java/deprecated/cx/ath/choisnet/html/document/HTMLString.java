// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   HTMLString.java

package deprecated.cx.ath.choisnet.html.document;

import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;
import deprecated.cx.ath.choisnet.html.HTMLWritable;

public class HTMLString implements HTMLWritable
{

    private final StringBuilder sb;

    public HTMLString()
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        sb = new StringBuilder();
    //    2    4:aload_0
    //    3    5:new             #2   <Class StringBuilder>
    //    4    8:dup
    //    5    9:invokespecial   #3   <Method void StringBuilder()>
    //    6   12:putfield        #4   <Field StringBuilder cx.ath.choisnet.html.document.HTMLString.sb>
    //    7   15:return
    }

    public HTMLString(String str)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        sb = new StringBuilder(str);
    //    2    4:aload_0
    //    3    5:new             #2   <Class StringBuilder>
    //    4    8:dup
    //    5    9:aload_1
    //    6   10:invokespecial   #5   <Method void StringBuilder(String)>
    //    7   13:putfield        #4   <Field StringBuilder cx.ath.choisnet.html.document.HTMLString.sb>
    //    8   16:return
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        out.write(sb.toString());
    //    0    0:aload_1
    //    1    1:aload_0
    //    2    2:getfield        #4   <Field StringBuilder cx.ath.choisnet.html.document.HTMLString.sb>
    //    3    5:invokevirtual   #6   <Method String StringBuilder.toString()>
    //    4    8:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //    5   13:return
    }

    public void append(String str)
    {
        sb.append(str);
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field StringBuilder cx.ath.choisnet.html.document.HTMLString.sb>
    //    2    4:aload_1
    //    3    5:invokevirtual   #8   <Method StringBuilder StringBuilder.append(String)>
    //    4    8:pop
    //    5    9:return
    }
}
