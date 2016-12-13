package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.io.File;

/**
 *
 */
interface JFileChooserSelector
{
    /**
     * Set default selection.
     *
     * @param file Default selected file
     */
    void goTo( File file );
}
