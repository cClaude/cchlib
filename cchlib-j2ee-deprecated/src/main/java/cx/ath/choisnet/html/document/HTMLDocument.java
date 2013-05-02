package cx.ath.choisnet.html.document;

import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.HTMLWritable;

public class HTMLDocument
    implements HTMLWritable
{
    protected Header header;
    protected Body body;

    public HTMLDocument(String title)
    {
        this(new Header(title), new Body());
    }

    public HTMLDocument(Header header, Body body)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #6   <Method void Object()>
        this.header = header;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #7   <Field cx.ath.choisnet.html.document.Header cx.ath.choisnet.html.document.HTMLDocument.header>
        this.body = body;
    //    5    9:aload_0
    //    6   10:aload_2
    //    7   11:putfield        #8   <Field cx.ath.choisnet.html.document.Body cx.ath.choisnet.html.document.HTMLDocument.body>
    //    8   14:return
    }

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        header.writeHTML(out);
    //    0    0:aload_0
    //    1    1:getfield        #7   <Field cx.ath.choisnet.html.document.Header cx.ath.choisnet.html.document.HTMLDocument.header>
    //    2    4:aload_1
    //    3    5:invokevirtual   #9   <Method void cx.ath.choisnet.html.document.Header.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
        body.writeHTML(out);
    //    4    8:aload_0
    //    5    9:getfield        #8   <Field cx.ath.choisnet.html.document.Body cx.ath.choisnet.html.document.HTMLDocument.body>
    //    6   12:aload_1
    //    7   13:invokevirtual   #10  <Method void cx.ath.choisnet.html.document.Body.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
    //    8   16:return
    }

    public void addHeader(HTMLWritable item)
        throws HTMLDocumentException
    {
        header.add(item);
    //    0    0:aload_0
    //    1    1:getfield        #7   <Field cx.ath.choisnet.html.document.Header cx.ath.choisnet.html.document.HTMLDocument.header>
    //    2    4:aload_1
    //    3    5:invokevirtual   #11  <Method void cx.ath.choisnet.html.document.Header.add(cx.ath.choisnet.html.HTMLWritable)>
    //    4    8:return
    }

    public void addBody(HTMLWritable item)
    {
        body.add(item);
    //    0    0:aload_0
    //    1    1:getfield        #8   <Field cx.ath.choisnet.html.document.Body cx.ath.choisnet.html.document.HTMLDocument.body>
    //    2    4:aload_1
    //    3    5:invokevirtual   #12  <Method void cx.ath.choisnet.html.document.Body.add(cx.ath.choisnet.html.HTMLWritable)>
    //    4    8:return
    }

    public void addBody(String html)
    {
        addBody( new HTMLString(html) );
    //    0    0:aload_0
    //    1    1:new             #13  <Class cx.ath.choisnet.html.document.HTMLString>
    //    2    4:dup
    //    3    5:aload_1
    //    4    6:invokespecial   #14  <Method void HTMLString(String)>
    //    5    9:invokevirtual   #15  <Method void cx.ath.choisnet.html.document.HTMLDocument.addBody(cx.ath.choisnet.html.HTMLWritable)>
    //    6   12:return
    }
}
