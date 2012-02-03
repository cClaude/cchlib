package com.googlecode.cchlib.i18n.config;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import cx.ath.choisnet.i18n.builder.I18nPropertyResourceBundleAutoUpdate;

/**
 * Create resources bundles files
 */
public class I18nPrepHelper
{
    private I18nPrepHelper()
    {
        // All static
    }

    /**
    *
    * @param mainFrame
    * @param defaultLocale
    * @param usageStatPrintStream
    * @throws IOException
    */
    public static void defaultPrep(
        final Locale                defaultLocale,
        final PrintStream           usageStatPrintStream,
        final PrintStream           notUsePrintStream,
        final I18nPrepAutoUpdatable mainFrame,
        final I18nAutoUpdatable...	otherFrames
        ) throws IOException
    {
        // Default language !
        Locale.setDefault( defaultLocale );

        //TODO: remove this        // Build frame
        //TODO: remove this CompareResourcesBundleFrame mainFrame = new CompareResourcesBundleFrame();

        AbstractI18nBundle abstractI18nBundle
            = DefaultI18nBundleFactory.createDefaultI18nBundle( mainFrame );

        // Prepare custom I18n to get all statics fields
        I18nPropertyResourceBundleAutoUpdate autoI18n
            = abstractI18nBundle.getI18nPropertyResourceBundleAutoUpdate();

        File outputFile = new File(
                new File(".").getAbsoluteFile(),
                mainFrame.getMessagesBundle()
                );

        autoI18n.setOutputFile( outputFile );

        mainFrame.performeI18n( autoI18n );

        for( I18nAutoUpdatable f : otherFrames ) {
        	f.performeI18n( autoI18n );
        	}
        
        ResourceBundle      rb          = abstractI18nBundle.getAutoI18nSimpleStatsResourceBundle().getResourceBundle();
        Enumeration<String> enu         = rb.getKeys();
        Map<String,String>  knowKeyMap  = new HashMap<String,String>();

        while( enu.hasMoreElements() ) {
            final String k = enu.nextElement();
            knowKeyMap.put( k, rb.getString( k ) );
            }

        autoI18n.close();

        Map<String,Integer> statsMap        = new HashMap<String,Integer>( abstractI18nBundle.getAutoI18nSimpleStatsResourceBundle().getUsageMap() );
        List<String>        sortedKeyList   = new ArrayList<String>( statsMap.keySet() );

        Collections.sort( sortedKeyList );

        usageStatPrintStream.flush();

        for( String key : sortedKeyList ) {
            usageStatPrintStream.println(
                    "K=" + key
                    + " Usage= " + statsMap.get( key )
                    );
            knowKeyMap.remove( key );
            }

        usageStatPrintStream.println();
        usageStatPrintStream.flush();

        sortedKeyList.clear();
        sortedKeyList.addAll( knowKeyMap.keySet() );
        Collections.sort( sortedKeyList );

        notUsePrintStream.flush();
        notUsePrintStream.println( "### not use list ###" );

        for( String key : sortedKeyList ) {
            notUsePrintStream.println( "### not use ["
                    + key + "=" + knowKeyMap.get( key )
                    + "]"
                    );
            }

        notUsePrintStream.println( "### Done" );
        notUsePrintStream.println();
        notUsePrintStream.flush();
    }
}
