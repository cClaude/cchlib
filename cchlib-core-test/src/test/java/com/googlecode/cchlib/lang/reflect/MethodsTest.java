package com.googlecode.cchlib.lang.reflect;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MethodsTest
{
    private static final Logger LOGGER = Logger.getLogger( MethodsTest.class );

    @Test
    public void test_static_on_File()
    {
        final Iterable<Method> methodsOnFile = Methods.getStaticMethods( File.class );

        LOGGER.info( "methodsOnFile = " + methodsOnFile );

        assertThat( methodsOnFile ).hasSize( 3 );

        final Iterator<Method> iterator = methodsOnFile.iterator();

        assertThat( iterator.hasNext() ).isTrue();
        final Method method0 = iterator.next();
        assertThat( method0.getName() ).isEqualTo( "createTempFile" );
        assertThat( method0.getParameterTypes() ).hasSize( 2 );

        assertThat( iterator.hasNext() ).isTrue();
        final Method method1 = iterator.next();
        assertThat( method1.getName() ).isEqualTo( "createTempFile" );
        assertThat( method1.getParameterTypes() ).hasSize( 3 );

        assertThat( iterator.hasNext() ).isTrue();
        assertThat( iterator.next().getName() ).isEqualTo( "listRoots" );

        assertThat( iterator.hasNext() ).isFalse();
    }

    @Test
    public void test_static_on_System()
    {
        final Iterable<Method> methodsOnSystem = Methods.getStaticMethods( System.class );
        LOGGER.info( "methodsOnSystem = " + methodsOnSystem );
        assertThat( methodsOnSystem ).hasSize( 27 );

        final Map<String,Set<Method>> methodsOnSystemByName = Methods.getStaticMethodsByNames( System.class );
        LOGGER.info( "methodsOnSystemByName = " + methodsOnSystemByName );

        assertThat( methodsOnSystemByName ).hasSize( 25 );
        assertThat( methodsOnSystemByName )
            .containsKey( "arraycopy" )             // 1
            .containsKey( "clearProperty" )         // 2
            .containsKey( "console" )               // 3
            .containsKey( "currentTimeMillis" )     // 4
            .containsKey( "exit" )                  // 5
            .containsKey( "gc" )                    // 6
            .containsKey( "getProperties" )         // 7
            .containsKey( "getProperty" )           // 8  (2 methods)
            .containsKey( "getSecurityManager" )    // 9
            .containsKey( "getenv" )                // 10 (2 methods)
            .containsKey( "identityHashCode" )      // 11
            .containsKey( "inheritedChannel" )      // 12
            .containsKey( "lineSeparator" )         // 13
            .containsKey( "load" )                  // 14
            .containsKey( "loadLibrary" )           // 15
            .containsKey( "mapLibraryName" )        // 16
            .containsKey( "nanoTime" )              // 17
            .containsKey( "runFinalization" )       // 18
            .containsKey( "runFinalizersOnExit" )   // 19
            .containsKey( "setErr" )                // 20
            .containsKey( "setIn" )                 // 21
            .containsKey( "setOut" )                // 22
            .containsKey( "setProperties" )         // 23
            .containsKey( "setProperty" )           // 24
            .containsKey( "setSecurityManager" );   // 25

        for( final Map.Entry<String, Set<Method>> nameMethods : methodsOnSystemByName.entrySet() ) {
            final String name = nameMethods.getKey();

            switch( name ) {
                case "getProperty" :
                case "getenv" :
                    assertThat( nameMethods.getValue() ).hasSize( 2 );
                    break;
                default:
                    assertThat( nameMethods.getValue() ).hasSize( 1 );
                    break;
            }
        }
    }

    @Test
    public void test_static_on_Method()
    {
        final Map<String, Set<Method>> methodsOnMethod = Methods.getStaticMethodsByNames( Method.class );

        LOGGER.info( "methodsOnMethod = " + methodsOnMethod );

        assertThat( methodsOnMethod ).hasSize( 1 );

        final Set<Method> setAccessibleSet = methodsOnMethod.get( "setAccessible" );
        assertThat( setAccessibleSet ).hasSize( 1 );

        final Iterator<Method> iterator = setAccessibleSet.iterator();

        assertThat( iterator.hasNext() ).isTrue();
        final Method setAccessibleMethod = iterator.next();

        @SuppressWarnings("unchecked")
        final Class<AccessibleObject> declaringClass = (Class<AccessibleObject>)setAccessibleMethod.getDeclaringClass();

        // this public static method is not on Method
        assertThat( declaringClass ).isEqualTo( AccessibleObject.class );

        assertThat( iterator.hasNext() ).isFalse();
    }

    @Test
    public void test_PublicMethods_on_File()
    {
        final Map<String, Set<Method>> publicMethodsOnFile = Methods.getPublicMethodByNames( File.class );

        LOGGER.info( "publicMethodsOnFile = " + publicMethodsOnFile );

        assertThat( publicMethodsOnFile ).hasSize( 47 ); // 57

        int count = 0;
        for( final Map.Entry<String, Set<Method>> nameMethods : publicMethodsOnFile.entrySet() ) {
            final String name = nameMethods.getKey();

            switch( name ) {
                case "compareTo" :
                case "createTempFile" :
                case "list" :
                case "setExecutable" :
                case "setReadable" :
                case "setWritable" :
                    assertThat( nameMethods.getValue() ).hasSize( 2 );
                    count += 2;
                    break;
                case "listFiles" :
                case "wait" :
                    assertThat( nameMethods.getValue() ).hasSize( 3 );
                    count += 3;
                    break;
                default:
                    assertThat( nameMethods.getValue() ).hasSize( 1 );
                    count += 1;
                    break;
            }
        }

        assertThat( count ).isEqualTo( 57 );
    }
}
