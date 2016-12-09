package com.googlecode.cchlib.jdbf.userpackage;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import com.googlecode.cchlib.jdbf.DBFField;
import com.googlecode.cchlib.jdbf.DBFType;

public class DBFTypeTest
{
    @Test
    public void test_types()
    {
        final Set<Byte> valueTypes = new HashSet<>();
        final DBFType[] values     = DBFType.values();

        for( final DBFType dbfType : values ) {
            final Byte type = dbfType.getType();

            assertThat( valueTypes ).doesNotContain( type );

            valueTypes.add( type );
        }
    }

    @Test
    public void test_get()
    {
        final DBFType type = DBFType.get( DBFField.FIELD_TYPE_C );

        assertThat( type ).isNotNull();
        assertThat( type.getType() ).isEqualTo( DBFField.FIELD_TYPE_C  );
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_get_fail()
    {
        DBFType.get( (byte)-1 );
    }
}
