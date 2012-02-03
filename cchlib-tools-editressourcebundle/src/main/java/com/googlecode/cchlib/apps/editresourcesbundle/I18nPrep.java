package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import com.googlecode.cchlib.i18n.config.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepAutoUpdatable;
import com.googlecode.cchlib.i18n.config.I18nPrepHelper;

import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;

/**
 * Create resources bundles files
 */
public class I18nPrep
{
    public static void main( String[] args ) throws IOException
    {
        I18nPrepAutoUpdatable   mainFrame               = new CompareResourcesBundleFrame();
        JFileChooserInitializer jFileChooserInitializer	= null;
        FilesConfig 			filesConfig 			= new FilesConfig();
        I18nAutoUpdatable   	loadFrame               = new LoadDialog(null/*mainFrame*/, jFileChooserInitializer, filesConfig);
        Locale                  defaultLocale           = Locale.ENGLISH;
        PrintStream             usageStatPrintStream    = System.err;
        PrintStream             notUsePrintStream       = System.out;

        I18nPrepHelper.defaultPrep( 
        	defaultLocale,
        	usageStatPrintStream, 
        	notUsePrintStream,
        	mainFrame,
        	loadFrame
        	);

        System.err.flush();
        System.out.flush();

        System.exit( 0 );
    }


    /*    public static void defaultPrep()
    {
        // Default language !
        Locale.setDefault( Locale.ENGLISH );

        // Build frame
        CompareResourcesBundleFrame mainFrame = new CompareResourcesBundleFrame();

        // Prepare custom I18n to get all statics fields
        I18nPropertyResourceBundleAutoUpdate autoI18n = I18nDefaultBundle.getI18nPropertyResourceBundleAutoUpdate();
        File outputFile = new File(
                new File(".").getAbsoluteFile(),
                I18nDefaultBundle.getMessagesBundle()
                );
        autoI18n.setOutputFile( outputFile );

        mainFrame.performeI18n( autoI18n );

        ResourceBundle      rb          = I18nDefaultBundle.getAutoI18nSimpleStatsResourceBundle().getResourceBundle();
        Enumeration<String> enu         = rb.getKeys();
        Map<String,String>  knowKeyMap  = new HashMap<String,String>();

        while( enu.hasMoreElements() ) {
            final String k = enu.nextElement();
            knowKeyMap.put( k, rb.getString( k ) );
            }

        autoI18n.close();

        Map<String,Integer> statsMap        = new HashMap<String,Integer>( I18nDefaultBundle.getAutoI18nSimpleStatsResourceBundle().getUsageMap() );
        List<String>        sortedKeyList   = new ArrayList<String>( statsMap.keySet() );

        Collections.sort( sortedKeyList );

        for( String key : sortedKeyList ) {
            System.out.println(
                    "K=" + key
                    + " Usage= " + statsMap.get( key )
                    );
            knowKeyMap.remove( key );
            }

        System.out.println();
        System.out.flush();

        sortedKeyList.clear();
        sortedKeyList.addAll( knowKeyMap.keySet() );
        Collections.sort( sortedKeyList );

        System.err.println();
        System.err.flush();
        System.err.println( "### not use list ###" );

        for( String key : sortedKeyList ) {
            System.err.println( "### not use ["
                    + key + "=" + knowKeyMap.get( key )
                    + "]"
                    );
            }

        System.err.println( "### Done" );
        System.err.println();
        System.err.flush();
        System.out.println( "Done." );
        System.out.flush();

    }*/
}
