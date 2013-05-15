package com.googlecode.cchlib.i18n.logging;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Convert fields to String for logging, add extra information
 */
public class LogFieldFormat
{
    private LogFieldFormat()
    {
        // static access
    }

    public static String toString( final Field f )
    {
        StringBuilder sb = new StringBuilder();

        for( Annotation annotation : f.getAnnotations() ) {
            sb.append( annotation );
            sb.append( ' ' );
            }
        sb.append( f );

        return sb.toString();
    }
}
