package com.googlecode.cchlib.tools.phone.recordsorter.conf.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;

public class XMLConfigFactory extends AbstractConfigFactory 
{
    private static XMLConfigFactory instance;
    private XMLConfigFactory() {}

    public static ConfigFactory getInstance()
    {
        if( instance == null ) {
            instance = new XMLConfigFactory();
        }
        return instance;
    }

    /**
     * Serialisation into a file
     */
    @Override
    public void encodeToFile( final Config config, final File file ) throws FileNotFoundException, IOException
    {
        assert config instanceof XMLConfigImpl;

        // ouverture de l'encodeur vers le fichier
        try( XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file)) ) {
            // serialisation de l'objet;
            encoder.writeObject( config );
            encoder.flush();
        }
    }

    @Override
    public Config newConfig()
    {
        return new XMLConfigImpl();
    }

    @Override
    public Config load( final InputStream inStream ) throws IOException
    {
        Config config;

        // ouverture de decodeur
        try( final XMLDecoder decoder = new XMLDecoder( inStream ) ) {
            config = (Config)decoder.readObject();
        }

        return config;
    }
}
