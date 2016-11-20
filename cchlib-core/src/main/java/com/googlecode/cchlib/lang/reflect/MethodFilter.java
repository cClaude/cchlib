package com.googlecode.cchlib.lang.reflect;

import java.io.Serializable;
import java.lang.reflect.Method;
import com.googlecode.cchlib.util.iterator.Selectable;

@FunctionalInterface
public interface MethodFilter extends Selectable<Method>, Serializable
{
    @Override
    boolean isSelected( Method method );
}
