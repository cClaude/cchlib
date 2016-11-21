package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.File;
import java.util.regex.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;

public class PhoneRecordSorterFileVisitorTest {

    private PhoneRecordSorterFileVisitor instance;

    @Before
    public void setup() throws CreateDestinationFolderException
    {
        final Config config             = Mockito.mock( Config.class );
        final File   destinationFolders = new File("");

        instance = new PhoneRecordSorterFileVisitor(config, destinationFolders);
    }

    @Test
    public void test_getPhoneNumber1()
    {
        final String number = instance.getPhoneNumber( "2013_11_08_13_40_12345678_Out.3gp" );

        Assert.assertEquals( "12345678", number );
    }

    @Test
    public void test_getPhoneNumber2()
    {
        final String number = instance.getPhoneNumber( "2013_11_08_13_40_hidden_In.3gp" );

        Assert.assertEquals( "hidden", number );
    }

    @Test
    public void test_getPhoneNumberMatcher()
    {
        final Matcher matcher = instance.getPhoneNumberMatcher( "2013_11_08_13_40_12345678_In.3gp" );

        Assert.assertEquals( 3, matcher.groupCount() );
    }
}
