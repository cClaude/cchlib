package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Field;

public interface MethodProvider extends Serializable
{
    public MethodContener getMethods( Class<?> clazz, Field f, String methodName )
        throws MethodProviderNoSuchMethodException, MethodProviderSecurityException;
}
