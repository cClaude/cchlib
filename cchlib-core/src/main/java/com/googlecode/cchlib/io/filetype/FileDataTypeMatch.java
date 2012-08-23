package com.googlecode.cchlib.io.filetype;

/**
 *
 * @since 4.1.7
 */
public interface FileDataTypeMatch
{
    /**
     * Returns {@link FileDataTypeDescription} for this match
     * @return {@link FileDataTypeDescription} for this match
     */
    public FileDataTypeDescription getFileDataTypeDescription();
    
    /**
     * Check if value is acceptable for this match at this offset
     * @param offset    offset to check
     * @param value     value to check
     * @return true if value is acceptable for this match at this offset
     * @throws IllegalStateException if giving offset is outside valid range.
     */
    public boolean isValid( int offset, int value ) throws StreamOverrunException;

    /**
     * 
     * @param offset
     * @return
     */
    public boolean isLastOffset( int offset );
}
