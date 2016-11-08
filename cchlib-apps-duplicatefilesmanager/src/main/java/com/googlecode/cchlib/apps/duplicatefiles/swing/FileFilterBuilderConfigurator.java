package com.googlecode.cchlib.apps.duplicatefiles.swing;

/**
 * Factory interface for {@link FileFilterBuilder}
 */
public interface FileFilterBuilderConfigurator {
    /**
     * Return FileFilterBuilder
     * @return FileFilterBuilder
     */
    FileFilterBuilder createExcludeDirectoriesFileFilterBuilder();
    /**
     * Return FileFilterBuilder
     * @return FileFilterBuilder
     */
    FileFilterBuilder createExcludeFilesFileFilterBuilder();
    /**
     * Return FileFilterBuilder
     * @return FileFilterBuilder
     */
    FileFilterBuilder createIncludeDirectoriesFileFilterBuilder();
    /**
     * Return FileFilterBuilder
     * @return FileFilterBuilder
     */
    FileFilterBuilder createIncludeFilesFileFilterBuilder();
}
