package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.io.FileFilter;
import java.util.EnumSet;
import java.util.List;

public class AutoRenameMain
{
    private final BadDateISOFileFilter level1FileFilter = new BadDateISOFileFilter(
            EnumSet.of(
                    DateISOFileFilter.Attrib.DIRECTORY_ONLY,
                    DateISOFileFilter.Attrib.REMENBER_IGNORED_DIRECTORIES
//                    DateISOFileFilter.Attrib.REMENBER_IGNORED_FILES
                    )
            );

   private final File homeDir;
   private static final FileFilter dirFileFilter    = new DirFileFilter();
   private static final FileFilter level2FileFilter = new DirNameFileFilter( "prive", EnumSet.of( DirNameFileFilter.Attrib.IGNORE_CASE ) );
//    //private static final Pattern pLevel1 = Pattern.compile( "\\d\\d\\d\\d\\.\\d\\d\\.\\d\\d\\..*" );
//    private static final String  pLevel1Str      =  "\\d\\d\\d\\d\\.";
//    private static final Pattern pLevel1         = Pattern.compile( pLevel1Str + ".*" );
//    //private static final Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
//    private static final Pattern[] pLevel1Part2 = {
//        Pattern.compile( "\\d\\d\\.\\d\\d\\..*" ),
//        Pattern.compile( "\\d\\d\\.__..*" ),
//        Pattern.compile( "__.__.*" )
//    };
//    private static final Pattern pLevel1Remplace = Pattern.compile( pLevel1Str );
//    private static final Pattern[] pLevel1Part2Remplace = {
//        Pattern.compile( "\\d\\d\\.\\d\\d\\." ),
//        Pattern.compile( "\\d\\d\\.__." ),
//        Pattern.compile( "__.__" )
//    };

    public AutoRenameMain( final File homeDir )
    {
        this.homeDir = homeDir;
    }

    public void doJob()
    {
        doJobLevel1( this.homeDir );
    }

    public void doJobLevel1( final File dirFile )
    {
        final File[] dirs = dirFile.listFiles( this.level1FileFilter );

        final List<File> ignoredFiles = this.level1FileFilter.getIgnoredFilesCopy();
        final List<File> ignoredDirs  = this.level1FileFilter.getIgnoredDirsCopy();
        final List<File> isoFiles     = this.level1FileFilter.getIgnoredGoodISOCopy();

        this.level1FileFilter.clear();

        if( dirs != null ) {
            for( final File d : dirs ) {
                doJobLevel2NotISO( d );
            }
        }

        for( final File f : isoFiles ) {
            doJobLevel2ISO( f );
        }

        for( final File f : ignoredFiles ) {
            println( "IGNORED FILE: " + f );
        }

        for( final File d : ignoredDirs ) {
            doJobLevel1( d );
        }
    }


    public void doJobLevel2NotISO( final File dirFile )
    {
        println( "LEVEL2 (NOT ISO): " + dirFile );

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
        final File newFile = this.level1FileFilter.normalize( dirFile );

        if( renameTo( dirFile, newFile ) ) {
            doJobLevel2ISO( newFile );
        }
    }


    public void doJobLevel2ISO( final File dirFile )
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
                final String dirName = d.getName();

                if( dirName.startsWith( parentOfParentName )) {
                    // Look good !
                    // println( "L3: already done: " + d );
                }
                else if( dirName.equalsIgnoreCase( "prive" ) ) {
                    final File newFile = new File( dirFile, parentOfParentName + "(prive)" );

                    println( "L3 Found PRIVE : " + d + " ==> : " + newFile );

                    renameTo( d, newFile );
                }
                else if( this.level1FileFilter.accept( dirFile ) ) {
                    // Look like bad iso !
                    final File newFile = this.level1FileFilter.normalize( dirFile );

                    println( "L3 Found BAD ISO : " + d + " ==> : " + newFile );
                }
                else {
                    final File newFile = new File( dirFile, parentOfParentName + "(" + d.getName() + ")" );

                    println( "L3 Found prive? : [" + d.getName() + "][" + d + "] ==> : " + newFile );

//                d.renameTo( d, newFile );
                }
            }
        }
    }

    public static void main( final String[] args )
    {
        String path;

        if( args.length > 0 ) {
            path = args[ 0 ];
        } else {
            path = "ZZ:/Datas/Photos";
        }

        launch( path );
    }

    public static void launch( final String path )
    {
        final File homeDir = new File( path );

        println( "Running from: " + homeDir );
        final AutoRenameMain instance = new AutoRenameMain( homeDir );

        instance.doJob();
        println( "Done." );
    }

    private static File buildRenameFile( final File f, final String newFilename )
    {
        return new File( f.getParentFile(), newFilename );
    }

    private static boolean rename( final File f, final String newFilename )
    {
        final File newFile = buildRenameFile( f, newFilename );

        return renameTo( f, newFile );
    }

    private static boolean renameTo( final File file, final File newFile )
    {
        final boolean res = file.renameTo( newFile );

        if( !res ) {
            errPrintln( "Can not rename \"" + file + "\" to \"" + newFile + '"' );
        }

        return res;
    }

    private static void println( final String str )
    {
        System.out.println( str );
    }

    private static void errPrintln( final String str )
    {
        System.out.println( str );
    }
}
