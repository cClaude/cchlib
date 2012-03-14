package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.Mappable;
import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import cx.ath.choisnet.servlet.debug.InfosServletDisplayAnchor;

import java.io.IOException;
import java.util.Map;

/**
 * TODOC
 *
 */
public class InfosServletDisplayImpl
    implements InfosServletDisplay
{
    private String                      title;
    private InfosServletDisplayAnchor   anchor;
    private Map<String,String>          map;
    private String                      messageIfMapEmpty;

    /**
     * TODOC
     *
     * @param title
     * @param anchor
     * @param aMap
     * @param messageIfMapEmpty
     */
    public InfosServletDisplayImpl(
            final String                      title,
            final InfosServletDisplayAnchor   anchor,
            final Map<String,String>          aMap,
            final String                      messageIfMapEmpty
            )
    {
        this.title              = title;
        this.anchor             = anchor;
        this.map                = aMap;
        this.messageIfMapEmpty  = messageIfMapEmpty;
    }

    /**
     * TODOC
     *
     * @param title
     * @param anchorName
     * @param aMap
     * @param messageIfMapEmpty
     */
    public InfosServletDisplayImpl(
            final String              title,
            final String              anchorName,
            final Map<String,String>  aMap,
            final String              messageIfMapEmpty
            )
    {
        this(
                title,
                new InfosServletDisplayAnchor()
                {
                    @Override
                    public String getDisplay()
                    {
                        return anchorName;
                    }
                    @Override
                    public String getId()
                    {
                        return anchorName.replaceAll("[\\)\\(\\.]", "_");
                    }
                },
                aMap,
                messageIfMapEmpty
                );
    }

    /**
     * TODOC
     *
     * @param title
     * @param anchorName
     * @param aMap
     */
    public InfosServletDisplayImpl(
            final String              title,
            final String              anchorName,
            final Map<String,String>  aMap
            )
    {
        this(title, anchorName, aMap, null);
    }

    /**
     * TODOC
     *
     * @param title
     * @param anchorName
     * @param aMappableObject
     */
    public InfosServletDisplayImpl(
            final String      title,
            final String      anchorName,
            final Mappable    aMappableObject
            )
    {
        this(title, anchorName, aMappableObject.toMap());
    }

    @Override
    public InfosServletDisplay put(final String key, final String value)
    {
        map.put(key, value);

        return this;
    }

    @Override
    public InfosServletDisplayAnchor getAnchor()
    {
        return anchor;
    }

    @Override
    public void appendHTML(final Appendable out) throws IOException
    {
        final String id = "data" + anchor.getId();

        out.append("<br />\n<hr />\n<br />\n<h4>");
        out.append("<input type=\"checkbox\" onclick=\"showhidecheckbox(this,'" )
           .append( id )
           .append( "');\"/>");
        out.append("<a name=\"")
           .append(anchor.getId())
           .append("\"><!-- --></a>\n");
        out.append(title).append("</h4>\n");
        out.append("<table style=\"display: none;\" id=\"" )
           .append( id )
           .append( "\" border=\"1\" cellpadding=\"3\" summary=\"")
           .append(title)
           .append("\">\n");

        if(map.size() == 0) {
            if(messageIfMapEmpty != null) {
                out.append("<tr><td class=\"message\" colspan=\"2\">")
                   .append(messageIfMapEmpty)
                   .append("</td></tr>\n");
            }
        }
        else {
            for( Map.Entry<String,String> entry : map.entrySet() ) {
                out.append("<tr><td class=\"name\">")
                   .append(entry.getKey())
                   .append("</td><td class=\"value\">")
                   .append(entry.getValue())
                   .append("</td></tr>\n");
            }
        }

        out.append("</table>\n");
    }

    /**
     *
     * @param out
     * @throws IOException
     */
    public static void appendJS(final Appendable out)
        throws IOException
    {
        out.append( "<script type=\"text/javascript\">\n"
                + "function show(divref)"
                + "{"
                + "var ele = document.getElementById(divref);"
                + "ele.style.display = \"block\";"
                + "}"
                + "\n"
                + "function hide(divref)"
                + "{"
                + "var ele = document.getElementById(divref);"
                + "ele.style.display = \"none\";"
                + "}"
                + "\n"
                + "function showhidecheckbox(checkbox,divref)"
                + "{"
                + "if(checkbox.checked) {"
                + "show(divref);"
                + "}"
                + "else {"
                + "hide(divref);"
                + "}"
                + "}\n"
                + "</script>\n"
            );
    }
}

