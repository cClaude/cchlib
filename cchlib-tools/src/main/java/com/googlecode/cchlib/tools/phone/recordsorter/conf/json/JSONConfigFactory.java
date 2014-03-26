package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JSONConfigFactory extends AbstractConfigFactory 
{
    private static JSONConfigFactory INSTANCE;

    private JSONConfigFactory() {}

    public static ConfigFactory getInstance()
    {
        if( INSTANCE == null ) {
            INSTANCE = new JSONConfigFactory();
            }

        return INSTANCE;
    }

    /**
     * Serialisation into a file
     */
    @Override
    public void encodeToFile( final Config config, final File file ) throws FileNotFoundException, IOException
    {
        final List<JSONContactImpl> list = new ArrayList<>();

        for( final Contact c : config.getContacts() ) {
            list.add( JSONContactImpl.class.cast( c ) );
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
    public Config load( final InputStream inStream ) throws IOException
    {
        List<JSONContactImpl> list;

        // ouverture de decodeur
        try( final Reader decoder = new InputStreamReader( inStream ) ) {
            list = new JSONDeserializer<List<JSONContactImpl>>().deserialize( decoder );
            }

        final JSONConfigImpl config = newJSONConfigImpl();

        for( final Contact c : list ) {
            config.addContact( c );
            }

        return config;
    }

    public JSONConfigImpl newJSONConfigImpl()
    {
        return new JSONConfigImpl();
    }

    @Override
    public Config newConfig()
    {
        return newJSONConfigImpl();
    }
}
