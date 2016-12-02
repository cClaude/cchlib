package com.googlecode.cchlib.net.download;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

public class SandboxTest
{
    interface MyInterface {
        // Test only
    }

    class MyParentClass implements MyInterface {
        // Test only
    }

    class MyClass extends MyParentClass {
        // Test only
    }

    @Test
    public void test_isAssignableFrom_1_with_class()
    {
        // MyInterface object = new MyClass();
        final Class<?> type = MyClass.class;
        final boolean actual = MyInterface.class.isAssignableFrom( type );

        assertThat( actual ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_1_with_types()
    {
        final MyInterface   myInterface   = new MyClass();
        final MyParentClass myParentClass = new MyParentClass();
        final MyClass       myClass       = new MyClass();

        final Class<? extends MyInterface>   myInterfaceType   = myInterface.getClass();
        final Class<? extends MyParentClass> myParentClassType = myParentClass.getClass();
        final Class<? extends MyClass>       myClassType       = myClass.getClass();

        // Case 1
        @SuppressWarnings("unused")
        MyInterface myInterface0;
        myInterface0 = myInterface;
        myInterface0 = myParentClass;
        myInterface0 = myClass;

        assertThat( myInterfaceType.isAssignableFrom( myInterfaceType ) ).isTrue();
        assertThat( myParentClassType.isAssignableFrom( myInterfaceType ) ).isTrue();
        assertThat( myClassType.isAssignableFrom( myInterfaceType ) ).isTrue();

        // Case 2
        @SuppressWarnings("unused")
        MyParentClass myParentClass0;
        // myParentClass0 = myInterface; - this fail
        myParentClass0 = myParentClass;
        myParentClass0 = myClass;

        assertThat( myInterfaceType.isAssignableFrom( myParentClassType ) ).isFalse();
        assertThat( myParentClassType.isAssignableFrom( myParentClassType ) ).isTrue();
        assertThat( myClassType.isAssignableFrom( myParentClassType ) ).isFalse();

        // Case 3
        @SuppressWarnings("unused")
        MyClass myClass0;
        //myClass0 = myInterface; - this fail
        //myClass0 = myParentClass; - this fail
        myClass0 = myClass;

        assertThat( myInterfaceType.isAssignableFrom( myClassType ) ).isFalse();
        assertThat( myParentClassType.isAssignableFrom( myClassType ) ).isFalse();
        assertThat( myClassType.isAssignableFrom( myClassType ) ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_trivial_1()
    {
        final Class<?> type = SandboxTest.class;

        final boolean actual = type.isAssignableFrom( SandboxTest.class );

        assertThat( actual ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_trivia_2()
    {
        final Class<?> type = SandboxTest.class;

        final boolean actual = SandboxTest.class.isAssignableFrom( type );

        assertThat( actual ).isTrue();
    }

    @Test
    public void test_isAssignableFrom_trivial_3()
    {
        final Class<?> type = MyClass.class;

        final boolean actual = SandboxTest.class.isAssignableFrom( type );

        assertThat( actual ).isFalse();
    }
}
