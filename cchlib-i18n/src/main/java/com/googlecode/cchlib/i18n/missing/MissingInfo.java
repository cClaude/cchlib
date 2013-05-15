package com.googlecode.cchlib.i18n.missing;

/**
 * TODOC
 * (internal)
 */
public interface MissingInfo
{
    /**
     * TODOC
     * (internal)
     */
    public enum Type {
        SIMPLE_KEY,
        LATE_KEY,
        METHODS_RESOLUTION,
        JCOMPONENT_TOOLTIPTEXT,
        }

    public Type getType();
    public String getKey();
}
