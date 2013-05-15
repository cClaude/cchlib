package com.googlecode.cchlib.i18n.missing;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 */
public class MissingMethodsResolution implements MissingInfo, Serializable 
{
    private static final long serialVersionUID = 1L;
    private String key;
    private Method setter;
    private Method getter;

    public MissingMethodsResolution( String key, Method setter, Method getter )
    {
        this.key = key;
        this.setter = setter;
        this.getter = getter;
    }

    public MissingMethodsResolution( String key, Method[] methods )
    {
        this( key, methods[0], methods[1] );
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.missing.MissingInfo#getType()
     */
    @Override
    public MissingInfo.Type getType()
    {
        return MissingInfo.Type.METHODS_RESOLUTION;
    }
    
    @Override
    public String getKey()
    {
        return key;
    }

    public Method getSetter()
    {
        return setter;
    }

    public Method getGetter()
    {
        return getter;
    }
}
