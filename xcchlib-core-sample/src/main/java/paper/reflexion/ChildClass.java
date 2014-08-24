// $codepro.audit.disable concatenationInAppend
package paper.reflexion;

import java.lang.reflect.InvocationTargetException;

public class ChildClass extends LegacyParentClass
{
    private static final Integer ONE = Integer.valueOf( 1 );

    private final ParentAccess<LegacyParentClass> parentAcces = new ParentAccess<LegacyParentClass>( LegacyParentClass.class );

    public ChildClass()
    {
        super();
    }

    public void doCustomization()
    {
        System.out.println( "***********" );
        System.out.println( this.parentAcces );
        System.out.println( "***********" );
        System.out.println( "toString()" + this );

        System.out.println( "***********" );
        this.parentAcces.silentSetObject( this, "aLong", Long.valueOf( 1000 ) );

        final String getAString = this.parentAcces.silentCall( this, String.class, "getAString" );
        System.out.println( "getAString=" + getAString );
        this.parentAcces.silentCall( this, Void.class, "setAString", "new value" );

        System.out.println( "***********" );
        Integer aInt = this.parentAcces.silentGetObject( this, "aInt", Integer.class );
        System.out.println( "aInt=" + aInt );
        this.parentAcces.silentSetObject( this, "aInt", ONE );
        aInt = this.parentAcces.silentGetObject( this, "aInt", Integer.class );
        System.out.println( "aInt=" + aInt );

        System.out.println( "***********" );
        // Yes ! Put something into a void object ! A dream :)
        @SuppressWarnings("unused")
        final
        Void aVoid = this.parentAcces.silentCall( this, Void.class, "doNothing" );

        System.out.println( "***********" );
        try {
            this.parentAcces.call( this, Void.class, "doNullPointerException" );
            }
        catch( final InvocationTargetException e ) {
            final Throwable trueCause = e.getTargetException();
            System.out.println( "crash OK: " + trueCause.getMessage() );
            }
        catch( final Exception e ) {
            throw new RuntimeException( e ); // Unexpected exception!!
            }

        System.out.println( "***********" );
        // BAD CALL !
        try {
            this.parentAcces.call( this, Void.class, "aProtectedMethod" );
            }
        catch( final ClassCastException e ) {
            System.out.println( "crash OK: " + e.getMessage() );
            }
        catch( final Exception e ) {
            throw new RuntimeException( e ); // Unexpected exception!!
            }

        System.out.println( "***********" );
        final int aProtectedAbstractMethod = this.parentAcces.silentCall( this, Integer.class, "aProtectedAbstractMethod" ).intValue();
        System.out.println( "aProtectedAbstractMethod()=" + aProtectedAbstractMethod );

        System.out.println( "***********" );
        System.out.println( "toString()" + this );
    }

    @Override
    protected int aProtectedAbstractMethod()
    {
        return super.aProtectedMethod() * 10000;
    }

    public static void main( final String[] args )
    {
        final ChildClass instance = new ChildClass();

        try {
            instance.doCustomization();
            }
        catch( final Exception e ) {
            e.printStackTrace();
            }
    }
}
