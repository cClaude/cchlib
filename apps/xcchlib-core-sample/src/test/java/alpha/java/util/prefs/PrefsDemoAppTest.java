package alpha.java.util.prefs;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

public class PrefsDemoAppTest
{
    @Test
    public void test_none()
    {
        final PrefsDemoApp instance = new PrefsDemoApp();

        assertThat( instance.getOperationName() ).isEqualTo( "GET" );
    }

    @Test
    public void test_put()
    {
        final PrefsDemoApp instance1 = new PrefsDemoApp( "put", "Test" );

        assertThat( instance1.getOperationName() ).isEqualTo( "PUT" );
        assertThat( instance1.getResult() ).isEqualTo( "Test" );

        final PrefsDemoApp instance2 = new PrefsDemoApp( "get", "export" );

        assertThat( instance2.getOperationName() ).isEqualTo( "GET" );
        assertThat( instance2.getResult() ).isEqualTo( "Test" );

        final PrefsDemoApp instance3 = new PrefsDemoApp( "clear" );

        assertThat( instance3.getOperationName() ).isEqualTo( "CLEAR" );
        assertThat( instance3.getResult() ).isNull();
    }

}
