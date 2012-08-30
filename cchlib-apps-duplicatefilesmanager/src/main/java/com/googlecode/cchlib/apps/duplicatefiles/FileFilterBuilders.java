package com.googlecode.cchlib.apps.duplicatefiles;

public interface FileFilterBuilders
{
    public boolean isIgnoreHiddenDirs();
    //public boolean isIgnoreReadOnlyDirs();
    public FileFilterBuilder getIncludeDirs();
    public FileFilterBuilder getExcludeDirs();
    public boolean isIgnoreEmptyFiles();
    public boolean isIgnoreHiddenFiles();
    public boolean isIgnoreReadOnlyFiles();
    public FileFilterBuilder getIncludeFiles();
    public FileFilterBuilder getExcludeFiles();
}