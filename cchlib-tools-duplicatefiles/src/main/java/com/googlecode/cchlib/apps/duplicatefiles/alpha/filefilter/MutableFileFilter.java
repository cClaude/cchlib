package com.googlecode.cchlib.apps.duplicatefiles.alpha.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

import cx.ath.choisnet.io.FileFilterHelper;

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
    public MutableFileFilter( FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
    }

    /**
    *
    */
    public void setFileFilter( FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
    }

    @Override
    public boolean accept( File pathFile )
    {
        return this.fileFilter.accept( pathFile );
    }
}
