package com.googlecode.cchlib.xutil.google.googlecontact;

import static org.junit.Assume.assumeTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleContactsTest
{
    private static final String FULL_PATH = "/com/googlecode/cchlib/xutil/google/googlecontact/";

    @Test
    public void test_createGoogleContacts_1() throws FileNotFoundException, IOException
    {
        // FIXME use something more standard...
        final File file = FileHelper.getUserHomeDirectoryFile(
                "Dropbox/#CallRecorder/#Config/google-contacts.csv"
                );

        assumeTrue( file.exists() );

        final List<GoogleContact> contacts = GoogleContacts.createGoogleContacts( file );

        Assert.assertNotNull( contacts );
        Assert.assertEquals( 583, contacts.size() );

        System.out.println( "########################" );
        System.out.println( GoogleContacts.toJSON( contacts ).toPrettyString() );
        System.out.println( "########################" );

//        for( final GoogleContact googleContact : GoogleContacts.all( contacts, findContactWithoutNameAndMail() ) ) {
//            System.out.println( "# NON NAME >>> ##################" );
//            System.out.println( GoogleContacts.toJSON( googleContact ).toString() );
//            System.out.println( "# NON NAME <<< ##################" );
//        }
        contacts.stream().filter( c-> c.getName() == null ).forEach( c -> {
            System.out.println( "# NO NAME >>> " + GoogleContacts.toJSON( c ).toString() );
        } );

        contacts.stream().filter( c-> ((c.getName() == null) && (c.getEmails().size() == 0)) ).forEach( c -> {
            System.out.println( "# NO NAME & NO MAIL >>> " +GoogleContacts.toJSON( c ).toString() );
        } );

        contacts.parallelStream().filter( c -> c.getEmails().size() > 2 ).forEach( c -> System.out.println(" AA" + c.getName() ) );

        final long count = contacts.stream().filter( c -> c.getEmails().size() > 2 ).count();
        System.out.println(" totola= " + count );
    }

//    private Selectable<GoogleContact> findContactWithoutNameAndMail()
//    {
//        return new Selectable<GoogleContact>(){
//            @Override
//            public boolean isSelected( final GoogleContact googleContact )
//            {
//                return googleContact.getName() == null && googleContact.getEmails().size() == 0;
//            }};
//    }
//
//    private Selectable<GoogleContact> findContactWithoutName()
//    {
//        return new Selectable<GoogleContact>(){
//            @Override
//            public boolean isSelected( final GoogleContact googleContact )
//            {
//                return googleContact.getName() == null;
//            }};
//    }

    @Test
    public void test_createGoogleContacts_2() throws IOException
    {
        final String resourceName = FULL_PATH + "GoogleContact1bis.csv";

        try(final InputStream inStream = this.getClass().getResourceAsStream( resourceName )) {
            Assert.assertNotNull( "File not found : " + resourceName, inStream );

            final List<GoogleContact> contacts = GoogleContacts.createGoogleContacts( inStream );

            Assert.assertNotNull( contacts );
            Assert.assertEquals( 1, contacts.size() );
        }
    }

    @Test
    public void test_createGoogleContacts_3() throws IOException
    {
        final String resourceName = FULL_PATH + "GoogleContactTest3.csv";

        try(final InputStream inStream = this.getClass().getResourceAsStream( resourceName )) {
            Assert.assertNotNull( "File not found : " + resourceName, inStream );

            final List<GoogleContact> contacts = GoogleContacts.createGoogleContacts( inStream );

            Assert.assertNotNull( contacts );
            Assert.assertEquals( 3, contacts.size() );

//            System.out.println( "########################" );
//            System.out.println( GoogleContacts.toJSON( contacts ).toPrettyString() );
//            System.out.println( "########################" );
        }
    }
}
