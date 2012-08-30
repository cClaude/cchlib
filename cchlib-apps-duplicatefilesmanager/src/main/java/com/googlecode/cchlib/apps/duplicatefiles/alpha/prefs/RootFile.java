package com.googlecode.cchlib.apps.duplicatefiles.alpha.prefs;

import java.io.File;
import java.io.Serializable;

/**
 * Describe root file object properties
 */
public interface RootFile extends Serializable
{
    /**
     * Returns {@link File} for this RootFile
     * @return {@link File} for this RootFile
     */
    public File getFile();
    /**
     * Returns {@link RootFileAction} for this RootFile
     * @return {@link RootFileAction} for this RootFile
     */
    public RootFileAction getRootFileAction();
}
