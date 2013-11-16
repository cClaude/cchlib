package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ConfigJSONFactory implements ConfigFactory
{
    private static ConfigJSONFactory INSTANCE;

    private ConfigJSONFactory() {}

    public static ConfigFactory getInstance()
    {
        if( INSTANCE == null ) {
            INSTANCE = new ConfigJSONFactory();
            }

        return INSTANCE;
    }

    /**
     * Serialisation into a file
     */
    @Override
    public void encodeToFile( final Config config, final File file ) throws FileNotFoundException, IOException
    {
        final List<ContactJSONImpl> list = new ArrayList<>();

        for( final Contact c : config.getContacts() ) {
            list.add( ContactJSONImpl.class.cast( c ) );
            }

        try( final Writer encoder = new FileWriter( file ) ) {
            new JSONSerializer().prettyPrint( true ).include( "numberSet" )/*.exclude("*.class", "description")*/.deepSerialize( list, encoder ); // $codepro.audit.disable spaceAroundPeriods
            encoder.flush();
            }

        final String s = new JSONSerializer().prettyPrint( true ).include( "numberSet" ).deepSerialize( list );

        System.out.println( s );
    }

    /**
     * Deserialisation from a file
     */
    @Override
    public Config decodeFromFile( final File file ) throws FileNotFoundException, IOException
    {
        List<ContactJSONImpl> list;

        // ouverture de decodeur
        try( final Reader decoder = new FileReader( file ) ) {
            list = new JSONDeserializer<List<ContactJSONImpl>>().deserialize( decoder );
            }

        final ConfigJSONImpl config = newConfigJSONImpl();

        for( final Contact c : list ) {
            config.addContact( c );
            }

        return config;
    }

    public ConfigJSONImpl newConfigJSONImpl()
    {
        return new ConfigJSONImpl();
    }

    @Override
    public Config newConfig()
    {
        return newConfigJSONImpl();
    }
}
