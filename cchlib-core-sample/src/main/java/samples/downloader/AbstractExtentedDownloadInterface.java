package samples.downloader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class AbstractExtentedDownloadInterface
    extends AbstractDownloadInterface
{
    private List<GenericDownloaderAppInterface.ComboBoxConfig> comboBoxConfigList;

    /**
     *
     * @param siteName
     * @param numberOfPicturesByPage
     * @param pageCount
     * @param comboBoxConfigs
     */
    protected AbstractExtentedDownloadInterface(
            final String    siteName,
            final int       numberOfPicturesByPage,
            final int       pageCount
            )
    {
        super( siteName, numberOfPicturesByPage, pageCount );

        this.comboBoxConfigList = new ArrayList<>();
    }

    protected void addComboBoxConfig( final GenericDownloaderAppInterface.ComboBoxConfig entry )
    {
        this.comboBoxConfigList.add( entry );
    }

    /**
     * @see DefaultComboBoxConfig
     */
    @Override// GenericDownloaderAppInterface
    public Collection<GenericDownloaderAppInterface.ComboBoxConfig> getComboBoxConfigCollection()
    {
        return this.comboBoxConfigList;
    }
}
