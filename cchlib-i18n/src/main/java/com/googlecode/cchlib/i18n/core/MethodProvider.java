package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.MethodProviderNoSuchMethodException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;

public interface MethodProvider extends Serializable
{
    MethodContener getMethods( Class<?> clazz, Field f, String methodName )
        throws MethodProviderNoSuchMethodException, MethodProviderSecurityException;
}
