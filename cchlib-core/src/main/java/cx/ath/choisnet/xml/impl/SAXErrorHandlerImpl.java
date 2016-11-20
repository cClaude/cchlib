package cx.ath.choisnet.xml.impl;

import java.io.PrintWriter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * NEEDDOC
 */
public class SAXErrorHandlerImpl implements ErrorHandler
{
    private final PrintWriter output;

    /**
     * <p>Constructor for SAXErrorHandlerImpl.</p>
     *
     * @param out a {@link java.io.PrintWriter} object.
     */
    public SAXErrorHandlerImpl(final PrintWriter out)
    {
        this.output = out;
    }

    private static final String getParseExceptionInfo(final SAXParseException spe)
    {
        final StringBuilder sb = new StringBuilder();

        if((spe.getSystemId() != null) && (spe.getPublicId() != null)) {
            sb.append("URI('systemId','publicId')=(");
            sb.append(spe.getSystemId());

            sb.append(',');
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

    /** {@inheritDoc} */
    @Override
    public void warning(final SAXParseException spe)
        throws SAXException
    {
        final StringBuilder message = new StringBuilder()
            .append("$Warning: ")
            .append(SAXErrorHandlerImpl.getParseExceptionInfo(spe));

        this.output.println(message);
    }

    /** {@inheritDoc} */
    @Override
    public void error(final SAXParseException spe)
        throws SAXException
    {
        final StringBuilder message = new StringBuilder()
            .append("$Error: ")
            .append(SAXErrorHandlerImpl.getParseExceptionInfo(spe));

        throw new SAXException(message.toString());
    }

    /** {@inheritDoc} */
    @Override
    public void fatalError(final SAXParseException spe)
        throws SAXException
    {
        final String message = (new StringBuilder())
            .append("$Fatal Error: ")
            .append(SAXErrorHandlerImpl.getParseExceptionInfo(spe))
            .toString();
        throw new SAXException(message);
    }
}
