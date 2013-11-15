package com.googlecode.cchlib.i18n.core;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface MethodContener extends Serializable
{
    String getBaseName();
    String getInvokeMethodName();
    Method getInvokeMethod() throws SecurityException, NoSuchMethodException;
}
