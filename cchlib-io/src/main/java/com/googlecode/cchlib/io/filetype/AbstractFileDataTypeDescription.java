package com.googlecode.cchlib.io.filetype;

/**
 *
 */
abstract class AbstractFileDataTypeDescription
    implements FileDataTypeDescription 
{
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        return builder.toString();
    }
}