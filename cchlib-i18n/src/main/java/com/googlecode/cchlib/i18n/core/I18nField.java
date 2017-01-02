package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;

@NeedDoc
public interface I18nField extends Serializable
{
    /**
     * Describe how an {@link I18nField} should be handle.
     */
    public enum FieldType {
        SIMPLE_KEY,
        LATE_KEY,
        METHODS_RESOLUTION,

        /** Handle tool tip text */
        JCOMPONENT_TOOLTIPTEXT,

        /** Handle tool tip text for JTabbedPane */
        JCOMPONENT_MULTI_TOOLTIPTEXT,
        }

    /**
     * @return the reflexion {@link Field} for this I18nField
     */
    Field getField();

    /**
     * @return key for this I18nField, warning this is the key base name, could
     * be different than the final key in resource bundle, specially for multiple values
     * support.
     */
    String getKeyBase();

    /**
     * @return {@link MethodContener} object for this field, if custom getter/setter define,
     * return null otherwise
     */
    MethodContener getMethodContener();

    @NeedDoc
    FieldType getFieldType();

    /**
     * @return {@link AutoI18nType} for this I18nField, if supported. Returns null otherwise.
     */
    AutoI18nType getAutoI18nTypes();


    // TODO: investigate why i18nResource is never use...
    @NeedDoc
    <T> I18nResolver createI18nResolver( T objectToI18n, I18nResource i18nResource );
}
