package com.googlecode.cchlib.cli.apachecli;

//not public
class OptionDuplicateFoundException extends OptionHelperRuntimeException
{
    private static final long serialVersionUID = 1L;

    protected OptionDuplicateFoundException( final String message )
    {
        super( message );
    }
}
