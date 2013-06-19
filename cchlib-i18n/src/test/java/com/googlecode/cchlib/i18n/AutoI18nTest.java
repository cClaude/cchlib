package com.googlecode.cchlib.i18n;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;

public class AutoI18nTest 
{
    @Test
    public void testCONSTANTS()
    {
        final String actual   = AutoI18n.DISABLE_PROPERTIES;
        final String expected = AutoI18n.class.getName() + ".disabled";

        Assert.assertEquals( "Bad AutoI18n.DISABLE_PROPERTIES", expected, actual );
        assertThat( actual ).isEqualTo( expected );
    }

}
