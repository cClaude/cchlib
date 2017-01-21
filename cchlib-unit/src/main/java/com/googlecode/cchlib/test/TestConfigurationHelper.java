package com.googlecode.cchlib.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.util.ArrayCollection;
import com.googlecode.cchlib.util.populator.Populator;
import com.googlecode.cchlib.util.populator.PropertiesPopulator;
import com.googlecode.cchlib.util.properties.PropertiesHelper;

/**
 * Allow test case to use specific configuration according
 * to current computer
 *
 * @since 4.1.7
 */
public class TestConfigurationHelper
{
    private final PropertiesPopulator<TestConfigurationHelper.Config> pp = new PropertiesPopulator<>( TestConfigurationHelper.Config.class );

    private final Config config;

    /**
     *
     */
    public static class Config
    {
        @Populator private String[] existingMACAddr;

        /**
         * Returns an unmodifiable collection of existing MAC Address
         * accessible by current computer.
         * @return an unmodifiable collection of existing MAC Address
         */
        public Collection<String> getExistingMACAddressCollection()
        {
            if( this.existingMACAddr == null ) {
                return Collections.emptyList();
                }
            else {
                return new ArrayCollection<>( this.existingMACAddr );
                }
        }

        /**
         * Set a collection of existing MAC Address accessible by current computer.
         *
         * @param macAddrs MAC Address collection
         */
        public void setExistingMACAddressCollection( final Collection<String> macAddrs )
        {
            this.existingMACAddr = new String[ macAddrs.size() ];

            int i = 0;

            for( final String macAddr : macAddrs ) {
                this.existingMACAddr[ i++ ] = macAddr;
                }
        }

        /**
         * Set a collection of existing MAC Address accessible by current computer.
         *
         * @param macAddrs MAC Address array
         */
        public void setExistingMACAddressCollection( final String...macAddrs )
        {
            this.existingMACAddr = new String[ macAddrs.length ];

            int i = 0;

            for( final String macAddr : macAddrs ) {
                this.existingMACAddr[ i++ ] = macAddr;
                }
        }
    }

    /**
     * Create a TestLocalConfig
     */
    public TestConfigurationHelper()
    {
        this.config = new TestConfigurationHelper.Config();
    }

    private static File getConfigSaveFile()
    {
        return FileHelper.getUserConfigDirectoryFile(
                TestConfigurationHelper.class.getName() + ".properties"
                );
    }

    private static File getConfigLoadFile()
    {
        final File configFile = getConfigSaveFile();

        if( configFile.isFile() ) {
            return configFile;
        }

        final File deprecatedConfigFile = FileHelper.getUserHomeDirectoryFile(
                TestConfigurationHelper.class.getName() + ".properties"
                );

        if( deprecatedConfigFile.isFile() ) {
            return deprecatedConfigFile;
        }

        return configFile;
    }

    /**
     * Load configuration.
     *
     * @throws IOException if configuration can not be loaded.
     */
    public void load() throws IOException
    {
        final Properties properties =
                PropertiesHelper.loadProperties(
                        getConfigLoadFile()
                        );

        this.pp.populateBean( properties , this.config );
    }

    /**
     * Save configuration.
     *
     * @throws IOException if configuration can not be saved.
     */
    public void save() throws IOException
    {
        final Properties properties = new Properties();

        this.pp.populateProperties( this.config, properties );

        PropertiesHelper.saveProperties( getConfigSaveFile(), properties );
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public Config getConfig() throws IllegalStateException
    {
        return this.config;
    }

}
