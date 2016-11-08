package com.googlecode.cchlib.io.exceptions;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODOC
 */
public class MultiIOException extends IOException implements Iterable<IOException>
{
    private static final long serialVersionUID = 1L;
    private final List<IOException> exceptions = new ArrayList<>();

    /**
     * Create a new empty MultiIOException
     */
    public MultiIOException()
    {
    }

    public void addIOException( IOException ioe )
    {
        this.exceptions.add( ioe );
    }

    public int size()
    {
        return this.exceptions.size();
    }

    @Override
    public Iterator<IOException> iterator()
    {
        return this.exceptions.iterator();
    }

    public boolean isEmpty()
    {
        return this.exceptions.size() == 0;
    }
    
    private void checkState() throws IllegalStateException
    {
        if( isEmpty() ) {
            throw new IllegalStateException( "No IOException, check MultiIOException.isEmpty()" );
            }
    }
    
    private IOException getFirstIOException()
    {
        checkState();
        
        return this.exceptions.get( 0 );
    }

    /**
     * TODOC
     */
    @Override
    public String getMessage()
    {
        return getFirstIOException().getMessage();
    }

    /**
     * TODOC
     */
    @Override
    public String getLocalizedMessage()
    {
        return getFirstIOException().getLocalizedMessage();
    }

    /**
     * TODOC
     */
    @Override
    public Throwable getCause()
    {
        return getFirstIOException().getCause();
    }

    /**
     * TODOC
     */
    @Override
    public void printStackTrace()
    {
        super.printStackTrace();
        
        for( IOException ioe : this.exceptions ) {
            ioe.printStackTrace();
            }
    }

    /**
     * TODOC
     */
    @Override
    public void printStackTrace( PrintStream s )
    {
        super.printStackTrace( s );
        
        for( IOException ioe : this.exceptions ) {
            ioe.printStackTrace( s );
            }
    }

    /**
     * TODOC
     */
    @Override
    public void printStackTrace( PrintWriter s )
    {
        super.printStackTrace( s );
        
        for( IOException ioe : this.exceptions ) {
            ioe.printStackTrace( s );
            }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "MultiIOException [exceptions=" );
        builder.append( exceptions );
        builder.append( ']' );
        return builder.toString();
    }
}
