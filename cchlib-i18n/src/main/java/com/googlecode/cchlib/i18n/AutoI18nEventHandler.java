package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * TODOC
 *
 */
public interface AutoI18nEventHandler
    extends Serializable
{
    /** 
     * TODOC
     */
    enum Cause {
        ANNOTATION,
        PRIMITIVE,
        NUMBER,
        ANNOTATION_I18nIgnore_DEFINE,
        NOT_HANDLED,
        NOT_A_I18nString
        };

    /**
     * Call when a Field is ignored
     *
     * @param f ignored Field
     * @param cause Cause
     */
    public void ignoredField(Field f,Cause cause);

    /**
     * Call when a Field is localized
     *
     * @param f localized Field
     */
    public void localizedField( Field f );
}
