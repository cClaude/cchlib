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
    private final String[] DIR_PATH_TO_IGNORE_FROM_ENV = {
        	"ProgramFiles",
        	"SystemRoot"
        	};	
    private final String[] DIR_PATH_TO_IGNORE = {
        	};	
    private final String[] dirPathToIgnore;
    private final String[] dirNameToIgnore = {
        //"program files".toLowerCase(), // FIME: use %ProgramFiles%
        //"windows".toLowerCase(), // FIME: user %SystemRoot%
        "i386".toLowerCase(),
        "temporary internet files".toLowerCase(),
        "apache software foundation".toLowerCase(),
        "system volume information".toLowerCase(), // NTFS (under root)
        "recycler".toLowerCase(), // NTFS (under root)
        "Recycled".toLowerCase(), // FAT32 (under root)
        
        "drivers".toLowerCase(),
        "apps".toLowerCase(),

        ".eclipse".toLowerCase(),
        };
    private final String[] endWithToIgnore = {
        "\\application data\\mozilla\\firefox".toLowerCase(),
        "\\application data\\microsoft".toLowerCase(),
        "\\application data\\thunderbird".toLowerCase(),
        "\\application data\\google".toLowerCase(),
        "\\application data\\sun\\java".toLowerCase(),
        "\\application data\\skype".toLowerCase(),
        "\\application data\\macromedia".toLowerCase(),
        "\\application data\\openoffice.org".toLowerCase(),
        "\\application data\\adobe".toLowerCase(),
        "\\Application Data\\Kaspersky Lab\\".toLowerCase(),
        "\\Application Data\\Symantec".toLowerCase(),
        "\\Temporary Files".toLowerCase(),
        
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

        "\\data\\java".toLowerCase()
        };

    private final static String[] PATH_REGEXP = {
    	".*\\\\oracle\\\\ora.*",
        };
    private final Pattern[] pathPatterns = new Pattern[PATH_REGEXP.length];
	private Writer writerIgnore;

	/**
	 * 
	 * @param writerIgnore
	 */
    public DefaultDirectoryFilter( final Writer writerIgnore )
    {
    	this.writerIgnore = writerIgnore;

    	final ArrayList<String> tmpList = new ArrayList<String>();
    	
    	for( String envname : DIR_PATH_TO_IGNORE ) {
    		String value = System.getenv( envname );
    		
    		if( value != null ) {
    			tmpList.add( value.toLowerCase() );
    			}
    		}
    	for( String path : DIR_PATH_TO_IGNORE_FROM_ENV ) {
    		tmpList.add( path.toLowerCase() );
    		}

    	dirPathToIgnore = tmpList.toArray( new String[ tmpList.size() ]);

        for( int i = 0; i<PATH_REGEXP.length; i++ ) {
            pathPatterns[ i ] = Pattern.compile( PATH_REGEXP[ i ] );
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
                catch (IOException e) {
                    logger.warn( sb.toString() );
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
                catch (IOException e) {
                    logger.warn( sb.toString() );
                    }

                return false;
                }
            }

        for( String s : endWithToIgnore ) {
            if( plc.endsWith( s ) ) {
                sb.setLength( 0 );
                sb.append( "DIEOP||||" ); // DirectoryIgnoreEndOfPath
                sb.append( path );
                sb.append( "\n" );

                try {
                    this.writerIgnore.write( sb.toString() );
                    }
                catch (IOException e) {
                    logger.warn( sb.toString() );
                    }

                return false;
                }
            }

        for( Pattern pattern : pathPatterns ) {
        	if( pattern.matcher( plc ).matches() ) {
                sb.setLength( 0 );
                sb.append( "DIRE||||" ); // DirectoryIgnoreRegExp
                sb.append( path );
                sb.append( "\n" );

                try {
                    this.writerIgnore.write( sb.toString() );
                    }
                catch (IOException e) {
                    logger.warn( sb.toString() );
                    }

                return false;
        		}
        	}

        return true;
    }
}
