package com.googlecode.cchlib.awt;

import static org.fest.assertions.api.Assertions.assertThat;
import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The class {@code ColorsTest} contains tests for the class {@link Colors}.
 *
 * @version $Revision: 1.0 $
 */
public class ColorsTest
{
    private static final Logger LOGGER = Logger.getLogger( ColorsTest.class );

    /**
     * Run the Color toColor() method test.
     */
    @Test
    public void testToColor_1()
        throws Exception
    {
        final Colors fixture = Colors.aliceblue;

        final Color result = fixture.toColor();

        // add additional test code here
        Assert.assertNotNull(result);
        Assert.assertEquals("java.awt.Color[r=240,g=248,b=255]", result.toString());
        Assert.assertEquals(255, result.getAlpha());
        Assert.assertEquals(240, result.getRed());
        Assert.assertEquals(255, result.getBlue());
        Assert.assertEquals(248, result.getGreen());
        Assert.assertEquals(-984833, result.getRGB());
        Assert.assertEquals(1, result.getTransparency());
    }


    /**
     * Run the Color toColor() method test.
     */
    @Test
    public void testColors_vs_Color()
        throws Exception
    {
        final Class<Color> colorClass = Color.class;

        for( final Field f : colorClass.getFields() ) {
            if( f.getType().isAssignableFrom( Color.class ) ) {
                if( Modifier.isStatic( f.getModifiers() ) ) {

                    final Color  color = getColor( f );
                    final Colors c     = Colors.find( color );
                    final String hex   = c == null ? "#??????" : c.toHexString();

                    LOGGER.info( "color " + color + " is " + f.getName() + " *** Colors is " + c + " -> " + hex );

                    checkColorByRGB( color, f.getName() );
                    checkColorByName( color, f.getName() );
               }
            }
        }
    }

    private void checkColorByRGB( final Color color, final String colorName )
    {
        final Colors c = Colors.find( color );

        assertThat( c ).isNotNull();
        assertThat( normalize( colorName ) ).isEqualTo( c.name().toLowerCase() );
    }

    private String normalize( final String colorName )
    {
        final String str = colorName.toLowerCase();

        return str.replaceAll( "_", "" );
    }


    private void checkColorByName( final Color color, final String colorName )
    {
        final Colors c = Colors.find( normalize( colorName ) );

        assertThat( c ).isNotNull();

        final Color newColor = c.toColor();

        assertThat( newColor ).isEqualTo( color );
    }

    private static Color getColor( final Field f )
    {
        try {
            return (Color)f.get( null );
            }
        catch( IllegalArgumentException | IllegalAccessException e ) {
            throw new RuntimeException( e );
            }
    }


    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp()
        throws Exception
    {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     */
    @After
    public void tearDown()
        throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     */
    public static void main(final String[] args)
    {
        new org.junit.runner.JUnitCore().run(ColorsTest.class);
    }
}
