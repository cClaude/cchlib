package cx.ath.choisnet.html.validation;

import cx.ath.choisnet.io.HTMLWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cx.ath.choisnet.html.HTMLDocumentException;

public class ValidationServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public ValidationServlet()
    {
    }

    @Override
    public void service(
            HttpServletRequest request, 
            HttpServletResponse response
            )
        throws IOException
    {
        response.setContentType("text/html");

        @SuppressWarnings("resource")
        PrintWriter out = response.getWriter();

        try {
            ValidationDocument document = new ValidationDocument( getClass().getName() );

            document.write(out, Locale.FRENCH);
        }
        catch(HTMLDocumentException e) {
            HTMLWriter htmlWriter = new HTMLWriter(out);

            htmlWriter.write((new StringBuilder()).append("HTMLDocumentException: ").append(e.getMessage()).append("\n").toString());
            htmlWriter.write(e);
            htmlWriter.flush();
            htmlWriter.close();
        }
        catch(Exception e) {
            HTMLWriter htmlWriter = new HTMLWriter(out);

            htmlWriter.write((new StringBuilder()).append("Exception: ").append(e.getMessage()).append("\n").toString());
            htmlWriter.write(e);
            htmlWriter.flush();
            htmlWriter.close();
        }
    }
}
