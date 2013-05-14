/**
 *
 */
package cx.ath.choisnet.tools.analysis;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import com.googlecode.cchlib.text.DateFormatHelper;

/**
 *
 *
 */
public class FileResultFormater
{
    private final StringBuilder sb          = new StringBuilder();
    private DateFormat          ISO_FORMAT  = DateFormatHelper.createFullDateISODateFormat();
    private final String        space10chars;
    /**
     *
     */
    public FileResultFormater()
    {
        sb.setLength( 0 );

        for( int i = 0; i<10; i++ ) {
            sb.append( ' ' );
            }
        space10chars = sb.toString();

    }

    /**
     *
     * @param f
     * @param prefix
     * @return
     */
    public String format(
        final File      f,
        final String    prefix
        )
    {
        final String fileLenStr = space10chars + f.length();

        sb.setLength( 0 );
        sb.append( prefix ); // F | D* | I*
        sb.append( '|' );
        sb.append( ISO_FORMAT.format( new Date( f.lastModified() ) ) );
        sb.append( '|' );
        sb.append( fileLenStr.substring( fileLenStr.length() - 10 ) );
        sb.append( "||" ); // space for MD5
        sb.append( f.getPath() );
        sb.append( '\n' );

        return sb.toString();
    }
}
