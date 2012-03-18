package com.googlecode.cchlib.apps.duplicatefiles;

/**
 * Usage level for GUI
 */
public enum ConfigMode
{
        BEGINNER,
        ADVANCED,
        EXPERT;

        public String toString()
        {
            return this.name();
        }
}
