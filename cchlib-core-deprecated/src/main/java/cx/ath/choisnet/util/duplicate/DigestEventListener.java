package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.util.EventListener;

/**
 * The listener interface for receiving start computing digest events.
 * @deprecated use {@link com.googlecode.cchlib.util.duplicate.DigestEventListener} instead
 */
@Deprecated
public interface DigestEventListener
    extends EventListener
{
    /**
     * Invoked before computing digest for this file
     *
     * @param file the file
     */
    void computeDigest(File file);

    /**
     * Invoked during computing digest for this file,
     * give current length done for this file.
     *
     * @param file the file
     * @param length the length actually computed
     */
    void computeDigest(File file, long length);

    /**
     * Invoked if any {@link IOException} occur.
     *
     * @param e     exception that append.
     * @param file  current file.
     */
    void ioError(IOException e, File file);

    /**
     * Invoked to check if process should be cancel.
     *
     * @return true if process should be stop.
     */
    boolean isCancel();
}
