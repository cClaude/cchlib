package paper.java.core;

public class DoubleVSdoubleEqualChecker {

    public static void main(final String...args)
    {
        checkDouble();
    }

    private static void checkDouble()
    {
        double i1 = 1.0;
        double i2 = 1.0;
        Double i3 = Double.valueOf( 1.0 );
        Double i4 = Double.valueOf( 1.0 );
        Double i5 = new Double( 1.0 );
        Double i6 = new Double( 1.0 );
        Double i7 = 1.0;
        Double i8 = 1.0;

        System.out.println("Double");
        System.out.println( "i1 == i2 : " + (i1 == i2) );
        System.out.println( "i1 == i3 : " + (i1 == i3) );
        System.out.println( "i1 == i4 : " + (i1 == i4) );
        System.out.println( "i1 == i5 : " + (i1 == i5) );
        System.out.println( "i1 == i6 : " + (i1 == i6) );
        System.out.println( "i1 == i7 : " + (i1 == i7) );
        System.out.println( "i1 == i8 : " + (i1 == i8) );

        System.out.println( "i2 == i3 : " + (i2 == i3) );
        System.out.println( "i2 == i4 : " + (i2 == i4) );
        System.out.println( "i2 == i5 : " + (i2 == i5) );
        System.out.println( "i2 == i6 : " + (i2 == i6) );
        System.out.println( "i2 == i7 : " + (i2 == i7) );
        System.out.println( "i2 == i8 : " + (i2 == i8) );

        System.out.println( "i3 == i4 : " + (i3 == i4) );
        System.out.println( "i3 == i5 : " + (i3 == i5) );
        System.out.println( "i3 == i6 : " + (i3 == i6) );
        System.out.println( "i3 == i7 : " + (i3 == i7) );
        System.out.println( "i3 == i8 : " + (i3 == i8) );

        System.out.println( "i4 == i5 : " + (i4 == i5) );
        System.out.println( "i4 == i6 : " + (i4 == i6) );
        System.out.println( "i4 == i7 : " + (i4 == i7) );
        System.out.println( "i4 == i8 : " + (i4 == i8) );

        System.out.println( "i5 == i6 : " + (i5 == i6) );
        System.out.println( "i5 == i7 : " + (i5 == i7) );
        System.out.println( "i5 == i8 : " + (i5 == i8) );

        System.out.println( "i6 == i7 : " + (i6 == i7) );
        System.out.println( "i6 == i8 : " + (i6 == i8) );

        System.out.println( "i7 == i8 : " + (i7 == i8) );

        System.out.println();
    }
}
