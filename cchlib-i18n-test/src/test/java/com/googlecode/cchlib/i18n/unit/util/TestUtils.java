package com.googlecode.cchlib.i18n.unit.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.AutoI18nFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepFactory;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;
import com.googlecode.cchlib.i18n.unit.PrepTestPart;

/**
 * @deprecated use {@link I18nResourceBuilderFactory} instead
 */
@Deprecated
public class TestUtils
{
    @Deprecated
    public static final I18nResourceBundleName VALID_MESSAGE_BUNDLE
        = newI18nResourceBundleName(
                new I18nITBaseConfig(){}.newI18nResourceBundleName_WithValidBundle()
                );

    @Deprecated
    public static final I18nResourceBundleName NOT_VALID_MESSAGE_BUNDLE_BUT_EXIST
        = newI18nResourceBundleName(
            new I18nITBaseConfig(){}.newI18nResourceBundleName_WithExistingButNotValidBundle()
            );

    private TestUtils()
    {
        // All static
    }

    private static I18nResourceBundleName newI18nResourceBundleName( final I18nResourceBundle i18nResourceBundle)
    {
        return () -> i18nResourceBundle.getResourceBundleFullBaseName();
    }

    @Deprecated
    public static PrepTestPart newPrepTestPart()
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Define output
        final PrintStream usageStatPrintStream = System.err;
        final PrintStream notUsePrintStream    = System.out;

        final Set<AutoI18nConfig> config   = getPrepConfig();
        final I18nPrep            autoI18n = I18nPrepFactory.newI18nPrep(
                config,
                NOT_VALID_MESSAGE_BUNDLE_BUT_EXIST,
                locale
                );
        final AutoI18nExceptionCollector exceptionCollector = new AutoI18nExceptionCollector();

        autoI18n.addAutoI18nExceptionHandler( exceptionCollector );

        return new PrepTestPart() {
            private final List<I18nAutoUpdatable> list = new ArrayList<>();
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
            public void add( final I18nAutoUpdatable frame )
            {
                this.list.add( frame );
            }
            @Override
            public I18nAutoUpdatable[] getI18nConteners()
            {
                return this.list.toArray( new I18nAutoUpdatable[ this.list.size() ] );
            }
            @Override
            public AutoI18nExceptionCollector getAutoI18nExceptionHandlerCollector()
            {
                return exceptionCollector;
            }
        };
    }

    @Deprecated
    public static void preparePrepTest(
        final PrepTestPart prepTest,
        final I18nAutoUpdatable frame
        )
    {
        prepTest.add( frame );
    }

    @Deprecated
   public static I18nPrepResult runPrepTest(
        final PrepTestPart prepTest
        ) throws I18nPrepException
    {
        final I18nPrepResult result = I18nPrepHelper.defaultPrep( prepTest.getAutoI18n(), prepTest.getI18nConteners());

        I18nPrepHelper.fmtUsageStatCollector( prepTest.getUsageStatPrintStream(), result );
        I18nPrepHelper.fmtNotUseCollector( prepTest.getNotUsePrintStream(), result );

        return result;
    }

    @Deprecated
    public static void performeI18n( final I18nAutoUpdatable frame )
    {
        final Set<AutoI18nConfig> config   = getDebugConfig();
        final AutoI18n        autoI18n = AutoI18nFactory.newAutoI18n(
                config,
                VALID_MESSAGE_BUNDLE,
                Locale.ENGLISH
                );

        frame.performeI18n( autoI18n );
    }

    private static Set<AutoI18nConfig> getDebugConfig()
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

    static ResourceBundle createResourceBundle(
        final I18nResourceBundleName resourceBundleName,
        final Locale                 locale
        )
    {
        return new I18nSimpleResourceBundle( resourceBundleName, locale ).getResourceBundle();
    }
}
