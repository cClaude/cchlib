package samples.downloader;

import java.util.Collection;

/**
 * No extra values
 */
public abstract class AbstractDownloadInterface2
    extends AbstractDownloadInterface
{
    /**
     * 
     * @param siteName
     * @param numberOfPicturesByPage
     * @param pageCount
     */
    protected AbstractDownloadInterface2(
            final String    siteName,
            final int       numberOfPicturesByPage,
            final int       pageCount
            )
    {
        super( siteName, numberOfPicturesByPage, pageCount );
    }

    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public boolean isExtraStringValue()
    {
        return false;
    }
    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public String getExtraStringLabel()
    {
        throw new UnsupportedOperationException();
    }
    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public Collection<String> getExtraStringValues()
    {
        throw new UnsupportedOperationException();
    }
    @Override// GenericDownloaderAppInterface
    final//FIXME remove this
    public String getExtraStringLabels( final int index )
    {
        throw new UnsupportedOperationException();
    }
}
