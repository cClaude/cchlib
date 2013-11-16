// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;
import cx.ath.choisnet.html.AbstractFormHTML;
import cx.ath.choisnet.html.HTMLFormException;
import cx.ath.choisnet.html.HTMLGadgetNotFoundException;
import cx.ath.choisnet.html.HTMLWritable;

public abstract class AbstractGadget
    implements AbstractFormHTML, HTMLWritable
{
    protected String gadgetName;

    protected AbstractGadget(String gadgetName)
    {
        this.gadgetName = gadgetName;
    }

    @Override
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
