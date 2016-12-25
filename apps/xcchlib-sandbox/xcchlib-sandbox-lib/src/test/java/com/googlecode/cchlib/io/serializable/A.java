package com.googlecode.cchlib.io.serializable;

import java.io.Serializable;
import java.util.Comparator;

class A extends AbstractComputeString implements Serializable, Comparator<A>
{
    private static final long serialVersionUID = 1L;

    private final String      content;
    private final String      hashCodeString;
    private B                 b;
    private C                 c;

    public A( final String content )
    {
        this.content       = content;
        this.b              = null;
        this.c              = null;
        this.hashCodeString = "HC:" + this.hashCode();
    }

    public A setB( final B b )
    {
        this.b = b;

        return this;
    }

    public A setC( final C c )
    {
        this.c = c;

        return this;
    }

    public C getC()
    {
        return this.c;
    }

    public A getCA()
    {
        final C c = this.getC();

        return c == null ? null : c.getA();
    }

    @Override
    public String getContent()
    {
        return this.content;
    }

    @Override
    public String getSubContent( final int maxlength )
    {
        final StringBuilder sb = new StringBuilder( " B:" );

        sb.append( this.b == null ? "*" : this.b.getFullContent( maxlength - 3 ) );

        sb.append( "; C:" );

        final int len = sb.length();

        sb.append( this.c == null ? "*" : this.c.getFullContent( maxlength - len ) );

        return sb.toString();
    }

    @SuppressWarnings("boxing")
    @Override
    public String toString()
    {
        return "{"
                + this.getClass().getSimpleName()
                + "@" + this.hashCode() + " :"
                + this.getContent() + ","
                + this.hashCodeString
                + " (B:" + (this.b == null ? "NULL" : this.b.hashCode())
                + ")(C:" + (this.c == null ? "NULL" : this.c.toString())
                + ")}";
    }

    public static final int compare( final String s1, final String s2 )
    {
        if( s1 == null ) {
            if( s2 == null ) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if( s2 == null ) {
                return 1;
            } else {
                return s1.compareTo( s2 );
            }
        }
    }

    @Override
    public int compare( final A a1, final A a2 )
    {
        return scompare( a1, a2 );
    }

    public static int scompare( final A a1, final A a2 )
    {
        if( a1 == a2 ) {
            return 0;
        }

        if( a1 == null ) {
            if( a2 == null ) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if( a2 == null ) {
                return 1;
            } else {
                int res = A.compare( a1.content, a2.content );

                if( res == 0 ) {
                    res = A.compare( a1.hashCodeString, a2.hashCodeString );
                }

                // Traitement du champ : B b - anti recursion infinie
                if( res == 0 ) {
                    if( (a1 != a1.b) || (a2 != a2.b) ) {
                        res = A.scompare( a1.b, a2.b );
                    }
                }

                // Traitement du champ : C c - anti recursion infinie
                if( res == 0 ) {
                    if( (a1 != a1.getCA()) || (a2 != a2.getCA()) ) {
                        res = C.scompare( a1.c, a2.c );
                    }
                }

                return res;
            }
        }
    }
}
