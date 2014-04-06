package com.googlecode.cchlib.tools.phone.recordsorter.conf.google;

import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.AbstractReadConfig;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.DefaultContact;
import com.googlecode.cchlib.xutil.google.googlecontact.GoogleContacts;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleConfigImpl extends AbstractReadConfig
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( GoogleConfigImpl.class );
    private int noNameIndex = 0;

    public GoogleConfigImpl setList( final List<GoogleContact> list )
    {
        internalContactCollection().clear();

        for( final GoogleContact googleContact : list ) {
            final DefaultContact contact = new DefaultContact();

            contact.setName( getValidName( googleContact ) );
            contact.setBackup( isBackup( googleContact ) );

//            for( BasicEntry phone : googleContact.getPhones() ) {
//                final String rawNumber = phone.getValue();
//
//                if( !rawNumber.isEmpty() ) {
//
//                    for( String singleNumber : GoogleContacts.toValues( rawNumber ) ) {
//                        final String normalizeNumber = normalizeNumber( singleNumber );
//
//                        if( !normalizeNumber.isEmpty() ) {
//                            contact.addNumber( normalizeNumber );
//                        }
//                    }
//                 }
                for( final String singleNumber : GoogleContacts.getPhoneValues( googleContact) ) {
                     final String normalizeNumber = normalizeNumber( singleNumber );

                    if( !normalizeNumber.isEmpty() ) {
                        contact.addNumber( normalizeNumber );
                    }
            }

            internalContactCollection().add( contact );
        }

        return this;
    }

    private String getValidName( final GoogleContact googleContact )
    {
        final String name = googleContact.getName();

        if( name != null ) {
            return name;
        }

        return "#NONAME#" + this.noNameIndex++;
    }


    private String normalizeNumber( final String rawNumber )
    {
        String normalizeNumber = rawNumber.replaceAll("\\s+","");

        normalizeNumber = normalizeNumber.replaceAll("\\.","");

        if( normalizeNumber.startsWith( "+" ) ) {
            normalizeNumber = "00" + normalizeNumber.substring( 1 );
        }

        LOGGER.info( "normalizeNumber[" + normalizeNumber + "] <- [" + rawNumber + ']' );

        return normalizeNumber; // TODO Auto-generated method stub
    }

    private boolean isBackup( final GoogleContact googleContact )
    {
        return true;  // TODO Auto-generated method stub
    }

    @Override
    public Contact newContact() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Contact addContact( final Contact contact ) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}
