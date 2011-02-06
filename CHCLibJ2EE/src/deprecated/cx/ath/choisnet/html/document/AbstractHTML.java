// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   AbstractHTML.java

package deprecated.cx.ath.choisnet.html.document;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;
import deprecated.cx.ath.choisnet.html.HTMLWritable;

// Referenced classes of package cx.ath.choisnet.html.document:
//            HTMLString

public abstract class AbstractHTML implements HTMLWritable
{

    private final List<HTMLWritable> htmlItemList = new LinkedList<HTMLWritable>();

    public AbstractHTML()
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
    //    2    4:aload_0
    //    3    5:new             #2   <Class java.util.LinkedList>
    //    4    8:dup
    //    5    9:invokespecial   #3   <Method void LinkedList()>
    //    6   12:putfield        #4   <Field java.util.List cx.ath.choisnet.html.document.AbstractHTML.htmlItemList>
    //    7   15:return
    }

    public void add(HTMLWritable item)
    {
        htmlItemList.add(item);
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field java.util.List cx.ath.choisnet.html.document.AbstractHTML.htmlItemList>
    //    2    4:aload_1
    //    3    5:invokeinterface #5   <Method boolean java.util.List.add(Object)>
    //    4   10:pop
    //    5   11:return
    }

    public void add(String html)
    {
        htmlItemList.add(new HTMLString(html));
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field java.util.List cx.ath.choisnet.html.document.AbstractHTML.htmlItemList>
    //    2    4:new             #6   <Class cx.ath.choisnet.html.document.HTMLString>
    //    3    7:dup
    //    4    8:aload_1
    //    5    9:invokespecial   #7   <Method void HTMLString(String)>
    //    6   12:invokeinterface #5   <Method boolean java.util.List.add(Object)>
    //    7   17:pop
    //    8   18:return
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        for(Iterator<HTMLWritable> iter0 = htmlItemList.iterator(); iter0.hasNext(); iter0.next().writeHTML(out)) { }
    //    0    0:aload_0
    //    1    1:getfield        #4   <Field java.util.List cx.ath.choisnet.html.document.AbstractHTML.htmlItemList>
    //    2    4:invokeinterface #8   <Method java.util.Iterator java.util.List.iterator()>
    //    3    9:astore_2
    //    4   10:aload_2
    //    5   11:invokeinterface #9   <Method boolean java.util.Iterator.hasNext()>
    //    6   16:ifeq            37
    //    7   19:aload_2
    //    8   20:invokeinterface #10  <Method Object java.util.Iterator.next()>
    //    9   25:checkcast       #11  <Class cx.ath.choisnet.html.HTMLWritable>
    //   10   28:aload_1
    //   11   29:invokeinterface #12  <Method void cx.ath.choisnet.html.HTMLWritable.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
    //*  12   34:goto            10
    //   13   37:return
    }
}
