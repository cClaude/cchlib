package com.googlecode.cchlib.cli.apachecli;

//not public
class OptionTryToAddExistingOptException extends OptionHelperRuntimeException
{
    private static final long serialVersionUID = 1L;

    protected OptionTryToAddExistingOptException( final String message )
    {
        super( message );
    }
}
