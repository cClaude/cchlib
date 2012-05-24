package com.googlecode.cchlib.swing.dnd;

import java.io.File;
import java.util.List;

/**
 * Implement this interface to listen for when files are dropped.
 * For example your class declaration may begin like this: <code><pre>
 *   public class MySimpleFileDropListener implements SimpleFileDropListener
 *   {
 *      ...
 *      public void filesDropped( final List<File> files )
 *      {
 *          ...
 *      }
 *   }
 * </pre></code>
 * @see SimpleFileDrop
 */
public interface SimpleFileDropListener
{
    /**
     * This method is called when files have been successfully dropped.
     *
     * @param files A list of <tt>File</tt>s that were dropped.
     */
    public abstract void filesDropped( List<File> files );
}
