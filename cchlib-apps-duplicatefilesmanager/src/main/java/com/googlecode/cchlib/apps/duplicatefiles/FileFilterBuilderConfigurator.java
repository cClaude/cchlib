package com.googlecode.cchlib.apps.duplicatefiles;

/**
 *
 */
public interface FileFilterBuilderConfigurator {
    FileFilterBuilder createExcludeDirectoriesFileFilterBuilder();
    FileFilterBuilder createExcludeFilesFileFilterBuilder();
    FileFilterBuilder createIncludeDirectoriesFileFilterBuilder();
    FileFilterBuilder createIncludeFilesFileFilterBuilder();
}
