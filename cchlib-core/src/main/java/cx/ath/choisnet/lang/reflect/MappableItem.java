package cx.ath.choisnet.lang.reflect;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Attributes for {@link MappableBuilder}
 */
public enum MappableItem
{
    /**
     *  Add all primitives to map
     */
    ALL_PRIMITIVE_TYPE,

    /**
     * Include {@link Mappable} child into map
     */
    DO_RECURSIVE,

    /**
     *  Include arrays into map
     */
    DO_ARRAYS,

    /**
     * Include {@link Iterator} into map
     */
    DO_ITERATOR,

    /**
     * Include {@link Iterable} into map
     */
    DO_ITERABLE,

    /**
     * Include {@link Enumeration} into map
     */
    DO_ENUMERATION,

    /**
     * Include parents classes into introspection, and
     * add result to map.
     */
    DO_PARENT_CLASSES,

    /**
     * Try to include private methods result into map
     */
    TRY_PRIVATE_METHODS,

    /**
     * Try to include protected methods result into map
     */
    TRY_PROTECTED_METHODS
}