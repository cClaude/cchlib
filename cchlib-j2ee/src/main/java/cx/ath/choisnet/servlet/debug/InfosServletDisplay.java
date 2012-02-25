package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
public interface InfosServletDisplay
{
    /**
     * TODO: Doc!
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
     * TODO: Doc!
     *
     * @return
     */
    public abstract Anchor getAnchor();

    /**
     * TODO: Doc!
     *
     * @param s
     * @param s1
     * @return
     */
    public abstract InfosServletDisplay put(String s, String s1);

    /**
     * TODO: Doc!
     *
     * @param appendable
     * @throws IOException
     */
    public abstract void appendHTML(Appendable appendable) throws IOException;
}
