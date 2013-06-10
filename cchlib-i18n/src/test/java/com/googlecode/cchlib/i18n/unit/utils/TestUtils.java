package com.googlecode.cchlib.i18n.unit.utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper.Result;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.unit.REF;

public class TestUtils
{
    public static final XI18nResourceBundleName validMessageBundle 
        = new XI18nResourceBundleName( REF.class.getPackage(), REF.class.getSimpleName() );
    public static final I18nResourceBundleName notValidMessageBundleButExist 
        = new  DefaultI18nResourceBundleName( REF.class.getPackage(), REF.class.getSimpleName() + "-empty" );

    private TestUtils()
    {
    }

    public static RunI18nTestInterface.PrepTest createPrepTest()
    {
        // Default language !
        final Locale locale = Locale.ENGLISH;

        // Define output
        final PrintStream usageStatPrintStream    = System.err;
        final PrintStream notUsePrintStream       = System.out;

        EnumSet<AutoI18nConfig> config   = getPrepConfig();
        final I18nPrep          autoI18n = I18nPrepHelper.createAutoI18nCore( 
                config, 
                notValidMessageBundleButExist,
                locale 
                );
        final AutoI18nExceptionCollector exceptionCollector = new AutoI18nExceptionCollector();
        
        autoI18n.addAutoI18nExceptionHandler( exceptionCollector );

        return new RunI18nTestInterface.PrepTest() {
            private List<I18nAutoCoreUpdatable> list = new ArrayList<I18nAutoCoreUpdatable>();
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
            public void add( I18nAutoCoreUpdatable frame )
            {
                list.add( frame );
            }
            @Override
            public I18nAutoCoreUpdatable[] getI18nConteners()
            {
                return list.toArray( new I18nAutoCoreUpdatable[ list.size() ] );
            }
            @Override
            public AutoI18nExceptionCollector getAutoI18nExceptionHandlerCollector()
            {
                return exceptionCollector;
            }
        };
    }
    
    public static void preparePrepTest( 
            RunI18nTestInterface.PrepTest prepTest,
            I18nAutoCoreUpdatable         frame 
            )
    {
        prepTest.add( frame );
    }
    
    public static I18nPrepHelper.Result runPrepTest( 
            RunI18nTestInterface.PrepTest prepTest
            )
    {
        Result r = I18nPrepHelper.defaultPrep( prepTest.getAutoI18n(), prepTest.getI18nConteners());

        I18nPrepHelper.fmtUsageStatCollector( prepTest.getUsageStatPrintStream(), r.getUsageStatCollector() );
        I18nPrepHelper.fmtNotUseCollector( prepTest.getNotUsePrintStream(), r.getNotUseCollector() );
        
        return r;
    }
    
    public static void runPerformeI18nTest( I18nAutoCoreUpdatable frame )
    {
        Locale locale = Locale.ENGLISH;

        EnumSet<AutoI18nConfig> config   = getDebugConfig();
        AutoI18nCore            autoI18n = AutoI18nCoreFactory.createAutoI18nCore(
                config, 
                validMessageBundle,
                locale
                );
        autoI18n.setLocale( locale );
        
        frame.performeI18n( autoI18n );
    }

    private static EnumSet<AutoI18nConfig> getDebugConfig()
    {
        return EnumSet.of( AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS );
    }
    
    private static EnumSet<AutoI18nConfig> getPrepConfig()
    {
        //return EnumSet.of( AutoI18nConfig.PRINT_STACKTRACE_IN_LOGS );
        return EnumSet.noneOf( AutoI18nConfig.class );
    }
}
