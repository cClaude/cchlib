package paper.reflexion.invoke.ex2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import paper.reflexion.invoke.ex2.tools.ParamTypesValues2;
import paper.reflexion.invoke.ex2.tools.ParamTypesValuesBuilder2;
import paper.reflexion.invoke.ex2.tools.ParamValues;
import paper.reflexion.invoke.ex2.tools.ParamValuesBuilder;

public class Example2
{
    private static final Integer TEN = Integer.valueOf( 10 );

    public static void main(final String...args) throws Exception
    {
        // Remark : return false !
        System.out.println( "int.class.isAssignableFrom( Integer.class ) = " + int.class.isAssignableFrom( Integer.class ) );
        System.out.println( "Integer.class.isAssignableFrom( int.class ) = " + Integer.class.isAssignableFrom( int.class ) );

        case_invoke_computed_params_types();
    }

    private static Object invokeMethod_polymorphicInvoke(
        final Class<?>   clazz,
        final Object     instance,
        final Class<?>[] paramsTypes,
        final Object[]   params
        ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        assert clazz.isAssignableFrom( instance.getClass() );
        assert paramsTypes.length == params.length;

        final Method method = clazz.getMethod( "polymorphicInvoke", paramsTypes );
        return method.invoke( instance, params );
    }

    private static void test_invokeMethod_polymorphicInvoke(
        final Class<?>   clazz,
        final Object     instance,
        final Class<?>[] paramsTypes,
        final Object[]   params,
        final boolean    shouldNotFail
        )
    {
        try {
            final Object r = invokeMethod_polymorphicInvoke( clazz, instance, paramsTypes, params );

            System.out.println( "r = " + r );
            }
        catch( final NoSuchMethodException e ) {
            if( shouldNotFail ) {
                e.printStackTrace();
                }
            else {
                System.out.println( "NoSuchMethodException error but OK: " + e.getMessage() );
                }
            }
        catch(  SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e ) {
            e.printStackTrace();
            }
    }

    private static void test_invokeMethod_polymorphicInvoke(
        final Class<?>   clazz,
        final Object     instance,
        final Class<?>   firstParamType,
        final Object     firstParam,
        final boolean    oneParam_shouldNotFail,
        final boolean    twoParam_shouldNotFail,
        final boolean    threeParam_shouldNotFail
        )
    {
        final ParamTypesValuesBuilder2 pb = new ParamTypesValuesBuilder2( firstParamType, firstParam, oneParam_shouldNotFail, twoParam_shouldNotFail, threeParam_shouldNotFail );

        for( final ParamTypesValues2 p : pb.toParamTypesValues2() ) {
            test_invokeMethod_polymorphicInvoke( clazz, instance, p.getTypes(), p.getValues(), p.isShouldNotFail() );
            }
    }

    private static void case_invoke_computed_params_types() throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        final Class<?> classAmbiguousClass                    = Class.forName( "paper.reflexion2.AmbiguousClass" );
        final Class<?> classNotAmbiguousClassMixing           = Class.forName( "paper.reflexion2.NotAmbiguousClassMixing" );
        final Class<?> classNotAmbiguousClassUsingIntegerOnly = Class.forName( "paper.reflexion2.NotAmbiguousClassUsingIntegerOnly" );
        final Class<?> classNotAmbiguousClassUsingIntOnly     = Class.forName( "paper.reflexion2.NotAmbiguousClassUsingIntOnly" );

        final Object instanceAmbiguousClass                    = classAmbiguousClass.newInstance();
        final Object instanceNotAmbiguousClassMixing           = classNotAmbiguousClassMixing.newInstance();
        final Object instanceNotAmbiguousClassUsingIntegerOnly = classNotAmbiguousClassUsingIntegerOnly.newInstance();
        final Object instanceotAmbiguousClassUsingIntOnly      = classNotAmbiguousClassUsingIntOnly.newInstance();

