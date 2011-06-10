package cx.ath.choisnet.html;

import java.io.Writer;
import java.util.Locale;

//TODO: use Appendable ?
public class HTMLDocumentWriterImplementation
    implements HTMLDocumentWriter//, Appendable
{
    private final Writer out;
    private final Locale locale;

    public HTMLDocumentWriterImplementation(Writer out, Locale locale)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        this.out = out;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #2   <Field java.io.Writer cx.ath.choisnet.html.HTMLDocumentWriterImplementation.out>
        this.locale = locale;
    //    5    9:aload_0
    //    6   10:aload_2
    //    7   11:putfield        #3   <Field java.util.Locale cx.ath.choisnet.html.HTMLDocumentWriterImplementation.locale>
    //    8   14:return
    }

    public void write(String htmlContent)
        throws HTMLDocumentException
    {
        try
        {
            out.write(htmlContent);
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field java.io.Writer cx.ath.choisnet.html.HTMLDocumentWriterImplementation.out>
    //    2    4:aload_1
    //    3    5:invokevirtual   #4   <Method void java.io.Writer.write(String)>
        }
    //*   4    8:goto            21
        catch(java.io.IOException e)
    //*   5   11:astore_2
        {
            throw new HTMLDocumentException(e);
    //    6   12:new             #6   <Class cx.ath.choisnet.html.HTMLDocumentException>
    //    7   15:dup
    //    8   16:aload_2
    //    9   17:invokespecial   #7   <Method void HTMLDocumentException(Throwable)>
    //   10   20:athrow
        }
    //   11   21:return
    }

    public Locale getLocale()
    {
        return locale;
    //    0    0:aload_0
    //    1    1:getfield        #3   <Field java.util.Locale cx.ath.choisnet.html.HTMLDocumentWriterImplementation.locale>
    //    2    4:areturn
    }

    public void flush()
        throws HTMLDocumentException
    {
        try
        {
            out.flush();
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field java.io.Writer cx.ath.choisnet.html.HTMLDocumentWriterImplementation.out>
    //    2    4:invokevirtual   #8   <Method void java.io.Writer.flush()>
        }
    //*   3    7:goto            20
        catch(java.io.IOException e)
    //*   4   10:astore_1
        {
            throw new HTMLDocumentException(e);
    //    5   11:new             #6   <Class cx.ath.choisnet.html.HTMLDocumentException>
    //    6   14:dup
    //    7   15:aload_1
    //    8   16:invokespecial   #7   <Method void HTMLDocumentException(Throwable)>
    //    9   19:athrow
        }
    //   10   20:return
    }

    public void close()
        throws HTMLDocumentException
    {
        try
        {
            out.close();
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field java.io.Writer cx.ath.choisnet.html.HTMLDocumentWriterImplementation.out>
    //    2    4:invokevirtual   #9   <Method void java.io.Writer.close()>
        }
    //*   3    7:goto            20
        catch(java.io.IOException e)
    //*   4   10:astore_1
        {
            throw new HTMLDocumentException(e);
    //    5   11:new             #6   <Class cx.ath.choisnet.html.HTMLDocumentException>
    //    6   14:dup
    //    7   15:aload_1
    //    8   16:invokespecial   #7   <Method void HTMLDocumentException(Throwable)>
    //    9   19:athrow
        }
    //   10   20:return
    }
}
