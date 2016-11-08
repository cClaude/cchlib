package com.googlecode.cchlib.servlet.simple.debug.impl;

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
import com.googlecode.cchlib.servlet.simple.debug.InfosServletDisplay;
import com.googlecode.cchlib.servlet.simple.debug.InfosServletDisplayAnchor;
import com.googlecode.cchlib.servlet.simple.debug.InfosServletDisplayer;
import com.googlecode.cchlib.util.enumeration.EmptyEnumeration;
import cx.ath.choisnet.util.ArrayHelper;

/**
 * Default implement for {@link InfosServletDisplayer}
 */
public class InfosServletDisplayerImpl
    implements InfosServletDisplayer
{
    private static final String NO_ATTRIBUTES = "No attributes";
    protected HttpServlet           httpServlet;
    protected ServletRequest        servletRequest;
    protected HttpSession           httpSession;
    protected ServletContext        servletContext;

    /**
     *
     * @param servlet
     * @param request
     * @param response
     * @throws IOException
     */
    public InfosServletDisplayerImpl(
            final HttpServlet         servlet,
            final HttpServletRequest  request,
            final HttpServletResponse response
            )
        throws IOException
    {
        this(servlet, request, response, request.getSession(false));
    }

    /**
     *
     * @param servlet
     * @param request
     * @param response
     * @param httpSession
     * @throws IOException
     */
    public InfosServletDisplayerImpl(
            final HttpServlet       servlet,
            final ServletRequest    request,
            final ServletResponse   response, // NOSONAR
            final HttpSession       httpSession
            )
        throws IOException
    {
        this.httpServlet        = servlet;
        this.servletRequest     = request;
        this.httpSession        = httpSession;
        this.servletContext     = servlet.getServletContext();
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
        if( this.servletRequest instanceof HttpServletRequest ) {
            return HttpServletRequest.class.cast(this.servletRequest);
            }
        return null;
    }

    @Override
    public void appendHTML(final Appendable out) throws IOException
    {
        out.append(
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
                + "\t\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html>\n<head>\n"
                + "<title>ServletInfos Output</title>\n"
                );
        InfosServletDisplayImplForMap.appendJS( out );

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
        out.append(this.servletContext.getServerInfo());
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

        final List<InfosServletDisplay> displayer = new ArrayList<>();

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
        displayer.add(getJVM_RuntimeISD());
        displayer.add(getJVM_SystemObjectISD());
        displayer.add(getJVM_SystemPropertiesISD());
        displayer.add(getJVM_System_getenvISD());

        out.append("<table class=\"menu\">\n");

        final StringBuilder sb = new StringBuilder();

        for( final InfosServletDisplay display : displayer ) {
            sb.setLength( 0 );

            final InfosServletDisplayAnchor anchor = display.getAnchor();
            sb.append("<tr><td><a href=\"#");
            sb.append(anchor.getId());
            sb.append("\">");
            sb.append(anchor.getDisplay());
            sb.append("</a></td></tr>\n");

            out.append( sb.toString() );
            }

        out.append("</table><br />\n");

        for(final InfosServletDisplay display : displayer) {
            display.appendHTML(out);
            }

        out.append("</div>\n</body>\n</html>\n\n");
    }

    /**
     * Build informations from javax.servlet.http.HttpServlet
     */
    private InfosServletDisplay getHttpServletISD()
    {
        return new InfosServletDisplayImplForGetterAndSetter(
                "Object: javax.servlet.http.HttpServlet<br />"
                    + StringTools.getObjectInfo(this.httpServlet),
                "HttpServlet",
                this.httpServlet
                )
            .put(
                "servlet.getClass().getName()",
                this.httpServlet.getClass().getName()
                );
    }

    /**
     * Build informations from HttpServletRequest
     */
    private InfosServletDisplay getServletRequestISD()
    {
        return new InfosServletDisplayImplForGetterAndSetter(
                "Object: ServletRequest<br />"
                    + StringTools.getObjectInfo(this.servletRequest),
                "ServletRequest",
                this.servletRequest
                );
    }

    /**
     * Build informations from HttpServletRequest.getHeaderNames()
     */
    private InfosServletDisplay getHttpServletRequest_getHeaderNamesISD() // NOSONAR
    {
        final HttpServletRequest request = getHttpServletRequest();

        final Map<String,String>  map   = new TreeMap<>();
        final StringBuilder       value = new StringBuilder();
        String                    name;

        for(
                final Enumeration<String> headerNames = StringTools.toEnumerationString(request.getHeaderNames());
                headerNames.hasMoreElements();
                map.put(name, value.toString() )
            ) {
            name = headerNames.nextElement();

            final Enumeration<?> headerValues = request.getHeaders(name);

            value.setLength(0);

            while( headerValues.hasMoreElements() ) {
                value.append( headerValues.nextElement() );
                value.append("<br />");
            }
        }

        return new InfosServletDisplayImplForMap(
                "Request header parameters<br/>request.getHeaderNames()",
                "HttpServletRequest.getHeaderNames()",
                map,
                "No request header parameters."
                );
    }

    private InfosServletDisplay getRequest_getParameterNamesISD() // NOSONAR
    {
        final Map<String,String> map     = new TreeMap<>();
        final StringBuilder      builder = new StringBuilder();
        String                   name;

        for(
                final Enumeration<String> enum0 = StringTools.toEnumerationString(this.servletRequest.getParameterNames());
                enum0.hasMoreElements();
                map.put(name, builder.toString())
                ) {
            name = enum0.nextElement();
            final String[] values = this.servletRequest.getParameterValues(name);

            builder.setLength(0);

            for( final String value : values ) {
                builder.append( value );
                builder.append( "<br/>" );
            }
        }

        return new InfosServletDisplayImplForMap(
                "Request url/form parameters<br />request.getParameterNames()",
                "request.getParameterNames()",
                map,
                "No request url/form parameters."
                );
    }

    /**
     *
     */
    private InfosServletDisplay getRequest_getAttributeNamesISD() // NOSONAR
    {
        final Map<String,String> map = new TreeMap<>();
        String name;
        Object value;

        for(
                final Enumeration<String> enum0 = StringTools.toEnumerationString(
                        this.servletRequest.getAttributeNames()
                        );
                enum0.hasMoreElements();
                map.put(name, StringTools.safeToString(value))
                ) {
            name = enum0.nextElement();
            value = this.servletRequest.getAttribute(name);
        }

        return new InfosServletDisplayImplForMap(
                "Request attributes<br />request.getAttributeNames()",
                "request.getAttributeNames()",
                map,
                NO_ATTRIBUTES
                );
    }

    private InfosServletDisplay getHttpServletRequestCookiesISD()
    {
        final HttpServletRequest request = getHttpServletRequest();

        final SortedMap<String,String>  map     = new TreeMap<>();
        final Enumeration<Cookie>       cookies = ArrayHelper.toEnumeration( request.getCookies() );
        Cookie                          cookie;

        while( cookies.hasMoreElements() ) {
            cookie = cookies.nextElement();
            map.put(cookie.getName(), cookie.getValue());
            }

        return new InfosServletDisplayImplForMap(
                "Cookies information",
                "request.getCookies()",
                map,
                "NO cookies !"
                );
    }

    private InfosServletDisplay getConfig_getInitParameterNamesISD() // NOSONAR
    {
        final ServletConfig       servletConfig = this.httpServlet.getServletConfig();
        final Map<String,String>  map           = new TreeMap<>();
        String name;

        for(final Enumeration<String> enum0 = StringTools.toEnumerationString(servletConfig.getInitParameterNames()); enum0.hasMoreElements(); ) {
            name = enum0.nextElement();
            map.put(name, servletConfig.getInitParameter(name));
            }

        return new InfosServletDisplayImplForMap(
            "ServletConfig init attributes<br />(servlet name  = '"
                + servletConfig.getServletName()
                + "')<br/>servletConfig.getInitParameterNames()",
            "servletConfig.getInitParameterNames()",
            map,
            NO_ATTRIBUTES
            );
    }

    private InfosServletDisplay getContextISD()
    {
        final StringBuilder valueOfgetResource = new StringBuilder();

        try {
            valueOfgetResource.append(this.servletContext.getResource("/"));
            }
        catch(final MalformedURLException e) {
            valueOfgetResource.append(e);
            }

        return new InfosServletDisplayImplForGetterAndSetter(
                "Informations from the ServletContext<br />"
                     + StringTools.getObjectInfo(this.servletContext),
                "ServletContext",
                this.servletContext
                )
            .put("getContext( \"/\" )", StringTools.safeToString(this.servletContext.getContext("/") ) )
            .put("getResource( \"/\" )", valueOfgetResource.toString())
            .put("getRealPath( \"/\" )", this.servletContext.getRealPath("/"))
            .put("getRequestDispatcher( \"/\" )", StringTools.safeToString(this.servletContext.getRequestDispatcher("/")))
            .put("getMimeType( \"file.hqx\" )", this.servletContext.getMimeType("file.hqx"))
            .put("getMimeType( \"file.png\" )", this.servletContext.getMimeType("file.png"))
            .put("getMimeType( \"toto.svg\" )", this.servletContext.getMimeType("file.svg"));
    }

    private InfosServletDisplay getContext_getAttributeNamesISD() // NOSONAR
    {
        final Map<String,String> map = new TreeMap<>();
        String name;

        for(
                final Enumeration<String> enum0 = StringTools.toEnumerationString(this.servletContext.getAttributeNames());
                enum0.hasMoreElements();
                map.put(name, StringTools.safeToString(this.servletContext.getAttribute(name)))) {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImplForMap(
                "Attributes from the ServletContext: servletContext.getAttributeNames()",
                "servletContext.getAttributeNames()",
                map,
                NO_ATTRIBUTES
                );
    }

    private InfosServletDisplay getContext_getInitParameterNamesISD() // NOSONAR
    {
        final Map<String,String> map = new TreeMap<>();
        String name;

        for(
                final Enumeration<String> enum0 = StringTools.toEnumerationString(this.servletContext.getInitParameterNames());
                enum0.hasMoreElements();
                map.put(name, this.servletContext.getInitParameter(name))
                ) {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImplForMap(
                "Init attributes from the ServletContext<br/>servletContext.getInitParameterNames()",
                "servletContext.getInitParameterNames()",
                map,
                NO_ATTRIBUTES
                );
    }

    private InfosServletDisplay getHttpSessionISD()
    {
        return new InfosServletDisplayImplForGetterAndSetter(
                "HttpSession<br />"
                    + StringTools.getObjectInfo(this.httpSession),
                "HttpSession",
                this.httpSession
                );
    }

    private InfosServletDisplay getHttpSession_getAttributeNamesISD() // NOSONAR
    {
        final Map<String,String> map   = new TreeMap<>();
        final StringBuilder      title = new StringBuilder("Here are the HttpSession : ");

        Enumeration<String> enum0;
        String              noDataMsg;

        if(this.httpSession == null) {
            title.append("NOT FOUND");

            enum0     = new EmptyEnumeration<>();
            noDataMsg = "There are no HttpSession !";
        }
        else {
            title.append("ID=");
            title.append(this.httpSession.getId());

            enum0     = StringTools.toEnumerationString(this.httpSession.getAttributeNames());
            noDataMsg = "There are no object on the current HttpSession.";
        }

        while( enum0.hasMoreElements() ) {
            final String name = enum0.nextElement();

            map.put(
                    name,
                    StringTools.safeToString( this.httpSession.getAttribute(name) )
                    );
        }

        return new InfosServletDisplayImplForMap(
                    title.toString(),
                    "HttpSession.getAttributeNames()",
                    map,
                    noDataMsg
                    );
    }

    private InfosServletDisplay getJVM_RuntimeISD() // NOSONAR
    {
        final Map<String,String>    map         = new TreeMap<>();
        final Runtime               runtime     = Runtime.getRuntime();

        final long freeMemory           = runtime.freeMemory();
        final long totalMemory          = runtime.totalMemory();
        final long maxMemory            = runtime.maxMemory();
        final int  availableProcessors  = runtime.availableProcessors();

        map.put("freeMemory()", Long.toString(freeMemory));
        map.put("totalMemory()", Long.toString(totalMemory));
        map.put("totalMemory() - freeMemory()", Long.toString(totalMemory - freeMemory));
        map.put("maxMemory()", Long.toString(maxMemory));
        map.put("availableProcessors()", Integer.toString(availableProcessors));

        return new InfosServletDisplayImplForMap(
                    "JVM Runtime (memory informations,...)",
                    "JVM_Runtime",
                    map
                    );
        }

    private InfosServletDisplay getJVM_SystemPropertiesISD() // NOSONAR
    {
        final Properties         prop = System.getProperties();
        final Map<String,String> map  = new TreeMap<>();

        String name;

        for(
                final Enumeration<String> enum0 = StringTools.toEnumerationString(prop.propertyNames());
                enum0.hasMoreElements();
                map.put(name, prop.getProperty(name))
                ) {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImplForMap(
                "JVM System.getProperties() informations : ",
                "System.getProperties()",
                map,
                "no property"
                );
    }

    private InfosServletDisplay getJVM_System_getenvISD() // NOSONAR
    {
        return new InfosServletDisplayImplForMap(
                "Here is the JVM getenv() informations",
                "Systemgetenv",
                System.getenv()
                );
    }

    private InfosServletDisplay getJVM_SystemObjectISD() // NOSONAR
    {
        final Map<String,String> map = new TreeMap<>();

        map.put("System.out", StringTools.safeToString(System.out)); // NOSONAR
        map.put("System.err", StringTools.safeToString(System.err)); // NOSONAR
        map.put("System.in",  StringTools.safeToString(System.in)); // NOSONAR

        return new InfosServletDisplayImplForMap(
                "Here is the JVM System Object informations",
                "System",
                map
                );
    }
}
