package com.googlecode.cchlib.tools.sortfiles.filetypes;

import com.googlecode.cchlib.io.SerializableFileFilter;

public interface XFileFilter extends SerializableFileFilter
{
    String toDisplay();
    FileType getFileType();
    void setFileType( FileType fileType );
}
