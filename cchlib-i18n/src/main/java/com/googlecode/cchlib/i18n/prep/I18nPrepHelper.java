package com.googlecode.cchlib.i18n.prep;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;

/**
 * Create resources bundles files
 */
public final class I18nPrepHelper
{
    private I18nPrepHelper()
    {
        // All static
    }

    /**
     * NEEDDOC
     *
     * @param usageStatPrintStream
     *            NEEDDOC
     * @param result
     *            NEEDDOC
     */
    public static void fmtUsageStatCollector(
        final PrintStream    usageStatPrintStream,
        final I18nPrepResult result
        )
    {
        final PrepCollector<Integer> usageStatCollector = result.getUsageStatCollector();

        usageStatPrintStream.println();
        usageStatPrintStream.println( "# In use list" );

        for( final Map.Entry<String, Integer> entry : usageStatCollector ) {
            usageStatPrintStream.println( "K=" + entry.getKey() + " Usage= " + entry.getValue() );
        }

        usageStatPrintStream.println( "# In use list done" );
        usageStatPrintStream.println();
    }

    /**
     * NEEDDOC
     *
     * @param notUsePrintStream
     *            NEEDDOC
     * @param result
     *            NEEDDOC
     */
    public static void fmtNotUseCollector(
        final PrintStream     notUsePrintStream,
        final I18nPrepResult result
        )
    {
        final PrepCollector<String> notUseCollector = result.getNotUseCollector();

        notUsePrintStream.println();
        notUsePrintStream.println( "# not use list" );

        for( final Map.Entry<String, String> entry : notUseCollector ) {
            notUsePrintStream.println( "### not use [" + entry.getKey() + '=' + entry.getValue() + ']' );
        }

        notUsePrintStream.println( "# not use list done" );
        notUsePrintStream.println();
    }

    /**
     * NEEDDOC
     *
     * @param i18nPrep
     *            NEEDDOC
     * @param i18nConteners
     *            NEEDDOC
     * @return NEEDDOC
     * @throws I18nPrepException
     *             NEEDDOC
     */
    public static I18nPrepResult defaultPrep(
        final I18nPrep                i18nPrep,
        final I18nAutoCoreUpdatable...i18nConteners
        ) throws I18nPrepException
    {
        final PrepCollector<Integer> usageStatCollector = new PrepCollector<>();
        final PrepCollector<String>  notUseCollector    = new PrepCollector<>();
        final File                   outputFile         = newOutputFile( i18nPrep );
        final AutoI18nCore           autoI18n           = i18nPrep.getAutoI18nCore();

        i18nPrep.openOutputFile( outputFile );

        defaultPrep( i18nPrep, autoI18n, usageStatCollector, notUseCollector, i18nConteners );

        closeAll( i18nPrep, outputFile );

        final I18nPrepStatResult i18nPrepStatResult = i18nPrep.getI18nPrepStatResult();

        return new I18nPrepResultImpl( notUseCollector, outputFile, usageStatCollector, i18nPrepStatResult );
    }

    private static File newOutputFile( final I18nPrep i18nPrep )
    {
        return new File(
            new File( "." ).getAbsoluteFile(),
            i18nPrep.getI18nResourceBundleName().getName()
            );
    }

    private static void defaultPrep(
        final I18nPrep                i18nPrep,
        final AutoI18nCore            autoI18n,
        final PrepCollector<Integer>  usageStatCollector,
        final PrepCollector<String>   notUseCollector,
        final I18nAutoCoreUpdatable...i18nConteners
        )
    {
        for( final I18nAutoCoreUpdatable i18nContener : i18nConteners ) {
            i18nContener.performeI18n( autoI18n );
        }

        final ResourceBundle      rb         = i18nPrep.getResourceBundle();
        final Enumeration<String> keys       = rb.getKeys();
        final Map<String, String> knowKeyMap = new HashMap<>();

        while( keys.hasMoreElements() ) {
            final String key = keys.nextElement();

            assert key != null : "Key is null";

            knowKeyMap.put( key, rb.getString( key ) );
        }

        final Map<String, Integer> statsMap = new HashMap<>( i18nPrep.getUsageMap() );

        for( final Entry<String, Integer> entry : statsMap.entrySet() ) {
            final String key = entry.getKey();

            usageStatCollector.add( key, entry.getValue() );
            knowKeyMap.remove( key );
        }

        for( final String key : statsMap.keySet() ) {
            notUseCollector.add( key, knowKeyMap.get( key ) );
        }
    }

    private static void closeAll(
        final I18nPrep i18nPrep,
        final File     outputFile
        ) throws I18nPrepException
    {
        try {
            i18nPrep.closeOutputFile();
        }
        catch( final IOException cause ) {
            throw new I18nPrepException( outputFile, cause );
        }
    }
}
