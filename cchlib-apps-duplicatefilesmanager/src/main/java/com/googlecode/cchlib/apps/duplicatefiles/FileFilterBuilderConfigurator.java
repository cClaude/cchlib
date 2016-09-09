package com.googlecode.cchlib.apps.duplicatefiles;

/**
 * Factory interface for {@link FileFilterBuilder}
 */
public interface FileFilterBuilderConfigurator {
    FileFilterBuilder createExcludeDirectoriesFileFilterBuilder();
    FileFilterBuilder createExcludeFilesFileFilterBuilder();
    FileFilterBuilder createIncludeDirectoriesFileFilterBuilder();
    FileFilterBuilder createIncludeFilesFileFilterBuilder();
}
