package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.Mappable;
import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import java.util.Iterator;
import java.util.Map;

public class InfosServletDisplayImpl
    implements cx.ath.choisnet.servlet.debug.InfosServletDisplay
{

    private String title;
    private InfosServletDisplay.Anchor anchor;
    private Map<String,String> map;
    private String messageIfMapEmpty;

    public InfosServletDisplayImpl(String title, InfosServletDisplay.Anchor anchor, Map<String,String> aMap, String messageIfMapEmpty)
    {
    //    0    0:aload_0
    //    1    1:invokespecial   #1   <Method void Object()>
        this.title = title;
    //    2    4:aload_0
    //    3    5:aload_1
    //    4    6:putfield        #2   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.title>
        this.anchor = anchor;
    //    5    9:aload_0
    //    6   10:aload_2
    //    7   11:putfield        #3   <Field cx.ath.choisnet.servlet.debug.InfosServletDisplay$Anchor cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.anchor>
        map = aMap;
    //    8   14:aload_0
    //    9   15:aload_3
    //   10   16:putfield        #4   <Field java.util.Map cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.map>
        this.messageIfMapEmpty = messageIfMapEmpty;
    //   11   19:aload_0
    //   12   20:aload           4
    //   13   22:putfield        #5   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.messageIfMapEmpty>
    //   14   25:return
    }

    public InfosServletDisplayImpl(String title, final String anchorName, Map<String,String> aMap, String messageIfMapEmpty)
    {
        this(title, new InfosServletDisplay.Anchor() {

//            final String val$anchorName;

            public String getHTMLName()
            {
                return anchorName.replaceAll("[\\)\\(\\.]", "_");
            //    0    0:aload_0
            //    1    1:getfield        #1   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl$1.val$anchorName>
            //    2    4:ldc1            #3   <String "[\\)\\(\\.]">
            //    3    6:ldc1            #4   <String "_">
            //    4    8:invokevirtual   #5   <Method String String.replaceAll(String, String)>
            //    5   11:areturn
            }

            public String getDisplay()
            {
                return anchorName;
            //    0    0:aload_0
            //    1    1:getfield        #1   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl$1.val$anchorName>
            //    2    4:areturn
            }


//            {
//                anchorName = s;
//            //    0    0:aload_0
//            //    1    1:aload_1
//            //    2    2:putfield        #1   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl$1.val$anchorName>
//                super();
//            //    3    5:aload_0
//            //    4    6:invokespecial   #2   <Method void Object()>
//            //    5    9:return
//            }
        }
, aMap, messageIfMapEmpty);
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:new             #6   <Class cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl$1>
    //    3    5:dup
    //    4    6:aload_2
    //    5    7:invokespecial   #7   <Method void InfosServletDisplayImpl$1(String)>
    //    6   10:aload_3
    //    7   11:aload           4
    //    8   13:invokespecial   #8   <Method void InfosServletDisplayImpl(String, cx.ath.choisnet.servlet.debug.InfosServletDisplay$Anchor, java.util.Map, String)>
    //    9   16:return
    }

    public InfosServletDisplayImpl(String title, String anchorName, Map<String,String> aMap)
    {
        this(title, anchorName, aMap, null);
    }

    public InfosServletDisplayImpl(String title, String anchorName, Mappable aMappableObject)
    {
        this(title, anchorName, aMappableObject.toMap());
    }

    public InfosServletDisplay put(String key, String value)
    {
        map.put(key, value);

        return this;
    }

    public InfosServletDisplay.Anchor getAnchor()
    {
        return anchor;
    }

    public void appendHTML(Appendable out)
    {
        try
        {
            out.append("<br /><hr /><br />\n");
    //    0    0:aload_1
    //    1    1:ldc1            #13  <String "<br /><hr /><br />\n">
    //    2    3:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //    3    8:pop
            out.append((new StringBuilder()).append("<a name=\"").append(anchor.getHTMLName()).append("\"><!-- --></a>\n").toString());
    //    4    9:aload_1
    //    5   10:new             #15  <Class StringBuilder>
    //    6   13:dup
    //    7   14:invokespecial   #16  <Method void StringBuilder()>
    //    8   17:ldc1            #17  <String "<a name=\"">
    //    9   19:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   10   22:aload_0
    //   11   23:getfield        #3   <Field cx.ath.choisnet.servlet.debug.InfosServletDisplay$Anchor cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.anchor>
    //   12   26:invokeinterface #19  <Method String cx.ath.choisnet.servlet.debug.InfosServletDisplay$Anchor.getHTMLName()>
    //   13   31:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   14   34:ldc1            #20  <String "\"><!-- --></a>\n">
    //   15   36:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   16   39:invokevirtual   #21  <Method String StringBuilder.toString()>
    //   17   42:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //   18   47:pop
            out.append((new StringBuilder()).append("<h2>").append(title).append("</h2>\n").toString());
    //   19   48:aload_1
    //   20   49:new             #15  <Class StringBuilder>
    //   21   52:dup
    //   22   53:invokespecial   #16  <Method void StringBuilder()>
    //   23   56:ldc1            #22  <String "<h2>">
    //   24   58:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   25   61:aload_0
    //   26   62:getfield        #2   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.title>
    //   27   65:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   28   68:ldc1            #23  <String "</h2>\n">
    //   29   70:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   30   73:invokevirtual   #21  <Method String StringBuilder.toString()>
    //   31   76:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //   32   81:pop
            out.append((new StringBuilder()).append("<table border=\"1\" cellpadding=\"3\" summary=\"").append(title).append("\">\n").toString());
    //   33   82:aload_1
    //   34   83:new             #15  <Class StringBuilder>
    //   35   86:dup
    //   36   87:invokespecial   #16  <Method void StringBuilder()>
    //   37   90:ldc1            #24  <String "<table border=\"1\" cellpadding=\"3\" summary=\"">
    //   38   92:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   39   95:aload_0
    //   40   96:getfield        #2   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.title>
    //   41   99:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   42  102:ldc1            #25  <String "\">\n">
    //   43  104:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   44  107:invokevirtual   #21  <Method String StringBuilder.toString()>
    //   45  110:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //   46  115:pop
            if(map.size() == 0)
    //*  47  116:aload_0
    //*  48  117:getfield        #4   <Field java.util.Map cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.map>
    //*  49  120:invokeinterface #26  <Method int java.util.Map.size()>
    //*  50  125:ifne            172
            {
                if(messageIfMapEmpty != null)
    //*  51  128:aload_0
    //*  52  129:getfield        #5   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.messageIfMapEmpty>
    //*  53  132:ifnull          265
                {
                    out.append((new StringBuilder()).append("<tr><td class=\"message\" colspan=\"2\">").append(messageIfMapEmpty).append("</td></tr>\n").toString());
    //   54  135:aload_1
    //   55  136:new             #15  <Class StringBuilder>
    //   56  139:dup
    //   57  140:invokespecial   #16  <Method void StringBuilder()>
    //   58  143:ldc1            #27  <String "<tr><td class=\"message\" colspan=\"2\">">
    //   59  145:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   60  148:aload_0
    //   61  149:getfield        #5   <Field String cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.messageIfMapEmpty>
    //   62  152:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   63  155:ldc1            #28  <String "</td></tr>\n">
    //   64  157:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   65  160:invokevirtual   #21  <Method String StringBuilder.toString()>
    //   66  163:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //   67  168:pop
                }
            } else
    //*  68  169:goto            265
            {
                Map.Entry<String,String> entry;
                for(Iterator<Map.Entry<String,String>> i$ = map.entrySet().iterator(); i$.hasNext(); out.append((new StringBuilder()).append("<tr><td class=\"name\">").append((String)entry.getKey()).append("</td><td class=\"value\">").append((String)entry.getValue()).append("</td></tr>\n").toString()))
    //*  69  172:aload_0
    //*  70  173:getfield        #4   <Field java.util.Map cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl.map>
    //*  71  176:invokeinterface #29  <Method java.util.Set java.util.Map.entrySet()>
    //*  72  181:invokeinterface #30  <Method java.util.Iterator java.util.Set.iterator()>
    //*  73  186:astore_2
    //*  74  187:aload_2
    //*  75  188:invokeinterface #31  <Method boolean java.util.Iterator.hasNext()>
    //*  76  193:ifeq            265
                {
                    entry = i$.next();
    //   77  196:aload_2
    //   78  197:invokeinterface #32  <Method Object java.util.Iterator.next()>
    //   79  202:checkcast       #33  <Class java.util.Map$Entry>
    //   80  205:astore_3
                }

    //   81  206:aload_1
    //   82  207:new             #15  <Class StringBuilder>
    //   83  210:dup
    //   84  211:invokespecial   #16  <Method void StringBuilder()>
    //   85  214:ldc1            #34  <String "<tr><td class=\"name\">">
    //   86  216:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   87  219:aload_3
    //   88  220:invokeinterface #35  <Method Object java.util.Map$Entry.getKey()>
    //   89  225:checkcast       #36  <Class String>
    //   90  228:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   91  231:ldc1            #37  <String "</td><td class=\"value\">">
    //   92  233:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   93  236:aload_3
    //   94  237:invokeinterface #38  <Method Object java.util.Map$Entry.getValue()>
    //   95  242:checkcast       #36  <Class String>
    //   96  245:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   97  248:ldc1            #28  <String "</td></tr>\n">
    //   98  250:invokevirtual   #18  <Method StringBuilder StringBuilder.append(String)>
    //   99  253:invokevirtual   #21  <Method String StringBuilder.toString()>
    //  100  256:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //  101  261:pop
            }
    //* 102  262:goto            187
            out.append("</table>\n");
    //  103  265:aload_1
    //  104  266:ldc1            #39  <String "</table>\n">
    //  105  268:invokeinterface #14  <Method Appendable Appendable.append(java.lang.CharSequence)>
    //  106  273:pop
        }
    //* 107  274:goto            287
        catch(java.io.IOException hideException)
    //* 108  277:astore_2
        {
            throw new RuntimeException(hideException);
    //  109  278:new             #41  <Class RuntimeException>
    //  110  281:dup
    //  111  282:aload_2
    //  112  283:invokespecial   #42  <Method void RuntimeException(Throwable)>
    //  113  286:athrow
        }
    //  114  287:return
    }
}
