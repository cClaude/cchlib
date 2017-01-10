package com.googlecode.cchlib.i18n;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;
import com.googlecode.cchlib.i18n.core.AutoI18n;

public class AutoI18nTest
{
    @Test
    public void testCONSTANTS()
    {
        final String actual   = AutoI18nConfig.DISABLE_PROPERTIES;
        final String expected = AutoI18n.class.getName() + ".disabled";

        assertThat( actual ).as( "Bad AutoI18n.DISABLE_PROPERTIES" ).isEqualTo( expected );
    }

}
