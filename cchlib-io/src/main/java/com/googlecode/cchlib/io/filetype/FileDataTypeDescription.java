package com.googlecode.cchlib.io.filetype;

/**
 * Description return by {@link FileDataTypes}
 * @since 4.1.5
 */
public interface FileDataTypeDescription
{
    /**
     * Returns extension for this type of file
     * @return extension for this type of file
     */
    String getExtension();

    /**
     * Returns short extension (dot and 3 characters) for this type of file
     * @return short extension for this type of file
     */
    String getShortExtension();

    /**
     * Returns {@link FileDataTypes.Type} for this type of file
     * @return {@link FileDataTypes.Type} for this type of file
     */
    FileDataTypes.Type getType();
}
