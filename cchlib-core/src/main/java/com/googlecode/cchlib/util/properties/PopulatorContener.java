package com.googlecode.cchlib.util.properties;

/**
 * TODOC
 *
 */
public interface PopulatorContener<E>
{
    public void init( String stringInitialization );
    public void set( E content );
    public E get();
}
