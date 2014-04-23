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
import java.util.Set;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nTypeLookup;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.resources.DefaultI18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

/**
 * Create resources bundles files
 */
public final class I18nPrepHelper
{
<<<<<<< HEAD
    /**
     * @deprecated Use {@link DefaultI18nResourceBundleName#DEFAULT_MESSAGE_BUNDLE_BASENAME} instead
     */
    @Deprecated
    public static final String DEFAULT_MESSAGE_BUNDLE_BASENAME = DefaultI18nResourceBundleName.DEFAULT_MESSAGE_BUNDLE_BASENAME;
=======
//    /**
//     * @deprecated Use {@link DefaultI18nResourceBundleName#DEFAULT_MESSAGE_BUNDLE_BASENAME} instead
//     */
//    @Deprecated
//    public static final String DEFAULT_MESSAGE_BUNDLE_BASENAME = DefaultI18nResourceBundleName.DEFAULT_MESSAGE_BUNDLE_BASENAME;
>>>>>>> cchlib-pre4-1-8

    private static final class DefaultResult implements Result {
        private final PrepCollector<String>  notUseCollector;
        private final File                   outputFile;
        private final PrepCollector<Integer> usageStatCollector;

        private DefaultResult(
                final PrepCollector<String>  notUseCollector,
                final File                   outputFile,
                final PrepCollector<Integer> usageStatCollector
                )
        {
            this.notUseCollector = notUseCollector;
            this.outputFile = outputFile;
            this.usageStatCollector = usageStatCollector;
        }

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
    }

    /**
     * Results for {@link I18nPrepHelper}
     */
    @NeedDoc
    public interface Result
    {
<<<<<<< HEAD
        /** TODOC */
        PrepCollector<Integer> getUsageStatCollector();
        /** TODOC */
        PrepCollector<String> getNotUseCollector();
        /** TODOC */
=======
        @NeedDoc
        PrepCollector<Integer> getUsageStatCollector();
        @NeedDoc
        PrepCollector<String> getNotUseCollector();
        @NeedDoc
>>>>>>> cchlib-pre4-1-8
        File getOutputFile();
    }

    private I18nPrepHelper()
    {
        // All static
    }

    /**
     * Invoke {@link #createI18nPrep(EnumSet, I18nResourceBundleName, Locale)},
     * just here to have same method name that I18n default process
     */
    public static I18nPrep createAutoI18nCore(
        final Set<AutoI18nConfig>     config,
        final I18nResourceBundleName  messageBundleName,
        final Locale                  locale
        )
    {
        return createI18nPrep( config, messageBundleName, locale );
    }

    @NeedDoc
    public static I18nPrep createI18nPrep(
        final Set<AutoI18nConfig>     config,
        final I18nResourceBundleName  messageBundleName,
        final Locale                  locale
        )
    {
        final AutoI18nTypeLookup defaultAutoI18nTypes = null; // Use default implementation

        return createI18nPrep( config, defaultAutoI18nTypes, messageBundleName, locale );
    }

    @NeedDoc
    public static I18nPrep createI18nPrep(
        final Set<AutoI18nConfig>     config,
        final AutoI18nTypeLookup      defaultAutoI18nTypes,
        final I18nResourceBundleName  messageBundleName,
        final Locale                  locale
        )
    {
        return new I18nPrep( config, defaultAutoI18nTypes, locale, messageBundleName );
    }

    @NeedDoc
    public static void fmtUsageStatCollector(
        final PrintStream usageStatPrintStream,
        final Result      result
        )
    {
        final PrepCollector<Integer> usageStatCollector = result.getUsageStatCollector();

        for( final Map.Entry<String,Integer> entry : usageStatCollector ) {
            usageStatPrintStream.println("K="+entry.getKey()+" Usage= "+entry.getValue());
            }

        usageStatPrintStream.println();
    }

    @NeedDoc
    public static void fmtNotUseCollector(
        final PrintStream notUsePrintStream,
        final Result      result
        )
    {
<<<<<<< HEAD
        PrepCollector<String> notUseCollector = result.getNotUseCollector();

        notUsePrintStream.println( "### not use list ###" );

         for( Map.Entry<String,String> entry : notUseCollector ) {
=======
        final PrepCollector<String> notUseCollector = result.getNotUseCollector();

        notUsePrintStream.println( "### not use list ###" );

         for( final Map.Entry<String,String> entry : notUseCollector ) {
>>>>>>> cchlib-pre4-1-8
            notUsePrintStream.println("### not use ["+entry.getKey()+'='+entry.getValue()+']');
            }

        notUsePrintStream.println( "### Done" );
        notUsePrintStream.println();
    }

    @NeedDoc
    public static Result defaultPrep(
        final I18nPrep                 i18nPrep,
        final I18nAutoCoreUpdatable... i18nConteners
        )
    {
        final PrepCollector<Integer> usageStatCollector = new PrepCollector<>();
        final PrepCollector<String>  notUseCollector    = new PrepCollector<>();
        final File                   outputFile         = new File(
            new File(".").getAbsoluteFile(),
            i18nPrep.getI18nResourceBundleName().getName()
            );

        final AutoI18nCore autoI18n = i18nPrep.getAutoI18nCore();

        i18nPrep.openOutputFile( outputFile );

        try {
            for( final I18nAutoCoreUpdatable i18nContener : i18nConteners ) {
                i18nContener.performeI18n( autoI18n );
                }

            final ResourceBundle      rb          = i18nPrep.getResourceBundle();
            final Enumeration<String> enu         = rb.getKeys();
            final Map<String,String>  knowKeyMap  = new HashMap<>();

            while( enu.hasMoreElements() ) {
                final String k = enu.nextElement();
<<<<<<< HEAD
                
                assert k != null : "Key is null";
                
=======

                assert k != null : "Key is null";

>>>>>>> cchlib-pre4-1-8
                knowKeyMap.put( k, rb.getString( k ) );
                }

            final Map<String,Integer> statsMap = new HashMap<>( i18nPrep.getUsageMap() );

            for( final String key : statsMap.keySet() ) {
                usageStatCollector.add( key, statsMap.get( key ) );
                knowKeyMap.remove( key );
                }

            for( final String key : statsMap.keySet() ) {
                notUseCollector.add( key, knowKeyMap.get( key ) );
                }
            }
        finally {
            try {
                i18nPrep.closeOutputFile();
                }
<<<<<<< HEAD
            catch( IOException e ) {
                throw new RuntimeException( e ); // Not handled
=======
            catch( final IOException e ) {
                throw new RuntimeException( e ); // FIXME Not handled
>>>>>>> cchlib-pre4-1-8
                }
            }

        return new DefaultResult( notUseCollector, outputFile, usageStatCollector );
    }
}
