// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl) 
// Source File Name:   HTMLWritable.java

package deprecated.cx.ath.choisnet.html;


// Referenced classes of package cx.ath.choisnet.html:
//            HTMLDocumentException, HTMLDocumentWriter

public interface HTMLWritable
{

    public abstract void writeHTML(HTMLDocumentWriter htmldocumentwriter)
        throws HTMLDocumentException;
}
