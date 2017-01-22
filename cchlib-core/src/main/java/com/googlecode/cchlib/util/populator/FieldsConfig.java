package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import javax.annotation.Nonnull;

/**
 * Implementation should specify how {@link Field} are discovered.
 *
 * @see FieldsConfigValue for some basics implementations
 *
 * @since 4.2
 */
@FunctionalInterface
public interface FieldsConfig
{
    /**
     * Returns an array of fields to need be analyzed for giving {@code type},
     * if no field should return an empty array.
     *
     * @param type
     *            Reference type
     * @return an array of fields
     */
    @Nonnull
    Field[] getFields( @Nonnull Class<?> type );
}
