package cx.ath.choisnet.html;

import java.util.Locale;

public class StringBufferHTMLDocumentWriter
    implements HTMLDocumentWriter
{

    private final StringBuilder sb = new StringBuilder();
    private final Locale locale;

    public StringBufferHTMLDocumentWriter(Locale locale)
    {
        this.locale = locale;
    }

    public void reset()
    {
        sb.setLength(0);
    }

    @Override
    public void write(String htmlContent)
        throws HTMLDocumentException
    {
        sb.append(htmlContent);
    }

    @Override
    public Locale getLocale()
    {
        return locale;
    }

    public String toHTML()
    {
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return sb.toString();
    }
}
