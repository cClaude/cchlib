/*
** $VER: InfosServletDisplay.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/InfosServletDisplay.java
** Description   :
** Encodage      : ANSI
**
**  2.01.032 2005.11.21 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.InfosServletDisplay
**
*/
package cx.ath.choisnet.servlet.debug;

/**
**
**
** @author Claude CHOISNET
** @since   2.01.032
** @version 2.01.032
*/
public interface InfosServletDisplay
{
    /**
    **
    */
    interface Anchor
    {
        /**
        ** Nom HTML de l'ancre
        */
        public String getHTMLName();

        /**
        ** Valeur d'affichage de l'ancre.
        */
        public String getDisplay();
    }
/**
**
*/
public Anchor getAnchor(); // ---------------------------------------------

/**
**
*/
public InfosServletDisplay put( String key, String value ); // ------------

/**
**
*/
public void appendHTML( Appendable out ); // ------------------------------

} // class

