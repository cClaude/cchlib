package deprecated.cx.ath.choisnet.html.javascript;


// Referenced classes of package cx.ath.choisnet.html.javascript:
//            AbstractJavascript

public class SimpleJavascript extends AbstractJavascript
{

    protected String rawJavascript;

    public SimpleJavascript(String rawJavascript)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void AbstractJavascript()>
        this.rawJavascript = rawJavascript;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #2   <Field String cx.ath.choisnet.html.javascript.SimpleJavascript.rawJavascript>
    //    5    9:return
    }

    public String toInLineJavascript()
    {
        return rawJavascript;
    //    0    0:aload_0
    //    1    1:getfield        #2   <Field String cx.ath.choisnet.html.javascript.SimpleJavascript.rawJavascript>
    //    2    4:areturn
    }
}
