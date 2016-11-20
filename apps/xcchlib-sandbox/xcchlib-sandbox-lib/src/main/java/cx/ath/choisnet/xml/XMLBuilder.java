/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/xml/XMLBuilder.java
** Description   :
** Encodage      : ANSI
**
**  2.01.001 2005.10.02 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.xml.XMLBuilder
**
*/
package cx.ath.choisnet.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
**
**
** @author Claude CHOISNET
** @since   2.01.001
** @version 2.01.001
**
** @see XMLDOMTools
*/
public class XMLBuilder
{
/** */
private final static String DEFAULT_TABULATION = "  ";

/** */
private Appendable anAppendableObject;

/** */
private String incTabulation;

/** */
private String tabulation;

/**
**
*/
public XMLBuilder( Appendable anAppendableObject ) // ---------------------
{
 this( anAppendableObject, "", DEFAULT_TABULATION );
}

/**
**
*/
public XMLBuilder( // -----------------------------------------------------
    Appendable  anAppendableObject,
    String      iniTabulation,
    String      incTabulation
    )
{
 this.anAppendableObject    = anAppendableObject;
 this.tabulation            = iniTabulation;
 this.incTabulation         = incTabulation;
}

/**
**
*/
public XMLBuilder append( // ----------------------------------------------
    Node aNode
    )
    throws java.io.IOException
{
 if( aNode.getNodeType() == Node.TEXT_NODE ) {
    this.anAppendableObject.append( this.tabulation + "{" + aNode.getTextContent() + "}\n" );
    }
 else {
    NodeList    nodeList    = aNode.getChildNodes();
    final int   len         = nodeList.getLength();

    this.anAppendableObject.append( this.tabulation + "<" + aNode.getNodeName() + ">    (" + len + ")\n" );

    this.append( nodeList );

    this.anAppendableObject.append( this.tabulation + "</" + aNode.getNodeName() + ">\n" );
    }

 return this;
}


/**
**
*/
public XMLBuilder append( // ----------------------------------------------
    NodeList nodeList
    )
    throws java.io.IOException
{
 final int len = nodeList.getLength();

 String saveTabulation = this.tabulation;

 this.tabulation += incTabulation;

 for( int i = 0; i<len; i++ ) {
    append( nodeList.item( i ) );
    }

 this.tabulation = saveTabulation;

 return this;
}

/**
**
**
** @return a String witch is the result of toString() of the Appendable object
*/
public String appendableToString() // -------------------------------------
{
 return this.anAppendableObject.toString();
}

/**
** <b>Debug purpose only</b><br />
**
** Build a String base on giving Node
**
** @see XMLDOMTools#toString( Node )
*/
public static String toString( Node aNode ) // ----------------------------
{
 final XMLBuilder builder = new XMLBuilder( new StringBuilder() );

 try {
    builder.append( aNode );
    }
 catch( java.io.IOException ignore ) {
    // No java.io.IOException using StringBuilder
    }

 return builder.appendableToString();
}

/**
** <b>Debug purpose only</b><br />
**
** Build a String base on giving NodeList
*/
public static String toString( NodeList nodeList ) // ---------------------
{
 final StringBuilder    sb      = new StringBuilder();
 final XMLBuilder       builder = new XMLBuilder( sb );

 sb.append( "--------------------\n" );

 try {
    builder.append( nodeList );
    }
 catch( java.io.IOException ignore ) {
    // No java.io.IOException using StringBuilder
    }

 sb.append( "--------------------\n" );

 return sb.toString();
}

} // interface
