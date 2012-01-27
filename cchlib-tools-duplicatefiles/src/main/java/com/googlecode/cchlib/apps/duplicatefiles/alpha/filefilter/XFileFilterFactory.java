/**
 *
 */
package com.googlecode.cchlib.apps.duplicatefiles.alpha.filefilter;

import java.io.FileFilter;

/**
 * @author Claude
 *
 */
public class XFileFilterFactory {

    /**
     *
     */
    public XFileFilterFactory() {
        // TODO Auto-generated constructor stub
    }

    public static XFileFilter createXX(
        final String			name,
        final String			description,
        final XFileFilterType 	type,
        final XFileFilterMode 	mode
        )
    {
        return new AbstractXFileFilter(name, description, type, mode)
        {
            @Override
            public FileFilter getFileFilter()
            {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }
}
