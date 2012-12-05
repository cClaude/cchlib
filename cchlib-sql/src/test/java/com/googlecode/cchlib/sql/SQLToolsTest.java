package com.googlecode.cchlib.sql;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.Const;

/**
 *
 */
public class SQLToolsTest
{
    private static Logger slogger = Logger.getLogger( SQLToolsTest.class );

    @Test
    public void test_parseFieldValue1()
    {
        final String s = "T1: AbCdEfGhIj";

        test_parseFieldValue( s, s );
    }

    @Test
    public void test_parseFieldValue2()
    {
        test_parseFieldValue( "T2: L\\'élève", "T2: L'élève" );
    }

    @Test
    public void test_parseFieldValue21()
    {
        test_parseFieldValue( "T21: L\\'élève L\\'élève", "T21: L'élève L'élève" );
    }

    @Test
    public void test_parseFieldValue3()
    {
        test_parseFieldValue( "T3: Le \\\\bô\\\\ texte", "T3: Le \\bô\\ texte" );
    }

    @Test
    public void test_parseFieldValue4()
    {
        test_parseFieldValue(
            "T4: 1\\\\2\\\\3\\\\4\\\\5\\\\6",
            "T4: 1\\2\\3\\4\\5\\6"
            );
    }

    @Test
    public void test_parseFieldValue5()
    {
        test_parseFieldValue(
            "T5: L\\'élève est \\\\bô\\\\!",
            "T5: L'élève est \\bô\\!"
            );
    }

    @Test
    public void test_parseFieldValue6()
    {
        test_parseFieldValue(
            "T6: -\\'-\\\\-\\'-\\\\-\\'-\\\\-\\'-\\\\-\\'-\\\\-\\'-",
            "T6: -'-\\-'-\\-'-\\-'-\\-'-\\-'-"
            );
    }

    @Test
    public void test_parseFieldValue7()
    {
        test_parseFieldValue(
            "category 4 (\\'en\\' only)",
            "category 4 ('en' only)"
            );
    }

    @Test
    public void test_parseFieldValue8()
    {
        test_parseFieldValue(
            "category 4 description(\\'en\\' only)",
            "category 4 description('en' only)"
            );
    }

    @Test
    public void test_parseFieldValue9()
    {
        test_parseFieldValue( Const.EMPTY_STRING, Const.EMPTY_STRING );
    }

    @Test
    public void test_parseFieldValue10()
    {
        test_parseFieldValue( " ", " " );
    }

    @Test
    public void test_parseFieldValue11()
    {
        test_parseFieldValue( "a \\'comment\\' b", "a 'comment' b" );
    }

    @Test
    public void test_parseFieldValue11Begin()
    {
        test_parseFieldValue( "\\'comment\\' b", "'comment' b" );
    }

    @Test
    public void test_parseFieldValue11End()
    {
        test_parseFieldValue( "a \\'comment\\'", "a 'comment'" );
    }

    @Test
    public void test_parseFieldValue11Both()
    {
        test_parseFieldValue( "\\'comment\\'", "'comment'" );
    }

    private static void test_parseFieldValue( String expStr, String s )
    {
        String r = SQLTools.parseFieldValue( s );

        slogger.info( "s      = [" + s + "]" );
        slogger.info( "r      = [" + r + "]" );
        slogger.info( "expStr = [" + expStr + "]" );

        Assert.assertEquals("Bad result for: [" + s + "]",expStr,r);
    }

}
