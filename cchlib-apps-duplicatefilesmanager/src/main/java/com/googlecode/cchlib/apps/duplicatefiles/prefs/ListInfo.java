package com.googlecode.cchlib.apps.duplicatefiles.prefs;

public interface ListInfo<T>
{
    /**
     * @return content object
     */
    T getContent();

    /**
     * @return display String for UI
     */
    @Override
    String toString();

}
