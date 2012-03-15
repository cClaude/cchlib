package com.googlecode.cchlib.util.tree;

/**
 * Root exception for {@link NamedTree}
 */
public class NamedTreeException extends Exception
{
    private static final long serialVersionUID = 1L;

    public NamedTreeException()
    {
    }

    public NamedTreeException( String message )
    {
        super( message );
    }

    public NamedTreeException( Throwable cause )
    {
        super( cause );
    }

    public NamedTreeException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
