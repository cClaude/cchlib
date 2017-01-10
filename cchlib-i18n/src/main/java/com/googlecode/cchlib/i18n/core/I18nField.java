package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;

/**
 * Private interface
 */
public interface I18nField extends Serializable
{
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

    /**
     * Returns field qualifier
     * @return field qualifier
     */
    I18nFieldType getFieldType();

    /**
     * @return {@link AutoI18nType} for this I18nField, if supported. Returns null otherwise.
     */
    AutoI18nType getAutoI18nTypes();


    // TODO: investigate why i18nResource is never use...
    @NeedDoc
    <T> I18nResolver createI18nResolver( T objectToI18n, I18nResource i18nResource );
}
