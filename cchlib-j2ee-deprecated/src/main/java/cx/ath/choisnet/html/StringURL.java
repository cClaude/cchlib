package cx.ath.choisnet.html;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StringURL
    implements java.io.Serializable
{

    private static final long serialVersionUID = 1L;
    private String baseURL;
    private List<String[]> params;

    public StringURL(String baseURL)
    {
        params = new LinkedList<String[]>();
        this.baseURL = baseURL;
    }

    public StringURL append(String paramName, String paramValue)
    {
        String entry[] = {
            paramName, paramValue
        };

        params.add(entry);

        return this;
    }

    public StringURL append(String paramName, int paramValue)
    {
        return append(paramName, Integer.toString(paramValue));
    }

    public StringURL append(String paramName, long paramValue)
    {
        return append(paramName, Long.toString(paramValue));
    }

    public StringURL append(String paramName, boolean paramValue)
    {
        return append(paramName, Boolean.toString(paramValue));
    }

    private String toString(String paramsSeparator, String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        boolean first;
        if(baseURL.indexOf('?') == -1) {
            first = true;
        } else {
            first = false;
        }
        StringBuilder sb = new StringBuilder(baseURL);
        String param[];
        for(Iterator<String[]> i$ = params.iterator(); i$.hasNext(); sb.append(URLEncoder.encode(param[1], charsetName))) {
            param = i$.next();
            
            if(first) {
                first = false;
                sb.append("?");
            } else {
                sb.append(paramsSeparator);
            }
            sb.append(param[0]);
            sb.append("=");
        }

        return sb.toString();
    }

    public String toString(String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        return toString("&", charsetName);
    }

    public String toHTML(String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        return toString("&amp;", charsetName);
    }
}
