package cx.ath.choisnet.swing.filechooser.accessory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provide a basic implementation for
 * {@link BookmarksAccessory.Configurator}
 */
public abstract class AbstractBookmarksAccessoryConfigurator
    implements BookmarksAccessory.Configurator
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private /*List*/ArrayList<File> bookmarks;

    /**
     *
     * @param bookmarkFiles
     */
    public AbstractBookmarksAccessoryConfigurator(
            final List<File> bookmarkFiles
            )
    {
        this.bookmarks = new ArrayList<File>( bookmarkFiles );
    }

    /**
     * TODO: Doc!
     * @param list
     * @param file
     * @return
     */
    //final // TO DO: remove this
    protected static boolean add( List<File> list, File file )
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
                return true;
            }
        }
        return false;
    }

//    private ArrayList<File> loadBookmarks()
//    {
//        ArrayList<File> list = new ArrayList<File>();
//        Properties      properties = new Properties();
//
//        try {
//            InputStream is = new FileInputStream(configFileProperties);
//            properties.load( is );
//            is.close();
//        }
//        catch( IOException e ) {
//            slog.warning( "File error : " + configFileProperties + " - " + e );
//        }
//
//        Set<String> keys = properties.stringPropertyNames();
//
//        for(String key:keys) {
//            String  value = properties.getProperty( key );
//            File    file  = new File( value );
//
//            add(list,file);
//        }
//
//        //slog.fine( "load from : " + configFileProperties );
//
//        return list;
//    }

    /**
     * TODO: Doc!
     *
     * @param filesList
     */
    protected abstract void storeBookmarks( ArrayList<File> filesList );

    @Override
    //final // TO DO: remove this
    public Collection<File> getBookmarks()
    {
        return bookmarks;
    }

    @Override
    //final // TO DO: remove this
    public boolean addBookmarkFile( File file )
    {
        if( ! bookmarks.contains( file ) ) {
            boolean isAdd = add(bookmarks,file);

            if( isAdd ) {
                storeBookmarks( bookmarks );
            }

            return isAdd;
        }
        return false;
    }

    @Override
    //final // TO DO: remove this
    public boolean removeBookmark( File file )
    {
        bookmarks.remove( file );

        storeBookmarks( bookmarks );

        return true;
    }
}
