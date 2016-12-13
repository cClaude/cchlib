package com.googlecode.cchlib.swing.filechooser;

import java.io.File;

/**
 * <p>An implementation of FileFilter that filters using
 * a specified set of extensions. The extension for a
 * file is the portion of the file name after the last ".".
 * Files whose name does not contain a "." have no file
 * name extension. File name extension comparisons are
 * case insensitive.
 *
 * <p>The following example creates a FileNameExtensionFilter
 * that will show jpg files:
 *
 * <pre>
 * FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
 * JFileChooser fileChooser = ...;
 * fileChooser.addChoosableFileFilter(filter);
 * </pre>
 *
 * Same has {@link javax.swing.filechooser.FileNameExtensionFilter}
 * witch is only available since Java 1.6 and witch is not serializable
 */
public class FileNameExtensionFilter extends SwingFileFilter
{
    private static final long serialVersionUID = 1L;

    private final String   description;
    private final String[] extensions;

    /**
     * Creates a FileNameExtensionFilter with the
     * specified description and file name extension.
     *
     * @param description String to describe filter
     * @param extension Extension to filter
     */
    public FileNameExtensionFilter( final String description, final String extension )
    {
        this.description    = description;
        this.extensions     = new String[1];
        this.extensions[0]  = extension.toLowerCase();
    }

    /**
     * Creates a FileNameExtensionFilter with the
     * specified description and file name extensions.
     *
     * @param description String to describe filter
     * @param extensions Arrays of extensions to filter
     * (need Java 1.5)
     */
    public FileNameExtensionFilter( final String description, final String...extensions )
    {
        this.description = description;
        this.extensions  = new String[extensions.length];

        for(int i=0;i<extensions.length;i++) {
            this.extensions[i] = extensions[i].toLowerCase();
        }
    }

    @Override
    public boolean accept( final File file )
    {
        if( file.isDirectory() ) {
            return true;
            }

        final String lowerCaseName = file.getName().toLowerCase();

        for( final String s : this.extensions ) {
            if( lowerCaseName.endsWith( '.' + s ) ) {
                return true;
                }
            }

        return false;
    }

    /**
     * @return The description of this filter.
     */
    @Override
    public String getDescription()
    {
        return this.description;
    }

    /**
     *  @return the set of file name extensions
     *  files are tested against.
     */
    public String[] getExtensions()
    {
        return this.extensions;
    }
}
