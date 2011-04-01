// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   AbstractGadget.java

package deprecated.cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;
import deprecated.cx.ath.choisnet.html.AbstractFormHTML;
import deprecated.cx.ath.choisnet.html.HTMLFormException;
import deprecated.cx.ath.choisnet.html.HTMLGadgetNotFoundException;
import deprecated.cx.ath.choisnet.html.HTMLWritable;

public abstract class AbstractGadget
    implements AbstractFormHTML, HTMLWritable
{
    protected String gadgetName;

    protected AbstractGadget(String gadgetName)
    {
        this.gadgetName = gadgetName;
    }

    public abstract Object getValue(ServletRequest servletrequest)
        throws HTMLFormException;

    public abstract long getLongValue(ServletRequest servletrequest)
        throws HTMLFormException;

    public abstract String getStringValue(ServletRequest servletrequest)
        throws HTMLFormException;

    public final Object getValue(ServletRequest request, Object defObject)
    {
        try {
            return getValue(request);
        }
        catch(HTMLFormException e) {
            return defObject;
        }
    }

    public final long getLongValue(ServletRequest request, long defaultValue)
    {
        try {
            return getLongValue(request);
        }
        catch(HTMLFormException e) {
            return defaultValue;
        }
    }

    public final String getStringValue(ServletRequest request, String defaultValue)
    {
        try {
            return getStringValue(request);
        }
        catch(HTMLFormException e) {
            return defaultValue;
        }
    }

    protected static final String[] getRequestParameterValues(ServletRequest request, String gadgetName)
        throws HTMLGadgetNotFoundException
    {
        String[] values = request.getParameterValues(gadgetName);

        if(values == null) {
            throw new HTMLGadgetNotFoundException(gadgetName);
        } 
        else {
            return values;
        }
    }
}
