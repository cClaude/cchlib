package com.googlecode.cchlib.tools.sortfiles.filetypes;

public interface XFileFilterFactory
{
    public XFileFilter createFileFilter( String extension,  String...others );
}
