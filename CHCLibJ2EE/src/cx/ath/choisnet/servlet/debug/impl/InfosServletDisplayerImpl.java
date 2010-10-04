package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import cx.ath.choisnet.servlet.debug.InfosServletDisplayer;
import cx.ath.choisnet.util.ArrayHelper;
import cx.ath.choisnet.util.enumeration.EnumerationHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InfosServletDisplayerImpl
    implements InfosServletDisplayer
{

    protected HttpServlet         servlet;
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected HttpSession         httpSession;
    protected ServletContext      servletContext;

    public InfosServletDisplayerImpl(
            HttpServlet         servlet, 
            HttpServletRequest  request, 
            HttpServletResponse response
            )
        throws java.io.IOException
    {
        this(servlet, request, response, request.getSession(false));
    }

    public InfosServletDisplayerImpl(
            HttpServlet         servlet, 
            HttpServletRequest  request,
            HttpServletResponse response, 
            HttpSession httpSession
            )
        throws java.io.IOException
    {
        this.servlet = servlet;
        this.request = request;
        this.response = response;
        this.httpSession = httpSession;
        this.servletContext = servlet.getServletContext();
    }

    public void appendHTML(Appendable out)
    {
        try {
            out.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n");
            out.append("\t\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
            out.append("<html>\n<head><title>ServletInfos Output</title></head>\n");
            out.append("<body>\n<center>\n");
            out.append("<h1>Summary</h1>\n");
            out.append("<table class=\"summary\">\n");
            out.append("<tr><td>getServerInfo()</td><td>");
            out.append(servletContext.getServerInfo());
            out.append("</td></tr>\n");
            out.append("<tr><td>java.runtime.version</td><td>");
            out.append(System.getProperties().getProperty("java.runtime.version"));
            out.append("</td></tr>\n");
            out.append("</table\n");
            out.append("<h1>ServletInfos Output</h1>\n");

            List<InfosServletDisplay> displayer = new LinkedList<InfosServletDisplay>();

            displayer.add(getHttpServlet());
            displayer.add(getHttpServletRequest());
            displayer.add(getRequest_getHeaderNames());
            displayer.add(getRequest_getParameterNames());
            displayer.add(getRequest_getAttributeNames());
            displayer.add(getCookies());
            displayer.add(getConfig_getInitParameterNames());
            displayer.add(getContext());
            displayer.add(getContext_getAttributeNames());
            displayer.add(getContext_getInitParameterNames());
            displayer.add(getHttpSession());
            displayer.add(getHttpSession_getAttributeNames());
            displayer.add(getJVM_Memory());
            displayer.add(getJVM_SystemObject());
            displayer.add(getJVM_SystemProperties());

            out.append("<table class=\"menu\">\n");

            StringBuilder sb = new StringBuilder();

            for( InfosServletDisplay display : displayer ) {
                sb.setLength( 0 );

                InfosServletDisplay.Anchor anchor = display.getAnchor();
                sb.append("<tr><td><a href=\"#");
                sb.append(anchor.getHTMLName());
                sb.append("\">");
                sb.append(anchor.getDisplay());
                sb.append("</a></td></tr>\n");

                out.append( sb.toString() );
            }

            out.append("</table><br />\n");

            for(InfosServletDisplay display : displayer ) {
                display.appendHTML(out);
            }
            
            out.append("</center>\n</body>\n</html>\n");
            out.append("\n");
        }
        catch(IOException hideException) {
            throw new RuntimeException(hideException);
        }
    }

    private InfosServletDisplay getHttpServlet()
    {
        return new InfosServletDisplayImpl2(
                "Here is the javax.servlet.http.HttpServlet object<br />"
                    + InfosServletDisplayerImpl.getObjectInfo(servlet),
                "HttpServlet", 
                servlet
                )
            .put(
                "servlet.getClass().getName()", 
                servlet.getClass().getName()
                );
    }

    private InfosServletDisplay getHttpServletRequest()
    {
        return new InfosServletDisplayImpl2((new StringBuilder()).append("Here is the HttpServletRequest object<br />").append(cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayerImpl.getObjectInfo(request)).toString(), "HttpServletRequest", request);
    }

    private InfosServletDisplay getRequest_getHeaderNames()
    {
        Map<String,String>  map   = new TreeMap<String,String>();
        StringBuilder       value = new StringBuilder();
        String              name;

        for(
                Enumeration<String> enum0 = toEnumerationString(request.getHeaderNames());
                enum0.hasMoreElements();
                map.put(name, value.toString() )
            ) {
            name = enum0.nextElement();

            Enumeration<?> enum2 = request.getHeaders(name);

            value.setLength(0);

            while( enum2.hasMoreElements() ) {
                value.append( enum2.nextElement() );
                value.append("<br/>"); 
            }
        }

        return new InfosServletDisplayImpl("Here are the request header parameters<br/>request.getHeaderNames()", "HttpServletRequest.getHeaderNames()", map, "There are no request header parameters.");
    }

    private cx.ath.choisnet.servlet.debug.InfosServletDisplay getRequest_getParameterNames()
    {
        Map<String,String> map = new TreeMap<String,String>();
        StringBuilder value = new StringBuilder();
        String name;
        for(
                Enumeration<String> enum0 = toEnumerationString(request.getParameterNames());
                enum0.hasMoreElements();
                map.put(name, value.toString())
                ) {
            name = enum0.nextElement();
            String values[] = request.getParameterValues(name);

            value.setLength(0);

            String arr$[] = values;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; i$++) {
                String v = arr$[i$];
                value.append((new StringBuilder()).append(v).append("<br/>").toString());
            }
        }

        return new InfosServletDisplayImpl("Here are the request url/form parameters<br />request.getParameterNames()", "request.getParameterNames()", map, "There are no request url/form parameters.");
    }

    private cx.ath.choisnet.servlet.debug.InfosServletDisplay getRequest_getAttributeNames()
    {
        Map<String,String> map = new TreeMap<String,String>();
        String name;
        Object value;
        for(
                Enumeration<String> enum0 = toEnumerationString(request.getAttributeNames());
                enum0.hasMoreElements();
                map.put(name, toString(value))
                ) {
            name = enum0.nextElement();
            value = request.getAttribute(name);
        }

        return new InfosServletDisplayImpl("Here are the request attributes<br />request.getAttributeNames()", "request.getAttributeNames()", map, "There are no request attributes.");
    }

    private InfosServletDisplay getCookies()
    {
        Map<String,String>  map = new TreeMap<String,String>();
        Cookie              cookie;
        Enumeration<Cookie> cookies = ArrayHelper.toEnumeration( request.getCookies() );
        
        while( cookies.hasMoreElements() ) {
            cookie = cookies.nextElement();
            map.put(cookie.getName(), cookie.getValue());
        }

        return new InfosServletDisplayImpl("Here is the Cookies information", "request.getCookies()", map, "NO cookies !");
    }

    private InfosServletDisplay getConfig_getInitParameterNames()
    {
        ServletConfig       servletConfig   = servlet.getServletConfig();
        Map<String,String>  map             = new TreeMap<String,String>();
        String name;
        
        for(Enumeration<String> enum0 = toEnumerationString(servletConfig.getInitParameterNames()); enum0.hasMoreElements(); ) {
            name = enum0.nextElement();
            map.put(name, servletConfig.getInitParameter(name));
        }

        return new InfosServletDisplayImpl((new StringBuilder()).append("Here are the ServletConfig init attributes<br />(servlet name  = '").append(servletConfig.getServletName()).append("')<br/>servletConfig.getInitParameterNames()").toString(), "servletConfig.getInitParameterNames()", map, "There are no Servlet Config attributes.");
    }

    private InfosServletDisplay getContext()
    {
        StringBuilder valueOfgetResource = new StringBuilder();

        try {
            valueOfgetResource.append(servletContext.getResource("/"));
        }
        catch(MalformedURLException e) {
            valueOfgetResource.append(e);
        }

        return new InfosServletDisplayImpl2(
                "Here is information from the ServletContext<br />"
                     + InfosServletDisplayerImpl.getObjectInfo(servletContext),
                "ServletContext",
                servletContext
                )
            .put("getContext( \"/\" )", toString(servletContext.getContext("/") ) )
            .put("getResource( \"/\" )", valueOfgetResource.toString())
            .put("getRealPath( \"/\" )", servletContext.getRealPath("/"))
            .put("getRequestDispatcher( \"/\" )", toString(servletContext.getRequestDispatcher("/")))
            .put("getMimeType( \"file.hqx\" )", servletContext.getMimeType("file.hqx"))
            .put("getMimeType( \"toto.png\" )", servletContext.getMimeType("toto.png"));
    }

    private InfosServletDisplay getContext_getAttributeNames()
    {
        Map<String,String> map = new TreeMap<String,String>();
        String name;
        for(
                Enumeration<String> enum0 = toEnumerationString(servletContext.getAttributeNames());
                enum0.hasMoreElements();
                map.put(name, toString(servletContext.getAttribute(name))))
        {
            name = enum0.nextElement();
        }
        return new InfosServletDisplayImpl("Here are attributes from the ServletContext: servletContext.getAttributeNames()", "servletContext.getAttributeNames()", map, "There is no attributes on the ServletContext");
    }

    private InfosServletDisplay getContext_getInitParameterNames()
    {
        Map<String,String> map = new TreeMap<String,String>();
        String name;
        for(Enumeration<String> enum0 = toEnumerationString(servletContext.getInitParameterNames()); enum0.hasMoreElements(); map.put(name, servletContext.getInitParameter(name)))
        {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImpl(
                "Here are  init attributs from the ServletContext<br/>servletContext.getInitParameterNames()",
                "servletContext.getInitParameterNames()",
                map,
                "There is no init attributs from the ServletContext"
                );
    }

    private InfosServletDisplay getHttpSession()
    {
        return new InfosServletDisplayImpl2(
                "Here is information from the HttpSession<br />"
                    + InfosServletDisplayerImpl.getObjectInfo(httpSession),
                "HttpSession",
                httpSession
                );
    }

    private InfosServletDisplay getHttpSession_getAttributeNames()
    {
        Map<String,String> map = new TreeMap<String,String>();
        StringBuilder title = new StringBuilder("Here are the HttpSession : ");

        Enumeration<String> enum0;
        String noDataMsg;

        if(httpSession == null) {
            title.append("NOT FOUND");

            enum0     = EnumerationHelper.empty();
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

    private InfosServletDisplay getJVM_Memory()
    {
        Map<String,String> map = new TreeMap<String,String>();

        Runtime thisRuntime = Runtime.getRuntime();
        long    freeMemory  = thisRuntime.freeMemory();
        long    totalMemory = thisRuntime.totalMemory();

        map.put("freeMemory()", Long.toString(freeMemory));
        map.put("totalMemory()", Long.toString(totalMemory));
        map.put("totalMemory() - freeMemory()", Long.toString(totalMemory - freeMemory));

        return new InfosServletDisplayImpl(
                    "JVM memory informations",
                    "JVM_Memory",
                    map
                    );
    }

    private InfosServletDisplay getJVM_SystemProperties()
    {
        Properties         prop = System.getProperties();
        Map<String,String> map  = new TreeMap<String,String>();

        String name;
        for(
                Enumeration<String> enum0 = toEnumerationString(prop.propertyNames());
                enum0.hasMoreElements();
                map.put(name, prop.getProperty(name))
                )
        {
            name = enum0.nextElement();
        }

        return new InfosServletDisplayImpl(
                "Here is the JVM System.getProperties() informations : ",
                "System.getProperties()",
                map,
                "There is no init attributs from the ServletContext"
                );
    }

    private InfosServletDisplay getJVM_SystemObject()
    {
        Map<String,String> map = new TreeMap<String,String>();

        map.put("System.out", toString(System.out));
        map.put("System.err", toString(System.err));
        map.put("System.in",  toString(System.in));

        return new InfosServletDisplayImpl("Here is the JVM System Object informations", "System", map);
    }

    private final String toString(Object o)
    {
        if(o != null) {
            return o.toString();
        }
        else {
            return "Object is null";
        }
    }

    private static final String getObjectInfo(Object o)
    {
        if(o != null) {
            return "ClassName:" + o.getClass().getName() + "<br/>" + o.toString();
        }
        else {
            return "NULL";
        }
    }

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
