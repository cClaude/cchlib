package com.googlecode.cchlib.util.emptydirectories.util;

import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;

/**
 * Allow to classify with a folder is empty.
 *
 * Type of {@link EmptyFolder}
 */
public enum EmptyFolderType {
    /**
     * This folder is empty.
     */
    IS_EMPTY,

    /**
     * This folder contain only empty folder or an empty
     * directory structure
     */
    CONTAINT_ONLY_EMPTY_FOLDERS
    }
