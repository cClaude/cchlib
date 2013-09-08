package com.googlecode.cchlib.tools.phone.recordsorter.conf.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;

public class ConfigXMLFactory implements ConfigFactory 
{
    private static ConfigXMLFactory instance;
    private ConfigXMLFactory() {}

    public static ConfigFactory getInstance()
    {
        if( instance == null ) {
            instance = new ConfigXMLFactory();
        }
        return instance;
    }
    
    /**
     * Serialisation into a file
     */
    @Override
    public void encodeToFile( Config config, File file ) throws FileNotFoundException, IOException
    {
        assert config instanceof ConfigXMLImpl;
        
        // ouverture de l'encodeur vers le fichier
        try( XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file)) ) {
            // serialisation de l'objet;
            encoder.writeObject( config );
            encoder.flush();
        }
    }

    /**
     * Deserialisation from a file
     */
    @Override
    public Config decodeFromFile(File file) throws FileNotFoundException, IOException
    {
        Config config;

        // ouverture de decodeur
        try( XMLDecoder decoder = new XMLDecoder(new FileInputStream(file)) ) {
            config = (Config)decoder.readObject();
        }

        return config;
    }

    @Override
    public Config newConfig()
    {
        return new ConfigXMLImpl();
    }
}
