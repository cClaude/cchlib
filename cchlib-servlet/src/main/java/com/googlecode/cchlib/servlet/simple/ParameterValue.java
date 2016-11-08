package com.googlecode.cchlib.servlet.simple;

public interface ParameterValue
{
    String[] toArray();
    String toString(String s);
    boolean booleanValue(); // $codepro.audit.disable booleanMethodNamingConvention
    boolean booleanValue(boolean flag); // $codepro.audit.disable booleanMethodNamingConvention
    int intValue();
    int intValue(int i);
    long longValue();
    long longValue(long l);
    float floatValue();
    float floatValue(float f);
    double doubleValue();
    double doubleValue(double d);
}
