package com.googlecode.cchlib.tools.sortfiles.impl;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.tools.sortfiles.FileParserException;
import com.googlecode.cchlib.tools.sortfiles.ParsedFilename;
import com.googlecode.cchlib.tools.sortfiles.RecordFileParser;

public class DefaultRecordFilerTest
{
    private RecordFileParser parser = new DefaultRecordFileParser();
    private DefaultRecordFileParser defaultRecordFileParser = new DefaultRecordFileParser();

    @Test
    public void parse1Test() throws FileParserException
    {
        File file = new File( "2013_09_05_12_52_33426731838_In.3gp" );

        ParsedFilename actual = parser.parse( file );

        Assert.assertEquals( "33426731838" , actual.getRawPhoneNumber() );
        Assert.assertEquals( "2013_09_05_12_52" , actual.getRawDate() );
   }

    @Test
    public void parse2Test() throws FileParserException
    {
        File file = new File( "2013_08_16_11_01_0761652621_Out.3gp" );

        ParsedFilename actual = parser.parse( file );

        Assert.assertEquals( "0761652621" , actual.getRawPhoneNumber() );
        Assert.assertEquals( "2013_08_16_11_01" , actual.getRawDate() );
   }

    @Test
    public void buildDateTest() throws FileParserException
    {
        String actual = defaultRecordFileParser.buildRawDate( "2013_09_05_12_52_33426731838_In.3gp");

        Assert.assertEquals( "2013_09_05_12_52" , actual );
   }

    @Test
    public void buildPhoneNumber1Test() throws FileParserException
    {
        String actual = defaultRecordFileParser.buildRawPhoneNumber( "2013_09_05_12_52_33426731838_In.3gp" );

        Assert.assertEquals( "33426731838" , actual );
   }

    @Test
    public void buildPhoneNumber2Test() throws FileParserException
    {
        String actual = defaultRecordFileParser.buildRawPhoneNumber( "2013_08_16_11_01_0761652621_Out.3gp" );

        Assert.assertEquals( "0761652621" , actual );
   }
}
