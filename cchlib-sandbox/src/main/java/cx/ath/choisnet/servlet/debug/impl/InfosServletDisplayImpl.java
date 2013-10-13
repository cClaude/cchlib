/*
** $VER: InfosServletDisplayImpl.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/impl/InfosServletDisplayImpl.java
** Description   :
** Encodage      : ANSI
**
**  2.01.032 2005.11.21 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayImpl
**
*/
package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.Mappable;
import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import java.util.Map;
import java.util.Map.Entry;

/**
**
**
** @author Claude CHOISNET
** @since   2.01.032
** @version 2.01.032
*/
public class InfosServletDisplayImpl
    implements InfosServletDisplay
{

/** */
private String title;

/** */
private Anchor anchor;

/** */
private Map<String,String> map;

/** */
private String messageIfMapEmpty;


/**
**
*/
public InfosServletDisplayImpl( // ----------------------------------------
    final String                title,
    final Anchor                anchor,
    final Map<String,String>    aMap,
    final String                messageIfMapEmpty
    )
{
 this.title             = title;
 this.anchor            = anchor;
 this.map               = aMap;
 this.messageIfMapEmpty = messageIfMapEmpty;
}

/**
**
*/
public InfosServletDisplayImpl( // ----------------------------------------
    final String                title,
    final String                anchorName,
    final Map<String,String>    aMap,
    final String                messageIfMapEmpty
    )
{
 this(
    title,
    new Anchor()
            {
                public String getHTMLName()
                {
                    return anchorName.replaceAll( "[\\)\\(\\.]","_" );
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

/**
**
*/
public InfosServletDisplayImpl( // ----------------------------------------
    final String                title,
    final String                anchorName,
    final Map<String,String>    aMap
    )
{
 this( title, anchorName, aMap, null );
}

/**
**
*/
public InfosServletDisplayImpl( // ----------------------------------------
    final String    title,
    final String    anchorName,
    final Mappable  aMappableObject
    )
{
 this( title, anchorName, aMappableObject.toMap() );
}

/**
**
*/
public InfosServletDisplay put( String key, String value ) // -------------
{
 this.map.put( key, value );

 return this;
}

/**
**
*/
public Anchor getAnchor() // ----------------------------------------------
{
 return this.anchor;
}

/**
**
*/
public void appendHTML( Appendable out ) // -------------------------------
{
 try {
    out.append( "<br /><hr /><br />\n" );
    out.append( "<a name=\"" + this.anchor.getHTMLName() + "\"><!-- --></a>\n" );
    out.append( "<h2>" + title + "</h2>\n" );
    out.append( "<table border=\"1\" cellpadding=\"3\" summary=\"" + title + "\">\n" );

    if( map.size() == 0 ) {
        if( messageIfMapEmpty != null ) {
            out.append( "<tr><td class=\"message\" colspan=\"2\">" + this.messageIfMapEmpty + "</td></tr>\n" );
            }
        }
    else {
        for( Map.Entry<String,String> entry : map.entrySet() ) {
            out.append(
                "<tr><td class=\"name\">"
                    + entry.getKey()
                    + "</td><td class=\"value\">"
                    + entry.getValue()
                    + "</td></tr>\n"
                    );
            }
        }

    out.append( "</table>\n" );
    }
 catch( java.io.IOException hideException ) {
    throw new RuntimeException( hideException );
    }
}

} // class


