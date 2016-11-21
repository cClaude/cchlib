/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/sql/SimpleQuery.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.00.___ 2005.03.15 Claude CHOISNET - Version initiale
 **  1.01.___ 2005.03.24 Claude CHOISNET
 **                      Add constructor form a valid DataSource
 **  2.01.001 2005.10.02 Claude CHOISNET
 **                      Implemente l'interface java.io.Closeable
 **  2.02.009 2005.12.14 Claude CHOISNET
 **                      La classe etend maintenant SimpleDataSource
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.sql.SimpleQuery
 **
 */
package cx.ath.choisnet.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;

/**
 ** <pre>
 * *  SimpleQuery  squery = new SimpleQuery( dataSourceName );
 * *  ResultSet    rst     = squery.executeQuery( "Select * ... " );
 * *
 * *  while( rst.next() ) {
 * *      caisse =  rst.getString( "FIELD" );
 * *      }
 * *
 * *  squery.close();
 * *  squery = null;
 ** </pre>
 ** 
 ** @author Claude CHOISNET
 ** @version 2.02.009
 */
public class SimpleQuery extends SimpleDataSource {
    /** Connection courante e la source de donnees */
    private Connection conn = null;

    /** Support de la requete courante */
    private Statement  stmt = null;

    /**
     ** @param ds
     *            a valid DataSource object
     */
    public SimpleQuery( DataSource ds ) // ------------------------------------
    {
        super( ds );
    }

    /**
     ** @param resourceName
     *            "java:comp/env/jdbc/resourceName"
     */
    public SimpleQuery( String resourceName ) // ------------------------------
            throws SimpleDataSourceException
    {
        super( getDataSource( resourceName ) );
    }

    /**
**
*/
    public ResultSet executeQuery( final String query ) // --------------------
            throws java.sql.SQLException
    {
        ResultSet rset = null;

        try {
            if( this.conn == null ) {
                openConnection();
            }

            if( conn != null ) {
                stmt = conn.createStatement();

                rset = stmt.executeQuery( query );
            }
        }
        finally {
            if( rset == null ) {
                //
                // Il y a eu un probleme
                //
                flush();
            }
        }

        return rset;
    }

    /**
     ** 
     ** @return une liste (List) de String contenant les n valeurs trouvees dans
     *         le ResultSet. Ne retourne jamais null.
     ** 
     ** @throws java.sql.SQLException
     */
    public static List<String> translateResultSetToStringList( // --------------------
            ResultSet rset ) throws java.sql.SQLException
    {
        final List<String> list = new java.util.LinkedList<String>();

        while( rset.next() ) {
            list.add( rset.getString( 1 ) );
        }

        return list;
    }

    /**
     ** 
     ** @return un tableau de String contenant les n valeurs trouvees dans le
     *         ResultSet. Ne retourne jamais null.
     ** 
     ** @throws java.sql.SQLException
     */
    public static String[] translateResultSetToStringArray( // ----------------
            ResultSet rset ) throws java.sql.SQLException
    {
        final List<String> list = translateResultSetToStringList( rset );
        String[] strings = new String[list.size()];
        int i = 0;

        for( java.util.Iterator<String> iter = list.iterator(); iter.hasNext(); ) {
            strings[ i++ ] = (String)iter.next();
        }

        return strings;
    }

    /**
     ** 
     ** @return un tableau de String contenant les n valeurs trouvees e l'aide de
     *         la requete. Ne retourne jamais null.
     ** 
     ** @throws java.sql.SQLException
     */
    public String[] translateQueryToStringArray( // ---------------------------
            final String query ) throws java.sql.SQLException
    {
        ResultSet rst = null;
        final String str[];

        try {
            rst = this.executeQuery( query );
            str = SimpleQuery.translateResultSetToStringArray( rst );
        }
        finally {
            if( rst != null ) {
                try {
                    rst.close();
                }
                catch( Exception ignore ) {}
            }
        }

        return str;
    }

    /**
     ** 
     ** @return un tableau de String contenant les n valeurs trouvees e l'aide de
     *         la requete. Ne retourne jamais null.
     ** 
     ** @throws SimpleQueryException
     ** @throws java.sql.SQLException
     */
    public static String[] translateQueryToStringArray( // --------------------
            final String dataSourceName, final String query )
            throws SimpleDataSourceException, java.sql.SQLException
    {
        SimpleQuery squery = null;
        final String str[];

        try {
            squery = new SimpleQuery( dataSourceName );
            str = squery.translateQueryToStringArray( query );
        }
        finally {
            if( squery != null ) {
                try {
                    squery.close();
                }
                catch( Exception ignore ) {}
            }
        }

        return str;
    }

    /**
     ** 
     ** @throws java.sql.SQLException
     */
    protected void openConnection() // ----------------------------------------
            throws java.sql.SQLException
    {
        if( conn != null ) {
            throw new java.sql.SQLException(
                    "SimpleQuery InvalidState [conn != null]" );
        }

        this.conn = getConnectionFromDataSource(); // getConnection( ds ); //
                                                   // conn = ds.getConnection();
    }

    /**
**
*/
    protected Connection getConnection() // -----------------------------------
    {
        return this.conn;
    }

    /**
**
*/
    protected void closeConnection() // ---------------------------------------
    {
        if( stmt != null ) {
            try {
                stmt.close();
            }
            catch( java.sql.SQLException e ) {
                /* ignore */
            }

            stmt = null;
        }

        if( conn != null ) {
            try {
                conn.close();
            }
            catch( java.sql.SQLException e ) {
                /* ignore */
            }

            conn = null;
        }
    }

    /**
     ** <P>
     * Libere les informations de la requete courante.
     * </P>
     ** 
     ** Autorise l'execution d'une nouvelle requete.
     */
    public void flush() // ----------------------------------------------------
    {
        closeConnection();
    }

    /**
     ** <P>
     * Libere les informations de la requete courante.
     * </P>
     */
    @Override
    public void close() // ----------------------------------------------------
            throws java.io.IOException
    {
        closeConnection();

        super.close();
    }

    /**
     ** Libere les ressources.
     */
    @Override
    protected void finalize() throws Throwable // -----------------------------
    {
        closeConnection();

        super.finalize();
    }

} // class
