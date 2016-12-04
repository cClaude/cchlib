package com.googlecode.cchlib.xutil.google.googlecontact;

import static com.googlecode.cchlib.xutil.google.googlecontact.assertions.GoogleContactAssertions.assertThat;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.junit.Test;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContacAnalyserException;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleContactFactoryTest extends Data
{
    @Test
    public void testDataBasic()
    {
        // validate data header
        assertEquals(
            HEADERS_BASIC.length,
            new HashSet<>( Arrays.asList( HEADERS_BASIC ) ).size()
            );

        // validate data entry
        assertEquals( HEADERS_BASIC.length, ENTRY_BASIC.length );
    }

    @Test
    public void testData2Mails()
    {
        // validate data header
        assertEquals(
            HEADERS_2MAILS.length,
            new HashSet<>( Arrays.asList( HEADERS_2MAILS ) ).size()
            );

        // validate data entry
        assertEquals( HEADERS_2MAILS.length, ENTRY_2MAILS.length );
    }

    @Test
    public void testData1()
    {
        // validate data header
        assertEquals(
            HEADERS0.length,
            new HashSet<>( Arrays.asList( HEADERS0 ) ).size()
            );
    }

    @Test
    public void testData2()
    {
        // validate data header
        assertEquals(
            HEADERS1.length,
            new HashSet<>( Arrays.asList( HEADERS1 ) ).size()
            );

        // validate data entry
        assertEquals( HEADERS1.length, ENTRY1.length );
    }

    @Test
    public void test_newGoogleContact_1() throws GoogleContacAnalyserException
    {
        final GoogleContactFactory builder      = new GoogleContactFactory( HEADERS1, false );
        final GoogleContact       googleContact = builder.newGoogleContact( ENTRY1 );

        assertThat( googleContact ).isNotNull();
        assertThat( googleContact ).hasBirthday( "2319-01-01" );

        final Collection<BasicEntry> customFields = googleContact.getCustomFields();

        assertThat( customFields ).hasSize( 1 );

        final BasicEntry firstCustomField = customFields.iterator().next();

        assertThat( firstCustomField )
            .hasType( "Custom1 Type" )
            .hasValue( "Custom1 Value" );

        final Collection<BasicEntry> emails = googleContact.getEmails();

        assertThat( emails ).hasSize( 2 );

        final Iterator<BasicEntry> emailsIterator = emails.iterator();
        final BasicEntry firstEmail = emailsIterator.next();

        assertThat( firstEmail ).hasType( "Home" );
        assertThat( firstEmail ).hasValue( "Email2@X.X" );

        final BasicEntry secondEmail = emailsIterator.next();

        assertThat( secondEmail ).hasType( "* Work" );
        assertThat( secondEmail ).hasValue( "Email1@X.X" );
    }

    @Test
    public void test_newGoogleContact_2MAILS() throws GoogleContacAnalyserException
    {
        final GoogleContactFactory builder = new GoogleContactFactory( HEADERS_2MAILS, true );
        final GoogleContact googleContact = builder.newGoogleContact( ENTRY_2MAILS );

        assertThat( googleContact ).isNotNull();

        assertThat( googleContact.getBirthday() ).isNull(); // Not Set
        assertThat( googleContact ).hasName( "With 2 Mails" );

        final Collection<BasicEntry> emails = googleContact.getEmails();

        assertThat( emails ).hasSize( 2 );

        final Iterator<BasicEntry> emailsIterator = emails.iterator();
        final BasicEntry firstEmail = emailsIterator.next();

        assertThat( firstEmail ).hasType( "Home" ).hasValue( "email2" );

        final BasicEntry secondEmail = emailsIterator.next();

        assertThat( secondEmail ).hasType( "* Work" ).hasValue( "email1" );
    }
}
