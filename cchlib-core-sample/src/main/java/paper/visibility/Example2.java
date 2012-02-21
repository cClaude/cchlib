package paper.visibility;

public class Example2 extends ExamplePublicClass
{
    public Example2()
    {
        // super() is invoked implicitly
    }

    // Does not override
    private String privateVisibility()
    {
        return "privateVisibility() from Example2";
    }

    public static void main(String[] args)
    {
        Example2 case1 = new Example2();

        System.out.println( case1.publicVisibility() );
        System.out.println( case1.defautVisibility() );
        System.out.println( case1.protectedVisibility() );
        System.out.println( case1.privateVisibility() );
    }
}
