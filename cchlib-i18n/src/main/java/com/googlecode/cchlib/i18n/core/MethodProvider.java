package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.MethodProviderNoSuchMethodException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;

/**
 * A {@link MethodProvider} is a object able to construct a {@link MethodContener}
 */
@FunctionalInterface
@SuppressWarnings("ucd") // API
public interface MethodProvider extends Serializable
{
    /**
     * NEEDDOC
     * @param type NEEDDOC
     * @param field NEEDDOC
     * @param methodName the name of the method
     * @return a {@link MethodContener} for the specified method.
     * @throws MethodProviderNoSuchMethodException if any reflexion error occur
     * @throws MethodProviderSecurityException if any reflexion error occur
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    MethodContener getMethods( Class<?> type, Field field, String methodName )
        throws MethodProviderNoSuchMethodException, MethodProviderSecurityException;
}
