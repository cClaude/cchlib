package com.googlecode.cchlib.io;

import java.io.File;
import java.nio.file.NotDirectoryException;
import javax.annotation.Nonnull;

/**
 * Miscellaneous tools to create commons {@link File} objects
 *
 * @since 4.1.6
 */
public final class FileHelper
{
    /** Maximum loop count when creating temp directories. */
    private static final int TEMP_DIR_ATTEMPTS = 10_000;

    private FileHelper()
    {
        // All static
    }

    /**
     * Returns File object for tmp directory according to java.io.tmpdir
     * Java property.
     *
     * @return File object for tmp directory
     * @since 4.1.6
     */
    public static File getTmpDirFile()
    {
        return new File( System.getProperty( "java.io.tmpdir" ) );
    }

    /**
     * Returns File object for current user home directory according
     * to user.home Java property.
     *
     * @return File object for current user home directory
     * @since 4.1.6
     * @deprecated use {@link #getUserHomeDirectoryFile()} instead
     */
    @Deprecated
    public static File getUserHomeDirFile()
    {
        return getUserHomeDirectoryFile();
    }

    /**
     * Returns File object for current user home directory
     * according to user.home Java property.
     *
     * @return File object for current user home directory
     * @since 4.1.6
     * @see #getUserHomeDirectoryFile(String)
     */
    public static File getUserHomeDirectoryFile()
    {
        return new File( System.getProperty( "user.home" ) );
    }

    /**
     * Returns File object for current user ".config" directory
     * according to user.home Java property. This directory will
     * be in user home directory.
     *
     * @return File object for current user home directory
     * @since 4.1.6
     * @see #getUserHomeDirectoryFile()
     * @see #getUserHomeDirectoryFile(String)
     * @see #getUserConfigDirectoryFile(String)
     */
    public static File getUserConfigDirectoryFile()
    {
        final File configFile = new File( getUserHomeDirectoryFile(), ".config" );

        if( ! configFile.isDirectory() ) {
            configFile.mkdirs();
        }

        return configFile;
    }

    /**
     * Returns File object relative to current user home directory
     * according to user.home Java property.
     *
     * @param relativePath
     *            Relative path to current user home
     *
     * @return File object relative to current user home directory
     * @since 4.1.6
     * @deprecated use {@link #getUserHomeDirectoryFile(String)}
     *             or {@link #getUserConfigDirectoryFile(String)}
     */
    @Deprecated
    public static File getUserHomeDirFile(
        final String relativePath
        )
    {
        return new File( getUserHomeDirectoryFile(), relativePath );
    }

    /**
     * Returns File object relative to current user home directory
     * according to user.home Java property.
     *
     * @param relativePath
     *            Relative path to current user home
     *
     * @return File object relative to current user home directory
     * @since 4.1.6
     * @see #getUserHomeDirectoryFile()
     * @see #getUserConfigDirectoryFile()
     * @see #getUserConfigDirectoryFile(String)
     */
    public static File getUserHomeDirectoryFile(
        final String relativePath
        )
    {
        return new File( getUserHomeDirectoryFile(), relativePath );
    }

    /**
     * Returns File object relative to current ".config" home directory
     * according to user.home Java property. see
     * {@link #getUserConfigDirectoryFile()}
     *
     * @param relativePath
     *            Relative path to current user home
     *
     * @return File object relative to current user home directory
     * @since 4.1.6
     * @see #getUserConfigDirectoryFile()
     */
    public static File getUserConfigDirectoryFile(
        final String relativePath
        )
    {
        return new File( getUserConfigDirectoryFile(), relativePath );
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
     * creates a new directory somewhere beneath the system's temporary
     * directory (as defined by the java.io.tmpdir
     * system property), and returns its name.
     *
     * <p>This method assumes that the temporary volume is writable,
     * has free inodes and free blocks.
     *
     * @return the newly-created directory
     * @throws IllegalStateException
     *             if the directory could not be created
     * @since 4.1.7
     */
    public static File createTempDir()
    {
        final File   baseDir  = new File( System.getProperty( "java.io.tmpdir" ) );
        final String baseName = System.currentTimeMillis() + "-";

        for( int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++ ) {
            final File tempDir = new File( baseDir, baseName + counter);

            if( tempDir.mkdir() ) {
                return tempDir;
                }
            }

        throw new IllegalStateException("Failed to create directory within "
                + TEMP_DIR_ATTEMPTS + " attempts (tried "
                + baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
    }

    /**
     * Returns an array of abstract pathnames denoting the files in
     * the directory denoted by parameter {@code directoryFile}.
     *
     * @param directoryFile
     *            {@link File} instance
     * @return an array of abstract pathnames denoting the files in
     *         the directory denoted by this abstract pathname.
     * @throws NotDirectoryException
     *             if parameter {@code directoryFile} is not a folder.
     * @since 4.2
     * @see File#listFiles()
     */
    @Nonnull
    public static File[] getFiles( @Nonnull final File directoryFile )
        throws NotDirectoryException
    {
        if( ! directoryFile.isDirectory() ) {
            throw new NotDirectoryException( directoryFile.getPath() );
            }

        final File[] files = directoryFile.listFiles();

        if( files == null ) {
            // Stupid line for tools like SonarQube and BugFix
            return new File[0];
        }

        return files;
    }
}
