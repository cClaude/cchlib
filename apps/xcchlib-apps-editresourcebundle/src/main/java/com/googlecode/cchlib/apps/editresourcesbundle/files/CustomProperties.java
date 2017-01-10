package com.googlecode.cchlib.apps.editresourcesbundle.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import javax.swing.event.ChangeListener;

public interface CustomProperties
    extends Serializable
{
    /**
     * @return FileObject use by the CustomProperties
     */
    FileObject getFileObject();

    /**
     * @return true if current object handle lines numbers,
     * false otherwise.
     */
    boolean isLinesNumberHandle();

    /**
     * Get line number for giving property
     * @param key key property
     * @return line number of property,
     *         0 if not found (should not occur)
     */
    int getLineNumber(String key);

    /**
     * Get value for giving property
     * @param key key property
     * @return value of property,
     *         null if not exist (should not occur)
     */
    String getProperty( String key );

    /**
     * @param key property to set
     * @param value for this property
     */
    void setProperty( String key, String value );

    /**
     * @return a set of String keys
     */
    Set<String> stringPropertyNames();

    /**
     * Save from fileObject
     *
     * @return true if file has been saved
     *
     * @throws FileNotFoundException
     *             if file not found
     * @throws IOException
     *             if any
     */
    @SuppressWarnings({"squid:S1160","squid:RedundantThrowsDeclarationCheck"})
    boolean store() throws FileNotFoundException, IOException;

    /**
     * Save content
     *
     * @param file
     *            {@link File} to use
     * @return true if file has been saved
     * @throws FileNotFoundException
     *             if file not found
     * @throws IOException
     *             if any
     */
    @SuppressWarnings({"squid:S1160","squid:RedundantThrowsDeclarationCheck"})
    boolean storeAs( final File file ) throws FileNotFoundException, IOException;

    /**
     * Returns false if content has not been edited.
     * @return false if content has not been edited.
     */
    boolean isEdited();

    /**
     * Adds a {@link ChangeListener}
     * @param listener the {@link ChangeListener} to add
     */
    void addChangeListener( ChangeListener listener );

    /**
     * Removes a {@link ChangeListener}
     * @param listener the {@link ChangeListener} to remove
     */
    void removeChangeListener( ChangeListener listener );
}
