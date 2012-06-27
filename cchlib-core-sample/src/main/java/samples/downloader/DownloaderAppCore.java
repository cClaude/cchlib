package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
                gdauir.getProxy(),
                gdauir.getCookieHandler(),
                gdauir.getAbstractLogger()
                )
        {
            @Override
            protected Collection<URL> collectURLs() throws IOException
            {
                Collection<URL> urls        = new HashSet<URL>();
                List<String>    contentList = loads( gdai.getURLDownloadAndParseCollection() );

                for( String  pageContent : contentList ) {
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
