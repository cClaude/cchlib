package cx.ath.choisnet.servlet.debug;

/**
 * 
 * @author Claude CHOISNET
 */
public interface InfosServletDisplay
{
    /**
     * 
     * @author Claude CHOISNET
     */
    public static interface Anchor
    {
        public abstract String getHTMLName();
        public abstract String getDisplay();
    }

    /**
     * 
     * @return
     */
    public abstract Anchor getAnchor();

    /**
     * 
     * @return
     */
    public abstract InfosServletDisplay put(String s, String s1);

    /**
     * 
     * @return
     */
    public abstract void appendHTML(Appendable appendable);
}
