package com.googlecode.cchlib.awt;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.*;
import com.googlecode.cchlib.awt.Colors;
import static org.junit.Assert.*;

/**
 * The class <code>ColorsTest</code> contains tests for the class <code>{@link Colors}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class ColorsTest
{
    private static final Logger logger = Logger.getLogger( ColorsTest.class );

    /**
     * Run the Color toColor() method test.
     */
    @Test
    public void testToColor_1()
        throws Exception
    {
        Colors fixture = Colors.aliceblue;

        Color result = fixture.toColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals("java.awt.Color[r=240,g=248,b=255]", result.toString());
        assertEquals(255, result.getAlpha());
        assertEquals(240, result.getRed());
        assertEquals(255, result.getBlue());
        assertEquals(248, result.getGreen());
        assertEquals(-984833, result.getRGB());
        assertEquals(1, result.getTransparency());
    }


    /**
     * Run the Color toColor() method test.
     */
    @Test
    public void testColors_vs_Color()
        throws Exception
    {
        Class<Color> colorClass = Color.class;

        for( Field f : colorClass.getFields() ) {
            if( f.getType().isAssignableFrom( Color.class ) ) {
                if( Modifier.isStatic( f.getModifiers() ) ) {

                    Color  color = getColor( f );
                    Colors c     = Colors.find( color );
                    String hex   = c == null ? "#??????" : c.toHexString();

                    logger.info( "color " + color + " is " + f.getName() + " *** Colors is " + c + " -> " + hex );

                    checkColorByRGB( color, f.getName() );
                    checkColorByName( color, f.getName() );
               }
            }
        }
    }

    private void checkColorByRGB( Color color, String colorName )
    {
        Colors c = Colors.find( color );

        Assertions.assertThat( c ).isNotNull();
        Assertions.assertThat( normalize( colorName ) ).isEqualTo( c.name().toLowerCase() );
    }

    private String normalize( String colorName )
    {
        String str = colorName.toLowerCase();

        return str.replaceAll( "_", "" );
    }


    private void checkColorByName( Color color, String colorName )
    {
        Colors c = Colors.find( normalize( colorName ) );

        Assertions.assertThat( c ).isNotNull();

        Color newColor = c.toColor();

        Assertions.assertThat( newColor ).isEqualTo( color );
    }

    private static Color getColor( Field f )
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
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(ColorsTest.class);
    }
}
