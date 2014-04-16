// $codepro.audit.disable concatenationInAppend
package paper.reflexion.invoke.ex1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Example1
{
    public static void main(String...args) throws Exception
    {
        case_invoke_well_known_params_types();
    }

    private static void case_invoke_well_known_params_types()
        throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        final int intValue = 10;

        // Class is not known at this time
        Class<?>    clazz          = Class.forName( "paper.reflexion.invoke.ex1.ClassToTest" );
        Object      instance       = clazz.newInstance();

        Method integerValueMethod = clazz.getMethod( "integerValue", new Class[] { Integer.class } );
        Object integerValueResult = integerValueMethod.invoke( instance, new Object[] { new Integer( intValue ) } );

        System.out.println( "integerValueMethod                 = " + integerValueMethod );
        System.out.println( "integerValueResult.getReturnType() = " + integerValueMethod.getReturnType() );
        System.out.println( "integerValueResult            = " + integerValueResult );
        System.out.println( "integerValueResult.getClass() = " + integerValueResult.getClass() );

        Method      intValueMethod = clazz.getMethod( "intValue", new Class[] { int.class } );
        Object      intValueResult = intValueMethod.invoke( instance, new Object[] { intValue } );

        System.out.println( "intValueMethod                 = " + intValueMethod );
        System.out.println( "intValueMethod.getReturnType() = " + intValueMethod.getReturnType() );
        System.out.println( "intValueResult            = " + intValueResult );
        System.out.println( "intValueResult.getClass() = " + intValueResult.getClass() );

        // so to get real type for value we need to do :
        int real_intValueResult = ((Integer)intValueResult).intValue();

        System.out.println( "real_intValueResult       = " + real_intValueResult );
    }
}
