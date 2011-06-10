package cx.ath.choisnet.html;

public interface HTMLWritable
{
    public abstract void writeHTML(HTMLDocumentWriter htmldocumentwriter)
        throws HTMLDocumentException;
}
