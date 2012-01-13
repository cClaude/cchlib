package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 *
 *
 */
class DefaultDirectoryFilter implements FileFilter
{
    private final static Logger logger = Logger.getLogger( DefaultDirectoryFilter.class );
    private final StringBuilder sb = new StringBuilder();
    private final Writer writerIgnore;

    //
    // Full path matching
    //
    private final static String[] DIR_PATH_TO_IGNORE_FROM_ENV = {
            "ProgramFiles",
            "SystemRoot"
            };
    private final static String[] DIR_PATH_TO_IGNORE = {
            };
    /** Exclude directory matching with this full path */
    private final String[] dirPathToIgnore;

    //
    // End of Path matching
    //
    private final String[] DIR_PATH_END_WITH_TO_IGNORE = {
        // Windows
        "\\application data\\microsoft",
        "\\Temporary Files",

        // Standards
        "\\application data\\mozilla\\firefox".toLowerCase(),
        "\\application data\\thunderbird".toLowerCase(),
        "\\application data\\google".toLowerCase(),
        "\\application data\\sun\\java".toLowerCase(),
        "\\application data\\skype".toLowerCase(),
        "\\application data\\macromedia".toLowerCase(),
        "\\application data\\openoffice.org".toLowerCase(),
        "\\application data\\adobe".toLowerCase(),
        "\\Application Data\\Kaspersky Lab\\".toLowerCase(),
        "\\Application Data\\Symantec".toLowerCase(),

        // Genetics
        "\\data\\java".toLowerCase(),

        // Java/Dev
        "\\jre\\lib".toLowerCase(),
        "\\docs\\manual".toLowerCase(), // doc files
        "\\javadoc".toLowerCase(), // doc files
        "\\classes".toLowerCase(),
        "\\samples".toLowerCase(),
        "\\src\\net".toLowerCase(), // sources files
        "\\src\\com".toLowerCase(), // sources files
        "\\src\\org".toLowerCase(), // sources files
        "\\src\\java".toLowerCase(), // sources files mvn
        "\\src\\test".toLowerCase(), // sources files mvn

        "\\cygwin\\usr".toLowerCase(),

        "\\workspace\\.metadata".toLowerCase(),
        "\\.metadata\\.plugins".toLowerCase(),
        "\\.m2\\repository".toLowerCase(),
        };
    private final String[] pathEndWithToIgnore = new String[ DIR_PATH_END_WITH_TO_IGNORE.length ];

    //
    // Full path RegExp matching
    //
    private final static String[] PATH_REGEXP_TO_IGNORE = {
        ".\\\\System Volume Information", // NTFS/FAT32 (under root)
        ".\\\\Recycler", // NTFS (under root)
        ".\\\\Recycled", // FAT32 (under root)

        ".*\\\\oracle\\\\ora.*",
        };
    private final Pattern[] pathPatternsToIgnore = new Pattern[PATH_REGEXP_TO_IGNORE.length];

    //
    // Name matching
    //
    private final static String[] DIR_NAME_TO_IGNORE = {
        // Windows
        "temporary internet files",

        // Standards
        ".eclipse",
        "apache software foundation",

        // Genetics
        "i386",
        "drivers",
        "apps",
        "Tutorials",
        "plugins",
        };
    private final String[] dirNameToIgnore = new String[DIR_NAME_TO_IGNORE.length];;

    //
    // Name RegExp matching
    //
    private final static String[] NAME_REX_EXP_TO_IGNORE = {
        "apache-tomcat-.*",
        "apache-ant-.*,"
        };

    /**
     *
     * @param writerIgnore
     */
    public DefaultDirectoryFilter( final Writer writerIgnore )
    {
        this.writerIgnore = writerIgnore;

        final ArrayList<String> tmpList = new ArrayList<String>();

        // Path to ignore (read from environment vars)
        for( String envname : DIR_PATH_TO_IGNORE_FROM_ENV ) {
            String value = System.getenv( envname );

            if( value == null ) {
                logger.warn( "Var not found: " + envname );
                }
            else {
                tmpList.add( value.toLowerCase() );

                logger.trace( "Var: " + envname + " = " + value );
                }
            }
        for( String path : DIR_PATH_TO_IGNORE ) {
            tmpList.add( path.toLowerCase() );
            }

        dirPathToIgnore = tmpList.toArray( new String[ tmpList.size() ]);

        for( String p2i : dirPathToIgnore ) {
            logger.debug( "dirPathToIgnore (env & static): " + p2i );
            }

        // Path RegExp filter
        for( int i = 0; i<PATH_REGEXP_TO_IGNORE.length; i++ ) {
            pathPatternsToIgnore[ i ] = Pattern.compile(
                                    PATH_REGEXP_TO_IGNORE[ i ],
                                    Pattern.CASE_INSENSITIVE
                                    );

            logger.debug( "pathPatternsToIgnore[" + i + "]: " + pathPatternsToIgnore[ i ] );
            }

        // full path endWith to ignore
        for( int i = 0; i<DIR_PATH_END_WITH_TO_IGNORE.length; i++ ) {
            pathEndWithToIgnore[ i ] = DIR_PATH_END_WITH_TO_IGNORE[ i ].toLowerCase();

            logger.debug( "pathEndWithToIgnore[" + i + "]: " + pathEndWithToIgnore[ i ] );
            }

        // Name to ignore
        for( int i = 0; i<DIR_NAME_TO_IGNORE.length; i++ ) {
            dirNameToIgnore[ i ] = DIR_NAME_TO_IGNORE[ i ].toLowerCase();

            logger.debug( "dirNameToIgnore[" + i + "]: " + dirNameToIgnore[ i ] );
            }

    }

    @Override
    public boolean accept( File dirFile )
    {
        final String path = dirFile.getPath();
        final String plc  = path.toLowerCase();
        final String nlc  = dirFile.getName().toLowerCase();

        for( String s : dirPathToIgnore ) {
            if( s.equals( plc ) ) {
                sb.setLength( 0 );
                sb.append( "DIFP||||" ); // DirectoryIgnoreFullPath
                sb.append( path );
                sb.append( "\n" );

                try {
                    this.writerIgnore.write( sb.toString() );
                    }
                catch( IOException e ) {
                    logger.error( sb.toString(), e );
                    }

                return false;
                }
            }

        for( String s : dirNameToIgnore ) {
            if( s.equals( nlc ) ) {
                sb.setLength( 0 );
                sb.append( "DIN||||" ); // DirectoryIgnoreName
                sb.append( path );
                sb.append( "\n" );

                try {
                    this.writerIgnore.write( sb.toString() );
                    }
                catch( IOException e ) {
                    logger.error( sb.toString(), e );
                    }

                return false;
                }
            }

        for( String s : pathEndWithToIgnore ) {
            if( plc.endsWith( s ) ) {
                sb.setLength( 0 );
                sb.append( "DIEOP||||" ); // DirectoryIgnoreEndOfPath
                sb.append( path );
                sb.append( "\n" );

                try {
                    this.writerIgnore.write( sb.toString() );
                    }
                catch( IOException e ) {
                    logger.error( sb.toString(), e );
                    }

                return false;
                }
            }

        for( Pattern pattern : pathPatternsToIgnore ) {
            if( pattern.matcher( plc ).matches() ) {
                sb.setLength( 0 );
                sb.append( "DIRE||||" ); // DirectoryIgnoreRegExp
                sb.append( path );
                sb.append( "\n" );

                try {
                    this.writerIgnore.write( sb.toString() );
                    }
                catch( IOException e ) {
                    logger.error( sb.toString(), e );
                    }

                return false;
                }
            }

        return true;
    }
}
