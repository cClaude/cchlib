package cx.ath.choisnet.html;

import java.util.Locale;

// TO_DO: use Appendable ?
public interface HTMLDocumentWriter
{
    void write(String s)
        throws HTMLDocumentException;

    Locale getLocale();
}
