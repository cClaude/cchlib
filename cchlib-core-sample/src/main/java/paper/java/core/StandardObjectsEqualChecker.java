package paper.java.core;

public class StandardObjectsEqualChecker {

    public static void main(final String...args)
    {
        checkInteger();
        checkDouble();
        checkString();
    }

    private static void checkInteger()
    {
        Integer i1 = 1;
        Integer i2 = 1;
        Integer i3 = Integer.valueOf( 1 );
        Integer i4 = Integer.valueOf( 1 );
        Integer i5 = new Integer( 1 );
        Integer i6 = new Integer( 1 );

        display( "Integer", i1, i2, i3, i4, i5, i6 );
    }

    private static void checkDouble()
    {
        Double i1 = 1.0;
        Double i2 = 1.0;
        Double i3 = Double.valueOf( 1.0 );
        Double i4 = Double.valueOf( 1.0 );
        Double i5 = new Double( 1.0 );
        Double i6 = new Double( 1.0 );

        display( "Double", i1, i2, i3, i4, i5, i6 );
    }

    private static void checkString()
    {
        String i1 = "1.0";
        String i2 = "1.0";
        String i3 = new String( "1.0" );
        String i4 = new String( "1.0" );
        String i5 = "1.0".intern();
        String i6 = "1.0".intern();

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
