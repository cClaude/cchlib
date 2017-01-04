package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * A {@link MethodContener} class is {@link Serializable} class able
 * to return a {@link Method}.
 */
public interface MethodContener extends Serializable
{
    /**
     * Returns the method name
     * @return the method name
     */
    String getMethodName();

    /**
     * Returns the method
     *
     * @return the method
     * @throws SecurityException
     *             if any reflexion error occur
     * @throws NoSuchMethodException
     *             if any reflexion error occur
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    Method getMethod() throws SecurityException, NoSuchMethodException;
}
