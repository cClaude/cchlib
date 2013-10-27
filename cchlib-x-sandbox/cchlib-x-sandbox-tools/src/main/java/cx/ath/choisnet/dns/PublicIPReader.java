/*
** -----------------------------------------------------------------------
** Nom           : cx\ath\choisnet\dns\PublicIPReaderInterface.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2005.02.27 Claude CHOISNET
**                  Nom: cx.ath.choisnet.dns.PublicIPReaderInterface
**  1.01 2006.03.26 Claude CHOISNET
**                  Ajout de extends java.io.Serializable
**  1.02 2006.03.26 Claude CHOISNET
**                  Nouveau nom: cx.ath.choisnet.dns.PublicIPReader
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.dns.PublicIPReader
**
*/
package cx.ath.choisnet.dns;

/**
**
*/
public interface PublicIPReader
    extends java.io.Serializable
{

/**
** <p>
**  Retrouve la valeur getCurrentPublicIP() lors du dernier appel é
**  la méthode storePublicIP()
** </p>
**
** @return un chaéne contenant l'IP courante sous la forme 10.11.12.13
*/
public String getPreviousPublicIP() // ------------------------------------
    throws PublicIPException;

/**
** Sauvegarde la valeur donnée par l'appel é la méthode getCurrentPublicIP()
*/
public void storePublicIP() // --------------------------------------------
    throws PublicIPException;

/**
** <p>Détermine l'adresse IP courante</p>
**
** @return un chaéne contenant l'IP courante sous la forme 10.11.12.13
**         retourne null s'il n'y a oas d'adresse IP Publique (non connecté)
*/
public String getCurrentPublicIP() // -------------------------------------
    throws PublicIPException;

} // interface
