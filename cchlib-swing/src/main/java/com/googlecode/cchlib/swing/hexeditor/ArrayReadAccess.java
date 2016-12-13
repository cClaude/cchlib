package com.googlecode.cchlib.swing.hexeditor;

import java.io.Closeable;

/**
 * NEEDDOC
 */
public interface ArrayReadAccess extends Closeable
{
	/**
	 * NEEDDOC
	 * @return NEEDDOC
	 */
    int getLength();

    /**
     * NEEDDOC
     * @param index NEEDDOC
     * @return NEEDDOC
     */
    byte getByte(int index);

    /**
     * NEEDDOC
     * @param index NEEDDOC
     * @return NEEDDOC
     */
    char getChar(int index);
}
