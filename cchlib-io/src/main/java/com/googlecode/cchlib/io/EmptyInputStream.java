package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * An always empty {@link InputStream}
 */
public class EmptyInputStream extends InputStream
{
    private boolean open;

    /**
     * Create an {@link EmptyInputStream}
     */
    public EmptyInputStream()
    {
        open = true;
    }

    @Override
    public int read() throws IOException
    {
        return -1;
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
}
