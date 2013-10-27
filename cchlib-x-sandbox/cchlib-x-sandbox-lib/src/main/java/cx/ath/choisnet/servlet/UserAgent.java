/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/UserAgent.java
** Description   :
** Encodage      : ANSI
**
**  2.01.030 2005.11.10 Claude CHOISNET - Version initiale
**  3.01.018 2006.04.11 Claude CHOISNET
**                      Inclus maintenant les informations liées é l'OS.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.UserAgent
**
*/
package cx.ath.choisnet.servlet;

/**
** <p>
** Enumération des navigateur WEB connus
** </p>
**
**
** @author  Claude CHOISNET
** @since   2.01.030
** @version 3.01.018
**
** @see cx.ath.choisnet.servlet.SimpleServletRequest
** @see cx.ath.choisnet.servlet.SimpleServletRequest#getUserAgentDetails()
*/
public enum UserAgent
{
    /*
    *
    ** Indique que le navigateur n'a pu étre identifier.
    ** @ de precated ne doit pas doit étre retournée par
    **             {@link cx.ath.choisnet.servlet.SimpleServletRequest#getUserAgentDetails()}
    UNKOWN,
    */

    /**
    ** Navigateur Mozilla ou clone.
    */
    MOZILLA,

    /**
    ** Indique qu'il s'agit du Navigateur Firefox (clone de Mozilla)
    */
    MOZILLA_FIREFOX,

    /**
    ** Navigateur MSIE ou clone.
    */
    MSIE,

    /**
    ** Navigateur Opera.
    */
    OPERA,

    /**
    ** OS non défini pour un Navigateur MSIE.
    */
    MSIE_UNKNOW_OS,

    /**
    ** Identifie un OS Windows
    */
    WINDOWS,

    /**
    ** Identifie Windows Vista : "Windows NT 6.0"
    */
    WINDOWS_VISTA,

    /**
    ** Identifie Windows Server 2003 : "Windows NT 5.2"
    */
    WINDOWS_2003,

    /**
    ** Identifie Windows XP : "Windows NT 5.1"
    */
    WINDOWS_XP,

    /**
    ** Identifie Windows 2000, Service Pack 1 (SP1) : "Windows NT 5.01"
    */
    WINDOWS_2000_SP1,

    /**
    ** Identifie Windows 2000 (inclu SP1) : "Windows NT 5.0"
    */
    WINDOWS_2000,

    /**
    ** Identifie Microsoft Windows NT 4.0 : "Windows NT 4.0"
    */
    WINDOWS_NT,

    /**
    ** Identifie Windows Millennium Edition (Windows Me) : "Windows 98; Win 9x 4.90"
    */
    WINDOWS_ME,

    /**
    ** Identifie Windows 98 (inclu ME) : "Windows 98"
    */
    WINDOWS_98,

    /**
    ** Identifie Windows 95 : "Windows 95"
    */
    WINDOWS_95,

    /**
    ** Identifie Windows CE : "Windows CE"
    */
    WINDOWS_CE,

    /**
    ** OS non défini pour un Navigateur MOZILLA.
    */
    MOZILLA_UNKNOW_OS,

    /**
    ** Identifie Macintosh : "Macintosh"
    */
    MACOS,

    /**
    ** Identifie Macintosh é base de processeur Motorola 68k  : "68K"
    */
    MACOS_68K,

    /**
    ** Identifie Macintosh é base de processeur Motorola PowerPC  : "PPC"
    */
    MACOS_PPC,

    /**
    ** Identifie un environment X Window : "X11"
    */
    X11,

    /**
    ** OS non défini pour un Navigateur OPERA.
    */
    OPERA_UNKNOW_OS,

} // enum
