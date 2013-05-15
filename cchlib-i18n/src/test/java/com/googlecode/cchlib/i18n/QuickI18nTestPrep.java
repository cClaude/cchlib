package com.googlecode.cchlib.i18n;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;

public class QuickI18nTestPrep
{
    private QuickI18nTestPrep()
    {
    }

    public static void main( String...args ) throws IOException
    {
        // Default language !
        Locale locale = Locale.ENGLISH;

        // Build frame
        QuickI18nTest frame = new QuickI18nTest();

        // Define output
        PrintStream usageStatPrintStream    = System.err;
        PrintStream notUsePrintStream       = System.out;

        // Other frames,panel,... if any
        I18nAutoUpdatable[] otherFrames = {};

        I18nPrepHelper.defaultPrep(
            locale,
            usageStatPrintStream,
            notUsePrintStream,
            frame,
            otherFrames
            );
    }
}
