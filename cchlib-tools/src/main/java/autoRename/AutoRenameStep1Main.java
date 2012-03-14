/**
 *
 */
package autoRename;

import java.io.File;
import java.io.FileFilter;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class AutoRenameStep1Main {

    private File homeDir;
    private final static FileFilter dirFileFilter    = new DirFileFilter();
    private final static FileFilter level2FileFilter = new DirNameFileFilter( "prive", EnumSet.of( DirNameFileFilter.Attrib.IGNORE_CASE ) );
    //private final static Pattern pLevel1 = Pattern.compile( "\\d\\d\\d\\d\\.\\d\\d\\.\\d\\d\\..*" );
    private final static String  pLevel1Str      =  "\\d\\d\\d\\d\\.";
    private final static Pattern pLevel1         = Pattern.compile( pLevel1Str + ".*" );
    //private final static Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
    private final static Pattern[] pLevel1Part2 = {
        Pattern.compile( "\\d\\d\\.\\d\\d\\..*" ),
        Pattern.compile( "\\d\\d\\.__..*" ),
        Pattern.compile( "__.__.*" )
    };
//    private final static Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
//    private final static Pattern[] pLevel1Part2Remplace = {
//        Pattern.compile( "\\d\\d\\.\\d\\d\\." ),
//        Pattern.compile( "\\d\\d\\.__." ),
//        Pattern.compile( "__.__" )
//    };

    public AutoRenameStep1Main( File homeDir )
    {
        this.homeDir = homeDir;
    }

    public void doJob()
    {
        doJobLevel1( this.homeDir );
    }

    public void doJobLevel1( final File dirFile )
    {
        File[] dirs = dirFile.listFiles( dirFileFilter );

        if( dirs != null ) {
            for( File d : dirs ) {
//                renameIfMatchLevel1( dir );
                String  name = d.getName();
                Matcher m1   = pLevel1.matcher( name );

                if( m1.matches() ) {
                    String part2 = name.substring( 5 );

                    for( int i = 0; i<pLevel1Part2.length; i++ ) {
                        Matcher m2 = pLevel1Part2[ i ].matcher( part2 );

                        if( m2.matches() ) {
                            String yyyy = name.substring( 0, 4 );
                            String mm   = name.substring( 5, 7 );
                            String dd   = name.substring( 8, 10 );
                            String end  = name.substring( 11 );

                            if( mm.equals( "__" ) ) {
                                mm = "00";
                            }

                            if( dd.equals( "__" ) ) {
                                dd = "00";
                            }

                            File newFile = new File( dirFile, yyyy + "-" + mm + "-" + dd + "." + end );

//                            System.out.println( "Found (case" + i + "): " + d +" ==> : " + newFile );

                            boolean res = d.renameTo( newFile );

                            if( res ) {
                                doJobLevel2( newFile );
                            }
                            else {
                                System.err.println( "Can't rename [" + d + "] to [" + newFile + "]" );
                            }
                            break;
                        }
                    }
                }
                else {
                    doJobLevel1( d );
                }
            }
        }
    }

    public void doJobLevel2( final File dirFile )
    {
        File[] dirs = dirFile.listFiles( level2FileFilter );

        if( dirs != null ) {
            for( File d : dirs ) {
                doJobLevel3( d );
            }
        }
    }

    public void doJobLevel3( final File dirFile )
    {
        File[] dirs = dirFile.listFiles( dirFileFilter );

        if( dirs != null ) {
            for( File d : dirs ) {
                String parentOfParentName = dirFile.getParentFile().getName();
                File newFile = new File( dirFile, parentOfParentName + "(" + d.getName() + ")" );

                //System.out.println( "Found PRIVE : " + d + " ==> : " + newFile );

                boolean res = d.renameTo( newFile );

                if( !res ) {
                    System.err.println( "Can't rename [" + d + "] to [" + newFile + "]" );
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        File homeDir = new File( "ZZ:/Datas/Photos" );

        System.out.println( "Running from: " + homeDir );
        AutoRenameStep1Main instance = new AutoRenameStep1Main( homeDir );

        instance.doJob();
        System.out.println( "Done." );
    }

}
