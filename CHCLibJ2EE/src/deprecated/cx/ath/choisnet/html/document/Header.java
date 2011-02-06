// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   Header.java

package deprecated.cx.ath.choisnet.html.document;

import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;

// Referenced classes of package cx.ath.choisnet.html.document:
//            AbstractHTML

public class Header extends AbstractHTML
{

    protected String title;
    protected boolean nocache;
    protected String extraDatas;

    public Header(String title)
    {
        this(title, false, null);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:iconst_0
    //    3    3:aconst_null
    //    4    4:invokespecial   #1   <Method void Header(String, boolean, String)>
    //    5    7:return
    }

    public Header(String title, boolean noCache)
    {
        this(title, noCache, null);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:iload_2
    //    3    3:aconst_null
    //    4    4:invokespecial   #1   <Method void Header(String, boolean, String)>
    //    5    7:return
    }

    public Header(String title, boolean nocache, String extraDatas)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #2   <Method void AbstractHTML()>
        this.title = title;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #3   <Field String cx.ath.choisnet.html.document.Header.title>
        this.nocache = nocache;
    //    5    9:aload_0
    //    6   10:iload_2
    //    7   11:putfield        #4   <Field boolean cx.ath.choisnet.html.document.Header.nocache>
        this.extraDatas = extraDatas;
    //    8   14:aload_0
    //    9   15:aload_3
    //   10   16:putfield        #5   <Field String cx.ath.choisnet.html.document.Header.extraDatas>
    //   11   19:return
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n");
    //    0    0:aload_1
    //    1    1:ldc1            #6   <String "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n">
    //    2    3:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        out.write("   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
    //    3    8:aload_1
    //    4    9:ldc1            #8   <String "   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n">
    //    5   11:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        out.write("<head>\n");
    //    6   16:aload_1
    //    7   17:ldc1            #9   <String "<head>\n">
    //    8   19:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n");
    //    9   24:aload_1
    //   10   25:ldc1            #10  <String "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n">
    //   11   27:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        if(nocache)
    //*  12   32:aload_0
    //*  13   33:getfield        #4   <Field boolean cx.ath.choisnet.html.document.Header.nocache>
    //*  14   36:ifeq            63
        {
            out.write("<meta http-equiv=\"Cache-Control\" content=\"no-store\" />\n");
    //   15   39:aload_1
    //   16   40:ldc1            #11  <String "<meta http-equiv=\"Cache-Control\" content=\"no-store\" />\n">
    //   17   42:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
            out.write("<meta http-equiv=\"pragma\" content=\"no-cache\" />\n");
    //   18   47:aload_1
    //   19   48:ldc1            #12  <String "<meta http-equiv=\"pragma\" content=\"no-cache\" />\n">
    //   20   50:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
            out.write("<meta http-equiv=\"Expires\" content=\"Fri, Jun 12 1981 08:20:00 GMT\" />\n");
    //   21   55:aload_1
    //   22   56:ldc1            #13  <String "<meta http-equiv=\"Expires\" content=\"Fri, Jun 12 1981 08:20:00 GMT\" />\n">
    //   23   58:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        }
        out.write((new StringBuilder()).append("<title>").append(title).append("</title>\n").toString());
    //   24   63:aload_1
    //   25   64:new             #14  <Class StringBuilder>
    //   26   67:dup
    //   27   68:invokespecial   #15  <Method void StringBuilder()>
    //   28   71:ldc1            #16  <String "<title>">
    //   29   73:invokevirtual   #17  <Method StringBuilder StringBuilder.append(String)>
    //   30   76:aload_0
    //   31   77:getfield        #3   <Field String cx.ath.choisnet.html.document.Header.title>
    //   32   80:invokevirtual   #17  <Method StringBuilder StringBuilder.append(String)>
    //   33   83:ldc1            #18  <String "</title>\n">
    //   34   85:invokevirtual   #17  <Method StringBuilder StringBuilder.append(String)>
    //   35   88:invokevirtual   #19  <Method String StringBuilder.toString()>
    //   36   91:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
        super.writeHTML(out);
    //   37   96:aload_0
    //   38   97:aload_1
    //   39   98:invokespecial   #20  <Method void cx.ath.choisnet.html.document.AbstractHTML.writeHTML(cx.ath.choisnet.html.HTMLDocumentWriter)>
        out.write("</head>\n");
    //   40  101:aload_1
    //   41  102:ldc1            #21  <String "</head>\n">
    //   42  104:invokeinterface #7   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //   43  109:return
    }
}
