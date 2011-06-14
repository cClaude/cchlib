package cx.ath.choisnet.html;

import java.util.Locale;

// TO_DO: use Appendable ?
public interface HTMLDocumentWriter
{

    public abstract void write(String s)
        throws HTMLDocumentException;

    public abstract Locale getLocale();
}
