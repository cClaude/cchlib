// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   Form.java

package deprecated.cx.ath.choisnet.html.document;

import java.util.LinkedList;
import java.util.List;
import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;
import deprecated.cx.ath.choisnet.html.gadgets.AbstractGadget;

// Referenced classes of package cx.ath.choisnet.html.document:
//            AbstractHTML

public abstract class Form extends AbstractHTML
{

    private String formName;
    private List<AbstractGadget> list;

    public Form()
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void AbstractHTML()>
        list = new LinkedList<AbstractGadget>();
    //    2    4:aload_0
    //    3    5:new             #2   <Class java.util.LinkedList>
    //    4    8:dup
    //    5    9:invokespecial   #3   <Method void LinkedList()>
    //    6   12:putfield        #4   <Field java.util.LinkedList cx.ath.choisnet.html.document.Form.list>
        formName = null;
    //    7   15:aload_0
    //    8   16:aconst_null
    //    9   17:putfield        #5   <Field String cx.ath.choisnet.html.document.Form.formName>
    //   10   20:return
    }

    public Form(String name)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void AbstractHTML()>
        list = new LinkedList<AbstractGadget>();
    //    2    4:aload_0
    //    3    5:new             #2   <Class java.util.LinkedList>
    //    4    8:dup
    //    5    9:invokespecial   #3   <Method void LinkedList()>
    //    6   12:putfield        #4   <Field java.util.LinkedList cx.ath.choisnet.html.document.Form.list>
        formName = name;
    //    7   15:aload_0
    //    8   16:aload_1
    //    9   17:putfield        #5   <Field String cx.ath.choisnet.html.document.Form.formName>
    //   10   20:return
    }

    public void add(AbstractGadget g)
    {
        list.add(g);
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field java.util.LinkedList cx.ath.choisnet.html.document.Form.list>
    //    2    4:aload_1
    //    3    5:invokevirtual   #6   <Method boolean java.util.LinkedList.add(Object)>
    //    4    8:pop
        super.add(g);
    //    5    9:aload_0
    //    6   10:aload_1
    //    7   11:invokespecial   #7   <Method void cx.ath.choisnet.html.document.AbstractHTML.add(cx.ath.choisnet.html.HTMLWritable)>
    //    8   14:return
    }

    public String getName()
    {
        return formName;
    //    0    0:aload_0
    //    1    1:getfield        #5   <Field String cx.ath.choisnet.html.document.Form.formName>
    //    2    4:areturn
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        out.write("<form");
    //    0    0:aload_1
    //    1    1:ldc1            #8   <String "<form">
    //    2    3:invokeinterface #9   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        if(formName != null)
    //*   3    8:aload_0
    //*   4    9:getfield        #5   <Field String cx.ath.choisnet.html.document.Form.formName>
    //*   5   12:ifnull          48
        {
            out.write((new StringBuilder()).append(" name=\"").append(formName).append("\"").toString());
    //    6   15:aload_1
    //    7   16:new             #10  <Class StringBuilder>
    //    8   19:dup
    //    9   20:invokespecial   #11  <Method void StringBuilder()>
    //   10   23:ldc1            #12  <String " name=\"">
    //   11   25:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   12   28:aload_0
    //   13   29:getfield        #5   <Field String cx.ath.choisnet.html.document.Form.formName>
    //   14   32:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   15   35:ldc1            #14  <String "\"">
    //   16   37:invokevirtual   #13  <Method StringBuilder StringBuilder.append(String)>
    //   17   40:invokevirtual   #15  <Method String StringBuilder.toString()>
    //   18   43:invokeinterface #9   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        }
        out.write(">\n");
    //   19   48:aload_1
    //   20   49:ldc1            #16  <String ">\n">
    //   21   51:invokeinterface #9   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        super.writeHTML(out);
    //   22   56:aload_0
    //   23   57:aload_1
    //   24   58:invokespecial   #17  <Method void cx.ath.choisnet.html.document.AbstractHTML.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
        out.write("</form>\n");
    //   25   61:aload_1
    //   26   62:ldc1            #18  <String "</form>\n">
    //   27   64:invokeinterface #9   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //   28   69:return
    }
}
