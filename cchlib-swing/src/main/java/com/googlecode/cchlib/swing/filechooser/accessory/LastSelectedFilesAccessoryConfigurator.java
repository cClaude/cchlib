package com.googlecode.cchlib.swing.filechooser.accessory;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

/**
 * Configuration interface for accessory
 * {@link LastSelectedFilesAccessory}
 *
 * @author Claude CHOISNET
 */
public interface LastSelectedFilesAccessoryConfigurator extends Serializable
{
    /**
     * @return collection of last selected File objects
     */
    public Collection<File> getLastSelectedFiles();

    /**
     * @param file File to add to last selected files Collection
     * @return true if File has been added
     */
    public boolean addLastSelectedFile(File file);

    /**
     * @param file File to remove to last selected files Collection
     * @return true if File has been removed
     */
    public boolean removeLastSelectedFile(File file);

    /**
     * @return If true, when double-click a last selected
     *         files, simulate approve button click.
     *         If false just select double-click last selected
     *         file.
     */
    public boolean getAutoApproveSelection();
}