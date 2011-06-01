package cx.ath.choisnet.testcase;

import java.io.Serializable;

/**
 *
 * @author Claude CHOISNET
 */
public class MyImpl implements MyInterface, Serializable, MyInterfaceSerializable
{
    private static final long serialVersionUID = 3L;
    private int a;
    private String b;
    public MyImpl()
    {
    }

    public MyImpl( int a, String b )
    {
        this( b );
        setA( a );
    }

    public MyImpl( String b )
    {
        this.b = b;
    }

    @Override
    public int getA()
    {
        return a;
    }

    final public void setA( int a )
    {
        this.a = a;
    }

    @Override
    public String getB()
    {
        return b;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "MyImpl [a=" + a + ", b=" + b + ", getA()="
                + getA() + ", getB()=" + getB() + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + a;
        result = prime * result + ((b == null) ? 0 : b.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( !(obj instanceof MyImpl) ) {
            return false;
        }
        MyImpl other = (MyImpl)obj;
        if( a != other.a ) {
            return false;
        }
        if( b == null ) {
            if( other.b != null ) {
                return false;
            }
        } else if( !b.equals( other.b ) ) {
            return false;
        }
        return true;
    }

}
