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
    public int getLength();
    
    /**
     * TODOC
     * @param index
     * @return TODOC
     */
    public byte getByte(int index);
    
    /**
     * TODOC
     * @param index
     * @return TODOC
     */
    public char getChar(int index);
}
