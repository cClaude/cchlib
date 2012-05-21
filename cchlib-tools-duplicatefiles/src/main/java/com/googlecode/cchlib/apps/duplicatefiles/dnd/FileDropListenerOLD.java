package com.googlecode.cchlib.apps.duplicatefiles.dnd;

import java.io.File;
import java.util.List;

/**
 * Implement this inner interface to listen for when files are dropped. For
 * example your class declaration may begin like this: <code><pre>
 *      public class MyClass implements FileDropListener
 *      ...
 *      public void filesDropped( List<File> files )
 *      {
 *          ...
 *      }   // end filesDropped
 *      ...
 * </pre></code>
 */
public interface FileDropListenerOLD
{
    /**
     * This method is called when files have been successfully dropped.
     *
     * @param files An array of <tt>File</tt>s that were dropped.
     */
    public abstract void filesDropped( List<File> files );
}
