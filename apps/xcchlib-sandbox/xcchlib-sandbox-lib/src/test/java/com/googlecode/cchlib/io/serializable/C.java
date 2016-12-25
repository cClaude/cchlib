package cx.ath.choisnet.io;

class C extends AbstractComputeString implements java.io.Serializable, java.util.Comparator<C> {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private final String      content;
    private final String      hashCodeString;
    private A                 a;

    public C( final String content )
    {
        this.content = content;
        this.hashCodeString = "HC:" + this.hashCode();
        this.a = null;
    }

    public C setA( final A a )
    {
        this.a = a;

        return this;
    }

    public A getA()
    {
        return this.a;
    }

    @Override
    public String getContent()
    {
        return this.content;
    }

    @Override
    public String getSubContent( final int maxlength )
    {
        return " A:" + (this.a == null ? "*" : this.a.getFullContent( maxlength - 3 ));
    }

    @Override
    public int compare( final C c1, final C c2 )
    {
        return scompare( c1, c2 );
    }

    public static int scompare( final C c1, final C c2 )
    {
        if( c1 == c2 ) {
            return 0;
        }

        if( c1 == null ) {
            if( c2 == null ) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if( c2 == null ) {
                return 1;
            } else {
                int res = A.compare( c1.content, c2.content );

                if( res == 0 ) {
                    res = A.compare( c1.hashCodeString, c2.hashCodeString );
                }

                if( res == 0 ) {
                    res = A.scompare( c1.a, c2.a );
                }

                return res;
            }
        }
    }

    @Override
    public String toString()
    {
        return "{" + this.getClass().getSimpleName() + "@" + this.hashCode() + " :" + this.content + "," + this.hashCodeString + " (A:"
                + (this.a == null ? "NULL" : this.a.toString()) + ")}";
    }
}