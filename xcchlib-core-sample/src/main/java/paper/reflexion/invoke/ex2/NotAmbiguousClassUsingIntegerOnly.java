package paper.reflexion.invoke.ex2;

public class NotAmbiguousClassUsingIntegerOnly
{
    public NotAmbiguousClassUsingIntegerOnly()
    {
    }

    public String polymorphicInvoke()
    {
        return "polymorphicInvoke()";
    }

    public String polymorphicInvoke( Integer integerValue )
    {
        return String.format( "polymorphicInvoke( Integer %s)", integerValue );
    }

    public String polymorphicInvoke( Integer integerValue, Object o1 )
    {
        return String.format( "polymorphicInvoke( Integer %s %s)", integerValue, o1 );
    }

    public String polymorphicInvoke( Integer integerValue, Object o1, Object o2 )
    {
        return String.format( "polymorphicInvoke( Integer %s %s %s)", integerValue, o1, o2 );
    }
}
