package com.googlecode.cchlib.io;

import java.io.File;

/**
 * Provide a very basic implementation
 * of {@link FileRoller#createNewRollFile()}
 *
 * @since 4.1.6
 */
public class DefaultFileRoller implements FileRoller
{
    private final File baseFile;
    private int currentFileIndex;

    /**
     * 
     * @param baseFile
     */
    public DefaultFileRoller(
        final File    baseFile
        )
    {
        this.baseFile = baseFile;
        this.currentFileIndex = 0;
    }

    @Override
    public File createNewRollFile()
    {
        final File currentFile = new File(
            this.baseFile.getAbsolutePath()
                + "." + this.currentFileIndex
                );

        this.currentFileIndex++;

        return currentFile;
    }
}
