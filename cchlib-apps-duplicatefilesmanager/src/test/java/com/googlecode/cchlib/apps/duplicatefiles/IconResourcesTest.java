// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles;

import com.googlecode.cchlib.lang.Objects;
import com.googlecode.cchlib.lang.reflect.Methods;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.Icon;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The class <code>MyStaticResourcesTest</code> contains tests for the class <code>{@link IconResources}</code>.
 */
public class IconResourcesTest
{
    private static final Logger LOGGER = Logger.getLogger( IconResourcesTest.class );
    private List<Method> staticMethodsList;
    private List<Method> methodsList;

    @Test
    @Ignore// No more that way (keep code as exemple)
    public void test_AllStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        for( Method method : staticMethodsList ) {
            LOGGER.info( "m: " + method );
            Object o = method.invoke( null, Objects.emptyArray() );

            LOGGER.info( "m: " + method + " => " + o );
            Icon result = (Icon)o;

            // add additional test code here
            Assertions.assertThat( result ).isNotNull();
            Assertions.assertThat( result.getIconWidth() ).isEqualTo( 16 );
            Assertions.assertThat( result.getIconHeight() ).isEqualTo( 16 );
            }
    }

      @Test
      public void test_AllNoneStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
      {
          IconResources intance = IconResources.getInstance();

          for( Method method : methodsList ) {
              LOGGER.info( "m: " + method );
              
              if( method.getReturnType().equals( Icon.class ) ) {
                  Object o = method.invoke( intance, Objects.emptyArray() );

                  LOGGER.info( "m: " + method + " => " + o );
                  Icon result = (Icon)o;

                  // add additional test code here
                  Assertions.assertThat( result ).isNotNull();
                  Assertions.assertThat( result.getIconWidth() ).isEqualTo( 16 );
                  Assertions.assertThat( result.getIconHeight() ).isEqualTo( 16 );
                  }
              }
      }

    @Before
    public void setUp() throws Exception
    {
        staticMethodsList = Methods.getStaticMethods( IconResources.class );
        methodsList       = Methods.getPublicMethods( IconResources.class );
    }

    @After
    public void tearDown() throws Exception
    {
    }


}
