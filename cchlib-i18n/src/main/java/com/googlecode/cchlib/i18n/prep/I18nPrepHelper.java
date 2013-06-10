package com.googlecode.cchlib.i18n.prep;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

/**
 * Create resources bundles files
 */
public class I18nPrepHelper
{
    public static final String DEFAULT_MESSAGE_BUNDLE_BASENAME = "MessagesBundle";

    /**
     * TODOC
     *
     */
    @NeedDoc
    public interface Result
    {
        public PrepCollector<Integer> getUsageStatCollector();
        public PrepCollector<String> getNotUseCollector();
        public File getOutputFile();
    }

    private I18nPrepHelper()
    {
        // All static
    }

    /**
     * Invoke {@link #createI18nPrep(EnumSet, Package, String, Locale)},
     * just here to have same method name that I18n default process
     */
    public static I18nPrep createAutoI18nCore(
        final EnumSet<AutoI18nConfig> config,
        final I18nResourceBundleName  messageBundleName,
        final Locale                  locale
        )
    {
        return createI18nPrep( config, messageBundleName, locale );
    }

    public static I18nPrep createI18nPrep(
        final EnumSet<AutoI18nConfig> config,
        final I18nResourceBundleName  messageBundleName,
        final Locale                  locale
        )
    {
        AutoI18nTypeLookup defaultAutoI18nTypes = null; // Use default implementation

        return createI18nPrep( config, defaultAutoI18nTypes, messageBundleName, locale );
    }

    public static I18nPrep createI18nPrep(
        final EnumSet<AutoI18nConfig> config,
        final AutoI18nTypeLookup      defaultAutoI18nTypes,
        final I18nResourceBundleName  messageBundleName,
        final Locale                  locale
        )
    {
        return new I18nPrep( config, defaultAutoI18nTypes, locale, messageBundleName );
    }

    public static void fmtUsageStatCollector(
        PrintStream            usageStatPrintStream,
        PrepCollector<Integer> usageStatCollector
        )
    {
        usageStatPrintStream.flush();

        for( Map.Entry<String,Integer> entry : usageStatCollector ) {
            usageStatPrintStream.println(
                "K=" + entry.getKey() + " Usage= " + entry.getValue()
                );
            }

        usageStatPrintStream.println();
        usageStatPrintStream.flush();
    }

    public static void fmtNotUseCollector(
        PrintStream           notUsePrintStream,
        PrepCollector<String> notUseCollector
        )
    {
        notUsePrintStream.flush();
        notUsePrintStream.println( "### not use list ###" );

        for( Map.Entry<String,String> entry : notUseCollector ) {
            notUsePrintStream.println(
                "### not use ["  + entry.getKey() + '=' + entry.getValue() + ']'
                );
            }

        notUsePrintStream.println( "### Done" );
        notUsePrintStream.println();
        notUsePrintStream.flush();
    }

    /**
     * @deprecated use {@link #defaultPrep(I18nPrep, I18nAutoCoreUpdatable...)} instead,
     * and build output using {@link #fmtUsageStatCollector(PrintStream, PrepCollector)}
     * and {@link #fmtNotUseCollector(PrintStream, PrepCollector)}
     */
    @Deprecated
    public static Result defaultPrep(
            final I18nPrep                 i18nPrep,
            final PrintStream              usageStatPrintStream,
            final PrintStream              notUsePrintStream,
            final I18nAutoCoreUpdatable... i18nConteners
            )
    {
        Result r = defaultPrep( i18nPrep, i18nConteners);

        fmtUsageStatCollector( usageStatPrintStream, r.getUsageStatCollector() );
        fmtNotUseCollector( notUsePrintStream, r.getNotUseCollector() );

        return r;
    }

    public static Result defaultPrep(
        final I18nPrep                 i18nPrep,
        final I18nAutoCoreUpdatable... i18nConteners
        )
    {
        final PrepCollector<Integer> usageStatCollector = new PrepCollector<Integer>();
        final PrepCollector<String>  notUseCollector    = new PrepCollector<String>();
        final File                   outputFile         = new File(
            new File(".").getAbsoluteFile(),
            i18nPrep.getI18nResourceBundleName().getName()
            );

        AutoI18nCore autoI18n = i18nPrep.getAutoI18nCore();

        i18nPrep.openOutputFile( outputFile );

        try {
            for( I18nAutoCoreUpdatable i18nContener : i18nConteners ) {
                i18nContener.performeI18n( autoI18n );
                }

            ResourceBundle      rb          = i18nPrep.getResourceBundle();
            Enumeration<String> enu         = rb.getKeys();
            Map<String,String>  knowKeyMap  = new HashMap<String,String>();

            while( enu.hasMoreElements() ) {
                final String k = enu.nextElement();
                knowKeyMap.put( k, rb.getString( k ) );
                }

            Map<String,Integer> statsMap = new HashMap<String,Integer>( i18nPrep.getUsageMap() );

            for( String key : statsMap.keySet() ) {
                usageStatCollector.add( key, statsMap.get( key ) );
                knowKeyMap.remove( key );
                }

            for( String key : statsMap.keySet() ) {
                notUseCollector.add( key, knowKeyMap.get( key ) );
                }
            }
        finally {
            try {
                i18nPrep.closeOutputFile();
                }
            catch( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
            }

        return new Result() {
            @Override
            public PrepCollector<Integer> getUsageStatCollector()
            {
                return usageStatCollector;
            }
            @Override
            public PrepCollector<String> getNotUseCollector()
            {
                return notUseCollector;
            }
            @Override
            public File getOutputFile()
            {
                return outputFile;
            }
        };
    }
}
