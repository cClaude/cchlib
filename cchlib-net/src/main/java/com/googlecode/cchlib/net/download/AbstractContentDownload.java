package com.googlecode.cchlib.net.download;

//Not public
abstract class AbstractContentDownload<R> extends AbstractDownload<R>
{
    private final ContentDownloadURI<R> contentDownloadURI;

    public AbstractContentDownload(
        final ContentDownloadURI<R> contentDownloadURI,
        final DownloadEvent         event
        )
    {
        super( contentDownloadURI, event );

        this.contentDownloadURI = null;
    }

}
