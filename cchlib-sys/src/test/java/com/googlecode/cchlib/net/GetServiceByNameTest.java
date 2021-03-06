package com.googlecode.cchlib.net;

import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.lang.UnsupportedSystemException;

public class GetServiceByNameTest
{
    @Test
    public void test_getServiceByName0() throws UnsupportedSystemException
    {
        GetServiceByName getServiceByName = new GetServiceByName();

        int port = getServiceByName.getServiceByName( "http", "tcp" );

        Assert.assertEquals( 80, port );
    }

    @Test
    public void test_getServiceByName1() throws UnsupportedSystemException
    {
        GetServiceByName getServiceByName = new GetServiceByName();

        int port = getServiceByName.getServiceByName( "shouldnotexist", Protocole.udp );

        Assert.assertEquals( -1, port );
    }

    @Test
    public void test_getServiceByName2() throws UnsupportedSystemException
    {
        GetServiceByName getServiceByName = new GetServiceByName();

        try {
            getServiceByName.getServiceByName( "http", "error" );
            
            Assert.fail();
            }
        catch( IllegalArgumentException e ) {
            // Ok !
            }
    }


}
