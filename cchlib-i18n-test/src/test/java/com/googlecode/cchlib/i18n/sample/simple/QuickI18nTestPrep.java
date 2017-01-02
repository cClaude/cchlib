package com.googlecode.cchlib.i18n.sample.simple;

import java.io.PrintStream;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepFactory;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory;

public class QuickI18nTestPrep
{
    static I18nPrepResult runTest() throws I18nPrepException
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Build frame
        final QuickI18nTestFrame frame = new QuickI18nTestFrame();

        // Define output
        final PrintStream usageStatPrintStream  = System.err;
        final PrintStream notUsePrintStream     = System.out;

        final I18nPrep autoI18n = I18nPrepFactory.newI18nPrep(
                    AutoI18nConfig.newAutoI18nConfig(),
                    I18nResourceBundleNameFactory.newI18nResourceBundleName(
                            frame.getClass()
                            ),
                    locale
                    );

        final I18nPrepResult result = I18nPrepHelper.defaultPrep(
                autoI18n,
                new I18nAutoCoreUpdatable[]{ frame } // Other frames,panel,... if any
                );

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );

        return result;
    }

    public static void main( final String...args ) throws Exception
    {
        runTest();
    }
}
