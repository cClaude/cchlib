/**
 * 
 */
package cx.ath.choisnet.zip;

import java.util.EventListener;
import java.util.zip.ZipEntry;

/**
 * 
 * @author Claude CHOISNET
 */
public interface ZipEventListener 
    extends EventListener
{
    /**
     * 
     * @param zipentry
     */
    void newFile(ZipEntry zipentry);
}
