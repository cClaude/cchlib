package paper.visibility;

public class Example1 extends ExamplePublicClass
{
    public Example1()
    {
        super();
    }

    //ERROR: private String protectedVisibility() { return null; }
    //cause: Cannot reduce the visibility of the inherited method from ExampleClass

    // protectedVisibility is now public !
    @Override
    public String protectedVisibility()
    {
        return super.protectedVisibility();
    }

    public static void main(String[] args)
    {
        Example1 case1 = new Example1();

        System.out.println( case1.publicVisibility() );
        System.out.println( case1.defautVisibility() );
        System.out.println( case1.protectedVisibility() );

        //ERROR: case1.privateVisibility();
        //cause: not visible !

        //
        // Direct instantiation
        //
        ExamplePublicClass case2 = new ExamplePublicClass();

        System.out.println( case2.publicVisibility() );
        System.out.println( case2.defautVisibility() );
        System.out.println( case2.protectedVisibility() );

        //ERROR: case2.privateVisibility();
        //cause: not visible !

        ExampleDefaultClass case3 = new ExampleDefaultClass();

        System.out.println( case3.publicVisibility() );
        System.out.println( case3.defautVisibility() );
        System.out.println( case3.protectedVisibility() );

        //ERROR: case3.privateVisibility();
        //cause: not visible !
    }
}
