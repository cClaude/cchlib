package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.io.FileFilter;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({
    "squid:S106", // Standard outputs
    "squid:CommentedOutCodeLine", // Keep code in comments
    "squid:S00115", // Constant names
    "squid:S134"
})
public class AutoRenameStep1Main
{
    private final File homeDir;

    private static final FileFilter dirFileFilter    = new DirFileFilter();
    private static final FileFilter level2FileFilter = new DirNameFileFilter( "prive", EnumSet.of( DirNameFileFilter.Attributes.IGNORE_CASE ) );
    //private static final Pattern pLevel1 = Pattern.compile( "\\d\\d\\d\\d\\.\\d\\d\\.\\d\\d\\..*" );
    private static final String  pLevel1Str      =  "\\d\\d\\d\\d\\.";
    private static final Pattern pLevel1         = Pattern.compile( pLevel1Str + ".*" );
    //private static final Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
    private static final Pattern[] pLevel1Part2 = {
        Pattern.compile( "\\d\\d\\.\\d\\d\\..*" ),
        Pattern.compile( "\\d\\d\\.__..*" ),
        Pattern.compile( "__.__.*" )
    };
//    private static final Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
//    private static final Pattern[] pLevel1Part2Remplace = {
//        Pattern.compile( "\\d\\d\\.\\d\\d\\." ),
//        Pattern.compile( "\\d\\d\\.__." ),
//        Pattern.compile( "__.__" )
//    };

    public AutoRenameStep1Main( final File homeDir )
    {
        this.homeDir = homeDir;
    }

    public void doJob()
    {
        doJobLevel1( this.homeDir );
    }

    public void doJobLevel1( final File dirFile )
    {
        final File[] dirs = dirFile.listFiles( dirFileFilter );

        if( dirs != null ) {
            for( final File d : dirs ) {
                final String  name = d.getName();
                final Matcher m1   = pLevel1.matcher( name );

                if( m1.matches() ) {
                    final String part2 = name.substring( 5 );

                    for( int i = 0; i<pLevel1Part2.length; i++ ) {
                        final Matcher m2 = pLevel1Part2[ i ].matcher( part2 );

                        if( m2.matches() ) {
                            final String yyyy = name.substring( 0, 4 );
                            String mm   = name.substring( 5, 7 );
                            String dd   = name.substring( 8, 10 );
                            final String end  = name.substring( 11 );

                            if( "__".equals( mm ) ) {
                                mm = "00";
                            }

                            if( "__".equals( dd ) ) {
                                dd = "00";
                            }

                            final File newFile = new File(
                                    dirFile,
                                    yyyy + "-" + mm + "-" + dd + "." + end
                                    );

//                            System.out.println( "Found (case" + i + "): " + d +" ==> : " + newFile );

                            final boolean res = d.renameTo( newFile );

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
        final File[] dirs = dirFile.listFiles( level2FileFilter );

        if( dirs != null ) {
            for( final File d : dirs ) {
                doJobLevel3( d );
            }
        }
    }

    public void doJobLevel3( final File dirFile )
    {
        final File[] dirs = dirFile.listFiles( dirFileFilter );

        if( dirs != null ) {
            for( final File d : dirs ) {
                final String parentOfParentName = dirFile.getParentFile().getName();
                final File newFile = new File( dirFile, parentOfParentName + "(" + d.getName() + ")" );

                //System.out.println( "Found PRIVE : " + d + " ==> : " + newFile );

                final boolean res = d.renameTo( newFile );

                if( !res ) {
                    System.err.println( "Can't rename [" + d + "] to [" + newFile + "]" );
                }
            }
        }
    }

    public static void main( final String[] args )
    {
        final File homeDir = new File( "ZZ:/Datas/Photos" );

        System.out.println( "Running from: " + homeDir );
        final AutoRenameStep1Main instance = new AutoRenameStep1Main( homeDir );

        instance.doJob();
        System.out.println( "Done." );
    }
}
