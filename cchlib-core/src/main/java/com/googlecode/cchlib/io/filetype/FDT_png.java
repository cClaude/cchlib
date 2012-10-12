package com.googlecode.cchlib.io.filetype;

/**
 * 
 * @since 4.1.7
 */
final class FDT_png extends AbstractFileDataTypeDescription
{
    @Override
    public String getExtension()
    {
        return ".png";
    }

    @Override
    public String getShortExtension()
    {
        return getExtension();
    }

    @Override
    public FileDataTypes.Type getType()
    {
        return FileDataTypes.Type.PNG;
    }
}