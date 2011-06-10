package cx.ath.choisnet.html.validation;

import cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException;
import cx.ath.choisnet.util.datetime.BasicTime;
import java.io.PrintWriter;
import java.util.Locale;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriterImplementation;
import cx.ath.choisnet.html.document.Form;
import cx.ath.choisnet.html.document.HTMLDocument;

public class ValidationDocument extends HTMLDocument
{
    public ValidationDocument(String servletName)
        throws HTMLDocumentException
    {
        super(servletName);

        BasicTime beginTime = new BasicTime();
        Form      myForm    = new ValidationForm("myForm");

        addBody((new StringBuilder()).append("start build() ").append(beginTime).append("<br />\n").toString());
        addBody(myForm);

        BasicTime endTime = new BasicTime();

        addBody((new StringBuilder()).append("end build() ").append(endTime).append("<br />\n").toString());

        try {
            addBody((new StringBuilder()).append("=> ").append(cx.ath.choisnet.util.datetime.BasicTime.subtract(endTime, beginTime)).append("<br />\n").toString());
        }
        catch(BasicDateTimeNegativeValueException e) { 
            
        }
    }

    public void write(PrintWriter out, Locale locale)
        throws HTMLDocumentException
    {
        writeHTML(new HTMLDocumentWriterImplementation(out, locale));
    }
}
