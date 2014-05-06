package com.googlecode.cchlib.util.properties;

//NOT public
interface PropertiesPopulatorAnnotation<E,METHOD_OR_FIELD>
{
    /**
     * Return annotation value
     */
    boolean isDefaultValueNull();
    /**
     * Return annotation value
     */
    String defaultValue();
    /**
     * Return object value has a String
     */
    String toString( Object o ) throws PropertiesPopulatorException;

    PropertiesPopulatorSetter<E,METHOD_OR_FIELD> getPropertiesPopulatorSetter();
}
