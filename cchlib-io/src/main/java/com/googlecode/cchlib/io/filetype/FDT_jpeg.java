package com.googlecode.cchlib.io.filetype;

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
    public FileDataTypes.Type getType()
    {
        return FileDataTypes.Type.JPEG;
    }
}