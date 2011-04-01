// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   AbstractBG.java

package deprecated.cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;
import deprecated.cx.ath.choisnet.html.HTMLFormException;

// Referenced classes of package cx.ath.choisnet.html.gadgets:
//            AbstractGadget

public abstract class AbstractBG
    extends AbstractGadget
{

    protected AbstractBG(String gadgetName)
    {
        super(gadgetName);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #1   <Method void AbstractGadget(String)>
    //    3    5:return
    }

    protected String protected_getStringValue(ServletRequest request)
        throws HTMLFormException
    {
        return AbstractBG.getRequestParameterValues(request, gadgetName)[0];
    //    0    0:aload_1
    //    1    1:aload_0
    //    2    2:getfield        #2   <Field String cx.ath.choisnet.html.gadgets.AbstractBG.gadgetName>
    //    3    5:invokestatic    #3   <Method String[] cx.ath.choisnet.html.gadgets.AbstractBG.getRequestParameterValues(javax.servlet.ServletRequest, String)>
    //    4    8:iconst_0
    //    5    9:aaload
    //    6   10:areturn
    }
}
