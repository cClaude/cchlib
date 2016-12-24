package com.googlecode.cchlib.util.function;


@FunctionalInterface
public interface Action<T>
{
    void doAction( T actionType );
}
