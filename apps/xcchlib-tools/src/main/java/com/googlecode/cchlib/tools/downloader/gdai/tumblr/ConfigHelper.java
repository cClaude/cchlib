package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.io.File;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

public class ConfigHelper
{
    private ConfigHelper()
    {
        // Empty
    }

    private static File getConfigFile()
    {
        return FileHelper.getUserConfigDirectoryFile(
            Config.class.getName() + ".json"
            );
    }

    public static void save( final Config config ) throws ConfigIOException
    {
        final File configFile = getConfigFile();

        try {
            JSONHelper.toJSON( configFile, config, true );
        }
        catch( final JSONHelperException e ) {
            throw new ConfigIOException( configFile.toString(), e );
        }
    }

    public static Config load() throws JSONHelperException
    {
        return JSONHelper.load( getConfigFile(), ConfigImpl.class );
        // return loadOldConfig();
    }

    private static Config loadOldConfig()
    {
        return new GDAI_tumblr_com_Config(); // (deprecated)
    }

    private static ConfigImpl loadOldConfigAndCreateNewConfig()
    {
        final ConfigImpl config = new ConfigImpl();
        config.setDataFromVector( loadOldConfig().toVector() );
        return config;
    }
}
