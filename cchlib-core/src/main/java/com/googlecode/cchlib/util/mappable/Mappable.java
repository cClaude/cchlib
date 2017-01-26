package com.googlecode.cchlib.util.mappable;

import java.util.Map;

/**
 * Give a view of all Attributes(name,value) for this object, could be use to
 * generate XML files, HTML, Swing or JavaFx view, debugging, ...
 *
 * @see MappableBuilder
 * @see MappableBuilderFactory
 * @see MappableHelper
 */
@FunctionalInterface
public interface Mappable
{
    /**
     * Returns a Map of attributes for this object.
     * <p>
     * Key should be attribute name, an Value should be a String representation
     * for this attribute value.
     * </p>
     * @return a Map of attributes for this object.
     */
    Map<String,String> toMap();
}
