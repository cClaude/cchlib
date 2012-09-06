package samples.downloader;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.util.Collection;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;

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
        * @return TODOC
        */
        public String getLabelString(); // before was: getExtraStringLabel

       /**
        *
        * @param index
        * @return TODOC
        */
       public String getLabelString( int index ); // before was: getExtraStringLabels

       /**
        *
        * @return TODOC
        */
       public Iterable<String> getComboBoxValues(); // before was: getExtraStringValues

       /**
        *
        * @return TODOC
        */
       public int getSelectedIndex();

       /**
        *
        * @param selectedIndex
        */
       public void setSelectedIndex( int selectedIndex );

       /**
        *
        * @return TODOC
        */
       public String getComboBoxSelectedValue();
    }

    /**
     * Returns the site name (for UI)
     * @return the site name
     */
    public String getSiteName();

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
    public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
        throws MalformedURLException;

    /**
     *
     * @param gdauir
     * @param content2Parse
     * @return TODOC
     * @throws MalformedURLException
     */
    public Collection<DownloadFileURL> getURLToDownloadCollection(
        GenericDownloaderAppUIResults   gdauir,
        String                          content2Parse
        ) throws MalformedURLException;

    /**
     *
     * @return TODOC
     */
    public Collection<ComboBoxConfig> getComboBoxConfigCollection();

    /**
     *
     * @return TODOC
     */
    public Proxy getProxy();

    /**
     *
     * @param proxy
     */
    public void setProxy( Proxy proxy );

}
