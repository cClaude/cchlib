package com.googlecode.cchlib.tools.sortfiles.filetypes;

public abstract class AbstractXFileFilter implements XFileFilter
{
    private static final long serialVersionUID = 1L;
    private FileType fileType;

    @Override
    final
    public void setFileType( FileType fileType )
    {
        this.fileType = fileType;
    }

    @Override
    final
    public FileType getFileType()
    {
        return fileType;
    }
}
