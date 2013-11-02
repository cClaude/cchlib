package com.googlecode.cchlib.apps.emptydirectories;

import java.io.IOException;

public interface EmptyFolder extends Folder
{
    boolean isEmpty();
    boolean isContaintOnlyEmptyFolders();
    void check() throws IOException;
}
