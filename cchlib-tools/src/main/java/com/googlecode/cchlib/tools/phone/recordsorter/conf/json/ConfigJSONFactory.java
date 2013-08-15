package samples.batchrunner.phone.recordsorter.conf.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import samples.batchrunner.phone.recordsorter.conf.Config;
import samples.batchrunner.phone.recordsorter.conf.ConfigFactory;
import samples.batchrunner.phone.recordsorter.conf.Contact;

public class ConfigJSONFactory implements ConfigFactory
{
    private static ConfigJSONFactory instance;
    private ConfigJSONFactory() {}

    public static ConfigFactory getInstance()
    {
        if( instance == null ) {
            instance = new ConfigJSONFactory();
        }
        return instance;
    }

    /**
     * Serialisation into a file
     */
    @Override
    public void encodeToFile( Config config, File file ) throws FileNotFoundException, IOException
    {
        List<ContactJSONImpl> list = new ArrayList<>();

        for( Contact c : config.getContacts() ) {
            list.add( ContactJSONImpl.class.cast( c ) );
            }
        
        try( Writer encoder = new FileWriter( file ) ) {
            new JSONSerializer().prettyPrint( true ).include( "numberSet" )/*.exclude("*.class", "description")*/.deepSerialize( list, encoder );
            encoder.flush();
        }
        String s = new JSONSerializer().prettyPrint( true ).include( "numberSet" ).deepSerialize( list );
        System.out.println(s);
    }

    /**
     * Deserialisation from a file
     */
    @Override
    public Config decodeFromFile( File file ) throws FileNotFoundException, IOException
    {
        List<ContactJSONImpl> list;

        // ouverture de decodeur
        try( Reader decoder = new FileReader( file ) ) {
            list = new JSONDeserializer<List<ContactJSONImpl>>().deserialize( decoder );
        }

        ConfigJSONImpl config = newConfigJSONImpl();

        for( Contact c : list ) {
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
