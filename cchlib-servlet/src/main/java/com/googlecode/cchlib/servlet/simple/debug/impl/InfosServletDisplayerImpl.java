package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import cx.ath.choisnet.servlet.debug.InfosServletDisplayAnchor;
import cx.ath.choisnet.servlet.debug.InfosServletDisplayer;
import cx.ath.choisnet.util.ArrayHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import com.googlecode.cchlib.util.enumeration.EmptyEnumeration;

/**
 *
 */
public class InfosServletDisplayerImpl
    implements InfosServletDisplayer
{
    protected HttpServlet           httpServlet;
    protected ServletRequest        servletRequest;
    protected HttpSession           httpSession;
    protected ServletContext        servletContext;
    //protected ServletResponse       servletResponse;

    /**
     *
     * @param servlet
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    public InfosServletDisplayerImpl(
            final HttpServlet         servlet,
            final HttpServletRequest  request,
            final HttpServletResponse response
            )
        throws java.io.IOException
    {
        this(servlet, request, response, request.getSession(false));
    }

    /**
     *
     * @param servlet
     * @param request
     * @param response
     * @param httpSession
     * @throws java.io.IOException
     */
    public InfosServletDisplayerImpl(
            final HttpServlet       servlet,
            final ServletRequest    request,
            final ServletResponse   response,
            final HttpSession       httpSession
            )
        throws java.io.IOException
    {
        this.httpServlet        = servlet;
        this.servletRequest     = request;
        this.httpSession        = httpSession;
        this.servletContext     = servlet.getServletContext();
        //this.servletResponse    = response;
    }

    /**
     *
     * @param servlet
     * @param pageContext
     * @throws IOException
     */
    public InfosServletDisplayerImpl(
            final HttpServlet   servlet,
            final PageContext   pageContext
            )
        throws IOException
    {
        this(
            servlet,
            pageContext.getRequest(),
            pageContext.getResponse(),
            pageContext.getSession()
            );
    }

    private HttpServletRequest getHttpServletRequest()
    {
        if( servletRequest instanceof HttpServletRequest ) {
            return HttpServletRequest.class.cast(servletRequest);
            }
        return null;
    }

//    private HttpServletResponse getHttpServletResponse()
//    {
//        if( response instanceof HttpServletResponse ) {
//            return HttpServletResponse.class.cast(response);
//            }
//        return null;
//    }

    @Override
    public void appendHTML(final Appendable out) throws IOException
    {
        out.append(
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
                + "\t\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html>\n<head>\n"
                + "<title>ServletInfos Output</title>\n"
                );
        InfosServletDisplayImpl.appendJS( out );
        // TODO: Add css (from servlet parameters ?)
        out.append(
            "</head>\n"
                + "<body>\n<div id=\"infosservletdisplayer\" style=\"margin:0 auto;\">\n"
                + "<h3 class=\"summary\">Summary</h3>\n"
                );

        out.append(
            "<table class=\"summary\">\n"
                + "<tr><td>getServerInfo()</td><td>"
                );
        out.append(servletContext.getServerInfo());
        out.append(
            "</td></tr>\n"
                + "<tr><td>java.runtime.version</td><td>"
                );
        out.append(System.getProperties().getProperty("java.runtime.version"));
        out.append(
            "</td></tr>\n"
                + "</table>\n\n"
                );

        out.append("<h3>ServletInfos Output</h3>\n");

        final List<InfosServletDisplay> displayer = new ArrayList<InfosServletDisplay>();

        displayer.add(getHttpServletISD());
        displayer.add(getServletRequestISD());

        if( getHttpServletRequest() != null ) {
            displayer.add(getHttpServletRequest_getHeaderNamesISD());
            displayer.add(getHttpServletRequestCookiesISD());
            }

        displayer.add(getRequest_getParameterNamesISD());
        displayer.add(getRequest_getAttributeNamesISD());
        displayer.add(getConfig_getInitParameterNamesISD());
        displayer.add(getContextISD());
        displayer.add(getContext_getAttributeNamesISD());
        displayer.add(getContext_getInitParameterNamesISD());
        displayer.add(getHttpSessionISD());
        displayer.add(getHttpSession_getAttributeNamesISD());
        displayer.add(getJVM_MemoryISD());
        displayer.add(getJVM_SystemObjectISD());
        displayer.add(getJVM_SystemPropertiesISD());
        displayer.add(getJVM_System_getenvISD());

        out.append("<table class=\"menu\">\n");

        final StringBuilder sb = new StringBuilder();

        for( InfosServletDisplay display : displayer ) {
            sb.setLength( 0 );

            InfosServletDisplayAnchor anchor = display.getAnchor();
            sb.append("<tr><td><a href=\"#");
            sb.append(anchor.getId());
            sb.append("\">");
            sb.append(anchor.getDisplay());
            sb.append("</a></td></tr>\n");

            out.append( sb.toString() );
            }

        out.append("</table><br />\n");

        for(InfosServletDisplay display : displayer) {
            display.appendHTML(out);
            }

        out.append("</div>\n</body>\n</html>\n\n");
    }

    /**
     * Build informations from javax.servlet.http.HttpServlet
     */
    private InfosServletDisplay getHttpServletISD()
    {
        return new InfosServletDisplayImpl2(
                "Object: javax.servlet.http.HttpServlet<br />"
                    + InfosServletDisplayerImpl.getObjectInfo(httpServlet),
                "HttpServlet",
                httpServlet
                )
            .put(
                "servlet.getClass().getName()",
                httpServlet.getClass().getName()
                );
    }

    /**
     * Build informations from HttpServletRequest
     */
    private InfosServletDisplay getServletRequestISD()
    {
        return new InfosServletDisplayImpl2(
                "Object: ServletRequest<br />"
                    + InfosServletDisplayerImpl.getObjectInfo(servletRequest),
                "ServletRequest",
                servletRequest
                );
    }

    /**
     * Build informations from HttpServletRequest.getHeaderNames()
     */
    private InfosServletDisplay getHttpServletRequest_getHeaderNamesISD()
    {
        HttpServletRequest request = getHttpServletRequest();

        final Map<String,String>  map   = new TreeMap<String,String>();
        final StringBuilder       value = new StringBuilder();
        String                    name;

        for(
                Enumeration<String> enum0 = toEnumerationString(request.getHeaderNames());
                enum0.hasMoreElements();
                map.put(name, value.toString() )
            ) {
            name = enum0.nextElement();

            final Enumeration<?> enum2 = request.getHeaders(name);

            value.setLength(0);

            while( enum2.hasMoreElements() ) {
                value.append( enum2.nextElement() );
                value.append("<br />");
            }
        }

        return new InfosServletDisplayImpl(
                "Request header parameters<br/>request.getHeaderNames()",
                "HttpServletRequest.getHeaderNames()",
                map,
                "No request header parameters."
                );
    }

    /**
     *
     */
    private InfosServletDisplay getRequest_getParameterNamesISD()
    {
        final Map<String,String> map   = new TreeMap<String,String>();
        final StringBuilder      value = new StringBuilder();
        String                   name;

        for(
                Enumeration<String> enum0 = toEnumerationString(servletRequest.getParameterNames());
                enum0.hasMoreElements();
                map.put(name, value.toString())
                ) {
            name = enum0.nextElement();
            final String[] values = servletRequest.getParameterValues(name);

            value.setLength(0);

            String[] arr$ = values;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; i$++) {
                String v = arr$[i$];
                value.append( v );
                value.append( "<br/>" );
            }
        }

        return new InfosServletDisplayImpl(
                "Request url/form parameters<br />request.getParameterNames()",
                "request.getParameterNames()",
                map,
                "No request url/form parameters."
                );
    }

    /**
     *
     */
    private InfosServletDisplay getRequest_getAttributeNamesISD()
    {
        final Map<String,String> map = new TreeMap<String,String>();
        String name;
        Object value;

        for(
                Enumeration<String> enum0 = toEnumerationString(servletRequest.getAttributeNames());
                enum0.hasMoreElements();
                map.put(name, toString(value))
                ) {
            name = enum0.nextElement();
            value = servletRequest.getAttribute(name);
        }

        return new InfosServletDisplayImpl(
                "Request attributes<br />request.getAttributeNames()",
                "request.getAttributeNames()",
                map,
                "No attributes"
                );
    }

    /**
     *
     */
    private InfosServletDisplay getHttpServletRequestCookiesISD()
    {
        HttpServletRequest request = getHttpServletRequest();

        final SortedMap<String,String>  map     = new TreeMap<String,String>();
        final Enumeration<Cookie>       cookies = ArrayHelper.toEnumeration( request.getCookies() );
        Cookie                          cookie;

        while( cookies.hasMoreElements() ) {
            cookie = cookies.nextElement();
            map.put(cookie.getName(), cookie.getValue());
            }

        return new InfosServletDisplayImpl(
                "Cookies information",
                "request.getCookies()",
                map,
                "NO cookies !"
                );
    }

    /**
     *
     */
    private InfosServletDisplay getConfig_getInitParameterNamesISD()
    {
        final ServletConfig       servletConfig = httpServlet.getServletConfig();
        final Map<String,String>  map           = new TreeMap<String,String>();
        String name;

        for(Enumeration<String> enum0 = toEnumerationString(servletConfig.getInitParameterNames()); enum0.hasMoreElements(); ) {
            name = enum0.nextElement();
            map.put(name, servletConfig.getInitParameter(name));
            }

        return new InfosServletDisplayImpl(
            "ServletConfig init attributes<br />(servlet name  = '"
                + servletConfig.getServletName()
                + "')<br/>servletConfig.getInitParameterNames()",
            "servletConfig.getInitParameterNames()",
            map,
            "No attributes"
            );
    }

    /**
     *
     */
    private InfosServletDisplay getContextISD()
    {
        final StringBuilder valueOfgetResource = new StringBuilder();

        try {
            valueOfgetResource.append(servletContext.getResource("/"));
            }
        catch(MalformedURLException e) {
            valueOfgetResource.append(e);
            }

        return new InfosServletDisplayImpl2(
                "Informations from the ServletContext<br />"
                     + InfosServletDisplayerImpl.getObjectInfo(servletContext),
                "ServletContext",
                servletContext
                )
            .put("getContext( \"/\" )", toString(servletContext.getContext("/") ) )
            .put("getResource( \"/\" )", valueOfgetResource.toString())
            .put("getRealPath( \"/\" )", servletContext.getRealPath("/"))
            .put("getRequestDispatcher( \"/\" )", toString(servletContext.getRequestDispatcher("/")))
            .put("getMimeType( \"file.hqx\" )", servletContext.getMimeType("file.hqx"))
            .put("getMimeType( \"file.png\" )", servletContext.getMimeType("file.png"))
            .put("getMimeType( \"toto.svg\" )", servletContext.getMimeType("file.svg"));
    }

    /**
     *
     */
    private InfosServletDisplay getContext_getAttributeNamesISD()
    {
        final Map<String,String> map = new TreeMap<String,String>();
        String name;

        for(
                Enumeration<String> enum0 = toEnumerationString(servletContext.getAttributeNames());
                enum0.hasMoreElements();
                map.put(name, toString(servletContext.getAttribute(name)))) {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImpl(
                "Attributes from the ServletContext: servletContext.getAttributeNames()",
                "servletContext.getAttributeNames()",
                map,
                "No attributes"
                );
    }

    /**
     *
     */
    private InfosServletDisplay getContext_getInitParameterNamesISD()
    {
        final Map<String,String> map = new TreeMap<String,String>();
        String name;

        for(
                Enumeration<String> enum0 = toEnumerationString(servletContext.getInitParameterNames());
                enum0.hasMoreElements();
                map.put(name, servletContext.getInitParameter(name))
                ) {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImpl(
                "Init attributes from the ServletContext<br/>servletContext.getInitParameterNames()",
                "servletContext.getInitParameterNames()",
                map,
                "No attributes"
                );
    }

    /**
     *
     */
    private InfosServletDisplay getHttpSessionISD()
    {
        return new InfosServletDisplayImpl2(
                "HttpSession<br />"
                    + InfosServletDisplayerImpl.getObjectInfo(httpSession),
                "HttpSession",
                httpSession
                );
    }

    /**
     *
     */
    private InfosServletDisplay getHttpSession_getAttributeNamesISD()
    {
        final Map<String,String> map   = new TreeMap<String,String>();
        final StringBuilder      title = new StringBuilder("Here are the HttpSession : ");

        Enumeration<String> enum0;
        String              noDataMsg;

        if(httpSession == null) {
            title.append("NOT FOUND");

            enum0     = new EmptyEnumeration<String>();
            noDataMsg = "There are no HttpSession !";
        }
        else {
            title.append("ID=");
            title.append(httpSession.getId());

            enum0     = toEnumerationString(httpSession.getAttributeNames());
            noDataMsg = "There are no object on the current HttpSession.";
        }

        while( enum0.hasMoreElements() ) {
            String name = enum0.nextElement();

            map.put(
                    name,
                    toString( httpSession.getAttribute(name) )
                    );
        }

        return new InfosServletDisplayImpl(
                    title.toString(),
                    "HttpSession.getAttributeNames()",
                    map,
                    noDataMsg
                    );
    }

    /**
     *
     */
    private InfosServletDisplay getJVM_MemoryISD()
    {
        final Map<String,String>    map         = new TreeMap<String,String>();
        final Runtime               thisRuntime = Runtime.getRuntime();
        final long                  freeMemory  = thisRuntime.freeMemory();
        final long                  totalMemory = thisRuntime.totalMemory();

        map.put("freeMemory()", Long.toString(freeMemory));
        map.put("totalMemory()", Long.toString(totalMemory));
        map.put("totalMemory() - freeMemory()", Long.toString(totalMemory - freeMemory));

        return new InfosServletDisplayImpl(
                    "JVM memory informations",
                    "JVM_Memory",
                    map
                    );
    }

    /**
     *
     */
    private InfosServletDisplay getJVM_SystemPropertiesISD()
    {
        final Properties         prop = System.getProperties();
        final Map<String,String> map  = new TreeMap<String,String>();

        String name;

        for(
                Enumeration<String> enum0 = toEnumerationString(prop.propertyNames());
                enum0.hasMoreElements();
                map.put(name, prop.getProperty(name))
                ) {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImpl(
                "JVM System.getProperties() informations : ",
                "System.getProperties()",
                map,
                "no property"
                );
    }

    private InfosServletDisplay getJVM_System_getenvISD()
    {
        return new InfosServletDisplayImpl(
                "Here is the JVM getenv() informations",
                "Systemgetenv",
                System.getenv()
                );
    }

    private InfosServletDisplay getJVM_SystemObjectISD()
    {
        final Map<String,String> map = new TreeMap<String,String>();

        map.put("System.out", toString(System.out));
        map.put("System.err", toString(System.err));
        map.put("System.in",  toString(System.in));

        return new InfosServletDisplayImpl(
                "Here is the JVM System Object informations",
                "System",
                map
                );
    }

    /**
     *
     * @param o
     * @return TODOC
     */
    private static String toString(Object o)
    {
        if(o != null) {
            return o.toString();
        }
        else {
            return "Object is null";
        }
    }

    /**
     *
     * @param o
     * @return TODOC
     */
    private static String getObjectInfo(Object o)
    {
        if(o != null) {
            return "ClassName:" + o.getClass().getName() + "<br/>" + o.toString();
        }
        else {
            return "NULL";
        }
    }

    /**
     *
     * @param enumerator
     * @return TODOC
     */
    private static Enumeration<String> toEnumerationString(
            final Enumeration<?> enumerator
            )
    {
        return new Enumeration<String>()
        {
            @Override
            public boolean hasMoreElements()
            {
                return enumerator.hasMoreElements();
            }
            @Override
            public String nextElement()
            {
                Object o = enumerator.nextElement();

                if( o == null ) {
                    return null;
                }
                else {
                    return o.toString();
                    }
            }
        };
    }

}
