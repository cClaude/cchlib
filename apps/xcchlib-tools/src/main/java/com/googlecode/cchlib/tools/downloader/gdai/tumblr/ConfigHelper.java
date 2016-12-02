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

    public static Config load()
    {
        final ConfigImpl config = loadOldConfig();

        return config;
    }

    private static ConfigImpl loadOldConfig()
    {
        // Was: return new GDAI_tumblr_com_Config(); (deprecated)
        final GDAI_tumblr_com_Config old = new GDAI_tumblr_com_Config();

        final ConfigImpl config = new ConfigImpl();
        config.setDataFromVector( old.toVector() );
        return config;
    }

}
