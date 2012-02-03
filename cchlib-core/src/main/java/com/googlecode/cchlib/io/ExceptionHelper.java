package com.googlecode.cchlib.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 */
public class ExceptionHelper
{
    private ExceptionHelper()
    {
    }

    /**
     * 
     * @param aThrowable
     * @return
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
     * 
     * @param aThrowable
     * @return
     */
    public static String[] getStackTraceHasLines(
        final Throwable aThrowable
        ) 
    {
        final String stackTrace = getStackTrace( aThrowable );

        return stackTrace.split( "\\n" );
      }
}
