package com.googlecode.cchlib.apps.duplicatefiles.console;

import java.io.FileFilter;
import java.io.Serializable;
import java.util.Collection;

class CustomFileFilterConfig implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Collection<String> excludeNames;

    public Collection<String> getExcludeNames()
    {
        return this.excludeNames;
    }

    public void setExcludeNames( final Collection<String> excludeNames )
    {
        this.excludeNames = excludeNames;
    }

    /**
     * Create a FileFilter based on current configuration
     *
     * @return a FileFilter
     */
    public FileFilter newIntance()
    {
        return new CustomFileFilter( getExcludeNames() );
    }
}
