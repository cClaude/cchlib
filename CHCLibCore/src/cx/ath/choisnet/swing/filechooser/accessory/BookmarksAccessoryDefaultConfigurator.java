package cx.ath.choisnet.swing.filechooser.accessory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 *
 * @author Claude CHOISNET
 */
public class BookmarksAccessoryDefaultConfigurator
    implements BookmarksAccessory.Configurator
{
    private static final long serialVersionUID = 1L;
    private static final Logger slog = Logger.getLogger( BookmarksAccessoryDefaultConfigurator.class.getName() );
    /** @serial */
    private /*List*/ArrayList<File> bookmarks;
    /** @serial */
    private File configFileProperties;

    /**
     *
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
     *
     * @param configFileProperties
     */
    public BookmarksAccessoryDefaultConfigurator(
            File configFileProperties
            )
    {
        this.configFileProperties = configFileProperties;
        this.bookmarks = loadBookmarks();
    }

    private static void add(List<File> list, File file )
    {
        if( file.isDirectory() ) {
            boolean found = false;

            for(File f:list) {
                if( f.getPath().equals( file.getPath() ) ) {
                    found = true;
                }
            }

            if( !found ) {
                list.add( file );
            }
        }
    }

    private ArrayList<File> loadBookmarks()
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

        //slog.fine( "load from : " + configFileProperties );

        return list;
    }

    /**
     *
     * @param list
     * @throws IOException
     */
    protected void storeBookmarks( ArrayList<File> list )
        throws IOException
    {
        Properties  properties  = new Properties();
        int         i           = 0;

        for(File f:list) {
            properties.put( Integer.toString( i++ ), f.getPath() );
        }

        Writer writer = new FileWriter(configFileProperties);
        properties.store( writer, "" );
        writer.close();

        //slog.fine( "Store to : " + configFileProperties );
    }
    @Override
    public Collection<File> getBookmarks()
    {
        return bookmarks;
    }
    @Override
    public boolean addBookmarkFile( File file )
    {
        if( ! bookmarks.contains( file ) ) {
            add(bookmarks,file);

            try {
                storeBookmarks( bookmarks );
            }
            catch( IOException e ) {
                slog.warning( "File error : " + configFileProperties + " - " + e );
            }

            return true;
        }
        return false;
    }
    @Override
    public boolean removeBookmark( File file )
    {
        bookmarks.remove( file );

        try {
            storeBookmarks( bookmarks );
        }
        catch( IOException e ) {
            slog.warning( "File error : " + configFileProperties + " - " + e );
        }

        return true;
    }
}
