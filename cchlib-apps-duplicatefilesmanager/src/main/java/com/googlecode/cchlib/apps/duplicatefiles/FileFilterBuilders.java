package com.googlecode.cchlib.apps.duplicatefiles;

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
