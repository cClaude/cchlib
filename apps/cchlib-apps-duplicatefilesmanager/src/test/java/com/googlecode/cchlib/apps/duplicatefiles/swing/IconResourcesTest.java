package com.googlecode.cchlib.apps.duplicatefiles.swing;

import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.Icon;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.lang.Objects;
import com.googlecode.cchlib.lang.reflect.Methods;

/**
 * The class <code>MyStaticResourcesTest</code> contains tests for the class <code>{@link IconResources}</code>.
 */
public class IconResourcesTest
{
    private static final Logger LOGGER = Logger.getLogger( IconResourcesTest.class );
    private List<Method>  staticMethodsList;
    private List<Method>  methodsList;
    private IconResources iconResourcesIntance;

    @Test
    public void test_AllStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        for( final Method method : this.staticMethodsList ) {
            LOGGER.info( "m: " + method );
            final Object methodResult = method.invoke( null, Objects.emptyArray() );

            LOGGER.info( "m: " + method + " => " + methodResult );

            if( methodResult instanceof IconResources ) {
                LOGGER.info( "IconResources: " + methodResult );
                LOGGER.info( "this.iconResourcesIntance: " + this.iconResourcesIntance );

               assertThat( methodResult == this.iconResourcesIntance ).isTrue();
            } else {
                Assert.fail();
            }
        }
    }

      @Test
      public void test_AllNoneStatic() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
      {

          for( final Method method : this.methodsList ) {
              LOGGER.info( "m: " + method );

              if( method.getReturnType().equals( Icon.class ) ) {
                  final Object o = method.invoke( this.iconResourcesIntance, Objects.emptyArray() );

                  LOGGER.info( "m: " + method + " => " + o );
                  final Icon result = (Icon)o;

                  // add additional test code here
                  assertThat( result ).isNotNull();
                  assertThat( result.getIconWidth() ).isEqualTo( 16 );
                  assertThat( result.getIconHeight() ).isEqualTo( 16 );
                  }
              }
      }

    @Before
    public void setUp() throws Exception
    {
        this.staticMethodsList    = Methods.getStaticMethods( IconResources.class );
        this.methodsList          = Methods.getPublicMethods( IconResources.class );
        this.iconResourcesIntance = IconResources.getInstance();
    }
}
