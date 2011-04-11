package cx.ath.choisnet.servlet;

import cx.ath.choisnet.util.Wrappable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ParameterValueWrapper
{
    private final Wrappable<String,String> wrapper;

    public ParameterValueWrapper(Wrappable<String,String> wrapper)
    {
        this.wrapper = wrapper;
    }

    public List<String> asList(HttpServletRequest request, String paramName)
    {
        return asList(request.getParameterValues(paramName));
    }

    public List<String> asList(ParameterValue paramValue)
    {
        return asList(paramValue.toArray());
    }

    private List<String> asList(final String[] values)
    {
        if( values == null) {
            return null;
        }

        final int    len  = values.length;
        List<String> list = new ArrayList<String>(len);

        for(int i = 0; i < len; i++) {
            list.add( wrapper.wrappe( values[i] ) );
        }

        return list;
    }
}