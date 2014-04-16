package paper.visibility;

/**
 *
 */
/* No qualifier here */ class ExampleDefaultClass
{
    String defautVisibility()
    {
        return "defautVisibility() of ExampleDefaultClass";
    }

    public String publicVisibility()
    {
        return "publicVisibility() of ExampleDefaultClass";
    }

    protected String protectedVisibility()
    {
        return "protectedVisibility() of ExampleDefaultClass invoke: "
                + privateVisibility();
    }

    private String privateVisibility()
    {
        return "privateVisibility() of ExampleDefaultClass";
    }
}
