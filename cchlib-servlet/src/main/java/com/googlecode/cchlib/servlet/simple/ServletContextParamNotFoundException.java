// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   ServletContextParamNotFoundException.java

package com.googlecode.cchlib.servlet.simple;


// Referenced classes of package cx.ath.choisnet.servlet:
//            SimpleServletContextException

public class ServletContextParamNotFoundException extends com.googlecode.cchlib.servlet.simple.SimpleServletContextException
{

    private static final long serialVersionUID = 1L;

    public ServletContextParamNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:aload_2
    //    3    3:invokespecial   #1   <Method void SimpleServletContextException(String, Throwable)>
    //    4    6:return
    }

    public ServletContextParamNotFoundException(String message)
    {
        super(message);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #2   <Method void SimpleServletContextException(String)>
    //    3    5:return
    }

    public ServletContextParamNotFoundException(Throwable cause)
    {
        super(cause);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:invokespecial   #3   <Method void SimpleServletContextException(Throwable)>
    //    3    5:return
    }
}
