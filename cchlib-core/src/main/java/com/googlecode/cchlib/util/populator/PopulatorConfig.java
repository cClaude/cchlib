package com.googlecode.cchlib.util.populator;

import com.googlecode.cchlib.lang.annotation.AnnotationLookup;
import com.googlecode.cchlib.lang.annotation.AnnotationLookupOrder;

/**
 * {@link PropertiesPopulator} configuration
 *
 * @since 4.2
 */
public class PopulatorConfig
{
    private final FieldsConfig     fieldsConfig;
    private final MethodsConfig    methodsConfig;
    private final AnnotationLookup methodsAnnotationLookup;

    public PopulatorConfig(
        final FieldsConfig     fieldsConfig,
        final MethodsConfig    methodsConfig,
        final AnnotationLookup methodsAnnotationLookup
        )
    {
        this.fieldsConfig            = fieldsConfig;
        this.methodsConfig           = methodsConfig;
        this.methodsAnnotationLookup = methodsAnnotationLookup;
    }

    /**
     * Return default configuration using {@link FieldsConfigValue#ALL_DECLARED_FIELDS},
     * {@link MethodsConfigValue#METHODS} and {@link AnnotationLookupOrder#SUPERCLASSES_FIRST}
     *
     * @return default configuration
     */
    public static PopulatorConfig getDefaultConfig()
    {
        return new PopulatorConfig(
                FieldsConfigValue.ALL_DECLARED_FIELDS,
                MethodsConfigValue.METHODS,
                AnnotationLookupOrder.SUPERCLASSES_FIRST
                );
    }

    public FieldsConfig getFieldsConfig()
    {
        return this.fieldsConfig;
    }

    public MethodsConfig getMethodsConfig()
    {
        return this.methodsConfig;
    }

    public AnnotationLookup getAnnotationLookup()
    {
        return this.methodsAnnotationLookup;
    }
}
