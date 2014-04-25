package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

public class MethodProviderSecurityException extends SecurityException
{
    private static final long serialVersionUID = 1L;
    
    public MethodProviderSecurityException(
            final Field     field,
            final String    fullMethodName,
            final Class<?>  clazz,
            final Throwable cause 
            )
        {
            super( MethodProviderNoSuchMethodException.createMessage( field, fullMethodName, clazz ), cause );
        }
    
}
