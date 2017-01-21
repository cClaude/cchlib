package com.googlecode.cchlib.util.populator;

/**
 * @since 4.1.7
 */
//NOT public
class ConvertCantNotHandleTypeException extends Exception
{
    private static final long serialVersionUID = 1L;

    public ConvertCantNotHandleTypeException( final String  message )
    {
        super( message );
    }
}
