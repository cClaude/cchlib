package com.googlecode.cchlib.io.filetype;

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
    public FileDataTypes.Type getType()
    {
        return FileDataTypes.Type.GIF;
    }
}