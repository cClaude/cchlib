package cx.ath.choisnet.zip;

/**
 * Listener for post-processing and processing
 * 
 * @deprecated no replacement
 */
public interface ZipListener 
{
    /**
     * Add a post-processing listener
     * @param listener Post-processing listener to add
     */
    void addPostProcessingListener(ZipEventListener listener);
    /**
     * Remove a post-processing listener
     * @param listener Post-processing listener to remove
     */
     void removePostProcessingListener(ZipEventListener listener);
    /**
     * Add a processing listener
     * @param listener Processing listener to add
     */
    void addProcessingListener(ZipEventListener listener);
    /**
     * Remove a processing listener
     * @param listener Processing listener to remove
     */
    void removeProcessingListener(ZipEventListener listener);
}
