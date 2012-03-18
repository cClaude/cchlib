package com.googlecode.cchlib.apps.duplicatefiles.prefs;

public interface ListInfo<T>
{
    /**
     * @return content object
     */
    public T getContent();

    /**
     * @return display String for UI
     */
    @Override
    public String toString();

}
