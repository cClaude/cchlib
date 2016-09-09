package com.googlecode.cchlib.apps.duplicatefiles.prefs;

/**
 * Runtime exception for preferences
 */
public class PreferencesException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * Create {@link PreferencesException} with specified message
     *
     * @param msg Reason of the exception
     */
    public PreferencesException( final String msg )
    {
        super( msg );
    }
}
