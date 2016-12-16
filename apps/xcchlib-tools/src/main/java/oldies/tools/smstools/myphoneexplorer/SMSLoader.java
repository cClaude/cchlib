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


public class SMSLoader
{
    private static final Logger LOGGER = Logger.getLogger( SMSLoader.class );

    private static final class CloneRemover
    {
        final HashMap<String,SMS> map           = new HashMap<>();
        final ArrayList<SMS>      badClonesList = new ArrayList<>();

        public void remove( final List<SMS> smsList ) throws InconsistantSMSException
        {
            for( final SMS sms : smsList ) {
                remove( sms );
            }

            smsList.clear();
            LOGGER.info( "SMS (no clone) count: " + this.map.size() );

            smsList.addAll( this.badClonesList );
            this.badClonesList.clear();

            for( final SMS sms : this.map.values() ) {
                smsList.add( sms );
            }

            this.map.clear();
        }

        private void remove( final SMS sms ) throws InconsistantSMSException
        {
            final String  pdu = sms.getPdu();
            final SMS     old = this.map.get( pdu );

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
                    LOGGER.warn(
                        "** Inconsistant 'Time' :"
                            + old.getTime()
                            + " / "
                            + sms.getTime()
                            + " (Not real clone)"
                            );
                    this.badClonesList.add( sms );
                }

                if( ! old.getStorage().equals( sms.getStorage() ) ) {
                    throw new InconsistantSMSException( "Storage", old, sms );
                }

                if( ! old.getPdu().equals( sms.getPdu() ) ) {
                    throw new InconsistantSMSException( "PDU", old, sms );
                }
            }
            else {
                this.map.put( sms.getPdu(), sms );
            }
        }
    }

    private final List<SMS> smsList = new ArrayList<>();

    public SMSLoader()
    {
        //Empty
    }

    /**
     * @param file SMS File (CVS formated)
     * @throws IOException if any
     * @throws ParseException if any
     */
    public void addFile( final File file ) throws IOException, ParseException
    {
        load( file );

        LOGGER.info( "SMS count: " + this.smsList.size() );
    }

    private void load( final File file ) throws IOException, ParseException
    {
        LOGGER.info( "reading: " + file );

        try( SMSCVSReader r = new SMSCVSReader( file ) ) {
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
        new CloneRemover().remove( this.smsList );
    }


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
     * @throws IOException if any
     */
    public void save( final File outputFile ) throws IOException
    {
        try( final Writer w = new BufferedWriter( new FileWriter( outputFile ) ) ) {

            for( final SMS sms : this.smsList ) {
                sms.appendEntry( w );
                w.append( "\r\n" );
            }
        }
    }
}
