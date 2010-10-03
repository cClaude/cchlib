/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.filechooser;

import java.io.File;
import java.util.EnumSet;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * On windows JFileChooser initialization is to slow! 
 * This class try to use Tread for creating JFileChooser 
 * in background,

 * @author Claude CHOISNET
 */
public class JFileChooserInitializer
{
    private JFileChooser    jFileChooser;
    private File            defaultDir; 
    private FileFilter      fileFilter; 
    private EnumSet<Attrib> attributes;

    /**
     * TODO: doc
     */
    public enum Attrib{
        UseShellFolder,
        defaultDirectoryIsUserDir,
        doNotSetFileSystemView
        };

    /**
     *
     */
    public JFileChooserInitializer()
    {
        this(null);
    }
    
    /**
     * @param defaultDir
     */
    public JFileChooserInitializer( final File defaultDir )
    {
        this( defaultDir, null, null );
    }

    /**
     * @param defaultDir
     * @param fileFilter
     */
    public JFileChooserInitializer( final File defaultDir, final FileFilter fileFilter )
    {
        this( defaultDir, fileFilter, null );
    }

    /**
     *
     * @param defaultDir
     * @param attribSet
     */
    public JFileChooserInitializer( final File defaultDir, EnumSet<Attrib> attribSet )
    {
        this( defaultDir, null, attribSet );
    }
    
    /**
     * @param defaultDir
     * @param fileFilter
     * @param attribSet
     */
    public JFileChooserInitializer( final File defaultDir, final FileFilter fileFilter, EnumSet<Attrib> attribSet )
    {
        if( attribSet == null ) {
            attribSet = EnumSet.noneOf( Attrib.class );
        }
        
        this.defaultDir = defaultDir;
        this.fileFilter = fileFilter;
        this.attributes = attribSet;

        init();
    }

    public boolean isReady()
    {
        return jFileChooser != null;
    }
    
    /**
     * @return a JFileChooser initialized using
     * giving arguments.
     */
    public JFileChooser getJFileChooser()
    {
        if( jFileChooser == null ) {
            // be sure init() has been call
            init();
            
            while( jFileChooser == null ) {
                try {
                    Thread.sleep( 500 );
                }
                catch( InterruptedException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        return jFileChooser;
    }

    /**
     * Launch initialization in background.
     */
    synchronized private void init()
    {
        if(this.jFileChooser==null) {
            SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run()
                    {
                        JFileChooser jfc = new JFileChooser();
    
                        if( jFileChooser != null ) {
                            // Synchronization exception
                            throw new RuntimeException("Synchronization error");
                        }
                        if( ! attributes.contains( Attrib.UseShellFolder ) ) {
                            // workaround: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6317789
                            jfc.putClientProperty("FileChooser.useShellFolder", Boolean.FALSE);
                        }
    
                        if( defaultDir != null ) {
                            jfc.setCurrentDirectory( defaultDir );
                        }
                        else if( ! attributes.contains( Attrib.defaultDirectoryIsUserDir ) ) {
                            jfc.setCurrentDirectory( new File( "." ) );
                        }
    
                        if( ! attributes.contains( Attrib.doNotSetFileSystemView ) ) {
                            jfc.setFileSystemView( FileSystemView.getFileSystemView() );
                        }
    
                        if( fileFilter != null ) {
                            jfc.setFileFilter( fileFilter );
                        }
    
                        jFileChooser = jfc;
                    }
                }
            );
        }
    }    
    
//    class JFileChooserSupport
//    {
//        
////        public JFileChooserSupport( 
////                final File          defaultDir, 
////                final FileFilter    fileFilter, 
////                EnumSet<Attrib>     attribSet 
////                )
////        {
////            this.defaultDir = defaultDir;
////            this.fileFilter = fileFilter;
////            this.attributes = attribSet;
////        }
//        
//        /**
//         * @return the jFileChooser
//         */
//        public JFileChooser getJFileChooser()
//        {
//
//        }
//        
//
//    }
}

//private static <T> void addToQueue( Queue<T> q, T o, int maxSize )
//{
//  q.add( o );
//
//  purgeQueue( q, maxSize );
//}

//private static <T> void purgeQueue( Queue<T> q, int maxSize )
//{
//  while( q.size() > maxSize ) {
//      q.poll();
//  }
//}

///**
//* @return the last Dirs
//*/
//public Collection<File> getLastDirs()
//{
//  return Collections.unmodifiableCollection( lastDirs );
//}
//
///**
//* @return the lastFiles
//*/
//public Collection<File> getLastFiles()
//{
//  return Collections.unmodifiableCollection( lastFiles );
//}
//
///**
//* @return the maxLastDirsSize
//*/
//public int getMaxLastDirsSize()
//{
//  return maxLastDirsSize;
//}
//
///**
//* @param maxLastDirsSize the maxLastDirsSize to set
//*/
//public void setMaxLastDirsSize( int maxLastDirsSize )
//{
//  this.maxLastDirsSize = maxLastDirsSize;
//
//  purgeQueue( this.lastDirs, maxLastDirsSize );
//}
//
///**
//* @return the maxLastFilesSize
//*/
//public int getMaxLastFilesSize()
//{
//  return maxLastFilesSize;
//}
//
///**
//* @param maxLastFilesSize the maxLastFilesSize to set
//*/
//public void setMaxLastFilesSize( int maxLastFilesSize )
//{
//  this.maxLastFilesSize = maxLastFilesSize;
//
//  purgeQueue( this.lastFiles, maxLastFilesSize );
//}

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

///** @serial */
//private Queue<File> lastDirs;
///** @serial */
//private Queue<File> lastFiles;
///** @serial */
//private int maxLastDirsSize  = 10; // TOD O ! (GUI)
///** @serial */
//private int maxLastFilesSize = 10; // TO DO ! (GUI)


