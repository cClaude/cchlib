package samples.batchrunner.tel.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import samples.batchrunner.phone.recordsorter.conf.Config;
import samples.batchrunner.phone.recordsorter.conf.ConfigDebug;
import samples.batchrunner.phone.recordsorter.conf.ConfigFactory;
import samples.batchrunner.phone.recordsorter.conf.json.ConfigJSONFactory;
import samples.batchrunner.phone.recordsorter.core.DestinationFolders;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.io.filefilter.FileFileFilter;

/**
 *
 *
 */
public class SortTelFile
{
    private File                sourceFolderFile;
    private DestinationFolders  destinationFolders;
    private Pattern             filenamePattern;
    private ConfigFactory       configFactory;
    private File                configFile;
    private Config              config; 

    public SortTelFile(File sourceFolderFile, File destinationFolderFile) throws IOException
    {
        this.sourceFolderFile   = sourceFolderFile;
 
        this.configFile     = new File( destinationFolderFile, "config.json" );
        this.configFactory  = ConfigJSONFactory.getInstance();
        this.config         = configFactory.decodeFromFile( configFile );

        ConfigDebug.debug( config, System.err );
        
        this.destinationFolders = new DestinationFolders( config, destinationFolderFile );

        log( "SOURCE:" + sourceFolderFile );
        log( "DEST  :" + destinationFolderFile );

        log( "SOURCE.exists():" + sourceFolderFile.exists() );
        log( "DEST.exists()  :" + destinationFolderFile.exists() );

        filenamePattern = Pattern.compile( "\\d\\d\\d\\d_\\d\\d_\\d\\d_\\d\\d_\\d\\d_.*" );
    }

    public void saveConf() throws FileNotFoundException, IOException
    {
        configFactory.encodeToFile( config, configFile );
    }
    
    public void sort()
    {
        FileIterator iter = new FileIterator( sourceFolderFile, new FileFileFilter() );

        log( "Explore:" + sourceFolderFile );

        while( iter.hasNext() ) {
            File   file     = iter.next();
            String filename = file.getName();

            Matcher m = filenamePattern.matcher( filename );
            if( m.find() ) {
                String number = filename.substring( 17 );
                number = number.substring( 0, number.indexOf( '_' ) );

                if( number.startsWith( "+33" ) ) {
                    number = '0' + number.substring( 3 );
                } else if( number.startsWith( "33" ) ) {
                    number = '0' + number.substring( 2 );
                } else if( number.startsWith( "+41" ) ) {
                    number = "00" + number.substring( 1 );
                } else if( number.startsWith( "41" ) ) {
                    number = "00" + number;
                }

                log( "> filename " + filename + " number " + number );
                File destination = destinationFolders.getFolder( number );

                if( destination == null ) {
                    destination = destinationFolders.createFolder( number, number );
                }

                log( "> move " + number + " to " + destination );
                moveFileTo( file, destination );
                }
            else {
                log( "ERR: bad filename [" + filename + ']' );
                }
        }
    }

    private void moveFileTo( File source, File destinationFolder )
    {
        assert source.isFile();
        assert destinationFolder != null;

        if( ! destinationFolder.exists() ) {
            destinationFolder.mkdir();
        }

        assert destinationFolder.exists() : destinationFolder + " does not exist";

        File target = new File( destinationFolder, source.getName() );

        assert !target.exists() : target + " already exist";

        log( "move to\n" + source + "\n" + target );

        try {
            Files.move( source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING );
            }
        catch( IOException e ) {
            log( "Can not move " + source + " to " + destinationFolder );
            }
    }

    private void log( String msg )
    {
        System.out.println( msg );
    }

    public static void main( String[] args ) throws IOException
    {
        File sourceFolderFile   = new File( args[ 0 ] );
        File destinationFolders = new File( args[ 1 ] );

        SortTelFile instance = new SortTelFile( sourceFolderFile, destinationFolders );

        instance.sort();
        instance.saveConf();
    }
}
