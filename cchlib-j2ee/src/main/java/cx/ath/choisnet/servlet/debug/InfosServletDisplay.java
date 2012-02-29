package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 */
public interface InfosServletDisplay
{
    /**
     * TODOC
     *
     * @author Claude CHOISNET
     */
    public static interface Anchor
    {
        @Deprecated public abstract String getHTMLName();
        public abstract String getId();
        public abstract String getDisplay();
    }

    /**
     * TODOC
     *
     * @return
     */
    public abstract Anchor getAnchor();

    /**
     * TODOC
     *
     * @param s
     * @param s1
     * @return
     */
    public abstract InfosServletDisplay put(String s, String s1);

    /**
     * TODOC
     *
     * @param appendable
     * @throws IOException
     */
    public abstract void appendHTML(Appendable appendable) throws IOException;
}
