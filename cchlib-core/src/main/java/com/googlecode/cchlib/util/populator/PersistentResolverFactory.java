package com.googlecode.cchlib.util.populator;

/**
 * NEEDDOC
 *
 *  @since 4.2
 */
@FunctionalInterface
public interface PersistentResolverFactory
{
    public PersistentResolver newPersistentResolver();
}
