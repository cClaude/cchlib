package com.googlecode.cchlib.swing.hexeditor;


/**
 * NEEDDOC
 */
public interface ArrayReadWriteAccess extends ArrayReadAccess
{
    /**
     * NEEDDOC
     * @param index Index of the byte
     * @param b Byte to set
     */
    void setByte(int index, byte b);
}
