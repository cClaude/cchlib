package com.googlecode.cchlib.i18n.sample.full;

import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;

public abstract class AbstractMessageBundleBaseName
{
    protected AbstractMessageBundleBaseName()
    {
    }

    public void start( 
        I18nAutoCoreUpdatable[]       i18nConteners
        ) throws IOException
    {
        // Default language !
        Locale locale = Locale.ENGLISH;

        // Define output
        PrintStream usageStatPrintStream    = System.err;
        PrintStream notUsePrintStream       = System.out;

        // Other frames,panel,... if any

        {
        EnumSet<AutoI18nConfig> config                   = null;
        I18nResourceBundleName  i18nResourceBundleName   = createI18nResourceBundleName();
        I18nPrep                autoI18n                 = I18nPrepHelper.createAutoI18nCore( config, i18nResourceBundleName, locale );

        //I18nPrepHelper.defaultPrep(
        //    autoI18n,
        //    usageStatPrintStream,
        //    notUsePrintStream,
        //    i18nConteners
        //    );
        Result r = I18nPrepHelper.defaultPrep( autoI18n, i18nConteners);

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, r.getUsageStatCollector() );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, r.getNotUseCollector() );
        }
    }

    public abstract I18nResourceBundleName createI18nResourceBundleName();
    //public abstract String getMessageBundleBaseName();
}
