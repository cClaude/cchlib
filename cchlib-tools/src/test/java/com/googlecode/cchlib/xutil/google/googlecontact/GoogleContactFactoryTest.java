package com.googlecode.cchlib.xutil.google.googlecontact;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.xutil.google.googlecontact.GoogleContactFactory;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContacAnalyserException;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleContactFactoryTest implements Data {

    @Test
    public void testDataBasic()
    {
        // validate data header
        Assert.assertEquals( HEADERS_BASIC.length, new HashSet<>( Arrays.asList( HEADERS_BASIC ) ).size() );

        // validate data entry
        Assert.assertEquals( HEADERS_BASIC.length, ENTRY_BASIC.length );
    }

    @Test
    public void testData2Mails()
    {
        // validate data header
        Assert.assertEquals( HEADERS_2MAILS.length, new HashSet<>( Arrays.asList( HEADERS_2MAILS ) ).size() );

        // validate data entry
        Assert.assertEquals( HEADERS_2MAILS.length, ENTRY_2MAILS.length );
    }

    @Test
    public void testData1()
    {
        // validate data header
        Assert.assertEquals( HEADERS0.length, new HashSet<>( Arrays.asList( HEADERS0 ) ).size() );
    }

    @Test
    public void testData2()
    {
        // validate data header
        Assert.assertEquals( HEADERS1.length, new HashSet<>( Arrays.asList( HEADERS1 ) ).size() );

        // validate data entry
        Assert.assertEquals( HEADERS1.length, ENTRY1.length );
    }

    @Test
    public void test_newGoogleContact_1() throws GoogleContacAnalyserException
    {
        GoogleContactFactory builder = new GoogleContactFactory( HEADERS1, false );

        final GoogleContact googleContact = builder.newGoogleContact( ENTRY1 );

        Assert.assertNotNull( googleContact );

        Assert.assertEquals( "2019-01-01", googleContact.getBirthday() );

        final Collection<BasicEntry> customFields = googleContact.getCustomFields();
        Assert.assertEquals( 1,  customFields.size()  );

        final BasicEntry firstCustomField = customFields.iterator().next();
        Assert.assertEquals( "Custom1 Type",  firstCustomField.getType()  );
        Assert.assertEquals( "Custom1 Value", firstCustomField.getValue() );

        final Collection<BasicEntry> emails = googleContact.getEmails();
        Assert.assertEquals( 2,  emails.size() );

        final Iterator<BasicEntry> emailsIterator = emails.iterator();
        final BasicEntry firstEmail = emailsIterator.next();
        Assert.assertEquals( "Home",  firstEmail.getType()  );
        Assert.assertEquals( "Email2@X.X", firstEmail.getValue() );

        final BasicEntry secondEmail = emailsIterator.next();
        Assert.assertEquals( "* Work",  secondEmail.getType()  );
        Assert.assertEquals( "Email1@X.X", secondEmail.getValue() );
    }

    @Test
    public void test_newGoogleContact_2MAILS() throws GoogleContacAnalyserException
    {
        GoogleContactFactory builder = new GoogleContactFactory( HEADERS_2MAILS, true );

        final GoogleContact googleContact = builder.newGoogleContact( ENTRY_2MAILS );

        Assert.assertNotNull( googleContact );

        Assert.assertEquals( null, googleContact.getBirthday() ); // Not Set
        Assert.assertEquals( "With 2 Mails", googleContact.getName() );

        final Collection<BasicEntry> emails = googleContact.getEmails();
        Assert.assertEquals( 2,  emails.size() );

        final Iterator<BasicEntry> emailsIterator = emails.iterator();
        final BasicEntry firstEmail = emailsIterator.next();
        Assert.assertEquals( "Home",  firstEmail.getType()  );
        Assert.assertEquals( "email2", firstEmail.getValue() );

        final BasicEntry secondEmail = emailsIterator.next();
        Assert.assertEquals( "* Work",  secondEmail.getType()  );
        Assert.assertEquals( "email1", secondEmail.getValue() );
    }
}
