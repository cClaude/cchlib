// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   SimpleServletContext.java

package cx.ath.choisnet.servlet;


// Referenced classes of package cx.ath.choisnet.servlet:
//            ServletContextParamNotFoundException

public interface SimpleServletContext
{

    public abstract String getInitParameter(String s)
        throws cx.ath.choisnet.servlet.ServletContextParamNotFoundException;

    public abstract String getInitParameter(String s, String s1);
}
