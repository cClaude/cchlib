package com.googlecode.cchlib.i18n.sample.full;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepFactory;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

abstract class AbstractMessageBundleBaseName
{
    protected AbstractMessageBundleBaseName()
    {
        // Empty
    }

    public I18nPrepResult start(
        final I18nAutoCoreUpdatable[] i18nConteners
        ) throws IOException, I18nPrepException
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Define output
        final PrintStream notUsePrintStream       = System.out;
        final PrintStream usageStatPrintStream    = System.err;

        // Other frames,panel,... if any

        return start( i18nConteners, locale, notUsePrintStream, usageStatPrintStream );
    }

    private I18nPrepResult start(
        final I18nAutoCoreUpdatable[] i18nConteners,
        final Locale                  locale,
        final PrintStream             notUsePrintStream,
        final PrintStream             usageStatPrintStream
        ) throws I18nPrepException
    {
        final Set<AutoI18nConfig>     config                   = AutoI18nConfig.newAutoI18nConfig();
        final I18nResourceBundleName  i18nResourceBundleName   = createI18nResourceBundleName();

        final I18nPrep autoI18n
            = I18nPrepFactory.newI18nPrep( config, i18nResourceBundleName, locale );

        final I18nPrepResult result
            = I18nPrepHelper.defaultPrep( autoI18n, i18nConteners );

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );

        return result;
    }

    public abstract I18nResourceBundleName createI18nResourceBundleName();
}
