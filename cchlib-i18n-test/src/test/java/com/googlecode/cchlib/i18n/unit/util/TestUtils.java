package com.googlecode.cchlib.i18n.unit.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepFactory;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory;
import com.googlecode.cchlib.i18n.resources.XI18nResourceBundleName;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;
import com.googlecode.cchlib.i18n.unit.REF;

public class TestUtils
{
    public static final XI18nResourceBundleName VALID_MESSAGE_BUNDLE
        = new XI18nResourceBundleName( REF.class.getPackage(), REF.class.getSimpleName() );

    public static final I18nResourceBundleName NOT_VALID_MESSAGE_BUNDLE_BUT_EXIST
        = I18nResourceBundleNameFactory.newI18nResourceBundleName(
                REF.class.getPackage(),
                REF.class.getSimpleName() + "-empty"
                );

    private TestUtils()
    {
        // All static
    }

    public static PrepTestPart newPrepTestPart()
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Define output
        final PrintStream usageStatPrintStream    = System.err;
        final PrintStream notUsePrintStream       = System.out;

        final Set<AutoI18nConfig> config   = getPrepConfig();
        final I18nPrep            autoI18n = I18nPrepFactory.newI18nPrep(
                config,
                NOT_VALID_MESSAGE_BUNDLE_BUT_EXIST,
                locale
                );
        final AutoI18nExceptionCollector exceptionCollector = new AutoI18nExceptionCollector();

        autoI18n.addAutoI18nExceptionHandler( exceptionCollector );

        return new PrepTestPart() {
            private final List<I18nAutoCoreUpdatable> list = new ArrayList<>();
            @Override
            public I18nPrep getAutoI18n()
            {
                return autoI18n;
            }
            @Override
            public PrintStream getUsageStatPrintStream()
            {
                return usageStatPrintStream;
            }
            @Override
            public PrintStream getNotUsePrintStream()
            {
                return notUsePrintStream;
            }
            @Override
            public void add( final I18nAutoCoreUpdatable frame )
            {
                this.list.add( frame );
            }
            @Override
            public I18nAutoCoreUpdatable[] getI18nConteners()
            {
                return this.list.toArray( new I18nAutoCoreUpdatable[ this.list.size() ] );
            }
            @Override
            public AutoI18nExceptionCollector getAutoI18nExceptionHandlerCollector()
            {
                return exceptionCollector;
            }
        };
    }

    public static void preparePrepTest(
        final PrepTestPart prepTest,
        final I18nAutoCoreUpdatable frame
        )
    {
        prepTest.add( frame );
    }

    public static I18nPrepResult runPrepTest(
        final PrepTestPart prepTest
        ) throws I18nPrepException
    {
        final I18nPrepResult result = I18nPrepHelper.defaultPrep( prepTest.getAutoI18n(), prepTest.getI18nConteners());

        I18nPrepHelper.fmtUsageStatCollector( prepTest.getUsageStatPrintStream(), result );
        I18nPrepHelper.fmtNotUseCollector( prepTest.getNotUsePrintStream(), result );

        return result;
    }

    public static void performeI18n( final I18nAutoCoreUpdatable frame )
    {
        final Set<AutoI18nConfig> config   = getDebugConfig();
        final AutoI18nCore        autoI18n = AutoI18nCoreFactory.newAutoI18nCore(
                config,
                VALID_MESSAGE_BUNDLE,
                Locale.ENGLISH
                );

        frame.performeI18n( autoI18n );
    }

    public static Set<AutoI18nConfig> getDebugConfig()
    {
        return Collections.unmodifiableSet(
                EnumSet.of( AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS )
                );
    }

    private static Set<AutoI18nConfig> getPrepConfig()
    {
        return Collections.unmodifiableSet(
                EnumSet.noneOf( AutoI18nConfig.class )
                );
    }
}
