// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   SGHidden.java

package deprecated.cx.ath.choisnet.html.gadgets.special;

import deprecated.cx.ath.choisnet.html.HTMLDocumentException;
import deprecated.cx.ath.choisnet.html.HTMLDocumentWriter;
import deprecated.cx.ath.choisnet.html.gadgets.BGInputText;

public class SGHidden extends BGInputText
{

    public SGHidden(String gadgetName, String value)
    {
        super(gadgetName, null, null, value, null);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:aconst_null
    //    3    3:aconst_null
    //    4    4:aload_2
    //    5    5:aconst_null
    //    6    6:invokespecial   #1   <Method void BGInputText(String, Integer, Integer, String, cx.ath.choisnet.html.javascript.AbstractJavascript)>
    //    7    9:return
    }

    public SGHidden(String gadgetName, int value)
    {
        super(gadgetName, null, null, String.valueOf(value), null);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:aconst_null
    //    3    3:aconst_null
    //    4    4:iload_2
    //    5    5:invokestatic    #2   <Method String String.valueOf(int)>
    //    6    8:aconst_null
    //    7    9:invokespecial   #1   <Method void BGInputText(String, Integer, Integer, String, cx.ath.choisnet.html.javascript.AbstractJavascript)>
    //    8   12:return
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        out.write(getHiddenHTMLDatas());
    //    0    0:aload_1
    //    1    1:aload_0
    //    2    2:invokevirtual   #3   <Method String cx.ath.choisnet.html.gadgets.special.SGHidden.getHiddenHTMLDatas()>
    //    3    5:invokeinterface #4   <Method void cx.ath.choisnet.html.HTMLDocumentWriter.write(String)>
    //    4   10:return
    }
}
