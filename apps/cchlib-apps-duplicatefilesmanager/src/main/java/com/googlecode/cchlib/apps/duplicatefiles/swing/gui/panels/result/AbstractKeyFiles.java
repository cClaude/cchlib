package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.util.Iterator;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFiles;

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
        return getFirstFileInSet().getName();
    }

    @Override
    public long length()
    {
        return getFirstFileInSet().getLength();
    }

    @Override
    public String getKey()
    {
        return this.key;
    }

    @Override
    public Iterator<KeyFileState> iterator()
    {
        return getFiles().iterator();
    }
}
