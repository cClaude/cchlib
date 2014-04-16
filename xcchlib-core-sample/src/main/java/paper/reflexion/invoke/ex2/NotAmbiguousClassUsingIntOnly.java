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

    public String polymorphicInvoke( int intValue )
    {
        return String.format( "polymorphicInvoke( int %s)", intValue );
    }

    public String polymorphicInvoke( int intValue, Object o1 )
    {
        return String.format( "polymorphicInvoke( int %s %s)", intValue, o1 );
    }

    public String polymorphicInvoke( int intValue, Object o1, Object o2 )
    {
        return String.format( "polymorphicInvoke( int %s %s %s)", intValue, o1, o2 );
    }
}
