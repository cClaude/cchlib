package com.googlecode.cchlib.apps.duplicatefiles.swing;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.Icon;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.lang.Objects;
import com.googlecode.cchlib.lang.reflect.Methods;

/**
 * This class contains tests for the class {@link IconResources}.
 */
public class IconResourcesTest
{
    private static final Logger LOGGER = Logger.getLogger( IconResourcesTest.class );

    @Test
    public void test_AllStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        final IconResources iconResourcesGetIntance = IconResources.getInstance();

        final List<Method> staticMethodsList = Methods.getStaticMethods( IconResources.class );

        assertThat( staticMethodsList ).hasSize( 1 );

        final Method method = staticMethodsList.get( 0 );

        LOGGER.info( "method: " + method );
        final Object methodResult = method.invoke( null, Objects.emptyArray() );

        LOGGER.info( "method: " + method + " => " + methodResult );
        LOGGER.info( "this.iconResourcesIntance: " + iconResourcesGetIntance );

        assertThat( methodResult ).isInstanceOf( IconResources.class );
        assertThat( methodResult == iconResourcesGetIntance ).isTrue();
    }

    @Test
    public void test_AllNoneStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        final IconResources iconResourcesGetIntance = IconResources.getInstance();

        testIconResourcesMethodsResults( iconResourcesGetIntance );
    }

    @Test
    public void test_serialization()
        throws  IllegalAccessException,
                IllegalArgumentException,
                InvocationTargetException,
                ClassNotFoundException,
                IOException
    {
        final IconResources iconResourcesGetIntance = IconResources.getInstance();
        final IconResources copy = SerializableHelper.clone( iconResourcesGetIntance, IconResources.class );

        // This is a copy (test serialization)
        assertThat( iconResourcesGetIntance == copy ).isFalse();

        testIconResourcesMethodsResults( copy );
    }

    private void testIconResourcesMethodsResults( final IconResources iconResources )
            throws  IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException
    {
        for( final Method method : Methods.getPublicMethods( IconResources.class ) ) {
            LOGGER.info( "method: " + method );

            if( method.getReturnType().equals( Icon.class ) ) {
                final Object result = method.invoke( iconResources, Objects.emptyArray() );

                LOGGER.info( "method: " + method + " => " + result );

                assertThat( result ).isNotNull();
                assertThat( result ).isInstanceOf( Icon.class );

                final Icon actual = (Icon)result;

                // add additional test code here
                assertThat( actual.getIconWidth() ).isEqualTo( 16 );
                assertThat( actual.getIconHeight() ).isEqualTo( 16 );
            }
        }
    }
}
