package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.Mappable;
import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import java.io.IOException;
import java.util.Map;

/**
 * TODO: doc!
 * 
 * @author Claude CHOISNET
 *
 */
public class InfosServletDisplayImpl
    implements InfosServletDisplay
{
    private String                      title;
    private InfosServletDisplay.Anchor  anchor;
    private Map<String,String>          map;
    private String                      messageIfMapEmpty;

    /**
     * 
     * @param title
     * @param anchor
     * @param aMap
     * @param messageIfMapEmpty
     */
    public InfosServletDisplayImpl(
            String                      title, 
            InfosServletDisplay.Anchor  anchor, 
            Map<String,String>          aMap,
            String                      messageIfMapEmpty
            )
    {
        this.title              = title;
        this.anchor             = anchor;
        this.map                = aMap;
        this.messageIfMapEmpty  = messageIfMapEmpty;
    }

    public InfosServletDisplayImpl(
            String              title,
            final String        anchorName, 
            Map<String,String>  aMap,
            String              messageIfMapEmpty
            )
    {
        this(
                title, 
                new InfosServletDisplay.Anchor() {
                    public String getHTMLName()
                    {
                        return anchorName.replaceAll("[\\)\\(\\.]", "_");
                    }
                    public String getDisplay()
                    {
                        return anchorName;
                    }
                },
                aMap,
                messageIfMapEmpty
                );
    }

    public InfosServletDisplayImpl(
            String              title, 
            String              anchorName, 
            Map<String,String>  aMap
            )
    {
        this(title, anchorName, aMap, null);
    }

    public InfosServletDisplayImpl(
            String      title, 
            String      anchorName, 
            Mappable    aMappableObject
            )
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
        try {
            out.append("<br /><hr /><br />\n");
            out.append("<a name=\"").append(anchor.getHTMLName()).append("\"><!-- --></a>\n");
            out.append("<h2>").append(title).append("</h2>\n");
            out.append("<table border=\"1\" cellpadding=\"3\" summary=\"").append(title).append("\">\n");

            if(map.size() == 0) {
                if(messageIfMapEmpty != null) {
                    out.append("<tr><td class=\"message\" colspan=\"2\">").append(messageIfMapEmpty).append("</td></tr>\n");
                }
            } 
            else {
                for( Map.Entry<String,String> entry : map.entrySet() ) {
                    out.append("<tr><td class=\"name\">").append(entry.getKey()).append("</td><td class=\"value\">").append(entry.getValue()).append("</td></tr>\n");
                }
            }

            out.append("</table>\n");
        }
        catch(IOException hideException) {
            throw new RuntimeException(hideException);
        }
    }
}
