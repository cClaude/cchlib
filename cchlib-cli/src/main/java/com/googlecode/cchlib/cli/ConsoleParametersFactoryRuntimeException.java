package com.googlecode.cchlib.cli;

class ConsoleParametersFactoryRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ConsoleParametersFactoryRuntimeException(
            final Class<?>                     type,
            final ReflectiveOperationException cause
            )
    {
        super( "type: " + type, cause );
    }
}
