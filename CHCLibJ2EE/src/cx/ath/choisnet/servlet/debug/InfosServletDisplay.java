package cx.ath.choisnet.servlet.debug;

public interface InfosServletDisplay
{
    public static interface Anchor
    {
        public abstract String getHTMLName();
        public abstract String getDisplay();
    }

    public abstract Anchor getAnchor();
    public abstract InfosServletDisplay put(String s, String s1);
    public abstract void appendHTML(Appendable appendable);
}
