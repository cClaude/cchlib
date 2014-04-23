// $codepro.audit.disable
package com.googlecode.cchlib.i18n.sample.simple;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Set;

import com.googlecode.cchlib.i18n.AutoI18nConfig;
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

    public static void main( final String...args ) throws IOException
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Build frame
        final QuickI18nTest frame = new QuickI18nTest();

        // Define output
        final PrintStream usageStatPrintStream    = System.err;
        final PrintStream notUsePrintStream       = System.out;

        // Other frames,panel,... if any
        final I18nAutoCoreUpdatable[] i18nConteners = { frame };

        //        I18nPrepHelper.defaultPrep(
        //            locale,
        //            usageStatPrintStream,
        //            notUsePrintStream,
        //            frame,
        //            otherFrames
        //            );
        final Set<AutoI18nConfig>         config                   = AutoI18nConfig.newAutoI18nConfig();
        final String                      messageBundleBaseName    = frame.getClass().getSimpleName();
        final Package                     packageMessageBundleBase = frame.getClass().getPackage();
        final I18nResourceBundleName      i18nResourceBundleName   = new DefaultI18nResourceBundleName(packageMessageBundleBase, messageBundleBaseName);
        final I18nPrep                    autoI18n                 = I18nPrepHelper.createAutoI18nCore( config, i18nResourceBundleName, locale );

        //I18nPrepHelper.defaultPrep(
        //        autoI18n,
        //        usageStatPrintStream,
        //        notUsePrintStream,
        //        i18nConteners
        //        );
        final Result result= I18nPrepHelper.defaultPrep( autoI18n, i18nConteners);

        I18nPrepHelper.fmtUsageStatCollector( usageStatPrintStream, result );
        I18nPrepHelper.fmtNotUseCollector( notUsePrintStream, result );
    }
}
