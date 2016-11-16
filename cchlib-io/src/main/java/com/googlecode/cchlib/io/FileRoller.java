package com.googlecode.cchlib.io;

import java.io.File;

/**
 * NEEDDOC
 *
 * @since 4.1.6
 */
public interface FileRoller
{
    /**
     * Returns new {@link File} object for next file
     * @return a new {@link File} object for next file
     */
    File createNewRollFile();
}
