package paper.reflexion.invoke.ex2.tools;

import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

public
class ParamValuesBuilder
{
    private Object firstParam;

    public ParamValuesBuilder(
            final Object   firstParam
             )
    {
        this.firstParam = firstParam;
    }

    final Object getFirstParam()
    {
        return firstParam;
    }

    public final Iterable<ParamValues> toParamValues()
    {
        return new Iterable<ParamValues>() {
            @Override
            public Iterator<ParamValues> iterator()
            {
                return iteratorParamValues();
            }
        };
    }
    
    private Iterator<ParamValues> iteratorParamValues()
    {
        ParamValues[] arrays = {
            new ParamValues() {
                @Override
                public Object[] getValues()
                {
                    return new Object[] { firstParam };
                }},
            new ParamValues() {
                @Override
                public Object[] getValues()
                {
                    return new Object[] { firstParam, "fake-param-1-string" };
                }},
            new ParamValues() {
                @Override
                public Object[] getValues()
                {
                    return new Object[] { firstParam, "fake-param-1", Integer.valueOf( -1 ) };
                }}
            };
        return new ArrayIterator<ParamValues>( arrays );
    }
}