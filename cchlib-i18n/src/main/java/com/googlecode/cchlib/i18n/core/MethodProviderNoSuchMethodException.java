package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.logging.LogFieldFormat;
import java.lang.reflect.Field;

/**
 *
 */
public class MethodProviderNoSuchMethodException extends NoSuchMethodException
{
    private static final long serialVersionUID = 1L;

    public MethodProviderNoSuchMethodException(
        final Field     field,
        final String    fullMethodName,
        final Class<?>  clazz,
        final Throwable cause
        )
    {
        super( createMessage(field, fullMethodName, clazz) );
        super.initCause( cause );
    }

    protected static String createMessage(
            final Field     field,
            final String    fullMethodName,
            final Class<?>  clazz
            )
        {
            return "Can not find method \"" + fullMethodName + "\" for field : "
                + LogFieldFormat.toString( field ) + " on class: " + clazz;
        }
}
