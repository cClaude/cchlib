package paper.visibility.otherpackage;

import paper.visibility.ExamplePublicClass;

//ERROR: import paper.visibility.ExampleDefaultClass;
//Cause: The type paper.visibility.ExampleDefaultClass is not visible

import paper.visibility.Example3CustoOfExampleDefaultClass;

public class Example3 extends ExamplePublicClass
{
    // If no constructor, this default code is generate
    // public Example3()
    // {
    //     super();
    // }

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
        Example3 case1 = new Example3();

        System.out.println( case1.publicVisibility() );

        //ERROR: case1.defautVisibility();
        //cause: not visible !

        System.out.println( case1.protectedVisibility() );

        //ERROR: case1.privateVisibility();
        //cause: not visible !

        //
        // Direct instantiation
        //
        ExamplePublicClass case2 = new ExamplePublicClass();

        System.out.println( case2.publicVisibility() );

        //ERROR: case2.defautVisibility();
        //cause: not visible !

        //ERROR: case2.protectedVisibility();
        //cause: not visible !

        //ERROR: case2.privateVisibility();
        //cause: not visible !

        Example3CustoOfExampleDefaultClass case3 = new Example3CustoOfExampleDefaultClass();

        System.out.println( case3.publicVisibility() );

        //ERROR: case3.defautVisibility() );
        //cause: not visible !

        //ERROR: case3.protectedVisibility() );
        //cause: not visible !

        //ERROR: case3.privateVisibility();
        //cause: not visible !
    }
}
