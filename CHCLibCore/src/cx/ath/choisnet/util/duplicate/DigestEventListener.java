package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.util.EventListener;

/**
 * The listener interface for receiving start computing digest events.
 * 
 * @author Claude CHOISNET
 */
public interface DigestEventListener
    extends EventListener
{
    /**
     * Invoked before computing digest for this file
     * 
     * @param file the file
     */
    public void computeDigest(File file);
    
    /**
     * Invoked if any {@link IOException} occur.
     * 
     * @param e     exception that append.
     * @param file  current file. 
     */
    public void ioError(IOException e, File file);
}
