package samples.downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
                gdai.getCacheRelativeDirectoryCacheName()
                ).getCanonicalFile();
        destinationFolderFile.mkdirs();

        GenericDownloader instance
            = new GenericDownloader(
                destinationFolderFile,
                gdauir.getDownloadThreadCount(),
                gdauir.getProxy(),
                gdauir.getAbstractLogger()
                )
        {
            @Override
            protected Iterable<URL> collectURLs() throws IOException
            {
                // Build a big string with all downloads URL (text or HTML is expected)
                String allContent;
                {
                    List<String>    contentList = loads( gdai.getURLDownloadAndParseCollection() );
                    StringBuilder   sb          = new StringBuilder();

                    for( String s: contentList ) {
                        sb.append( s );
                        }

                    allContent = sb.toString();
                    contentList.clear();
                    sb.setLength( 0 );
                }

                return gdai.getURLToDownloadCollection( gdauir, allContent );
            }
        };

        gdauir.getAbstractLogger().info( "destinationFolderFile = " + destinationFolderFile );
        instance.downloadAll();
        gdauir.getAbstractLogger().info( "done" );
    }
}
