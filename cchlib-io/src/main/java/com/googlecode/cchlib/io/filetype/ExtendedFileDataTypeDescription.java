package com.googlecode.cchlib.io.filetype;

import java.awt.Dimension;

/**
 *
 */
public interface ExtendedFileDataTypeDescription 
    extends FileDataTypeDescription 
{
    /**
     * Returns {@link Dimension} for current object if exists
     * @return {@link Dimension} for object if exists, null otherwise
     */
    public Dimension getDimension();
    
    /**
     * Returns a String identifying the format of the current object. 
     * @return the format name, as a String.
     */
    public String getFormatName();
}
