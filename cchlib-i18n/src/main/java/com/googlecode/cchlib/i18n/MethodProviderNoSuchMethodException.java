package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.logging.LogFieldFormat;

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

    /* package */ static String createMessage(
            final Field     field,
            final String    fullMethodName,
            final Class<?>  clazz
            )
        {
            return "Can not find method \"" + fullMethodName + "\" for field : "
                + LogFieldFormat.toString( field ) + " on class: " + clazz;
        }
}
