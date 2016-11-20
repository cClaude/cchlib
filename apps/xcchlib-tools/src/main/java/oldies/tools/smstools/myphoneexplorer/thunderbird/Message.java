package oldies.tools.smstools.myphoneexplorer.thunderbird;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.googlecode.cchlib.lang.ByteArrayBuilder;

/**
 *
 */
public class Message
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final byte EOL_0D = 0x0D;
    private static final byte EOL_0A = 0x0A;

    private String              fromLine;
    private ArrayList<String>   headerList = new ArrayList<String>();
    private String              body;

    /**
     *
     * @param mis
     * @throws IOException
     */
    public Message( final MessageInputStream mis )
        throws IOException
    {
        final byte[]            buffer   = new byte[ 8192 ];
        final ByteArrayBuilder  bab      = new ByteArrayBuilder();
        boolean                 prev0D   = false;
        boolean                 inHeader = true;
        int                     len;

        // "\r\n"
        while( (len = mis.read( buffer, 0, buffer.length )) > 0) {

            for( int i = 0; i<len; i++ ) {
                byte c = buffer[ i ];

                if( inHeader ) {
                    // in header: BEGIN
                    if( c == EOL_0D ) {
                        if( prev0D ) {
                            bab.append( EOL_0D );
                        }

                        // next line ?
                        prev0D = true;
                    }
                    else if( c == EOL_0A ){

                        if( prev0D ) {
                            // EOL found !
                            //***prevEOF = true;

                            if( fromLine == null ) {
                                // Add From_ line
                                fromLine = bab.toString();
                                bab.setLength( 0 );
                            }
                            else {
                                if( bab.length() == 0 ) {
                                    // Goto body part!
                                    inHeader = false;
                                }
                                else {
                                    // Add header !
                                    headerList.add(  bab.toString() );
                                    bab.setLength( 0 );
                                }
                            }
                        }
                        else {
                            bab.append( EOL_0A );
                        }

                        prev0D = false;
                    }
                    else {
                        bab.append( buffer[ i ] );
                        prev0D = false;
                    }
                    // in header: END
                }
                else {
                    // in body: BEGIN
                    bab.append( c );
                    // in body: END
                }
            } //for(reading buffer)
        }// while(reading file)

        body = bab.toString();
    }

    /**
     * Returns first line of mail (From_ line)
     * @return From_ line content
     */
    public String getFromLine()
    {
        return this.fromLine;
    }

    /**
     * Returns list of String with header strings
     * @return list of String with header strings
     */
    public List<String> getHeaders()
    {
        return Collections.unmodifiableList( this.headerList );
    }

    public String[] getHeaderValues( final String name )
    {
        return null;
    }

    /**
     * Returns body message
     * @return body message in a String
     */
    public String getBody()
    {
        return body;
    }
}

