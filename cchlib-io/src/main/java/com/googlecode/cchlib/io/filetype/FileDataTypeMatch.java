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
    FileDataTypeDescription getFileDataTypeDescription();

    /**
     * Check if value is acceptable for this match at this offset
     * @param offset    offset to check
     * @param value     value to check
     * @return true if value is acceptable for this match at this offset
     * @throws IllegalStateException if giving offset is outside valid range.
     */
    boolean isValid( int offset, int value ) throws StreamOverrunException;

    /**
     * Returns return true if 'offset' is the last offset (or after last offset)
     * @param offset
     * @return return true if 'offset' is the last offset
     */
    boolean isLastOffset( int offset );
}
