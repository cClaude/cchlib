package com.googlecode.cchlib.util.populator;

import java.lang.annotation.Annotation;
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
    private final FieldsConfig              fieldsConfig;
    private final MethodsConfig             methodsConfig;
    private final AnnotationLookup          methodsAnnotationLookup;
    private final PersistentResolverFactory persistentResolverFactory;

    /**
     * Create an invariant {@link PopulatorConfig}
     *
     * @param fieldsConfig
     *            The fields configuration
     * @param methodsConfig
     *            The methods configuration
     * @param methodsAnnotationLookup
     *            The annotations configuration
     * @param persistentResolverFactory
     *            The {@link Persistent} resolver factory (see
     *            {@link PersistentResolver} and {@link Persistent})
     */
    public PopulatorConfig(
        final FieldsConfig              fieldsConfig,
        final MethodsConfig             methodsConfig,
        final AnnotationLookup          methodsAnnotationLookup,
        final PersistentResolverFactory persistentResolverFactory
        )
    {
        this.fieldsConfig              = fieldsConfig;
        this.methodsConfig             = methodsConfig;
        this.methodsAnnotationLookup   = methodsAnnotationLookup;
        this.persistentResolverFactory = persistentResolverFactory;
    }

    /**
     * Returns default configuration using
     * {@link FieldsConfigValue#ALL_DECLARED_FIELDS},
     * {@link MethodsConfigValue#METHODS},
     * {@link AnnotationLookupOrder#SUPERCLASSES_FIRST}
     * and
     * {@link PersistentResolverFactories#newDefaultPersistentResolverFactory()}
     *
     * @return default configuration
     */
    public static PopulatorConfig getDefaultConfig()
    {
        return new PopulatorConfig(
                FieldsConfigValue.ALL_DECLARED_FIELDS,
                MethodsConfigValue.METHODS,
                AnnotationLookupOrder.SUPERCLASSES_FIRST,
                PersistentResolverFactories.newDefaultPersistentResolverFactory()
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

    /**
     * Returns {@link PersistentResolver} factory
     * @return {@link PersistentResolver} factory
     */
    public PersistentResolverFactory getPersistentResolverFactory()
    {
        return this.persistentResolverFactory;
    }
}
