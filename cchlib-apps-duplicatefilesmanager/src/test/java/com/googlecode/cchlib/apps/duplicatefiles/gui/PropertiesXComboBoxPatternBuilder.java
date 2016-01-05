package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.combobox.XComboBoxPattern;

public class PropertiesXComboBoxPatternBuilder
    extends XComboBoxPatternBuilder
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( PropertiesXComboBoxPatternBuilder.class );

    private final PropertiesFileLock propertiesFile; // $codepro.audit.disable declareAsInterface
    private final String keyPrefix;
    private XComboBoxPattern xComboBoxPattern;

    public PropertiesXComboBoxPatternBuilder(
        final PropertiesFileLock    propertiesFile,
        final String                keyPrefix
        )
    {
        this.keyPrefix      = keyPrefix;
        this.propertiesFile = propertiesFile;

        for( int i = 0;; i++ ) {
            final String key    = keyPrefix + "." + i;
            final String value  = propertiesFile.getProperty( key );

            LOGGER.info("add k=" + key + " v=" + value );

            if( value == null ) {
                break;
                }

            super.add( value );
            }
    }

    /**
     *
     * @param propertiesFile
     * @param keyPrefix
     * @throws IOException
     */
    public PropertiesXComboBoxPatternBuilder(
       final File     propertiesFile,
       final String keyPrefix
       ) throws IOException
    {
        this( new PropertiesFileLock( propertiesFile ), keyPrefix );

        propertiesFile.deleteOnExit();
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void save()
        throws FileNotFoundException, IOException
    {
        LOGGER.info("save count=" + this.xComboBoxPattern.getItemCount() );

        for( int i = 0; i<this.xComboBoxPattern.getItemCount(); i++ ) {
            final String key     = keyPrefix + "." + i;
            final String value    = this.xComboBoxPattern.getItemAt( i );

            LOGGER.info("save k=" + key + " v=" + value );

            this.propertiesFile.setProperty( key, value );
            }

        this.propertiesFile.store( StringHelper.EMPTY );
    }

    /**
     * Can be called only once. User {@link #getXComboBoxPattern()} instead.
     */
    @Override
    public XComboBoxPattern createXComboBoxPattern()
    {
        if( this.xComboBoxPattern != null ) {
            throw new RuntimeException( "already created" );
            }

        this.xComboBoxPattern = super.createXComboBoxPattern();

        return this.xComboBoxPattern;
    }

    public XComboBoxPattern getXComboBoxPattern()
    {
        if( this.xComboBoxPattern == null ) {
            this.createXComboBoxPattern();
            }

        return this.xComboBoxPattern;
    }

    public static PropertiesXComboBoxPatternBuilder createPropertiesXComboBoxPatternBuilder(
        final File        propertiesFile,
        final String    keyPrefix
        ) throws IOException
    {
        return new PropertiesXComboBoxPatternBuilder(
                propertiesFile,
                keyPrefix
                );
    }

    public void saveOnExit()
    {
        SaveOnExitHook.add( this );
    }

    private static class SaveOnExitHook
    {
        private static Set<PropertiesXComboBoxPatternBuilder> COMBOS
            = new LinkedHashSet<>();

//        static {
//            // DeleteOnExitHook must be the last shutdown hook to be invoked.
//            // Application shutdown hooks may add the first file to the
//            // delete on exit list and cause the DeleteOnExitHook to be
//            // registered during shutdown in progress. So set the
//            // registerShutdownInProgress parameter to true.
//            sun.misc.SharedSecrets.getJavaLangAccess()
//                .registerShutdownHook(2 /* Shutdown hook invocation order */,
//                    true /* register even if shutdown in progress */,
//                    new Runnable() {
//                        public void run() {
//                           runHooks();
//                        }
//                    }
//            );
//        }

        static {
            Runtime.getRuntime().addShutdownHook( new Thread( SaveOnExitHook::runHooks, "SaveOnExitHook"));
        }

        private SaveOnExitHook() {}

        static synchronized void add( final PropertiesXComboBoxPatternBuilder combo ) // $codepro.audit.disable synchronizedMethod
        {
            if( COMBOS == null) {
                // DeleteOnExitHook is running. Too late to add a file
                throw new IllegalStateException("Shutdown in progress");
                }

            COMBOS.add( combo );
        }

        static void runHooks()
        {
            Set<PropertiesXComboBoxPatternBuilder> theCombos;
            synchronized( SaveOnExitHook.class ) {
                theCombos = COMBOS;
                COMBOS = null;
                }
            final List<PropertiesXComboBoxPatternBuilder> toBeSaved
                = new ArrayList<>( theCombos );

            for( final PropertiesXComboBoxPatternBuilder c : toBeSaved ) {
                try {
                    c.save();
                    }
                catch (final IOException e) {
                    e.printStackTrace();
                    }
                }
        }
    }
}
