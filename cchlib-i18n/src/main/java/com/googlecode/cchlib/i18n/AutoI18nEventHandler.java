package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Handler to manage action on field to ignore or on field who
 * need internationalization
 */
public interface AutoI18nEventHandler extends Serializable
{
    /**
     * Call when a Field is ignored
     *
     * @param field      ignored Field
     * @param key        String key for this field, if available, null otherwise
     * @param eventCause {@link EventCause} qualification
     * @param causeDecription Optional cause description (could be null)
     */
    void ignoredField( Field field, String key, EventCause eventCause, String causeDecription );

    /**
     * Call when a Field is localized
     *
     * @param field Localized Field
     * @param key   Key for this Field
     */
    void localizedField( Field field, String key );
}
