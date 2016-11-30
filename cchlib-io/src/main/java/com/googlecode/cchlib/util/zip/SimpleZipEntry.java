package com.googlecode.cchlib.util.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.zip.ZipEntry;

/**
 * This interface represent a ZIP file entry.
 */
public interface SimpleZipEntry extends Serializable
{
    /**
     * Returns ZipEntry for current file
     * @return ZipEntry for current file
     * @throws IOException if any I/O error occur.
     */
    ZipEntry getZipEntry() throws IOException;

    /**
     * Returns InputStream for current file
     * @return InputStream for current file
     * @throws IOException if any I/O error occur.
     */
    InputStream createInputStream() throws IOException;
}
