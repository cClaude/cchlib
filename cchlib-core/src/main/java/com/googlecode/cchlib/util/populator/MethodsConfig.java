package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Method;
import javax.annotation.Nonnull;

/**
 * Implementation should specify how {@link Method} are discovered.
 * <p>
 * Implementation should be invariant.
 *
 * @see MethodsConfigValue for some basics implementations
 *
 * @since 4.2
 */
@FunctionalInterface
public interface MethodsConfig
{
    /**
     * Returns an array of methods to need be analyzed for giving {@code type},
     * if no method should return an empty array.
     *
     * @param type
     *            Reference type
     * @return an array of methods
     */
    @Nonnull Method[] getMethods( @Nonnull Class<?> type );
}
