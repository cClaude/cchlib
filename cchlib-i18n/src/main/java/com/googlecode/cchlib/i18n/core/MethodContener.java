package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * A {@link MethodContener} class is {@link Serializable} class able
 * to return a {@link Method}.
 */
public interface MethodContener extends Serializable
{
    String getMethodName();
    Method getMethod() throws SecurityException, NoSuchMethodException;
}
