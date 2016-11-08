package com.googlecode.cchlib.io;

import java.io.FilenameFilter;
import java.io.Serializable;

/**
 * A {@link Serializable} {@link FilenameFilter}
 */
public interface SerializableFilenameFilter
    extends FilenameFilter, Serializable
{
    // empty, just to define both interface on same interface
}
