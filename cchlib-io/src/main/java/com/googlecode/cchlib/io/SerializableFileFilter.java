package com.googlecode.cchlib.io;

import java.io.FileFilter;
import java.io.Serializable;

/**
 * Define a {@link FileFilter} witch is {@link Serializable}
 * <p>
 * For more detail about serialization: <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/serialTOC.html">Java Object Serialization Specification</a>
 * </p>
 */
public interface SerializableFileFilter
    extends Serializable, FileFilter
{
    // empty, just to define both interface on same interface
}
