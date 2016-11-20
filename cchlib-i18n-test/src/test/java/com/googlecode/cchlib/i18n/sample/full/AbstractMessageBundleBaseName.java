package com.googlecode.cchlib.i18n.sample.full;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

public abstract class AbstractMessageBundleBaseName
{
    protected AbstractMessageBundleBaseName()
    {
    }

    public void start(
        final I18nAutoCoreUpdatable[] i18nConteners
        ) throws IOException, I18nPrepException
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Define output
        final PrintStream usageStatPrintStream    = System.err;
        final PrintStream notUsePrintStream       = System.out;

        // Other frames,panel,... if any

        {
        final Set<AutoI18nConfig>     config                   = AutoI18nConfig.newAutoI18nConfig();
        final I18nResourceBundleName  i18nResourceBundleName   = createI18nResourceBundleName();
        final I18nPrep                autoI18n                 = I18nPrepHelper.createAutoI18nCore( config, i18nResourceBundleName, locale );

        final I18nPrepHelper.Result result = I18nPrepHelper.defaultPrep( autoI18n, i18nConteners);

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );
        }
    }

    public abstract I18nResourceBundleName createI18nResourceBundleName();
    //public abstract String getMessageBundleBaseName();
}
