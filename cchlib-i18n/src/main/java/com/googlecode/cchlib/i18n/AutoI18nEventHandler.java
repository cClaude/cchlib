package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Handler to manage action on field to ignore and on field ton localize
 */
public interface AutoI18nEventHandler extends Serializable
{
    /**
     * Call when a Field is ignored
     *
     * @param f          ignored Field
     * @param key        String key for this field, if available, null otherwise
     * @param eventCause {@link EventCause} qualification
     * @param causeDecription Optional cause description (could be null)
     */
    void ignoredField( Field f, String key, EventCause eventCause, String causeDecription );

    /**
     * Call when a Field is localized
     *
     * @param f localized Field
     */
    void localizedField( Field f, String key );
}
