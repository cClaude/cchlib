package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.Collection;

/**
 * Find empty directories
 *
 */
public interface EmptyDirectoriesDeleterInterface
    extends EmptyDirectoriesLookup
{
    /**
     *
     */
    public Collection<File> getCollection();

    /**
     *
     */
    public int doAction( EmptyDirectoriesDeleterAction action );

    /**
     *
     */
    public int doDelete( EmptyDirectoriesDeleterActionListener listener );

   /**
    *
    */
    public int doDelete();
}
