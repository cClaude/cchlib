package paper.visibility;

/**
 *
 */
public class ExamplePublicClass
{
    public ExamplePublicClass()
    {
        // ...
    }

    String defautVisibility()
    {
        return "defautVisibility() of ExamplePublicClass";
    }

    public String publicVisibility()
    {
        return "publicVisibility() of ExamplePublicClass";
    }

    protected String protectedVisibility()
    {
        return "protectedVisibility() of ExamplePublicClass invoke: "
                + privateVisibility();
    }

    private String privateVisibility()
    {
        return "privateVisibility() of ExamplePublicClass";
    }
}
