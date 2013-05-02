package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

public interface Folder extends Serializable, Comparable<Folder>
{
    public Path getPath();
    public File getFile();
}
