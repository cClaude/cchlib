package com.googlecode.cchlib.i18n.sample.full;

import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Set;

public abstract class AbstractMessageBundleBaseName
{
    protected AbstractMessageBundleBaseName()
    {
    }

    public void start(
        final I18nAutoCoreUpdatable[] i18nConteners
        ) throws IOException // $codepro.audit.disable unnecessaryExceptions
    {
        // Default language !
        Locale locale = Locale.ENGLISH;

        // Define output
        PrintStream usageStatPrintStream    = System.err;
        PrintStream notUsePrintStream       = System.out;

        // Other frames,panel,... if any

        {
        Set<AutoI18nConfig>     config                   = AutoI18nConfig.newAutoI18nConfig();
        I18nResourceBundleName  i18nResourceBundleName   = createI18nResourceBundleName();
        I18nPrep                autoI18n                 = I18nPrepHelper.createAutoI18nCore( config, i18nResourceBundleName, locale );

        I18nPrepHelper.Result result = I18nPrepHelper.defaultPrep( autoI18n, i18nConteners);

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );
        }
    }

    public abstract I18nResourceBundleName createI18nResourceBundleName();
    //public abstract String getMessageBundleBaseName();
}
