package com.googlecode.cchlib.io;

import java.io.FileFilter;
import java.io.Serializable;

/**
 * Define a {@link FileFilter} witch is {@link Serializable}
 */
public interface SerializableFileFilter
    extends Serializable, FileFilter
{
    // empty, just to define both interface on same interface
}
