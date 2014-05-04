package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;

//NOT public
abstract class AbstractKeyFiles
    implements KeyFiles
{
    private static final long serialVersionUID = 1L;
    private final String key;

    public AbstractKeyFiles(
        final String key
        )
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return getFirstFile().getName();
    }

    @Override
    public long length()
    {
        return getFirstFile().length();
    }

    @Override
    public String getKey()
    {
        return key;
    }

    @Override
    public Iterator<KeyFileState> iterator()
    {
        return getFiles().iterator();
    }

    @Override
    public abstract File getFirstFile();

    @Override
    public abstract Collection<KeyFileState> getFiles();

}
