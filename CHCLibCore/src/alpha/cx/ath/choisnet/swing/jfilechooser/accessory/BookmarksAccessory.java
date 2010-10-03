/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package alpha.cx.ath.choisnet.swing.jfilechooser.accessory;

import java.io.File;
import java.util.Queue;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author Claude CHOISNET
 */
public class BookmarksAccessory extends JPanel
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private Queue<File> lastDirs;
    /** @serial */
    private Queue<File> lastFiles;
    /** @serial */
    private int maxLastDirsSize  = 10; // TODO ! (GUI)
    /** @serial */
    private int maxLastFilesSize = 10; // TODO ! (GUI)

    /**
     * @return the maxLastDirsSize
     */
    public int getMaxLastDirsSize()
    {
        return maxLastDirsSize;
    }
  
    /**
     * @param maxLastDirsSize the maxLastDirsSize to set
     */
    public void setMaxLastDirsSize( int maxLastDirsSize )
    {
        this.maxLastDirsSize = maxLastDirsSize;
  
        purgeQueue( this.lastDirs, maxLastDirsSize );
    }
  
    /**
     * @return the maxLastFilesSize
     */
    public int getMaxLastFilesSize()
    {
        return maxLastFilesSize;
    }
  
    /**
     * @param maxLastFilesSize the maxLastFilesSize to set
     */
    public void setMaxLastFilesSize( int maxLastFilesSize )
    {
        this.maxLastFilesSize = maxLastFilesSize;
  
        purgeQueue( this.lastFiles, maxLastFilesSize );
    }    
    
    private static <T> void addToQueue( Queue<T> q, T o, int maxSize )
    {
        q.add( o );
  
        purgeQueue( q, maxSize );
    }

    private static <T> void purgeQueue( Queue<T> q, int maxSize )
    {
        while( q.size() > maxSize ) {
            q.poll();
        }
    }
}


///* (non-Javadoc)
//* @see javax.swing.JFileChooser#getSelectedFile()
//*/
//public File getSelectedFile()
//{
//  File file = jFileChooserSupport.getjFileChooser().getSelectedFile();
//  
////  if( file != null ) {
////      addToQueue( this.lastFiles, file, maxLastFilesSize );
////
////      if( file.isDirectory() ) {
////          addToQueue( this.lastDirs, file, maxLastDirsSize );
////      } else {
////          addToQueue( this.lastDirs, file.getParentFile(), maxLastDirsSize );
////      }
////  }
//
//  return file;
//}


