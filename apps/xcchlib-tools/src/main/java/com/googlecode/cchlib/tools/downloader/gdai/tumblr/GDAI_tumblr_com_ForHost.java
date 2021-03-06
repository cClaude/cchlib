package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import com.googlecode.cchlib.net.download.ContentDownloadURI;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry.Item;
import com.googlecode.cchlib.tools.downloader.comboconfig.DefaultComboBoxConfig;

@SuppressWarnings({
    "squid:S00101", // Class naming convention
    })
class GDAI_tumblr_com_ForHost
    extends GDAI_tumblr_com
{
    private static final long serialVersionUID = 1L;

    private final DefaultComboBoxConfig  comboBoxConfig;
    private String                      _hostname_;
    private Config                       config;

    protected final Frame ownerFrame;
    private String comboBoxConfig_hostname_;

    public GDAI_tumblr_com_ForHost(
        final Frame     ownerFrame,
        final String    hostname
        )
    {
        super(
                String.format( TumblrComData.SITE_NAME_FMT, hostname ),
                TumblrComData.NUMBER_OF_PICTURES_BY_PAGE,
                TumblrComData.DEFAULT_MAX_PAGES_BLOGS
                );

        this.ownerFrame     = ownerFrame;
        this.comboBoxConfig = null;
        this._hostname_     = hostname;
    }

    public GDAI_tumblr_com_ForHost(
        final Frame                 ownerFrame,
        final DefaultComboBoxConfig comboBoxConfig,
        final Config                config
        )
    {
        super( TumblrComData.SITE_NAME_GENERIC, TumblrComData.DEFAULT_MAX_PAGES_BLOGS );

        this.ownerFrame     = ownerFrame;
        this.comboBoxConfig = comboBoxConfig;
        this.config         = config;
        this.comboBoxConfig_hostname_ = comboBoxConfig.getComboBoxSelectedValue();

        super.addComboBoxConfig( comboBoxConfig );
    }

    @Override
    protected String getCurrentHostName()
    {
        return ( this.comboBoxConfig == null ) ?
                this._hostname_
                :
                this.comboBoxConfig_hostname_;
    }

    @Override
    public ContentDownloadURI<String> getDownloadStringURL( final int pageNumber )
            throws MalformedURLException, URISyntaxException
    {
        return TumblrComHelper.getDownloadStringURL( getCurrentHostName(), pageNumber, getProxy() );
    }

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return new GenericDownloaderAppButton()
        {
            @Override
            public String getLabel()
            {
                return "Configure";
            }
            @Override
            public void onClick()
            {
                final GDAI_tumblr_com_ConfigJDialogApp dialog
                    = new GDAI_tumblr_com_ConfigJDialogApp( GDAI_tumblr_com_ForHost.this.ownerFrame, GDAI_tumblr_com_ForHost.this.config );

                dialog.setVisible( true );
            }
        };
    }

    @Override
    public void doSelectedItems( final List<Item> selectedItems )
    {
        if( ! selectedItems.isEmpty() ) {
            this.comboBoxConfig_hostname_ = selectedItems.get( 0 ).getJComboBoxText();
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "GDAI_tumblr_com_ForHost [comboBoxConfig=" );
        builder.append( this.comboBoxConfig );
        builder.append( ", _hostname_=" );
        builder.append( this._hostname_ );
        builder.append( ", config=" );
        builder.append( this.config );
        builder.append( ", ownerFrame=" );
        builder.append( this.ownerFrame );
        builder.append( ", comboBoxConfig_hostname_=" );
        builder.append( this.comboBoxConfig_hostname_ );
        builder.append( ", getCurrentHostName()=" );
        builder.append( getCurrentHostName() );
        builder.append( ']' );
        return builder.toString();
    }
}
