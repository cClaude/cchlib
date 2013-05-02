package cx.ath.choisnet.html.javascript;

public class SimpleJavascript extends AbstractJavascript
{
    protected String rawJavascript;

    public SimpleJavascript(String rawJavascript)
    {
        this.rawJavascript = rawJavascript;
    }

    @Override
    public String toInLineJavascript()
    {
        return rawJavascript;
    }
}
