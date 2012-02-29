package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODOC
 * 
 * @author Claude CHOISNET
 */
public interface InfosServletDisplayer
{
    /**
     * TODOC
     * 
     * @param appendable
     */
    public abstract void appendHTML(Appendable appendable) throws IOException;
}
