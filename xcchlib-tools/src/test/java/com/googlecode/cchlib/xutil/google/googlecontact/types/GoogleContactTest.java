package com.googlecode.cchlib.xutil.google.googlecontact.types;

import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.xutil.google.googlecontact.types.AddressEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;
import com.googlecode.cchlib.xutil.google.googlecontact.types.IMEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.OrganizationEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;

public class GoogleContactTest {

    @Test(expected=RuntimeException.class)
    public void test_newGoogleContactType_Error()
    {
        GoogleContact.newGoogleContactType( String.class );
    }

    @Test
    public void test_newGoogleContactType_BasicEntry()
    {
        final GoogleContactType newGoogleContactType = GoogleContact.newGoogleContactType( BasicEntry.class );

        Assert.assertTrue( newGoogleContactType instanceof BasicEntry );
        Assert.assertFalse( newGoogleContactType instanceof IMEntry );
        Assert.assertFalse( newGoogleContactType instanceof OrganizationEntry );
        Assert.assertFalse( newGoogleContactType instanceof AddressEntry );
        Assert.assertFalse( newGoogleContactType instanceof GoogleContact );
   }

    @Test
    public void test_newGoogleContactType_IMEntry()
    {
        final GoogleContactType newGoogleContactType = GoogleContact.newGoogleContactType( IMEntry.class );

        Assert.assertTrue( newGoogleContactType instanceof BasicEntry );
        Assert.assertTrue( newGoogleContactType instanceof IMEntry );
        Assert.assertFalse( newGoogleContactType instanceof OrganizationEntry );
        Assert.assertFalse( newGoogleContactType instanceof AddressEntry );
        Assert.assertFalse( newGoogleContactType instanceof GoogleContact );
    }

    @Test
    public void test_newGoogleContactType_OrganizationEntry()
    {
        final GoogleContactType newGoogleContactType = GoogleContact.newGoogleContactType( OrganizationEntry.class );

        Assert.assertFalse( newGoogleContactType instanceof BasicEntry );
        Assert.assertFalse( newGoogleContactType instanceof IMEntry );
        Assert.assertTrue( newGoogleContactType instanceof OrganizationEntry );
        Assert.assertFalse( newGoogleContactType instanceof AddressEntry );
        Assert.assertFalse( newGoogleContactType instanceof GoogleContact );
    }

    @Test
    public void test_newGoogleContactType_AddressEntry()
    {
        final GoogleContactType newGoogleContactType = GoogleContact.newGoogleContactType( AddressEntry.class );

        Assert.assertFalse( newGoogleContactType instanceof BasicEntry );
        Assert.assertFalse( newGoogleContactType instanceof IMEntry );
        Assert.assertFalse( newGoogleContactType instanceof OrganizationEntry );
        Assert.assertTrue( newGoogleContactType instanceof AddressEntry );
        Assert.assertFalse( newGoogleContactType instanceof GoogleContact );
    }

    @Test(expected=RuntimeException.class)
    public void test_newGoogleContactType_GoogleContact_Error()
    {
        GoogleContact.newGoogleContactType( new GoogleContactType(){
            private static final long serialVersionUID = 1L;}.getClass() );
    }
}
