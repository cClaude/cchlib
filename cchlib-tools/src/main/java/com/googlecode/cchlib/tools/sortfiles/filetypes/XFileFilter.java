package com.googlecode.cchlib.tools.sortfiles.filetypes;

import com.googlecode.cchlib.io.SerializableFileFilter;

public interface XFileFilter extends SerializableFileFilter
{
    public String toDisplay();
    public FileType getFileType();
    public void setFileType( FileType fileType );
}
