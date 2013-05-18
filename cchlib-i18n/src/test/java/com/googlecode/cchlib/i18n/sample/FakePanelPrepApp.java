package com.googlecode.cchlib.i18n.sample;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;

public class FakePanelPrepApp
{
    private FakePanelPrepApp()
    {
    }

    public static void main( String...args ) throws IOException
    {
        // Default language !
        Locale locale = Locale.ENGLISH;

        // Build frame
        FakePanel frameOrPanel = new FakePanel();

        // Define output
        PrintStream usageStatPrintStream    = System.err;
        PrintStream notUsePrintStream       = System.out;

        // Other frames,panel,... if any
        I18nAutoUpdatable[] otherFrames = {};

        I18nPrepHelper.defaultPrep(
            locale,
            usageStatPrintStream,
            notUsePrintStream,
            frameOrPanel,
            otherFrames
            );
    }
}
