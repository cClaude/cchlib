package com.googlecode.cchlib.i18n;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

public class AutoI18nTest
{
    private static final String SHOULD_NOT_BE_CHANGED = "com.googlecode.cchlib.i18n.AutoI18n.disabled";

    @Test
    public void testCONSTANTS()
    {
        final String actual   = AutoI18nConfig.DISABLE_PROPERTIES;
        final String expected = AutoI18n.class.getName() + ".disabled";

        assertThat( actual )
            .as( "Bad AutoI18n.DISABLE_PROPERTIES" )
            .isEqualTo( SHOULD_NOT_BE_CHANGED );

        assertThat( actual ).as( "Bad AutoI18n.DISABLE_PROPERTIES" ).isEqualTo( expected );
    }

}
