package com.googlecode.cchlib.util.properties;

/**
 * TODOC
 *
 */
public interface PopulatorContener<E>
{
	/**
	 * TODOC
	 * @param stringInitialization
	 */
    public void init( String stringInitialization );
    /**
     * TODOC
     * @param content
     */
    public void set( E content );
    /**
     * TODOC
     * @return TODOC
     */
    public E get();
}
