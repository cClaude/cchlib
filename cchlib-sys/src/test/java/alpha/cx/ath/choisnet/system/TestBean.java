package alpha.cx.ath.choisnet.system;

import java.io.Serializable;

public class TestBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int anInt;
    private String aString;

    public TestBean( int anInt, String aString )
    {
        this.anInt    = anInt;
        this.aString  = aString;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((aString == null) ? 0 : aString.hashCode());
        result = (prime * result) + anInt;
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( !(obj instanceof TestBean) ) return false;
        TestBean other = (TestBean)obj;
        if( aString == null ) {
            if( other.aString != null ) return false;
        } else if( !aString.equals( other.aString ) ) return false;
        if( anInt != other.anInt ) return false;
        return true;
    }
}
