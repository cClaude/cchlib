package paper.reflexion.invoke.ex2;

public class NotAmbiguousClassUsingIntOnly
{
    public NotAmbiguousClassUsingIntOnly()
    {
    }

    public String polymorphicInvoke()
    {
        return "polymorphicInvoke()";
    }

    public String polymorphicInvoke( final int intValue )
    {
        return String.format( "polymorphicInvoke( int %s)", Integer.valueOf( intValue ) );
    }

    public String polymorphicInvoke( final int intValue, final Object o1 )
    {
        return String.format( "polymorphicInvoke( int %s %s)", Integer.valueOf( intValue ), o1 );
    }

    public String polymorphicInvoke( final int intValue, final Object o1, final Object o2 )
    {
        return String.format( "polymorphicInvoke( int %s %s %s)", Integer.valueOf( intValue ), o1, o2 );
    }
}
