// $codepro.audit.disable concatenationInAppend
package paper.reflexion;

import java.lang.reflect.InvocationTargetException;

public class ChildClass extends LegacyParentClass
{
    private ParentAccess<LegacyParentClass> parentAcces = new ParentAccess<LegacyParentClass>( LegacyParentClass.class );

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

        String getAString = this.parentAcces.silentCall( this, String.class, "getAString" );
        System.out.println( "getAString=" + getAString );
        this.parentAcces.silentCall( this, Void.class, "setAString", "new value" );

        System.out.println( "***********" );
        Integer aInt = this.parentAcces.silentGetObject( this, "aInt", Integer.class );
        System.out.println( "aInt=" + aInt );
        this.parentAcces.silentSetObject( this, "aInt", 1 );
        aInt = this.parentAcces.silentGetObject( this, "aInt", Integer.class );
        System.out.println( "aInt=" + aInt );

        System.out.println( "***********" );
        // Yes ! Put something into a void object ! A dream :)
        @SuppressWarnings("unused")
        Void aVoid = this.parentAcces.silentCall( this, Void.class, "doNothing" );

        System.out.println( "***********" );
        try {
            this.parentAcces.call( this, Void.class, "doNullPointerException" );
            }
        catch( InvocationTargetException e ) {
            Throwable trueCause = e.getTargetException();
            System.out.println( "crash OK: " + trueCause.getMessage() );
            }
        catch( Exception e ) {
            throw new RuntimeException( e ); // Unexpected exception!!
            }

        System.out.println( "***********" );
        // BAD CALL !
        try {
            this.parentAcces.call( this, Void.class, "aProtectedMethod" );
            }
        catch( ClassCastException e ) {
            System.out.println( "crash OK: " + e.getMessage() );
            }
        catch( Exception e ) {
            throw new RuntimeException( e ); // Unexpected exception!!
            }

        System.out.println( "***********" );
        int aProtectedAbstractMethod = this.parentAcces.silentCall( this, Integer.class, "aProtectedAbstractMethod" ).intValue();
        System.out.println( "aProtectedAbstractMethod()=" + aProtectedAbstractMethod );

        System.out.println( "***********" );
        System.out.println( "toString()" + this );
    }

    @Override
    protected int aProtectedAbstractMethod()
    {
        return super.aProtectedMethod() * 10000;
    }

    public static void main( String[] args )
    {
        ChildClass instance = new ChildClass();

        try {
            instance.doCustomization();
            }
        catch( Exception e ) {
            e.printStackTrace();
            }
    }
}
