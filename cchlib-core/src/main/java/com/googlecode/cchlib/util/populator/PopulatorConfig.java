package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.googlecode.cchlib.lang.annotation.AnnotationLookup;
import com.googlecode.cchlib.lang.annotation.AnnotationLookupOrder;

/**
 * Invariant configuration for {@link MapPopulator} or {@link PropertiesPopulator}
 *
 * @see MapPopulator
 * @see PropertiesPopulator
 *
 * @since 4.2
 */
public class PopulatorConfig
{
    private final FieldsConfig     fieldsConfig;
    private final MethodsConfig    methodsConfig;
    private final AnnotationLookup methodsAnnotationLookup;

    /**
     * Create an invariant {@link PopulatorConfig}
     *
     * @param fieldsConfig
     *            The fields configuration
     * @param methodsConfig
     *            The methods configuration
     * @param methodsAnnotationLookup
     *            The annotations configuration
     */
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
     * Returns default configuration using
     * {@link FieldsConfigValue#ALL_DECLARED_FIELDS},
     * {@link MethodsConfigValue#METHODS}
     * and
     * {@link AnnotationLookupOrder#SUPERCLASSES_FIRST}
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

    /**
     * Returns implementation that specify how {@link Field} are discovered
     * @return implementation that specify how {@link Field} are discovered
     */
    public FieldsConfig getFieldsConfig()
    {
        return this.fieldsConfig;
    }

    /**
     * Returns implementation that specify how {@link Method} are discovered
     * @return implementation that specify how {@link Method} are discovered
     */
    public MethodsConfig getMethodsConfig()
    {
        return this.methodsConfig;
    }

    /**
     * Returns implementation that specify how {@link Annotation} are discovered
     * @return implementation that specify how {@link Annotation} are discovered
     */
    public AnnotationLookup getAnnotationLookup()
    {
        return this.methodsAnnotationLookup;
    }
}
