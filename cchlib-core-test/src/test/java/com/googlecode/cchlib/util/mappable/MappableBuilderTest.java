package com.googlecode.cchlib.util.mappable;

import static org.junit.Assume.assumeFalse;
import java.io.File;
import javax.swing.JLabel;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

/**
 *
 */
public class MappableBuilderTest extends MappableBuilderTestHelper
{

    @Test
    public void tstMappableJLabel_TRY_PROTECTED_METHODS()
    {
        final MappableBuilderFactory factory = newMappableBuilder_TRY_PROTECTED_METHODS();

        final Object object1 = newJLabelObject( "testString" );
        final Object object2 = newJLabelObject( "testString2" );

        runTest( factory, object1, object2 );
    }

    @Test
    public void tstMappableJLabel_TRY_PRIVATE_METHODS()
    {
        final MappableBuilderFactory factory = newMappableBuilder_TRY_PRIVATE_METHODS();

        final Object object1 = newJLabelObject( "testString" );
        final Object object2 = newJLabelObject( "testString2" );

        runTest( factory, object1, object2 );
    }

    @Test
    public void tstMappableJLabel_only_public_METHODS()
    {
        final MappableBuilderFactory factory = newMappableBuilder_only_public_METHODS();

        final Object object1 = newJLabelObject( "testString" );
        final Object object2 = newJLabelObject( "testString2" );

        runTest( factory, object1, object2 );
   }

    @Test
    public void tstMappable_TRY_PROTECTED_METHODS()
    {
        final MappableBuilderFactory factory = newMappableBuilder_TRY_PROTECTED_METHODS();

        final Object object1 = newObject( "testString" );
        final Object object2 = newObject( "testString2" );

        runTest( factory, object1, object2 );
    }

    @Test
    public void tstMappable_TRY_PRIVATE_METHODS()
    {
        final MappableBuilderFactory factory = newMappableBuilder_TRY_PRIVATE_METHODS();

        final Object object1 = newObject( "testString" );
        final Object object2 = newObject( "testString2" );

        runTest( factory, object1, object2 );
    }

    @Test
    public void tstMappable_only_public_METHODS()
    {
        final MappableBuilderFactory factory = newMappableBuilder_only_public_METHODS();

        final Object object1 = newObject( "testString" );
        final Object object2 = newObject( "testString2" );

        runTest( factory, object1, object2 );
    }

    private static Object newObject( final String text )
    {
        return new File( text );
    }

    private static Object newJLabelObject( final String text )
    {
        // Stop if GUI usage is not allowed
        assumeFalse( SafeSwingUtilities.isHeadless() );

        return new JLabel( text );
    }

}
