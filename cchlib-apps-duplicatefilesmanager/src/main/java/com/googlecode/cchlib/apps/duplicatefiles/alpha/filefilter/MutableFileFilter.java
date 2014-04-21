package com.googlecode.cchlib.apps.duplicatefiles.alpha.filefilter;

import com.googlecode.cchlib.io.FileFilterHelper;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

/**
 *
 *
 */
public class MutableFileFilter
    implements FileFilter, Serializable
{
    private static final long serialVersionUID = 1L;
    private FileFilter fileFilter;

    /**
     *
     */
    protected MutableFileFilter()
    {
        this.fileFilter = FileFilterHelper.trueFileFilter();
    }

    /**
     *
     */
    public MutableFileFilter( final FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
    }

    /**
    *
    */
    public void setFileFilter( final FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
    }

    @Override
    public boolean accept( final File pathFile )
    {
        return this.fileFilter.accept( pathFile );
    }
}
