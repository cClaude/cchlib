package com.googlecode.cchlib.apps.duplicatefiles.alpha.filefilter;

import java.io.FileFilter;

/**
 *
 */
public interface XFileFilter
{
    public String getName();
    public String getDescription();
    public XFileFilterType getType();
    public XFileFilterMode getMode();
    public FileFilter getFileFilter();
}
