// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   Body.java

package deprecated.cx.ath.choisnet.html.document;

import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;

// Referenced classes of package cx.ath.choisnet.html.document:
//            AbstractHTML

public class Body extends AbstractHTML
{

    private final String cssClass;

    public Body()
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void AbstractHTML()>
        cssClass = null;
    //    2    4:aload_0
    //    3    5:aconst_null
    //    4    6:putfield        #2   <Field String cx.ath.choisnet.html.document.Body.cssClass>
    //    5    9:return
    }

    public Body(String cssClass)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void AbstractHTML()>
        this.cssClass = cssClass;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #2   <Field String cx.ath.choisnet.html.document.Body.cssClass>
    //    5    9:return
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        if(cssClass == null)
    //*   0    0:aload_0
    //*   1    1:getfield        #2   <Field String cx.ath.choisnet.html.document.Body.cssClass>
    //*   2    4:ifnonnull       18
        {
            out.write("<body>");
    //    3    7:aload_1
    //    4    8:ldc1            #3   <String "<body>">
    //    5   10:invokeinterface #4   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        } else
    //*   6   15:goto            51
        {
            out.write((new StringBuilder()).append("<body class=\"").append(cssClass).append("\">").toString());
    //    7   18:aload_1
    //    8   19:new             #5   <Class StringBuilder>
    //    9   22:dup
    //   10   23:invokespecial   #6   <Method void StringBuilder()>
    //   11   26:ldc1            #7   <String "<body class=\"">
    //   12   28:invokevirtual   #8   <Method StringBuilder StringBuilder.append(String)>
    //   13   31:aload_0
    //   14   32:getfield        #2   <Field String cx.ath.choisnet.html.document.Body.cssClass>
    //   15   35:invokevirtual   #8   <Method StringBuilder StringBuilder.append(String)>
    //   16   38:ldc1            #9   <String "\">">
    //   17   40:invokevirtual   #8   <Method StringBuilder StringBuilder.append(String)>
    //   18   43:invokevirtual   #10  <Method String StringBuilder.toString()>
    //   19   46:invokeinterface #4   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        }
        super.writeHTML(out);
    //   20   51:aload_0
    //   21   52:aload_1
    //   22   53:invokespecial   #11  <Method void cx.ath.choisnet.html.document.AbstractHTML.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
        out.write("</body>");
    //   23   56:aload_1
    //   24   57:ldc1            #12  <String "</body>">
    //   25   59:invokeinterface #4   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //   26   64:return
    }
}
