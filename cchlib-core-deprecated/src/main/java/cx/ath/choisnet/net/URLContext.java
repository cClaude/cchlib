package cx.ath.choisnet.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 *
 * @deprecated no replacement
 */
@Deprecated
public class URLContext implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private /*Map*/TreeMap<String,String> requestProperty;
    /** @serial */
    private Proxy proxy; // NOT SERIALIZABLE
    /** @serial */
    private Boolean doInput;
    /** @serial */
    private Boolean doOutput;

    public URLContext()
    {
        doInput = null;
        doOutput = null;

        init(null, null);
    }

    public URLContext(URLContext context)
    {
        doInput = null;
        doOutput = null;

        init(context, null);
    }

    public URLContext(URLContext context, Proxy proxy)
    {
        doInput = null;
        doOutput = null;

        init(context, proxy);
    }

    private void init(URLContext context, Proxy proxy)
    {
        if( context == null ) {
            requestProperty = new TreeMap<String,String>();
            }
        else {
            requestProperty = new TreeMap<String,String>(context.requestProperty);
        }

        setProxy( proxy );
    }

    public void setProxy(Proxy proxy)
    {
        this.proxy = proxy;
    }

    public URLConnection openConnection(URL url)
        throws java.io.IOException
    {
        URLConnection connection;

        if(proxy == null){
            connection = url.openConnection();
        }
        else {
            connection = url.openConnection(proxy);
        }

        for( Map.Entry<String,String> entry : requestProperty.entrySet() ) {
            connection.setRequestProperty(
                    entry.getKey(),
                    entry.getValue()
                    );
        }

        if(doInput != null) {
            connection.setDoInput(doInput.booleanValue());
        }

        if(doOutput != null) {
            connection.setDoOutput(doOutput.booleanValue());
        }

        return connection;
    }

    public HttpURLConnection openHttpConnection(URL url)
        throws IOException, ClassCastException
    {
        return HttpURLConnection.class.cast(openConnection(url));
    }

    public void update(URLConnection connection)
    {
        String cookie = connection.getHeaderField("Set-Cookie");

        if(cookie != null) {
            int index = cookie.indexOf(';');

            if(index >= 0) {
                cookie = cookie.substring(0, index);
            }

            requestProperty.put("Cookie", cookie);
        }
    }

    public void setRequestProperty(String key, String value)
    {
        requestProperty.put(key, value);
    }

    public void removeRequestProperty(String key)
    {
        requestProperty.remove(key);
    }

    public Set<Map.Entry<String,String>> getRequestProperties()
    {
        return Collections.unmodifiableSet(requestProperty.entrySet());
    }

    public void setDoInput(boolean doinput)
    {
        doInput = Boolean.valueOf(doinput);
    }

    public void setDoOutput(boolean dooutput)
    {
        doOutput = Boolean.valueOf(dooutput);
    }

    public String toString()
    {
        return "URLContext[" + requestProperty + ']';
    }
}
