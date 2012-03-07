package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODOC
 */
public interface InfosServletDisplay
{
//    /**
//     * TODOC
//     */
//    @Deprecated
//    public static interface Anchor extends InfosServletDisplayAnchor
//    {
//        @Deprecated public abstract String getHTMLName();
//        @Override
//        public abstract String getId();
//        @Override
//        public abstract String getDisplay();
//    }

    /**
     * TODOC
     *
     * @return TODOC
     */
    public abstract InfosServletDisplayAnchor getAnchor();

    /**
     * TODOC
     *
     * @param s
     * @param s1
     * @return TODOC
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
