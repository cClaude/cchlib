package com.googlecode.cchlib.servlet.simple;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.googlecode.cchlib.util.Wrappable;

public class ParameterValueWrapper<T>
{
    private final Wrappable<String,T> wrapper;

    public ParameterValueWrapper(final Wrappable<String,T> wrapper)
    {
        this.wrapper = wrapper;
    }

    public List<T> asList(final HttpServletRequest request, final String paramName)
    {
        return asList(request.getParameterValues(paramName));
    }

    public List<T> asList(final ParameterValue paramValue)
    {
        return asList(paramValue.toArray());
    }

    private List<T> asList(final String[] values)
    {
        if( values == null) {
            return null;
        }

        final int     len  = values.length;
        final List<T> list = new ArrayList<T>(len);

        for(int i = 0; i < len; i++) {
            list.add( this.wrapper.wrap( values[i] ) );
        }

        return list;
    }
}
