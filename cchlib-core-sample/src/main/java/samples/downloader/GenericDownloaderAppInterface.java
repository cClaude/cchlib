package samples.downloader;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.util.Collection;
import com.googlecode.cchlib.net.download.FileDownloadURL;
import com.googlecode.cchlib.net.download.StringDownloadURL;

/**
 *
 */
public interface GenericDownloaderAppInterface
{
    /**
    *
    *
    */
   public interface ComboBoxConfig
   {
       /**
        *
        * @return
        */
       public String getLabelString(); // before was: getExtraStringLabel

       /**
        *
        * @param i
        * @return
        */
       public String getLabelString( int index ); // before was: getExtraStringLabels

       /**
        *
        * @return
        */
       public Iterable<String> getComboBoxValues(); // before was: getExtraStringValues

       /**
        *
        * @return
        */
       public int getSelectedIndex();

       /**
        *
        * @param selectedIndex
        */
       public void setSelectedIndex( int selectedIndex );

       /**
        *
        * @return
        */
       public String getComboBoxSelectedValue();
   }

    /**
     * Returns the site name (for UI)
     * @return the site name
     */
    public String getSiteName();

//    /**
//     * @return a {@link CookieHandler} if site need it, null otherwise
//     */
//    public CookieHandler getCookieHandler();

    /**
     * Returns average pictures by page (for UI)
     * @return average pictures by page.
     */
    public int getNumberOfPicturesByPage();

    /**
     * Returns number of page to download (for UI). Value must be greater than 0 and
     * less or equal than value return by {@link #getMaxPageCount()}.
     * @return number of page to download
     */
    public int getPageCount();

    /**
     * Set number of page to download
     * @see #getPageCount()
     */
    public void setPageCount( int pageCount );

    /**
     * Returns max number of page to download (for UI)
     * @return max number of page to download
     */
    public int getMaxPageCount();

    /**
     *
     * @return relative directory cache name
     */
    public String getCacheRelativeDirectoryCacheName();

    /**
     * Returns a list of URL of json values to parse
     * @return a list of URL of json values to parse
     * @throws MalformedURLException
     */
    public Collection<StringDownloadURL> getURLDownloadAndParseCollection()
        throws MalformedURLException;

    /**
     *
     * @param gdauir
     * @param content2Parse
     * @return
     * @throws MalformedURLException
     */
    public Collection<FileDownloadURL> getURLToDownloadCollection(
        GenericDownloaderAppUIResults   gdauir,
        String                          content2Parse
        ) throws MalformedURLException;

    /**
     *
     * @return
     */
    public Collection<ComboBoxConfig> getComboBoxConfigCollection();

//    /**
//     *
//     * @return
//     */
//    public Map<String, String> getRequestPropertyMap();

    /**
     *
     * @return
     */
    public Proxy getProxy();

    /**
     *
     * @param proxy
     */
    public void setProxy( Proxy proxy );

}
