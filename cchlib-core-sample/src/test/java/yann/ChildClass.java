package yann;

import java.lang.reflect.InvocationTargetException;

public class ChildClass extends ParentClass
{
    private ParentAccess<ParentClass> parentAcces = new ParentAccess<ParentClass>( ParentClass.class );

    public ChildClass()
    {
        super();
    }

    public void doCusto() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        System.out.println( this.parentAcces );
        System.out.println( "toString()" + this );

        this.parentAcces.setObject( this, "aLong", new Long( 1000 ) );

        String getAString = this.parentAcces.Call( this, String.class, "getAString" );

        System.out.println( "getAString=" + getAString );

        this.parentAcces.Call( this, Void.class, "setAString", "new value" );

        System.out.println( "toString()" + this );
    }


    public static void main( String[] args )
    {
        ChildClass instance = new ChildClass();

        try {
            instance.doCusto();
            }
        catch( Exception e ) {
            e.printStackTrace();
            }
    }

    @Override
    protected int aProtectedAbstractMethod()
    {
        return super.aProtectedMethod() * 10000;
    }

}
