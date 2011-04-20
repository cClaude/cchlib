package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 */
public interface InfosServletDisplayer
{
    /**
     * TODO: Doc!
     * 
     * @param appendable
     */
    public abstract void appendHTML(Appendable appendable) throws IOException;
}
