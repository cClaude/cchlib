package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODOC
 *
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
