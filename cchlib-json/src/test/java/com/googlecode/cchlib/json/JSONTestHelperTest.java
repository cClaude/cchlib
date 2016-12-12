package com.googlecode.cchlib.json;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;

public class JSONTestHelperTest
{
    @Test(expected=JSONHelperException.class)
    public void crash_empty_bean() throws IOException, JSONHelperException
    {
        final EmptyBean original = new EmptyBean();

        JSONTestHelper.serializationOverJSON( original, EmptyBean.class );
    }

    @Test
    public void basic_test() throws IOException, JSONHelperException
    {
        final MyBean original = new MyBean();
        final MyBean actual   = JSONTestHelper.serializationOverJSON( original, MyBean.class );

        assertThat( actual ).isNotNull().isInstanceOf( MyBean.class );
        assertThat( actual.getCollection() ).isNull();
        assertThat( actual.getInterger() ).isEqualTo( 0 );
        assertThat( actual.getString() ).isNull();
    }

    @Test
    public void test_with_values() throws IOException, JSONHelperException
    {
        final MyBean original = new MyBean()
                                .setInterger( 123 )
                                .setString( "bla" )
                                .setCollection( createCollection() );
        final MyBean actual   = JSONTestHelper.serializationOverJSON( original, MyBean.class );

        assertThat( actual ).isNotNull().isInstanceOf( MyBean.class );
        assertThat( actual.getCollection() ).contains(
                new BeanInCollection().setBeanName( "Entry2" ),
                new BeanInCollection().setBeanName( "Entry1" )
                );
        assertThat( actual.getInterger() ).isEqualTo( 123 );
        assertThat( actual.getString() ).isEqualTo( "bla" );
    }

    private Collection<BeanInCollection> createCollection()
    {
        final ArrayList<BeanInCollection> collection = new ArrayList<>();

        collection.add( new BeanInCollection().setBeanName( "Entry1" ) );
        collection.add( new BeanInCollection().setBeanName( "Entry2" ) );

        return collection;
    }

}
