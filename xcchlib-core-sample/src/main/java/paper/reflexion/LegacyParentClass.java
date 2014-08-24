package paper.reflexion;

/**
 *
 */
public abstract class LegacyParentClass
{
    private static final Long TEN = Long.valueOf( 10L );
    public static final String aPublicFinalStaticField = "**aPublicFinalStaticField**";
    protected final String aProtectedFinalField = "**aProtectedFinalField**";
    protected String aProtectedField = "**aProtectedField**";
    private Long aLong;
    private String aString;
    private int aInt;

    public LegacyParentClass()
    {
        this.aLong      = TEN;
        this.aString    = "FOO";
        this.aInt       = 20;

        doNothing(); // Just to hide warning !
        try {
            doNullPointerException();
            }
        catch( final NullPointerException ignore ) {} // $codepro.audit.disable logExceptions, emptyCatchClause
    }

    private void doNothing()
    {
        // Well, nothing !
    }

    private void doNullPointerException()
    {
        throw new NullPointerException( "Exception for testing" );
    }

    public String doFoo( final String value )
    {
        final String prev = this.getAString();
        this.setAString( value );
        return prev;
    }

    private String getAString()
    {
        return this.aString;
    }

    private void setAString( final String value )
    {
        this.aString = value;
    }

    protected abstract int aProtectedAbstractMethod();

    protected int aProtectedMethod()
    {
        return - this.aInt;
    }

    public void aPublicMethod(
        final int       intValue,
        final Long      longObject,
        final String    stringObject
        )
    {
        this.aInt       = intValue;
        this.aLong      = longObject;
        this.aString    = stringObject;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("ParentClass [aProtectedFinalField=");
        builder.append(aProtectedFinalField);
        builder.append(", aProtectedField=");
        builder.append(aProtectedField);
        builder.append(", aLong=");
        builder.append(aLong);
        builder.append(", aString=");
        builder.append(aString);
        builder.append(", aInt=");
        builder.append(aInt);
        builder.append(']');
        return builder.toString();
    }

}
