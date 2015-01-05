package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import com.googlecode.cchlib.util.duplicate.digest.FileDigestListener;

/**
 * Listener handler for {@link DFFPass2} and
 * {@link FileDigestListener} handler pour {@link DFFPass2}
 *
 * @since 4.2
 */
public abstract class AbstractDFFPass2WithFileDigestListener extends AbstractDFFPass2 implements DFFPass2 {

    private final class PrivateFileDigestListener implements FileDigestListener {

        @Override
        public void computeDigest( final File file, final int length )
        {
            notify_analysisStatus( file, length );
        }

        @Override
        public boolean isCancel()
        {
            return getConfig().isCancelProcess();
        }
    }

    private final FileDigestListener fileDigestListener = new PrivateFileDigestListener();

    public AbstractDFFPass2WithFileDigestListener( final DFFConfig config )
    {
        super( config );
    }

    protected FileDigestListener getFileDigestListener()
    {
        return this.fileDigestListener;
    }
}
