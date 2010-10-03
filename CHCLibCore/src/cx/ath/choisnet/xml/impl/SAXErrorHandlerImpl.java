package cx.ath.choisnet.xml.impl;

import java.io.PrintWriter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SAXErrorHandlerImpl implements ErrorHandler
{
    private final PrintWriter output;

    public SAXErrorHandlerImpl(PrintWriter out)
    {
        output = out;
    }

    private static final String getParseExceptionInfo(SAXParseException spe)
    {
        StringBuilder sb = new StringBuilder();

        if(spe.getSystemId() != null && spe.getPublicId() != null) {
            sb.append("URI('systemId','publicId')=(");
            sb.append(spe.getSystemId());

            sb.append(",");
            sb.append(spe.getPublicId());

            sb.append(") ");
        }

        sb.append("(L:");
        sb.append(spe.getLineNumber());

        sb.append(" ,C:");
        sb.append(spe.getColumnNumber());

        sb.append("):");
        sb.append(spe.getMessage());

        if(spe.getCause() != null) {
            sb.append(" cause by : ");
            sb.append(spe.getCause());
        }

        return sb.toString();
    }

    public void warning(SAXParseException spe)
        throws org.xml.sax.SAXException
    {
        StringBuilder message = new StringBuilder()
            .append("$Warning: ")
            .append(SAXErrorHandlerImpl.getParseExceptionInfo(spe));

        output.println(message);
    }

    public void error(org.xml.sax.SAXParseException spe)
        throws org.xml.sax.SAXException
    {
        StringBuilder message = new StringBuilder()
            .append("$Error: ")
            .append(SAXErrorHandlerImpl.getParseExceptionInfo(spe));

        throw new SAXException(message.toString());
    }

    public void fatalError(org.xml.sax.SAXParseException spe)
        throws org.xml.sax.SAXException
    {
        String message = (new StringBuilder()).append("$Fatal Error: ").append(SAXErrorHandlerImpl.getParseExceptionInfo(spe)).toString();
        throw new SAXException(message);
    }
}
