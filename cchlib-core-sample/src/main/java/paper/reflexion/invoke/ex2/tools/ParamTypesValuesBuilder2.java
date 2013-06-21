package paper.reflexion.invoke.ex2.tools;

import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

public
class ParamTypesValuesBuilder2 extends ParamTypesValuesBuilder
{
    private boolean  oneParam_shouldNotFail;
    private boolean  twoParam_shouldNotFail;
    private boolean  threeParam_shouldNotFail;

    public ParamTypesValuesBuilder2(
            final Class<?> firstParamType, 
            final Object   firstParam, 
            final boolean  oneParam_shouldNotFail, 
            final boolean  twoParam_shouldNotFail, 
            final boolean  threeParam_shouldNotFail
            )
    {
        super( firstParamType, firstParam );
        
        this.oneParam_shouldNotFail   = oneParam_shouldNotFail;
        this.twoParam_shouldNotFail   = twoParam_shouldNotFail;
        this.threeParam_shouldNotFail = threeParam_shouldNotFail;
    }

    final public Iterable<ParamTypesValues2> toParamTypesValues2()
    {
        return new Iterable<ParamTypesValues2>() {
            @Override
            public Iterator<ParamTypesValues2> iterator()
            {
                return iteratorParam2();
            }
        };
    }
    
    private Iterator<ParamTypesValues2> iteratorParam2()
    {
        ParamTypesValues2[] arrays = {
            new ParamTypesValues2() {
                @Override
                public Class<?>[] getTypes()
                {
                    return  new Class<?>[] { getFirstParamType() };
                }
                @Override
                public Object[] getValues()
                {
                    return new Object[] { getFirstParam() };
                }
                @Override
                public boolean isShouldNotFail()
                {
                    return oneParam_shouldNotFail;
                }},
            new ParamTypesValues2() {
                @Override
                public Class<?>[] getTypes()
                {
                    return new Class<?>[] { getFirstParamType(), Object.class };
                }
                @Override
                public Object[] getValues()
                {
                    return new Object[] { getFirstParam(), "fake-param-1-string" };
                }
                @Override
                public boolean isShouldNotFail()
                {
                    return twoParam_shouldNotFail;
                }},
            new ParamTypesValues2() {
                @Override
                public Class<?>[] getTypes()
                {
                    return new Class<?>[] { getFirstParamType(), Object.class, Object.class };
                }
                @Override
                public Object[] getValues()
                {
                    return new Object[] { getFirstParam(), "fake-param-1", Integer.valueOf( -1 ) };
                }
                @Override
                public boolean isShouldNotFail()
                {
                    return threeParam_shouldNotFail;
                }}
            };
        return new ArrayIterator<ParamTypesValues2>( arrays );
    }
}