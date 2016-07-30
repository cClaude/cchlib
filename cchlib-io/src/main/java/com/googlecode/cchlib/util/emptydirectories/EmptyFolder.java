package com.googlecode.cchlib.util.emptydirectories;

import java.io.IOException;

public interface EmptyFolder extends Folder
{
    boolean isEmpty();
    boolean isContaintOnlyEmptyFolders();
    void check() throws IOException;
}
