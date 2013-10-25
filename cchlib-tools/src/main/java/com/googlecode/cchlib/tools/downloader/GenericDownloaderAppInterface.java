package com.googlecode.cchlib.tools.downloader;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.net.download.DownloadFileURL;
import com.googlecode.cchlib.net.download.DownloadStringURL;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;


/**
 *
 */
public interface GenericDownloaderAppInterface
{
    /**
     * TODOC
     *
     */
    public interface Button 
    {
        /**
         * 
         * @return TODOC
         */
        public String getLabel();

        /**
         * TODOC
         */
        public void onClick();
    }

    /**
     * TODOC
     *
     */
    public interface ComboBoxConfig
    {
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
       
       /**
       *
       * @return TODOC
       */
       public String getDescription();
       
       /**
       *
       * @return TODOC
       */
       public List<GenericDownloaderUIPanelEntry.Item> getJComboBoxEntry();
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
     * Returns a list of {@link DownloadStringURL} to parse
     * @return a list of {@link DownloadStringURL} to parse
     * @throws MalformedURLException
     * @throws URISyntaxException 
     */
    public Collection<DownloadStringURL> getURLDownloadAndParseCollection()
        throws MalformedURLException, URISyntaxException;

    /**
     *
     * @param gdauir
     * @param content2Parse
     * @return TODOC
     * @throws MalformedURLException
     * @throws URISyntaxException 
     */
    public Collection<DownloadFileURL> getURLToDownloadCollection(
        GenericDownloaderAppUIResults   gdauir,
        DownloadStringURL               content2Parse
        ) throws MalformedURLException, URISyntaxException;

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

    /**
     *
     * @return TODOC
     */
    public Button getButtonConfig();

    public void setSelectedItems( List<Item> selectedItems );
}
