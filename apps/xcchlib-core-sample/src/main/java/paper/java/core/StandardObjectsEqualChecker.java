package paper.java.core;

public class StandardObjectsEqualChecker {

    public static void main(final String...args)
    {
        checkInteger();
        checkDouble();
        checkString();
    }

    @SuppressWarnings("boxing")
    private static void checkInteger()
    {
        final Integer i1 = 1;
        final Integer i2 = 1;
        final Integer i3 = Integer.valueOf( 1 );
        final Integer i4 = Integer.valueOf( 1 );
        final Integer i5 = new Integer( 1 );
        final Integer i6 = new Integer( 1 );

        display( "Integer", i1, i2, i3, i4, i5, i6 );
    }

    @SuppressWarnings("boxing")
    private static void checkDouble()
    {
        final Double i1 = 1.0;
        final Double i2 = 1.0;
        final Double i3 = Double.valueOf( 1.0 );
        final Double i4 = Double.valueOf( 1.0 );
        final Double i5 = new Double( 1.0 );
        final Double i6 = new Double( 1.0 );

        display( "Double", i1, i2, i3, i4, i5, i6 );
    }

    private static void checkString()
    {
        final String i1 = "1.0";
        final String i2 = "1.0";
        final String i3 = new String( "1.0" );
        final String i4 = new String( "1.0" );
        final String i5 = "1.0".intern();
        final String i6 = "1.0".intern();

        display( "String", i1, i2, i3, i4, i5, i6 );
    }

    private static void display(
        final String message,
        final Object i1,
        final Object i2,
        final Object i3,
        final Object i4,
        final Object i5,
        final Object i6 )
    {
        System.out.println(message);
        System.out.println( "i1 == i2 : " + (i1 == i2) );
        System.out.println( "i1 == i3 : " + (i1 == i3) );
        System.out.println( "i1 == i4 : " + (i1 == i4) );
        System.out.println( "i1 == i5 : " + (i1 == i5) );
        System.out.println( "i1 == i6 : " + (i1 == i6) );

        System.out.println( "i2 == i3 : " + (i2 == i3) );
        System.out.println( "i2 == i4 : " + (i2 == i4) );
        System.out.println( "i2 == i5 : " + (i2 == i5) );
        System.out.println( "i2 == i6 : " + (i2 == i6) );

        System.out.println( "i3 == i4 : " + (i3 == i4) );
        System.out.println( "i3 == i5 : " + (i3 == i5) );
        System.out.println( "i3 == i6 : " + (i3 == i6) );

        System.out.println( "i4 == i5 : " + (i4 == i5) );
        System.out.println( "i4 == i6 : " + (i4 == i6) );

        System.out.println( "i5 == i6 : " + (i5 == i6) );
        System.out.println();
    }
}
