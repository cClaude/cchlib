package com.googlecode.cchlib.servlet.simple.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContext;
import com.googlecode.cchlib.servlet.simple.ServletContextParamNotFoundException;
import com.googlecode.cchlib.servlet.simple.SimpleServletContext;

/**
 * Default {@link SimpleServletContext} implementation
 */
public class SimpleServletContextImpl
    implements SimpleServletContext
{
    private final ServletContext    servletContext;
    private Map<String,String>      initParametersMap;

    /**
     * Wrap {@link ServletContext}
     *
     * @param servletContext {@link ServletContext} to inspect
     */
    public SimpleServletContextImpl( final ServletContext servletContext )
    {
        this.servletContext = servletContext;
    }

    @Override
    public String getInitParameter( final String paramName )
        throws ServletContextParamNotFoundException
    {
        final String value = this.servletContext.getInitParameter( paramName );

        if( value != null ) {
            return value;
        } else {
            throw new ServletContextParamNotFoundException( paramName );
        }
    }

    @Override
    public String getInitParameter( final String paramName, final String defaultValue )
    {
        final String value = this.servletContext.getInitParameter( paramName );

        if( value != null ) {
            return value;
        } else {
            return defaultValue;
        }
    }

    public Map<String,String> getInitParameters()
    {
        if( this.initParametersMap == null ) {

            synchronized(this) {
                final Map<String,String>  map = new HashMap<>();
                final Enumeration<?>      en  = this.servletContext.getInitParameterNames();
                String              name;

                while( en.hasMoreElements() ) {
                    name = en.nextElement().toString();

                    map.put(
                        name,
                        this.servletContext.getInitParameter(name)
                        );
                    }

                this.initParametersMap = Collections.unmodifiableMap(map);
                }
        }

        return this.initParametersMap;
    }

    public Iterator<String> getInitParameterNames()
    {
        return getInitParameters().keySet().iterator();
    }
}
