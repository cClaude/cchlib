package com.googlecode.cchlib.io.filetype;

import com.googlecode.cchlib.io.filetype.FileDataTypes.Type;

/**
 * 
 * @since 4.1.7
 */
class FDT_jpeg extends AbstractFileDataTypeDescription
{
    @Override
    public String getExtension()
    {
        return ".jpeg";
    }
    @Override
    public String getShortExtension()
    {
        return ".jpg";
    }
    @Override
    public Type getType()
    {
        return Type.JPEG;
    }
}