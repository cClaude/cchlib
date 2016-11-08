package com.googlecode.cchlib.swing.hexeditor;

import java.io.Closeable;

/**
 * TODOC
 */
public interface ArrayReadAccess extends Closeable
{
	/**
	 * TODOC
	 * @return TODOC
	 */
    int getLength();
    
    /**
     * TODOC
     * @param index
     * @return TODOC
     */
    byte getByte(int index);
    
    /**
     * TODOC
     * @param index
     * @return TODOC
     */
    char getChar(int index);
}
