package deprecated.cx.ath.choisnet.html;

import java.util.Locale;

// TODO: use Appendable ?
public interface HTMLDocumentWriter
{

    public abstract void write(String s)
        throws HTMLDocumentException;

    public abstract Locale getLocale();
}