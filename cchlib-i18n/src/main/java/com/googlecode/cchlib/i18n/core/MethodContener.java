package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface MethodContener extends Serializable
{
    public String getBaseName();
    public String getInvokeMethodName();
    public Method getInvokeMethod() throws SecurityException, NoSuchMethodException;
    //public String getSetterName();
    //public Method getSetter() throws SecurityException, NoSuchMethodException;
}
