package com.googlecode.cchlib.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Utilities for managing {@link Throwable} objects
 */
public class ExceptionHelper
{
    private ExceptionHelper()
    {
    }

    /**
     * Returns stack trace into a String
     * @param aThrowable Exception where one wishes retrieve the stack
     * @return String with Stack trace 
     */
    public static String getStackTrace(
        final Throwable aThrowable
        )
    {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
      }

    /**
     * Returns stack trace into a Strings array
     * @param aThrowable Exception where one wishes retrieve the stack
     * @return Strings array with Stack trace
     */
    public static String[] getStackTraceLines(
        final Throwable aThrowable
        )
    {
        final String stackTrace = getStackTrace( aThrowable );

        return stackTrace.split( "\\n" );
      }
}
