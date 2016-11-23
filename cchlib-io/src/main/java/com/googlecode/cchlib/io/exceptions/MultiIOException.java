package com.googlecode.cchlib.io.exceptions;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NEEDDOC
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
        // Empty
    }

    public void addIOException( final IOException ioe )
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
        return this.exceptions.isEmpty();
    }

    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
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
     * NEEDDOC
     */
    @Override
    public String getMessage()
    {
        return getFirstIOException().getMessage();
    }

    /**
     * NEEDDOC
     */
    @Override
    public String getLocalizedMessage()
    {
        return getFirstIOException().getLocalizedMessage();
    }

    /**
     * NEEDDOC
     */
    @Override
    public Throwable getCause()
    {
        return getFirstIOException().getCause();
    }

    /**
     * NEEDDOC
     */
    @Override
    public void printStackTrace()
    {
        super.printStackTrace();

        for( final IOException ioe : this.exceptions ) {
            ioe.printStackTrace();
            }
    }

    /**
     * NEEDDOC
     */
    @Override
    public void printStackTrace( final PrintStream s )
    {
        super.printStackTrace( s );

        for( final IOException ioe : this.exceptions ) {
            ioe.printStackTrace( s );
            }
    }

    /**
     * NEEDDOC
     */
    @Override
    public void printStackTrace( final PrintWriter s )
    {
        super.printStackTrace( s );

        for( final IOException ioe : this.exceptions ) {
            ioe.printStackTrace( s );
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "MultiIOException [exceptions=" );
        builder.append( this.exceptions );
        builder.append( ']' );
        return builder.toString();
    }
}
