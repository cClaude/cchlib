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
            "ProgramFiles",     // Typically C:\Program Files
            "SystemRoot"        // Typically C:\WINDOWS
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
        "\\application data\\mozilla\\firefox",
        "\\application data\\thunderbird",
        "\\application data\\google",
        "\\application data\\sun\\java",
        "\\application data\\skype",
        "\\application data\\macromedia",
        "\\application data\\openoffice.org",
        "\\application data\\adobe",
        "\\Application Data\\Kaspersky Lab",
        "\\Application Data\\Symantec",

        // Genetics
        //"\\data\\java",

        // Java/Dev
        "\\jre\\lib",
        "\\docs\\manual", // doc files
        "\\javadoc", // doc files
        "\\classes",
        "\\samples",
        "\\src\\net", // sources files
        "\\src\\com", // sources files
        "\\src\\org", // sources files
        "\\src\\java", // sources files mvn
        "\\src\\test", // sources files mvn

        "\\cygwin\\usr",

        "\\workspace\\.metadata",   // eclipse
        "\\.metadata\\.plugins",    //
        "\\.m2\\repository",        // maven
        };
    private final String[] pathEndWithToIgnore = new String[ DIR_PATH_END_WITH_TO_IGNORE.length ];

    //
    // Full path RegExp matching
    //
    private final static String[] PATH_REGEXP_TO_IGNORE = {
        ".:\\\\System Volume Information", // NTFS/FAT32 (under root)
        ".:\\\\Recycler", // NTFS (under root)
        ".:\\\\Recycled", // FAT32 (under root)

        ".*\\\\oracle\\\\ora.*",
        };
    private final Pattern[] pathRexExpToIgnore = new Pattern[PATH_REGEXP_TO_IGNORE.length];

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

    private final Pattern[] nameRexExpToIgnore = new Pattern[NAME_REX_EXP_TO_IGNORE.length];

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
            pathRexExpToIgnore[ i ] = Pattern.compile(
                                    PATH_REGEXP_TO_IGNORE[ i ],
                                    Pattern.CASE_INSENSITIVE
                                    );

            logger.debug( "pathRexExpToIgnore[" + i + "]: " + pathRexExpToIgnore[ i ] );
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


        // Name RegExp filter
        for( int i = 0; i<NAME_REX_EXP_TO_IGNORE.length; i++ ) {
            nameRexExpToIgnore[ i ] = Pattern.compile(
                            NAME_REX_EXP_TO_IGNORE[ i ],
                            Pattern.CASE_INSENSITIVE
                            );

            logger.debug( "nameRexExpToIgnore[" + i + "]: " + nameRexExpToIgnore[ i ] );
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

        for( Pattern regExp : pathRexExpToIgnore ) {
            if( regExp.matcher( plc ).matches() ) {
                sb.setLength( 0 );
                sb.append( "DIPRE||||" ); // DirectoryIgnorePathRegExp
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

        for( Pattern regExp : nameRexExpToIgnore ) {
            if( regExp.matcher( nlc ).matches() ) {
                sb.setLength( 0 );
                sb.append( "DINRE||||" ); // DirectoryIgnoreNameRegExp
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
