/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/tools/servlet/InstantMailServlet.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2005.03.17 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.servlet.InstantMailServlet
**
*/
package cx.ath.choisnet.tools.servlets;

import cx.ath.choisnet.sql.SimpleDataSourceException;
import cx.ath.choisnet.sql.SimpleQuery;
import cx.ath.choisnet.sql.SimpleUpdate;
import cx.ath.choisnet.sql.mysql.MySQLTools;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
** <P><B>Servlet :</B> InstantMailServlet</P>
**
**
** <B>Configuration :</B><BR>
**
** <XMP>
** </XMP>
**
**
** @author Claude CHOISNET
** @version 1.00
*/
public class InstantMailServlet
    extends HttpServlet
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
**
*/
@Override
public void service( // ---------------------------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws
        javax.servlet.ServletException,
        java.io.IOException
{
 final String action    = request.getParameter( "ACTION" );
 final String nextURL   = request.getParameter( "NEXTURL" );

 if( "CREATEUSER".equals( action ) ) {
    tryCreateUser( request );
    }
 else {
    getLogger().error( "ACTION = [" + action + "]" );
    }
/*
 HttpSession session = request.getSession();

 session.setAttribute( "RESULT"     , Integer.toString( result ) );
 session.setAttribute( "EXCEPTION"  , exception );
*/
 this.getServletContext().getRequestDispatcher( nextURL ).forward( request, response );
}

/**
**
*/
private void tryCreateUser( // --------------------------------------------
    HttpServletRequest  request
    )
{
 final String   nickname    = MySQLTools.parseFieldValue( request.getParameter( "NICKNAME" ) );
 HttpSession    session     = request.getSession();

 session.setAttribute( "EXCEPTION", null );

 try {
    final String uuid = findUser( nickname );

    if( uuid == null ) {
        //
        // L'utilisateur n'existe pas
        //
        String result = createUser( request, nickname );

        session.setAttribute( "OK", result );
        }
    else {
        //
        // L'utilisateur existe deje
        //

        session.setAttribute( "Message", "Creation impossible - L'utilisateur existe deje" );
        session.setAttribute( "OK", "KO" );
        }

    }
 catch( Exception e ) {
    getLogger().warn( "Probleme lors de l'ajout de : " + nickname, e );

    //result      = -1;
    //exception   = e;

    session.setAttribute( "EXCEPTION", e );
    session.setAttribute( "OK", "KO" );
    session.setAttribute( "Message", "Creation impossible" );
    }
}

/**
 * @throws SimpleDataSourceException 
**
*/
final String findUser( final String nickName ) // -------------------------
    throws
        java.sql.SQLException, SimpleDataSourceException
{
 final String query = "SELECT `uuid` FROM `im_users` WHERE `nickname`='" + nickName + "'";

 SimpleQuery    simpleQuery = null;
 String         result;

 try {
    simpleQuery = new SimpleQuery( "jdbc/tools-resource" );

    List<String> list = SimpleQuery.translateResultSetToStringList( simpleQuery.executeQuery( query ) );

    switch( list.size() ) {
        case 0 : // Non trouvé
            result = null;
            break;

        case 1 : // Trouvé, c'est le 1er élément
            result = list.get(0);
            break;

        default : // Trouvé plusieurs, impossible !
            throw new RuntimeException( "more than one user with same name" );
        }
    }
 finally {
    if( simpleQuery != null ) {
        try { simpleQuery.close(); } catch( java.io.IOException ignore ) {}
        }
    }

 return result;
}

/**
 * @throws SimpleDataSourceException 
**
*/
private String createUser( // ---------------------------------------------
    HttpServletRequest  request,
    final String        nickname
    )
    throws
        java.sql.SQLException, SimpleDataSourceException
{
 final String password      = MySQLTools.parseFieldValue( request.getParameter( "PASSWORD" ) );
 final String autoIpAddress = request.getRemoteAddr();

 final String query
    = "INSERT INTO `im_users` (`CreateDate`,`CreateIp`, "
        + "`nickname`, `password`) "
        + "VALUES ( NOW(),'" + autoIpAddress + "', "
        + "'" + nickname + "', '" + password + "');";

 SimpleUpdate   simpleUpdate    = null;
 int            doUpdateResult  = -1;

 try {
    simpleUpdate = new SimpleUpdate( "jdbc/tools-resource" );

    doUpdateResult = simpleUpdate.doUpdate( query );
    }
 finally {
    if( simpleUpdate != null ) {
        try { simpleUpdate.close(); } catch( java.io.IOException ignore ) {}
        }
    }

 return doUpdateResult == 1 ? "OK" : "KO";
}


/**
** Gestion des traces
*/
final protected org.apache.log4j.Logger getLogger() // --------------------
{
 return org.apache.log4j.Logger.getLogger( this.getClass() );
}

} // class

/*
            <input name="NICKNAME" type="TEXT" value="" size="32" maxlength="32" />
          </td>
        </tr>
        <tr>
          <td>Mot de passe</td>
          <td>
            <input name="PASSWORD" type="PASSWORD" value="" size="32" maxlength="32" />
            <br />
            7 caracteres ou plus incluant des chiffres et des lettres
          </td>
        </tr>
        <tr>
          <td>Veuillez resaisir votre mot de passe</td>
          <td>
            <input name="PASSWORD2" type="PASSWORD" value="" size="32" maxlength="32" />
          </td>
        </tr>
        <tr>
          <td>X</td>
          <td>
            <input type="SUBMIT" value=" Enregistrer " />


CREATE TABLE `im_users` (
  `uuid`        int(10) unsigned    NOT NULL auto_increment COMMENT 'User UID',
  `nickname`    varchar(32)         NOT NULL default '',
  `password`    varchar(32)         NOT NULL default '',
  `active`      tinyint             NOT NULL default '1',
  `CreateDate`  datetime            NOT NULL default '0000-00-00 00:00:00',
  `CreateIp`    char(16)            NOT NULL default '000.000.000.000',
  UNIQUE  KEY  `nickname` (`nickname`),
  PRIMARY KEY  (`uuid`),
  PRIMARY KEY  (`nickname`)

*/
