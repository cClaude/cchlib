package cx.ath.choisnet.zip;

import java.util.EventListener;
import java.util.zip.ZipEntry;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.zip.ZipListener}
 * or {@link com.googlecode.cchlib.util.zip.UnZipListener} instead
 */
@Deprecated
public interface ZipEventListener 
    extends EventListener
{
    /**
     * 
     * @param zipentry
     */
    void newFile(ZipEntry zipentry);
}
