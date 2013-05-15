package com.googlecode.cchlib.i18n.logging;

import java.io.PrintStream;

/**
 * {@link com.googlecode.cchlib.i18n.AutoI18nEventHandler}
 * using PrintStream to trace Localization events.
 * <p>
 * This object is not Serializable, you must use
 * this debugging your application only!
 * </p>
 */
public class AutoI18nPrintStreamLoggingEventHandler
    extends AbstractAutoI18nLoggingEventHandler
{
    private static final long serialVersionUID = 1L;
    private transient PrintStream out; // TODO: NOT SERIALIZABLE !!

    /**
     * Create object using {@link System#err}
     */
    public AutoI18nPrintStreamLoggingEventHandler()
    {
        this(System.err);
    }

    /**
     * Create object using giving PrintStream
     *
     * @param output output to use for logging
     */
    public AutoI18nPrintStreamLoggingEventHandler(
            PrintStream output
            )
    {
        this.out = output;
    }

    @Override
    protected void logIgnoredField( String msg )
    {
        out.println( msg );
    }

    @Override
    protected void logLocalizedField( String msg )
    {
        out.println( msg );
    }
}
