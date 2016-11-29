package com.googlecode.cchlib.swing.dnd;

import java.io.File;
import java.util.List;

/**
 * Implement this interface to listen for when files are dropped.
 * For example your class declaration may begin like this:
 * <pre>
 *   public class MySimpleFileDropListener implements SimpleFileDropListener
 *   {
 *      ...
 *      public void filesDropped( final List&lt;File&gt; files )
 *      {
 *          ...
 *      }
 *   }
 * </pre>
 * @see SimpleFileDrop
 * @since 1.4.7
 */
@FunctionalInterface
public interface SimpleFileDropListener
{
    /**
     * This method is called when files have been successfully dropped.
     *
     * @param files A list of <tt>File</tt>s that were dropped.
     */
    void filesDropped( List<File> files );
}
