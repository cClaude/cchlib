package com.googlecode.cchlib.i18n.sample.simple;

import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
//import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
//import com.googlecode.cchlib.i18n.config.I18nPrepHelper;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

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
        I18nAutoCoreUpdatable[] i18nConteners = { frame };

        //        I18nPrepHelper.defaultPrep(
        //            locale,
        //            usageStatPrintStream,
        //            notUsePrintStream,
        //            frame,
        //            otherFrames
        //            );
        EnumSet<AutoI18nConfig> config                   = null;
        String                      messageBundleBaseName    = frame.getClass().getSimpleName();
        Package                     packageMessageBundleBase = frame.getClass().getPackage();
        I18nResourceBundleName      i18nResourceBundleName   = new DefaultI18nResourceBundleName(packageMessageBundleBase, messageBundleBaseName);
        I18nPrep                    autoI18n                 = I18nPrepHelper.createAutoI18nCore( config, i18nResourceBundleName, locale );

        //I18nPrepHelper.defaultPrep(
        //        autoI18n,
        //        usageStatPrintStream,
        //        notUsePrintStream,
        //        i18nConteners
        //        );
        Result r = I18nPrepHelper.defaultPrep( autoI18n, i18nConteners);

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, r.getUsageStatCollector() );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, r.getNotUseCollector() );
    }
}
