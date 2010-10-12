package cx.ath.choisnet.swing.filechooser.accessory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Provide a basic implementation for 
 * {@link BookmarksAccessory.Configurator}
 * based on {@link Properties}
 *
 * @author Claude CHOISNET
 */
public class BookmarksAccessoryDefaultConfigurator
    extends AbstractBookmarksAccessoryConfigurator
{
    private static final long serialVersionUID = 1L;
    private static final Logger slog = Logger.getLogger( BookmarksAccessoryDefaultConfigurator.class.getName() );
    /** @serial */
    private File configFileProperties;

    /**
     * TODO: Doc!
     */
    public BookmarksAccessoryDefaultConfigurator()
    {
        this(
            new File(
                new File( System.getProperty("user.home") ),
                BookmarksAccessoryDefaultConfigurator.class.getName()
                )
            );
    }

    /**
     * TODO: Doc!
     *
     * @param configFileProperties
     */
    public BookmarksAccessoryDefaultConfigurator(
            File configFileProperties
            )
    {
        super( loadBookmarks(configFileProperties) );
        this.configFileProperties = configFileProperties;
    }

    private static ArrayList<File> loadBookmarks(
            File configFileProperties
            )
    {
        ArrayList<File> list = new ArrayList<File>();
        Properties      properties = new Properties();

        try {
            InputStream is = new FileInputStream(configFileProperties);
            properties.load( is );
            is.close();
        }
        catch( IOException e ) {
            slog.warning( "File error : " + configFileProperties + " - " + e );
        }

        Set<String> keys = properties.stringPropertyNames();

        for(String key:keys) {
            String  value = properties.getProperty( key );
            File    file  = new File( value );

            add(list,file);
        }
        
        return list;
    }

    @Override
    protected void storeBookmarks( ArrayList<File> list )
    {
        Properties  properties  = new Properties();
        int         i           = 0;

        for(File f:list) {
            properties.put( Integer.toString( i++ ), f.getPath() );
        }

        try {
            Writer writer = new FileWriter(configFileProperties);
            properties.store( writer, "" );
            writer.close();
        }
        catch( IOException e ) {
            slog.warning( "File error : " + configFileProperties + " - " + e );
        }
        //slog.fine( "Store to : " + configFileProperties );
    }
}
