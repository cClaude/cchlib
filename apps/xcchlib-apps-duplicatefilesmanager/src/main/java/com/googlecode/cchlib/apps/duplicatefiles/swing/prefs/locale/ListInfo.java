package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.locale;

@SuppressWarnings("squid:S1609") // For sure this not a Functional interface
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
