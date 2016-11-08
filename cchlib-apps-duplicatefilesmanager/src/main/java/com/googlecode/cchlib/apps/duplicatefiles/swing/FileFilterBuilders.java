package com.googlecode.cchlib.apps.duplicatefiles.swing;

/**
 *
 */
public interface FileFilterBuilders
{
    boolean isIgnoreHiddenDirs();
    FileFilterBuilder getIncludeDirs();
    FileFilterBuilder getExcludeDirs();
    boolean isIgnoreEmptyFiles();
    boolean isIgnoreHiddenFiles();
    boolean isIgnoreReadOnlyFiles();
    FileFilterBuilder getIncludeFiles();
    FileFilterBuilder getExcludeFiles();
}
