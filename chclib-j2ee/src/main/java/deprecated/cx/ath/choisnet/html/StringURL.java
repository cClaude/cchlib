// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl)
// Source File Name:   StringURL.java

package deprecated.cx.ath.choisnet.html;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StringURL
    implements java.io.Serializable
{

    private static final long serialVersionUID = 1L;
    private String baseURL;
    private List<String[]> params;

    public StringURL(String baseURL)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        params = new LinkedList<String[]>();
    //    2    4:aload_0
    //    3    5:new             #2   <Class java.util.LinkedList>
    //    4    8:dup
    //    5    9:invokespecial   #3   <Method void LinkedList()>
    //    6   12:putfield        #4   <Field java.util.LinkedList cx.ath.choisnet.html.StringURL.params>
        this.baseURL = baseURL;
    //    7   15:aload_0
    //    8   16:aload_1
    //    9   17:putfield        #5   <Field String cx.ath.choisnet.html.StringURL.baseURL>
    //   10   20:return
    }

    public StringURL append(String paramName, String paramValue)
    {
        String entry[] = {
            paramName, paramValue
        };
    //    0    0:iconst_2
    //    1    1:anewarray       String[]
    //    2    4:dup
    //    3    5:iconst_0
    //    4    6:aload_1
    //    5    7:aastore
    //    6    8:dup
    //    7    9:iconst_1
    //    8   10:aload_2
    //    9   11:aastore
    //   10   12:astore_3
        params.add(entry);
    //   11   13:aload_0
    //   12   14:getfield        #4   <Field java.util.LinkedList cx.ath.choisnet.html.StringURL.params>
    //   13   17:aload_3
    //   14   18:invokevirtual   #7   <Method boolean java.util.LinkedList.add(Object)>
    //   15   21:pop
        return this;
    //   16   22:aload_0
    //   17   23:areturn
    }

    public StringURL append(String paramName, int paramValue)
    {
        return append(paramName, Integer.toString(paramValue));
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:iload_2
    //    3    3:invokestatic    #8   <Method String Integer.toString(int)>
    //    4    6:invokevirtual   #9   <Method cx.ath.choisnet.html.StringURL cx.ath.choisnet.html.StringURL.append(String, String)>
    //    5    9:areturn
    }

    public StringURL append(String paramName, long paramValue)
    {
        return append(paramName, Long.toString(paramValue));
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:lload_2
    //    3    3:invokestatic    #10  <Method String Long.toString(long)>
    //    4    6:invokevirtual   #9   <Method cx.ath.choisnet.html.StringURL cx.ath.choisnet.html.StringURL.append(String, String)>
    //    5    9:areturn
    }

    public StringURL append(String paramName, boolean paramValue)
    {
        return append(paramName, Boolean.toString(paramValue));
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:iload_2
    //    3    3:invokestatic    #11  <Method String Boolean.toString(boolean)>
    //    4    6:invokevirtual   #9   <Method cx.ath.choisnet.html.StringURL cx.ath.choisnet.html.StringURL.append(String, String)>
    //    5    9:areturn
    }

    private String toString(String paramsSeparator, String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        boolean first;
        if(baseURL.indexOf('?') == -1)
    //*   0    0:aload_0
    //*   1    1:getfield        #5   <Field String cx.ath.choisnet.html.StringURL.baseURL>
    //*   2    4:bipush          63
    //*   3    6:invokevirtual   #12  <Method int String.indexOf(int)>
    //*   4    9:iconst_m1
    //*   5   10:icmpne          18
        {
            first = true;
    //    6   13:iconst_1
    //    7   14:istore_3
        } else
    //*   8   15:goto            20
        {
            first = false;
    //    9   18:iconst_0
    //   10   19:istore_3
        }
        StringBuilder sb = new StringBuilder(baseURL);
    //   11   20:new             #13  <Class StringBuilder>
    //   12   23:dup
    //   13   24:aload_0
    //   14   25:getfield        #5   <Field String cx.ath.choisnet.html.StringURL.baseURL>
    //   15   28:invokespecial   #14  <Method void StringBuilder(String)>
    //   16   31:astore          4
        String param[];
        for(Iterator<String[]> i$ = params.iterator(); i$.hasNext(); sb.append(java.net.URLEncoder.encode(param[1], charsetName)))
    //*  17   33:aload_0
    //*  18   34:getfield        #4   <Field java.util.LinkedList cx.ath.choisnet.html.StringURL.params>
    //*  19   37:invokevirtual   #15  <Method java.util.Iterator java.util.LinkedList.iterator()>
    //*  20   40:astore          5
    //*  21   42:aload           5
    //*  22   44:invokeinterface #16  <Method boolean java.util.Iterator.hasNext()>
    //*  23   49:ifeq            123
        {
            param = i$.next();
    //   24   52:aload           5
    //   25   54:invokeinterface #17  <Method Object java.util.Iterator.next()>
    //   26   59:checkcast       #18  <Class String[]>
    //   27   62:astore          6
            if(first)
    //*  28   64:iload_3
    //*  29   65:ifeq            81
            {
                first = false;
    //   30   68:iconst_0
    //   31   69:istore_3
                sb.append("?");
    //   32   70:aload           4
    //   33   72:ldc1            #19  <String "?">
    //   34   74:invokevirtual   #20  <Method StringBuilder StringBuilder.append(String)>
    //   35   77:pop
            } else
    //*  36   78:goto            88
            {
                sb.append(paramsSeparator);
    //   37   81:aload           4
    //   38   83:aload_1
    //   39   84:invokevirtual   #20  <Method StringBuilder StringBuilder.append(String)>
    //   40   87:pop
            }
            sb.append(param[0]);
    //   41   88:aload           4
    //   42   90:aload           6
    //   43   92:iconst_0
    //   44   93:aaload
    //   45   94:invokevirtual   #20  <Method StringBuilder StringBuilder.append(String)>
    //   46   97:pop
            sb.append("=");
    //   47   98:aload           4
    //   48  100:ldc1            #21  <String "=">
    //   49  102:invokevirtual   #20  <Method StringBuilder StringBuilder.append(String)>
    //   50  105:pop
        }

    //   51  106:aload           4
    //   52  108:aload           6
    //   53  110:iconst_1
    //   54  111:aaload
    //   55  112:aload_2
    //   56  113:invokestatic    #22  <Method String java.net.URLEncoder.encode(String, String)>
    //   57  116:invokevirtual   #20  <Method StringBuilder StringBuilder.append(String)>
    //   58  119:pop
    //*  59  120:goto            42
        return sb.toString();
    //   60  123:aload           4
    //   61  125:invokevirtual   #23  <Method String StringBuilder.toString()>
    //   62  128:areturn
    }

    public String toString(String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        return toString("&", charsetName);
    //    0    0:aload_0
    //    1    1:ldc1            #24  <String "&">
    //    2    3:aload_1
    //    3    4:invokespecial   #25  <Method String cx.ath.choisnet.html.StringURL.toString(String, String)>
    //    4    7:areturn
    }

    public String toHTML(String charsetName)
        throws java.io.UnsupportedEncodingException
    {
        return toString("&amp;", charsetName);
    //    0    0:aload_0
    //    1    1:ldc1            #26  <String "&amp;">
    //    2    3:aload_1
    //    3    4:invokespecial   #25  <Method String cx.ath.choisnet.html.StringURL.toString(String, String)>
    //    4    7:areturn
    }
}
