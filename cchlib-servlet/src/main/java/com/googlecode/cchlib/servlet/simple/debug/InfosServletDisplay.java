package cx.ath.choisnet.servlet.debug;

import java.io.IOException;

/**
 * TODOC
 */
public interface InfosServletDisplay
{
    /**
     * TODOC
     *
     * @return TODOC
     */
    InfosServletDisplayAnchor getAnchor();

    /**
     * TODOC
     *
     * @param s
     * @param s1
     * @return TODOC
     */
    InfosServletDisplay put(String s, String s1);

    /**
     * TODOC
     *
     * @param appendable
     * @throws IOException
     */
    void appendHTML(Appendable appendable) throws IOException;
}
