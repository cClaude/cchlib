package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;

/**
 * TODOC
 *
 */
@NeedDoc
public interface I18nField extends Serializable
{
    /**
     * TODOC
     * (internal)
     */
    public enum FieldType {
        SIMPLE_KEY,
        LATE_KEY,
        METHODS_RESOLUTION,
        JCOMPONENT_TOOLTIPTEXT,
        }

    /**
     * @return the reflexion {@link Field} for this I18nField
     */
    public Field getField();
    
    /**
     * @return key for this I18nField, warning this is the key base name, could
     * be different than the final key in resource bundle, specially for multiple values
     * support.
     */
    public String getKeyBase();
    
    /**
     * @return {@link MethodContener} object for this field, if custom getter/setter define,
     * return null otherwise
     */
    public MethodContener getMethods();

    public FieldType getFieldType();
    
    /**
     * @return {@link AutoI18nType} for this I18nField, if supported. Returns null otherwise.
     */
    public AutoI18nType getAutoI18nTypes();
    
    @NeedDoc
    public <T> I18nResolver createI18nResolver( T objectToI18n, I18nInterface i18nInterface );
}
