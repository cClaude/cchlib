package com.googlecode.cchlib.apps.emptydirectories;

import java.io.IOException;

public interface EmptyFolder extends Folder
{
    //public EmptyFolderType getType();
    public boolean isEmpty();
    boolean containtOnlyEmptyFolders();
    
    public void check() throws /*NotDirectoryException, DirectoryNotEmptyException,*/ IOException;
}
