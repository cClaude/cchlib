package samples.batchrunner.phone.recordsorter.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import com.googlecode.cchlib.io.filefilter.DirectoryFileFilter;
import samples.batchrunner.phone.recordsorter.conf.Config;
import samples.batchrunner.phone.recordsorter.conf.ConfigDebug;
import samples.batchrunner.phone.recordsorter.conf.ConfigFactory;
import samples.batchrunner.phone.recordsorter.conf.Contact;
import samples.batchrunner.phone.recordsorter.conf.json.ConfigJSONFactory;

public class BuildConf
{
    private final ConfigFactory factory;
    private final Config config;

    public static void main( String[] args ) throws IOException
    {
        Locale.setDefault( Locale.ENGLISH );
        
        // File xmlFile = new File( args[1] + ".xml" );
        //config = ConfigFactory.newConfig();
        //File xmlFile = new File( args[1] + ".xml" );
        // ConfigFactory factory = ConfigXMLFactory.getInstance();

        ConfigFactory factory = ConfigJSONFactory.getInstance();
        File configFile = new File( args[1] + ".json" );

        BuildConf instance = new BuildConf( factory, configFile );

        instance.loadFolder( new File( args[0] ) );
        
        ConfigDebug.debug( instance.config, System.out );
 
        instance.save( configFile );
    }

    public BuildConf( ConfigFactory factory, File file ) throws FileNotFoundException, IOException
    {
        this.factory = factory;

        Config config;
        try {
            config  = factory.decodeFromFile( file );
            
            ConfigDebug.debug( config, System.out );
        } catch( FileNotFoundException e ) {
            System.err.println( "Config file not found: " + file );
            config  = factory.newConfig();
        } catch( Exception e ) {
            System.err.println( "Config file error : " + file + " " + e );
            config  = factory.newConfig();
        }

        this.config = config;
    }

    private void save( File file ) throws FileNotFoundException, IOException
    {
//        {
//            ConfigFactory factory1 = ConfigXMLFactory.getInstance();
//            File          file1    = new File( fullfilename + ".xml" );
//            factory1.encodeToFile( config, file1 );
//        }
//        {
//            ConfigFactory factory2 = ConfigJSONFactory.getInstance();
//            File          file2    = new File( fullfilename + ".json" );
//
//            factory2.encodeToFile( config, file2 );
//        }
        factory.encodeToFile( config, file );
    }

    private void loadFolder( File folderFile )
    {
        File[] files = folderFile.listFiles( new DirectoryFileFilter() );

        if( files != null ) {
            for( File file : files ) {
                addLine( file.getName() );
            }
        }          
    }

    private void addLine( String line )
    {
        String[] content = line.split( "\\." );
        boolean  remove  = line.startsWith( "zempty_" );

        System.out.println( "*** " + line + " " + content.length + " * " + remove);

        for( int i = 1; i<content.length; i++) {
            addEntry( content[ i ], line, remove );
        }
    }

    private void addEntry( final String number, final String folder, final boolean remove )
    {
        Contact contact = config.findContactByName( folder );

        System.out.println( "[" + number + "] = " + folder + "(" + remove + ") " + contact );

        if( contact == null ) {
            contact = config.addContact( folder );

            if( remove ) {
                contact.setBackup( false );
                }
        }

        contact.addNumber( number );
    }
}
