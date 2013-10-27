/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLFormEntry.java
** Description   :
**
**  3.02.034 2006.08.02 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.HTMLDocumentWriter
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLDocumentWriter
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLFormEntry
**
*/
package cx.ath.choisnet.html.util;

import javax.servlet.ServletRequest;
import java.util.Collection;
import cx.ath.choisnet.html.util.FormItem;

/**
** <p>
** Interface commune é tous les objets HTML dépendant d'un formulaire,
** cette interface décris les méthodes permettant de construire
** la page finale é partir des différents éléments ainsi que de récupérer
** les données de cette méme page lorsqu'il s'agit d'un formulaire.
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.034
** @version 3.02.037
*/
public abstract interface HTMLFormEntry<T extends HTMLObjectInterface,U>
    extends
        HTMLObjectInterface<T>,
        HTMLWritable,
        java.io.Serializable
{
/**
** Initialise la valeur (par défaut) de l'entrée du formulaire é partir
** de son type natif
**
** @throws UnsupportedOperationException
*/
public T setCurrentValue( U value ) // ------------------------------------
    throws UnsupportedOperationException;

/**
** Lecture des données la lecture des données depuis la page HTML (formulaire)
**
** @return la valeur
**
** @throws UnsupportedOperationException
** @throws HTMLFormException
*/
public U getCurrentValue( ServletRequest request ) // ---------------------
    throws UnsupportedOperationException, HTMLFormException;

/**
**
*/
public T append( Collection<FormItem> a ); // -----------------------------

} // interface


