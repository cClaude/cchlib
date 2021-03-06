package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.Reader;

/**
 * An always empty {@link Reader}
 */
public class EmptyReader extends Reader
{
    private boolean open;

    /**
     * Create an {@link EmptyReader}
     */
    public EmptyReader()
    {
        open = true;
    }

    @Override
    public void close() throws IOException
    {
        if(!open) {
            throw new IOException("aleary close");
            }
        else {
            open = false;
        }
    }

    @Override
    public int read(char[] cbuf, int off, int len)
        throws java.io.IOException
    {
        return -1;
    }
}
