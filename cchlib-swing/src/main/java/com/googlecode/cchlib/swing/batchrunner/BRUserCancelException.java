package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;

/**
 * {@link BRUserCancelException} could is generate by an user action.
 * <p>
 * Example:
 * <BR>{@link BRExecutionEvent} could receive a cancel notification from user,
 * if event create a progress monitor and if user click on cancel button
 * during process.
 * <BR>
 * Result must be check by {@link BRRunnable#execute(BRExecutionEvent)}.
 * </p>
 *
 * @since 4.1.8
 */
public class BRUserCancelException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private File file;

    public BRUserCancelException( File file )
    {
        this.file = file;
    }

    public File getFile()
    {
        return file;
    }
}
