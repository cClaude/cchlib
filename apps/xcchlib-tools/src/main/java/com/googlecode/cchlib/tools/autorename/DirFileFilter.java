/**
 * 
 */
package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.io.FileFilter;

/**
 * @author CC
 *
 */
public class DirFileFilter implements FileFilter {

    /**
     * 
     */
    public DirFileFilter()
    {
        // Empty
    }

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept( File file )
    {
        return file.isDirectory();
    }

}
