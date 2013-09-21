package com.googlecode.cchlib.io;

import java.io.File;

/**
 * Miscellaneous tools to create commons {@link File} objects
 * @since 4.1.6
 */
public final class FileHelper
{
    /** Maximum loop count when creating temp directories. */
    private static final int TEMP_DIR_ATTEMPTS = 10000;

    // All static
    public FileHelper() {}

    /**
     * Returns File object for tmp directory
     * according to java.io.tmpdir Java property.
     *
     * @return File object for tmp directory
     * @since 4.1.6
     */
    public static File getTmpDirFile()
    {
        return new File( System.getProperty("java.io.tmpdir" ) );
    }

    /**
     * Returns File object for current user home directory
     * according to user.home Java property.
     *
     * @return File object for current user home directory
     * @since 4.1.6
     */
    public static File getUserHomeDirFile()
    {
        return new File( System.getProperty("user.home") );
    }

    /**
     * Returns File object relative to current user home
     * directory according to user.home Java property.
     *
     * @param relativePath Relative path to current user home
     *
     * @return File object relative to current user home directory
     * @since 4.1.6
     */
    public static File getUserHomeDirFile(
        final String relativePath
        )
    {
        return new File(
            getUserHomeDirFile(),
            relativePath
            );
    }

    /**
     * Returns File object for root system directory
     * @return File object for root system directory
     * @since 4.1.6
     */
    public static File getSystemRootFile()
    {
        return new File( "/" );
    }

    /**
     * creates a new directory somewhere beneath the system's temporary directory
     * (as defined by the java.io.tmpdir system property), and returns its name.
     * <br/>
     * This method assumes that the temporary volume is writable, has free inodes and free blocks.
     * 
     * @return the newly-created directory
     * @throws IllegalStateException if the directory could not be created
     * @since 4.1.7
     */
    public static File createTempDir() 
    {
        File   baseDir  = new File( System.getProperty("java.io.tmpdir") );
        String baseName = System.currentTimeMillis() + "-";
        
        for( int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++ ) {
            File tempDir = new File( baseDir, baseName + counter);
            
            if( tempDir.mkdir() ) {
                return tempDir;
                }
            }

        throw new IllegalStateException("Failed to create directory within "
                + TEMP_DIR_ATTEMPTS + " attempts (tried "
                + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
    }
}
