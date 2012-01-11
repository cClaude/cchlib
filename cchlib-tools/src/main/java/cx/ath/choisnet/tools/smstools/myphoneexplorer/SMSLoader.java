package cx.ath.choisnet.tools.smstools.myphoneexplorer;

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
    private final static Logger slogger = Logger.getLogger(SMSLoader.class);
    private List<SMS> smsList = new ArrayList<SMS>();

    public SMSLoader()
    {
        //Empty
    }

    /**
     * @param args
     * @throws IOException 
     * @throws ParseException 
     */
    public void addFile( File f ) throws IOException, ParseException
    {
        load( f );

        slogger.info( "SMS count: " + smsList.size() );
    }

    private void load(final File f) throws IOException, ParseException
    {
        slogger.info( "reading: " + f );

        SMSCVSReader r = new SMSCVSReader( f );
            
        smsList.addAll( r.getSMSList() );
        
        r.close();
    }

    /**
     * Remove clone SMS from list
     * 
     * @throws InconsistantSMSException 
     */
    public void removeClones() throws InconsistantSMSException
    {
        slogger.info( "removeClones()" );

        // Remove clone
        HashMap<String,SMS> map             = new HashMap<String,SMS>();
        ArrayList<SMS>      badClonesList   = new ArrayList<SMS>();
        
        for(SMS sms:smsList) {
            String  pdu = sms.getPdu();
            SMS     old = map.get( pdu );
            
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
                    slogger.warn(
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

        smsList.clear();
        slogger.info( "SMS (no clone) count: " + map.size() );

        smsList.addAll( badClonesList );
        badClonesList.clear();

        for(SMS sms:map.values()) {
            smsList.add( sms );
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
        Collections.sort( smsList, new SMSDateComparator() );

        slogger.info( "SMS (sorted list) count: " + smsList.size() );
    }

    /**
     * Save SMS to file
     * 
     * @param outputFile output File
     * @throws IOException
     */
    public void save(File outputFile) throws IOException
    {
        Writer w = new BufferedWriter(
                new FileWriter( outputFile )
                );

        for(SMS sms:smsList) {
            sms.appendEntry( w );
            w.append( "\r\n" );
        }

        w.close();
    }
}
