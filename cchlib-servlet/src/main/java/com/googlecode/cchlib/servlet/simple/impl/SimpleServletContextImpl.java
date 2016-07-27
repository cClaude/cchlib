package cx.ath.choisnet.servlet.impl;

import cx.ath.choisnet.servlet.ServletContextParamNotFoundException;
import cx.ath.choisnet.servlet.SimpleServletContext;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 * TODOC
 *
 */
public class SimpleServletContextImpl
    implements SimpleServletContext, Serializable
{
	private static final long serialVersionUID = 1L;
	private final ServletContext         servletContext;
    private transient Map<String,String> initParametersMap;

    public SimpleServletContextImpl(final ServletContext servletContext )
    {
        this.servletContext    = servletContext;
        this.initParametersMap = null;
    }

    @Override
    public String getInitParameter( final String paramName)
        throws ServletContextParamNotFoundException
    {
        try {
            return servletContext.getInitParameter(paramName).toString();
        	}
        catch(Exception e) {
            throw new ServletContextParamNotFoundException(paramName);
        	}
    }

    @Override
    public String getInitParameter(String paramName, String defaultValue)
    {
        try {
            return getInitParameter(paramName);
        	}
        catch(ServletContextParamNotFoundException e) { // $codepro.audit.disable logExceptions
            return defaultValue;
        	}
    }

    public Map<String,String> getInitParameters()
    {
        if( initParametersMap == null ) {

            synchronized(this) {
                Map<String,String>  map = new HashMap<String,String>();
                Enumeration<?>      en  = servletContext.getInitParameterNames();
                String              name;

                while( en.hasMoreElements() ) {
                    name = en.nextElement().toString();

                    map.put(
                        name,
                        servletContext.getInitParameter(name)
                        );
                	}

                initParametersMap = Collections.unmodifiableMap(map);
            	}
        }

        return initParametersMap;
    }

    public Iterator<String> getInitParameterNames()
    {
        return getInitParameters().keySet().iterator();
    }
}
