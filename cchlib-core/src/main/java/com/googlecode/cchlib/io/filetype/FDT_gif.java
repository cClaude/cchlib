package com.googlecode.cchlib.io.filetype;

import com.googlecode.cchlib.io.filetype.FileDataTypes.Type;

/**
 * 
 * @since 4.1.7
 */
final class FDT_gif extends AbstractFileDataTypeDescription
{
    @Override
    public String getExtension()
    {
        return ".gif";
    }

    @Override
    public String getShortExtension()
    {
        return getExtension();
    }

    @Override
    public Type getType()
    {
        return Type.GIF;
    }
}