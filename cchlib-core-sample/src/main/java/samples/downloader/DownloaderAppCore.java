package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import com.googlecode.cchlib.net.download.FileDownloadURL;

/**
 *
 *
 *
 */
public class DownloaderAppCore
{
    /**
     * Start Sample here !
     */
    public void startDownload(
            final GenericDownloaderAppInterface gdai,
            final GenericDownloaderAppUIResults gdauir
            )
        throws IOException, NoSuchAlgorithmException, ClassNotFoundException
    {
        final File destinationFolderFile =
            new File(
                new File(".").getAbsoluteFile(),
                "output" // gdai.getCacheRelativeDirectoryCacheName()
                ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        GenericDownloader instance
            = new GenericDownloader(
                destinationFolderFile,
                gdai.getCacheRelativeDirectoryCacheName(),
                gdauir.getDownloadThreadCount(),
//                gdauir.getRequestPropertyMap(),
//                gdauir.getProxy(),
//                gdauir.getCookieHandler(),
                gdauir.getAbstractLogger()
                )
        {
            @Override
            protected Collection<FileDownloadURL> collectDownloadURLs() throws IOException
            {
                final Collection<FileDownloadURL>   urls        = new HashSet<FileDownloadURL>();
                final List<String>                  contentList = loads( gdai.getURLDownloadAndParseCollection() );

                for( String pageContent : contentList ) {
                    urls.addAll(
                        gdai.getURLToDownloadCollection( gdauir, pageContent )
                        );
                    }

                return urls;
            }
        };

        gdauir.getAbstractLogger().info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        gdauir.getAbstractLogger().info( "done" );
    }
}
