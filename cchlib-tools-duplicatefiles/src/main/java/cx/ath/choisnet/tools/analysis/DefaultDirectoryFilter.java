package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Writer;
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
    private final String[] dirNameToIgnore = {
        "Program Files",
        "WINDOWS",
        "I386",
        "Temporary Internet Files",
        "Apache Software Foundation",
        "System Volume Information",
        "RECYCLER",
        "drivers",
        "Apps",

        ".eclipse",
        };
    private final String[] endWithToIgnore = {
        "Application Data\\Mozilla\\Firefox",
        "Application Data\\Microsoft",
        "Application Data\\Thunderbird",
        "Application Data\\Google",
        "Application Data\\Sun\\Java",
        "Application Data\\Skype",
        "Application Data\\Macromedia",
        "Application Data\\OpenOffice.org",
        "Application Data\\Adobe",

        "workspace\\.metadata",
        ".metadata\\.plugins",
        ".m2\\repository",

        "Data\\Java"
        };

    private final String[] pathRegExp = {

        };
    private final Pattern[] pathPattern = new Pattern[pathRegExp.length];
	private Writer writerIgnore;

	/**
	 * 
	 * @param writerIgnore
	 */
    public DefaultDirectoryFilter( final Writer writerIgnore )
    {
    	this.writerIgnore = writerIgnore;
    	
        for( int i = 0; i<pathRegExp.length; i++ ) {
            pathPattern[ i ] = Pattern.compile( pathRegExp[ i ] );
            }
    }

    @Override
    public boolean accept( File dirFile )
    {
        //pattern.matcher("str").matches();
        String p = dirFile.getPath();
        String n = dirFile.getName();

        for( String s : dirNameToIgnore ) {
            if( s.equalsIgnoreCase( n ) ) {
                sb.setLength( 0 );
                sb.append( "D||||" );
                sb.append( p );
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
            if( p.endsWith( s ) ) {
                sb.setLength( 0 );
                sb.append( "D||||" );
                sb.append( p );
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
