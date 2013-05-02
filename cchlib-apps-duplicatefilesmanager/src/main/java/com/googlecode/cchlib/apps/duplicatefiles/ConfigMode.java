package com.googlecode.cchlib.apps.duplicatefiles;

/**
 * Usage level for GUI
 */
public enum ConfigMode
{
        BEGINNER,
        ADVANCED,
        EXPERT;

        @Override
        public String toString()
        {
            return this.name();
        }
}
