package com.googlecode.cchlib.util.populator;


public class PopulatorConfig
{
    private final FieldsConfig  fieldsConfig;
    private final MethodsConfig methodsConfig;

    public PopulatorConfig(
        final FieldsConfig  fieldsConfig,
        final MethodsConfig methodsConfig
        )
    {
        this.fieldsConfig  = fieldsConfig;
        this.methodsConfig = methodsConfig;
    }

    public static PopulatorConfig getDefaultConfig()
    {
        return new PopulatorConfig(
                FieldsConfig.ALL_DECLARED_FIELDS,
                MethodsConfig.METHODS
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
}
