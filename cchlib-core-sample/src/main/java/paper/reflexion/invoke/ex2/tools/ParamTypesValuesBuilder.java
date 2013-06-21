package paper.reflexion.invoke.ex2.tools;

import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

public class ParamTypesValuesBuilder extends ParamValuesBuilder
{
    private Class<?> firstParamType;

    ParamTypesValuesBuilder(
            final Class<?> firstParamType, 
            final Object   firstParam
             )
    {
        super( firstParam );
 
        this.firstParamType = firstParamType;
    }

    final Class<?> getFirstParamType()
    {
        return firstParamType;
    }

    final public Iterable<ParamTypesValues> toParamTypesValues()
    {
        return new Iterable<ParamTypesValues>() {
            @Override
            public Iterator<ParamTypesValues> iterator()
            {
                return iteratorParam();
            }
        };
    }
    
    private Iterator<ParamTypesValues> iteratorParam()
    {
        ParamTypesValues[] arrays = {
            new ParamTypesValues() {
                @Override
                public Class<?>[] getTypes()
                {
                    return  new Class<?>[] { firstParamType };
                }
                @Override
                public Object[] getValues()
                {
                    return new Object[] { getFirstParam() };
                }},
            new ParamTypesValues() {
                @Override
                public Class<?>[] getTypes()
                {
                    return new Class<?>[] { firstParamType, Object.class };
                }
                @Override
                public Object[] getValues()
                {
                    return new Object[] { getFirstParam(), "fake-param-1-string" };
                }},
            new ParamTypesValues() {
                @Override
                public Class<?>[] getTypes()
                {
                    return new Class<?>[] { firstParamType, Object.class, Object.class };
                }
                @Override
                public Object[] getValues()
                {
                    return new Object[] { getFirstParam(), "fake-param-1", Integer.valueOf( -1 ) };
                }}
            };
        return new ArrayIterator<ParamTypesValues>( arrays );
    }
}