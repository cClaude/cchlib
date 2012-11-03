package samples.downloader.gdai;

import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import samples.downloader.DefaultComboBoxConfig;
import com.googlecode.cchlib.net.download.DownloadStringURL;

class GDAI_tumblr_com_ForHost
    extends GDAI_tumblr_com
{
	private static final long serialVersionUID = 1L;
	private static final String SITE_NAME_GENERIC = "*.tumblr.com";

    private DefaultComboBoxConfig   comboBoxConfig;
    private String                  _hostname;
    private GDAI_tumblr_com_Config config;

    protected final Frame ownerFrame;
    
    public GDAI_tumblr_com_ForHost( 
        final Frame     ownerFrame,
        final String    hostname 
        )
    {
        super(
                String.format( SITE_NAME_FMT, hostname ),
                NUMBER_OF_PICTURES_BY_PAGE,
                DEFAULT_MAX_PAGES_BLOGS
                );

        this.ownerFrame     = ownerFrame;
        this.comboBoxConfig = null;
        this._hostname      = hostname;
    }        

    public GDAI_tumblr_com_ForHost( 
        final Frame                     ownerFrame,
        final DefaultComboBoxConfig     comboBoxConfig, 
        final GDAI_tumblr_com_Config    config
        )
    {
        super( SITE_NAME_GENERIC, DEFAULT_MAX_PAGES_BLOGS );
        
        this.ownerFrame     = ownerFrame;
        this.comboBoxConfig = comboBoxConfig;
        this.config         = config;
        
        super.addComboBoxConfig( comboBoxConfig );
    }
    
    protected String getCurrentHostName()
    {
        return ( comboBoxConfig == null ) ?
                _hostname
                :
                comboBoxConfig.getComboBoxSelectedValue();
    }

    @Override
    public DownloadStringURL getDownloadStringURL( int pageNumber )
            throws MalformedURLException, URISyntaxException
    {
        return getDownloadStringURL( getCurrentHostName(), pageNumber, getProxy() );
    }

    @Override
    public Button getButtonConfig()
    {
        return new Button()
        {
            @Override
            public String getLabel()
            {
                return "Configure";
            }
            @Override
            public void onClick()
            {
                GDAI_tumblr_com_ConfigJDialog dialog 
                    = new GDAI_tumblr_com_ConfigJDialog( ownerFrame, config );
                
                dialog.setVisible( true );
            }
            
        };
    }
}