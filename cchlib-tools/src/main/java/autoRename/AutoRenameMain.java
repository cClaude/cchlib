package autoRename;

import java.io.File;
import java.io.FileFilter;
import java.util.EnumSet;
import java.util.List;

/**
 *
 */
public class AutoRenameMain {
    private BadDateISOFileFilter level1FileFilter = new BadDateISOFileFilter( 
            EnumSet.of( 
                    DateISOFileFilter.Attrib.DIRECTORY_ONLY, 
                    DateISOFileFilter.Attrib.REMENBER_IGNORED_DIRECTORIES
//                    DateISOFileFilter.Attrib.REMENBER_IGNORED_FILES
                    )
            );
    
   private File homeDir;
   private final static FileFilter dirFileFilter    = new DirFileFilter();
   private final static FileFilter level2FileFilter = new DirNameFileFilter( "prive", EnumSet.of( DirNameFileFilter.Attrib.IGNORE_CASE ) );
//    //private final static Pattern pLevel1 = Pattern.compile( "\\d\\d\\d\\d\\.\\d\\d\\.\\d\\d\\..*" );
//    private final static String  pLevel1Str      =  "\\d\\d\\d\\d\\.";
//    private final static Pattern pLevel1         = Pattern.compile( pLevel1Str + ".*" );
//    //private final static Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
//    private final static Pattern[] pLevel1Part2 = {
//        Pattern.compile( "\\d\\d\\.\\d\\d\\..*" ),
//        Pattern.compile( "\\d\\d\\.__..*" ),
//        Pattern.compile( "__.__.*" )
//    };
//    private final static Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
//    private final static Pattern[] pLevel1Part2Remplace = {
//        Pattern.compile( "\\d\\d\\.\\d\\d\\." ),
//        Pattern.compile( "\\d\\d\\.__." ),
//        Pattern.compile( "__.__" )
//    };
    
    public AutoRenameMain( File homeDir )
    {
        this.homeDir = homeDir;
    }
    
    public void doJob()
    {
        doJobLevel1( this.homeDir );
    }
    
    public void doJobLevel1( final File dirFile )
    {
        //System.out.println( "LEVEL1: " + dirFile );

        File[] dirs = dirFile.listFiles( level1FileFilter );
        
        List<File> ignoredFiles = level1FileFilter.getIgnoredFilesCopy();
        List<File> ignoredDirs  = level1FileFilter.getIgnoredDirsCopy();
        List<File> isoFiles     = level1FileFilter.getIgnoredGoodISOCopy();
        
        level1FileFilter.clear();
        
        if( dirs != null ) {
            for( File d : dirs ) {
                doJobLevel2NotISO( d );
            }
        }
        
        for( File f : isoFiles ) {
            doJobLevel2ISO( f );
        }

        for( File f : ignoredFiles ) {
            System.out.println( "IGNORED FILE: " + f );
        }

        for( File d : ignoredDirs ) {
            doJobLevel1( d );
        }
    }

    public void doJobLevel2NotISO( final File dirFile )
    {
        System.out.println( "LEVEL2 (NOT ISO): " + dirFile );
        
//        String oldName = dirFile.getName();
//        StringBuilder newName = new StringBuilder();
//        
//        // yyyy-mm-dd
//        for( int i = 0; i<oldName.length(); i++ ) {
//            char c = oldName.charAt( i );
//            
//            if( i == 4 || i == 7 ) {
//                newName.append( c );
//            }
//            else if( i < 10 ) {
//                if( Character.isDigit( c ) ) {
//                    newName.append( c );
//                }
//                else {
//                    newName.append( '0' );
//                }
//            }
//            else {
//                newName.append( c );
//            }
//        }
//
//        File newFile = new File( dirFile.getParentFile(), newName.toString() );
//        
        File newFile = level1FileFilter.normalize( dirFile );
        
        if( dirFile.renameTo( newFile ) ) {
            doJobLevel2ISO( newFile );
        }
        else {
            System.err.println( "Can't rename [" + dirFile + "] to [" + newFile + "]" );
        }
      
    }
    
    public void doJobLevel2ISO( final File dirFile )
    {
        // System.out.println( "LEVEL2 (ISO): " + dirFile );
        File[] dirs = dirFile.listFiles( level2FileFilter );
        
        if( dirs != null ) {
            for( File d : dirs ) {
                doJobLevel3( d );
            }
        }
    }
   
    public void doJobLevel3( final File dirFile )
    {
        //System.out.println( "LEVEL3: " + dirFile );
        File[] dirs = dirFile.listFiles( dirFileFilter );
        
        if( dirs != null ) {
            for( File d : dirs ) {
                String parentOfParentName = dirFile.getParentFile().getName();
                String dirName = d.getName();
                
                if( dirName.startsWith( parentOfParentName )) {
                    // Look good !
                    // System.out.println( "L3: already done: " + d );
                }
                else if( dirName.equalsIgnoreCase( "prive" ) ) {
                    File newFile = new File( dirFile, parentOfParentName + "(prive)" );

                    System.out.println( "L3 Found PRIVE : " + d + " ==> : " + newFile );
                    
                    boolean res = d.renameTo( newFile );

                    if( !res ) {
                        System.err.println( "Can't rename [" + d + "] to [" + newFile + "]" );
                    }
                }
                else if( level1FileFilter.accept( dirFile ) ) {
                    // Look like bad iso !
                    File newFile = level1FileFilter.normalize( dirFile );
                    
                    System.out.println( "L3 Found BAD ISO : " + d + " ==> : " + newFile );
                }
                else {
                    File newFile = new File( dirFile, parentOfParentName + "(" + d.getName() + ")" );

                    System.out.println( "L3 Found prive? : [" + d.getName() + "][" + d + "] ==> : " + newFile );
                
//                boolean res = d.renameTo( newFile );
//                
//                if( !res ) {
//                    System.err.println( "Can't rename [" + d + "] to [" + newFile + "]" );
//                }
                }
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args )
    {
        // TODO Auto-generated method stub
        File homeDir = new File( "ZZ:/Datas/Photos" );
        
        System.out.println( "Running from: " + homeDir );
        AutoRenameMain instance = new AutoRenameMain( homeDir );
        
        instance.doJob();
        System.out.println( "Done." );
    }
/*
    private static File buildRenameFile( File f, String newFilename )
    {
        return new File( f.getParentFile(), newFilename );
    }

    private static boolean rename( File f, String newFilename )
    {
        File newFile = buildRenameFile( f, newFilename );

        boolean res = f.renameTo( newFile );

        if( !res ) {
            System.err.println( "Can't rename [" + f + "] to [" + newFile + "]" );
        }
        
        return res;
    }
*/
}
