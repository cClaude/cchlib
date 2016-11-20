package oldies.tools.smstools.myphoneexplorer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 */
public class SMSLoader
{
    private static final Logger LOGGER = Logger.getLogger(SMSLoader.class);

    private final List<SMS> smsList = new ArrayList<SMS>();

    public SMSLoader()
    {
        //Empty
    }

    /**
     * @param f SMS File (CVS formated)
     * @throws IOException
     * @throws ParseException
     */
    public void addFile( final File f ) throws IOException, ParseException
    {
        load( f );

        LOGGER.info( "SMS count: " + this.smsList.size() );
    }

    private void load(final File f) throws IOException, ParseException
    {
        LOGGER.info( "reading: " + f );

        try( SMSCVSReader r = new SMSCVSReader( f ) ) {
            this.smsList.addAll( r.getSMSList() );
        }
    }

    /**
     * Remove clone SMS from list
     *
     * @throws InconsistantSMSException
     */
    public void removeClones() throws InconsistantSMSException
    {
        LOGGER.info( "removeClones()" );

        // Remove clone
        final HashMap<String,SMS> map             = new HashMap<String,SMS>();
        final ArrayList<SMS>      badClonesList   = new ArrayList<SMS>();

        for(final SMS sms:this.smsList) {
            final String  pdu = sms.getPdu();
            final SMS     old = map.get( pdu );

            if( old != null ) {
                //already a SMS with this PDU

                if( ! old.getFrom().equals( sms.getFrom() ) ) {
                    throw new InconsistantSMSException( "From", old, sms );
                }
                if( ! old.getTo().equals( sms.getTo() ) ) {
                    throw new InconsistantSMSException( "To", old, sms );
                }
                if( ! old.getBody().equals( sms.getBody() ) ) {
                    throw new InconsistantSMSException( "Body", old, sms );
                }
                if( ! old.getTime().equals( sms.getTime() ) ) {
                    //throw new InconsistantSMSException( "Time", old, sms );
                    LOGGER.warn(
                        "** Inconsistant 'Time' :"
                            + old.getTime()
                            + " / "
                            + sms.getTime()
                            + " (Not real clone)"
                            );
                    badClonesList.add( sms );
                }
                if( ! old.getStorage().equals( sms.getStorage() ) ) {
                    throw new InconsistantSMSException( "Storage", old, sms );
                }
                if( ! old.getPdu().equals( sms.getPdu() ) ) {
                    throw new InconsistantSMSException( "PDU", old, sms );
                }
            }
            else {
                map.put( sms.getPdu(), sms );
            }
        }

        this.smsList.clear();
        LOGGER.info( "SMS (no clone) count: " + map.size() );

        this.smsList.addAll( badClonesList );
        badClonesList.clear();

        for(final SMS sms:map.values()) {
            this.smsList.add( sms );
        }

        map.clear();
    }

    /**
     *
     */
    public void sort()
    {
        //
        // Sort SMS
        //
        Collections.sort( this.smsList, new SMSDateComparator() );

        LOGGER.info( "SMS (sorted list) count: " + this.smsList.size() );
    }

    /**
     * Save SMS to file
     *
     * @param outputFile output File
     * @throws IOException
     */
    public void save(final File outputFile) throws IOException
    {
        try( final Writer w = new BufferedWriter( new FileWriter( outputFile ) ) ) {

            for( final SMS sms : this.smsList ) {
                sms.appendEntry( w );
                w.append( "\r\n" );
            }
        }
    }
}
