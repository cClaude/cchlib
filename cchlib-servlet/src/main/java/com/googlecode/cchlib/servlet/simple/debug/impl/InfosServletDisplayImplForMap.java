package com.googlecode.cchlib.servlet.simple.debug.impl;

import java.io.IOException;
import java.util.Map;
import com.googlecode.cchlib.servlet.simple.debug.InfosServletDisplay;
import com.googlecode.cchlib.servlet.simple.debug.InfosServletDisplayAnchor;
import com.googlecode.cchlib.util.mappable.Mappable;

//NOT public
class InfosServletDisplayImplForMap
    implements InfosServletDisplay
{
    private final String                      titleEntry;
    private final InfosServletDisplayAnchor   anchorEntry;
    private final Map<String,String>          map;
    private final String                      messageIfMapEmpty;

    InfosServletDisplayImplForMap(
            final String                      title,
            final InfosServletDisplayAnchor   anchor,
            final Map<String,String>          aMap,
            final String                      messageIfMapEmpty
            )
    {
        this.titleEntry         = title;
        this.anchorEntry        = anchor;
        this.map                = aMap;
        this.messageIfMapEmpty  = messageIfMapEmpty;
    }

    InfosServletDisplayImplForMap(
            final String              title,
            final String              anchorName,
            final Map<String,String>  aMap,
            final String              messageIfMapEmpty
            )
    {
        this(
                title,
                new DefaultInfosServletDisplayAnchor( anchorName ),
                aMap,
                messageIfMapEmpty
                );
    }

    InfosServletDisplayImplForMap(
            final String              title,
            final String              anchorName,
            final Map<String,String>  aMap
            )
    {
        this(title, anchorName, aMap, null);
    }

    InfosServletDisplayImplForMap(
            final String      title,
            final String      anchorName,
            final Mappable    aMappableObject
            )
    {
        this( title, anchorName, aMappableObject.toMap() );
    }

    @Override
    public InfosServletDisplay put(final String key, final String value)
    {
        this.map.put(key, value);

        return this;
    }

    @Override
    public InfosServletDisplayAnchor getAnchor()
    {
        return this.anchorEntry;
    }

    @Override
    public void appendHTML(final Appendable out) throws IOException
    {
        final String id = "data" + this.anchorEntry.getId();

        out.append("<br />\n<hr />\n<br />\n<h4>");
        out.append("<input type=\"checkbox\" onclick=\"showhidecheckbox(this,'" )
           .append( id )
           .append( "');\"/>");
        out.append("<a name=\"")
           .append(this.anchorEntry.getId())
           .append("\"><!-- --></a>\n");
        out.append(this.titleEntry).append("</h4>\n");
        out.append("<table style=\"display: none;\" id=\"" )
           .append( id )
           .append( "\" border=\"1\" cellpadding=\"3\" summary=\"")
           .append(this.titleEntry)
           .append("\">\n");

        if(this.map.size() == 0) {
            if(this.messageIfMapEmpty != null) {
                out.append("<tr><td class=\"message\" colspan=\"2\">")
                   .append(this.messageIfMapEmpty)
                   .append("</td></tr>\n");
            }
        }
        else {
            for( final Map.Entry<String,String> entry : this.map.entrySet() ) {
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

