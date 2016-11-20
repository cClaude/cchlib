package com.googlecode.cchlib.util.emptydirectories;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

public interface Folder extends Serializable, Comparable<Folder>
{
    Path getPath();
    File getFile();
}