        {
            final Class<?>[] noParamTypes = new Class<?>[ 0 ];
            final Object[]   noParam      = new Object[ 0 ];

            test_invokeMethod_polymorphicInvoke( classAmbiguousClass, instanceAmbiguousClass, noParamTypes, noParam, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassMixing, instanceNotAmbiguousClassMixing, noParamTypes, noParam, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassUsingIntegerOnly, instanceNotAmbiguousClassUsingIntegerOnly, noParamTypes, noParam, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassUsingIntOnly, instanceotAmbiguousClassUsingIntOnly, noParamTypes, noParam, true );
        }

        {
            final Class<?> firstType  = int.class;
            final Object   firstParam = TEN;

            test_invokeMethod_polymorphicInvoke( classAmbiguousClass, instanceAmbiguousClass, firstType, firstParam, true, true, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassMixing, instanceNotAmbiguousClassMixing, firstType, firstParam, false /* NotAmbiguousClassMixing.polymorphicInvoke(int) does not exist */, true, false /* NotAmbiguousClassMixing.polymorphicInvoke(int, Object, Object) does not exist */ );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassUsingIntegerOnly, instanceNotAmbiguousClassUsingIntegerOnly, firstType, firstParam, false /* NotAmbiguousClassMixing.polymorphicInvoke(int) does not exist */, false /* NotAmbiguousClassMixing.polymorphicInvoke(int, Object) does not exist */, false /* NotAmbiguousClassMixing.polymorphicInvoke(int, Object, Object) does not exist */ );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassUsingIntOnly, instanceotAmbiguousClassUsingIntOnly, firstType, firstParam, true, true, true );

            final ParamValuesBuilder paramBuilder = new ParamValuesBuilder( TEN );
            test_bestMatchInvoke( instanceAmbiguousClass, "polymorphicInvoke", paramBuilder );
            test_bestMatchInvoke( instanceNotAmbiguousClassMixing, "polymorphicInvoke", paramBuilder );
            test_bestMatchInvoke( instanceNotAmbiguousClassUsingIntegerOnly, "polymorphicInvoke", paramBuilder );
            test_bestMatchInvoke( instanceotAmbiguousClassUsingIntOnly, "polymorphicInvoke", paramBuilder );
        }

        {
            final Class<?> firstType  = Integer.class;
            final Object   firstParam = TEN;

            test_invokeMethod_polymorphicInvoke( classAmbiguousClass, instanceAmbiguousClass, firstType, firstParam, true, true, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassMixing, instanceNotAmbiguousClassMixing, firstType, firstParam, true, false /* NotAmbiguousClassMixing.polymorphicInvoke(Integer, Object) does not exist */, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassUsingIntegerOnly, instanceNotAmbiguousClassUsingIntegerOnly, firstType, firstParam, true, true, true );
            test_invokeMethod_polymorphicInvoke( classNotAmbiguousClassUsingIntOnly, instanceotAmbiguousClassUsingIntOnly, firstType, firstParam, false /* NotAmbiguousClassMixing.polymorphicInvoke(Integer) does not exist */, false /* NotAmbiguousClassMixing.polymorphicInvoke(Integer) does not exist */, false /* NotAmbiguousClassMixing.polymorphicInvoke(Integer) does not exist */ );
        }
    }

    private static void test_bestMatchInvoke(
        final Object             instance,
        final String             methodName,
        final ParamValuesBuilder paramBuilder
        )
    {
        final Class<?> clazz = instance.getClass();

        for( final ParamValues p : paramBuilder.toParamValues() ) {
            try {
                InvokeHelper.bestMatchInvoke( clazz, instance, methodName, p.getValues() );
                }
            catch( NoSuchMethodException | SecurityException
                    | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e ) {
                e.printStackTrace();
                }
            catch( final MethodResolutionException e ) {
                e.printStackTrace();
            }
            }
    }
}
