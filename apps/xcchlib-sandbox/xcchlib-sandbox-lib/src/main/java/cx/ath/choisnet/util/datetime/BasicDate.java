package cx.ath.choisnet.util.datetime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//** Elle ne gere pas les notions d'heure ou de calendrier, elle pourra etre
//** complete en utilisant les methodes statiques de FullCalendar

/**
 * Cette classe gere les problemes de date au sens commun (jour, mois, annee)
 * <P>
 * Elle ne gere pas les notions d'heure ou de calendrier.
 *
 * <PRE>
 *  Exemple 1:
 *      BasicDate simpledate = new BasicDate();
 *
 *      // Produit une sortie au format yyyyMMdd
 *      System.err.println( "Nous sommes le : " + simpledate );
 * </PRE>
 *
 * <PRE>
 *  Exemple 2: Parcours d'une periode.
 *      BasicDate  firstDayDate = new BasicDate( getDebutDePeriode() );
 *      BasicDate  lastDayDate  = new BasicDate( getFinDePeriode() );
 *
 *      for(    BasicDate currentDay = new BasicDate( firstDayDate );
 *              currentDay.compareTo( lastDayDate ) <= 0;
 *              currentDay.incDay()
 *              ) {
 *          // Reste du traitement : function( currentDay );
 *          }
 *
 * </PRE>
 * <P>
 * NOTE:<br>
 * - Cette classe est {@link Serializable}<br>
 * - Mais cette classe n'est pas "compatible an 10 000" ;-)
 *
 * @since 1.03
 * @see BasicTime
 */
public class BasicDate implements Serializable, DateInterface<BasicDate>
{
    private static final long serialVersionUID = 1L;

    private static final long MILLISECONDS_BY_DAY = (1000L * 60L * 60L * 24L);

    /**
     * {@link DateFormat} format : {@value}
     */
    public static final String           DATEFMT          = "yyyyMMdd";

    /** Integer contenant le numero de l'annee de 0 e 9999 */
    private transient int                 year             = -1;

    /** Integer contenant le numero du mois 1 e 12 */
    private transient int                 month            = -1;

    /** Integer contenant le numero du jour 1 e 31 */
    private transient int                 day              = -1;

    /**
     ** Construit avec la date du jour
     */
    public BasicDate() // -----------------------------------------------------
    {
        this( new java.util.Date() );
    }

    /**
     ** Construit une date BasicDate avec une date standard Java
     **
     ** @param javaDate
     *            Date au format java.util.Date
     **
     ** @see #getJavaDate()
     */
    public BasicDate( final java.util.Date javaDate ) // ----------------------------
    {
        set( javaDate );
    }

    /**
     ** Construit e partir d'un autre object BasicDate
     ** <P>
     **
     * @param date
     *            BasicDate <B>valide</B>
     */
    public BasicDate( final BasicDate date ) // -----------------------------------
    {
        // class_year = new Integer( date.getYear () );
        // class_month = new Integer( date.getMonth() );
        // class_day = new Integer( date.getDay () );
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
    }

    /**
     ** Construit un calendrier avec une chaene au format specifiee
     **
     ** @param date
     *            date sous forme de chaene.
     ** @param fmt
     *            format de la chaene, conformement e la classe BasicDateFormat.
     **
     ** @exception ParseException if any
     */
    public BasicDate( final String date, final SimpleDateFormat fmt ) // -----------------
        throws ParseException
    {
        this( fmt.parse( date ) ); // -> BasicDate( java.util.Date javadate )
    }

    // /*
    // *
    // ** Construit une date BasicDate avec une chaene ayant le format YYYYMMDD
    // **
    // ** @param date date au format YYYYMMDD
    // **
    // ** @exception BasicDateException
    // */
    // public BasicDate( String date ) throws BasicDateException // ------------
    // {
    // if( date.length() != 8 ) {
    // throw new BasicDateException( "BasicDate bad length" );
    // }
    //
    // SimpleDateFormat fmt = new SimpleDateFormat( SIMPLE_DATE_FORMAT );
    //
    // try {
    // set( fmt.parse( date ) );
    // }
    // catch( ParseException bug ) {
    // throw new RuntimeException( "BasicDate( String ) INTERNAL ERROR" );
    // }
    // }

    /**
     * Construit une date BasicDate avec la date specifiee
     *
     * @param year
     *            l'annee
     * @param month
     *            le mois intervalle [1..12]
     * @param day
     *            le jour
     *
     * @see #set(int, int, int )
     *
     * @exception BasicDateException if any
     */
    public BasicDate( final int year, final int month, final int day ) // ----------------------
            throws BasicDateException
    {
        set( year, month, day );
    }

    /**
     * Construit une date BasicDate avec une date SQL
     *
     * @param sqlDate
     *            Date au format java.sql.Date
     *
     * @see #getSQLDate()
     */
    public BasicDate( final java.sql.Date sqlDate ) // -----------------------------
    {
        final String strDate = sqlDate.toString();

        // Formats a date in JDBC date escape format.
        // Returns: a String in yyyy-mm-dd format
        //
        // 0123456789
        // YYYY-MM-DD

        this.year  = Integer.parseInt( strDate.substring( 0, 4 ) );
        this.month = Integer.parseInt( strDate.substring( 5, 7 ) );
        this.day   = Integer.parseInt( strDate.substring( 8 ) );
    }

    /*
     **
     ** @exception BasicDateException
     *                public void set( int year, int month, int day ) // ------------------------ throws
     *                BasicDateException { set( new Integer( year ), new Integer( month ), new Integer( day ) ); }
     */

    /**
     ** Initialise l'objet BasicDate e partir de l'annee, du mois et du jour.
     ** <P>
     * Cette methode lance un exception si les parametres de la date ne sont pas consistant vis-e-vis du calendrier.
     **
     ** @param year
     *            l'annee interval [0..9999]
     ** @param month
     *            le mois interval [1..12]
     ** @param day
     *            le jour interval [1..31] (depend du mois et de l'annee)
     **
     ** @exception BasicDateException if any
     */
    public void set( final int year, final int month, final int day )
        throws BasicDateException
    {
        if( (year < 0) || (year > 9999) ) {
            throw new BasicDateException( "BasicDate 'year' invalid = " + year );
        }

        this.year = year;

        if( (month < 1) || (month > 12) ) {
            throw new BasicDateException( "BasicDate 'month' invalid = " + month );
        }

        this.month = month;

        if( (day < 1) || (day > 31) ) {
            throw new BasicDateException( "BasicDate 'day' invalid = " + day );
        }

        this.day = day;

        check();
    }

    /**
     * Modifie le numero de l'annee de l'objet BasicDate
     *
     * @param year
     *            le numero de l'annee [0..9999]
     *
     * @exception BasicDateException if any
     */
    public void setYear( final int year ) throws BasicDateException // -------------
    {
        set( year, -1, -1 );
    }

    /**
     * Modifie le numero du mois de l'objet BasicDate
     *
     * @param month
     *            le mois [1..12]
     *
     * @exception BasicDateException if any
     */
    public void setMonth( final int month ) throws BasicDateException // -----------
    {
        set( -1, month, -1 );
    }

    /**
     * Modifie le numero du jour de l'objet BasicDate
     *
     * @param day
     *            le jour [1..31]
     *
     * @exception BasicDateException if any
     */
    public void setDay( final int day ) throws BasicDateException // ---------------
    {
        set( -1, -1, day );
    }

    /**
     * Initialise l'objet BasicDate e partir d'un object java.util.Date
     *
     * @param javaDate
     *            date
     */
    public void set( final Date javaDate ) // ----------------------------
    {
        setWithFmtString( new SimpleDateFormat( DATEFMT ).format( javaDate ) );
    }

    /*
     * Initialise l'objet BasicDate avec une String formatee avec le format interne
     */
    protected void setWithFmtString( final String fmtTime ) // ----------------------
    {
        // ** @see #getSimpleDateFormat
        // yyyyMMdd
        // 012345678
        this.year = Integer.parseInt( fmtTime.substring( 0, 4 ) );
        this.month = Integer.parseInt( fmtTime.substring( 4, 6 ) );
        this.day = Integer.parseInt( fmtTime.substring( 6 ) );
    }

    /**
     * retourne le numero du jour.
     *
     * @return le numero du jour [1..31]
     */
    public int getDay()
    {
        return this.day;
    } // -------------------------------

    /**
     ** retourne le numero du mois.
     **
     ** @return le numero du mois [1..12]
     */
    public int getMonth()
    {
        return this.month;
    } // ---------------------------

    /**
     ** retourne le numero de l'annee.
     **
     ** @return le numero de l'annee
     */
    public int getYear()
    {
        return this.year;
    } // -----------------------------

    /**
     ** retourne la date au format java.util.Date.
     **
     ** @return une date au format java.util.Date
     **
     ** @see #BasicDate(java.util.Date )
     */
    public java.util.Date getJavaDate() // ------------------------------------
    {
        try {
            return new SimpleDateFormat( DATEFMT ).parse( this.toString() );
        }
        catch( final ParseException bug ) {
            throw new BasicDateTimeRuntimeException( "BasicDate.getJavaDate() INTERNAL ERROR", bug );
        }
    }

    /**
     ** retourne la date au format java.sql.Date.
     **
     ** @return une date au format java.sql.Date
     **
     ** @see #BasicDate(java.sql.Date )
     */
    public java.sql.Date getSQLDate() // --------------------------------------
    {
        return new java.sql.Date( this.getJavaDate().getTime() );
    }

//    /**
//     ** @see #equals(DateInterface )
//     */
//    @Override
//    public boolean equals( final Object o ) // --------------------------------------
//    {
//        try {
//            return this.equals( (DateInterface)o );
//        }
//        catch( final ClassCastException e ) {
//            return false;
//        }
//    }

    /**
     ** Compare deux BasicDate.
     **
     ** @return true si les 2 dates correspondent au meme jour, false autrement.
     */
    @Override
    public boolean isEqualTo( final BasicDate anotherDate )
    {
        return compareTo( anotherDate ) == 0;
    }

    /**
     ** @see #compareTo(DateInterface ) public int compareTo( Object o ) // ---------------------------------------
     *      throws ClassCastException { return compareTo( (DateInterface)o ); }
     */

    /**
     ** Compare deux BasicDate.
     **
     ** @return la valeur 0 si les 2 dates correspondent au meme jour, une valeur negative si la date de l'object courant
     *         est plus vielle que la date passe en parametre. une valeur positive si la date de l'object courant est
     *         plus recente que la date passe en parametre.
     */
    @Override
    public int compareTo( final BasicDate anotherDate )
    {
        int cmp = this.year - anotherDate.year;

        if( cmp == 0 ) {
            cmp = this.month - anotherDate.month;

            if( cmp == 0 ) {
                cmp = this.day - anotherDate.day;
            }
        }

        return cmp;
    }

    /**
     * Tests if this date is before the specified date.
     *
     * @param anotherDate
     *            a BasicDate.
     *
     * @return true if and only if the instant of time represented by this BasicDate object is strictly earlier than the
     *         instant represented by when; false otherwise.
     */
    @Override
    public boolean isBefore( final BasicDate anotherDate )
    {
        return compareTo( anotherDate ) > 0;
    }

    /**
     * Tests if this date is after the specified date.
     *
     * @param anotherDate
     *            a BasicDate.
     *
     * @return true if and only if the instant represented by thisBasicDate object is strictly later than the instant
     *         represented by when; false otherwise.
     */
    @Override
    public boolean isAfter( final BasicDate anotherDate )
    {
        return compareTo( anotherDate ) < 0;
    }

    /**
     ** Surcharge de la methode toString() de la classe Object, permet d'utiliser la classe comme une chaene. Retourne la
     * date au format interne de la classe BasicDate.
     **
     ** @return la date au format YYYYMMDD
     */
    @Override
    public String toString() // -----------------------------------------------
    {
        return toStringYear() + toStringMonth() + toStringDay();
    }

    public String toStringYear() // -------------------------------------------
    {
        final String yearStr = "000" + this.year;

        return yearStr.substring( yearStr.length() - 4 );
    }

    public String toStringMonth() // ------------------------------------------
    {
        return ((this.month > 9) ? "" : "0") + this.month;
    }

    public String toStringDay() // --------------------------------------------
    {
        return ((this.day > 9) ? "" : "0") + this.day;
    }

    /**
     ** Renvoie la date associee au calendrier conformement e l'oject de formatage.
     **
     ** @param formatter
     *            Object BasicDateFormat contenant les caracteristiques du format attendu.
     **
     ** @return la date formatee
     */
    @Override
    public String toString( final Format formatter ) // -----------------------------
    {
        return formatter.format( this.getJavaDate() );
    }


    /**
     ** NOTE: cette methode devrait retourner le nombre de jour depuis le 1er janvier de l'an 1.
     **
     ** @return le nombre de jour depuis le 1er January 1970,
     */
    @Override
    public long longValue() // ------------------------------------------------
    {
        return this.getJavaDate().getTime() / MILLISECONDS_BY_DAY;
    }

    /**
     ** Ajoute de UN an e la date courante.
     ** <P>
     * Passe automatiquement e l'annee suivante, en cas de depassement de capacite de l'annee (9999) retour e l'an 0.
     */
    public void incYear() // -------------------------------------------------
    {
        int newYear = this.getYear() + 1; // Incremente

        if( newYear > 9999 ) {
            // Afin d'eviter e transmettre une exception, presque inutile ;-)
            newYear = 0;
        }

        try {
            set( newYear, this.getMonth(), this.getDay() );
        }
        catch( final BasicDateException ignore ) {
            throw new BasicDateTimeRuntimeException( "incMonth() INTERNAL ERROR", ignore );
        }
    }

    /**
     ** Ajoute de UN mois e la date courante.
     ** <P>
     * Passe automatiquement e l'annee suivante, en cas de depassement de capacite de l'annee (9999) retour e l'an 0.
     */
    public void incMonth() // -------------------------------------------------
    {
        int newYear  = this.getYear();
        int newMonth = this.getMonth() + 1; // Incremente

        if( newMonth > 12 ) {
            newMonth = 1; // Janvier
            newYear += 1;

            if( newYear > 9999 ) {
                // Afin d'eviter e transmettre une exception, presque inutile ;-)
                newYear = 0;
            }
        }

        try {
            set( newYear, newMonth, this.getDay() );
        }
        catch( final BasicDateException ignore ) {
            throw new BasicDateTimeRuntimeException( "incMonth() INTERNAL ERROR", ignore );
        }
    }

    /**
     * Methode permettant de calculer la duree en jours entre 2 dates BasicDate la date le l'object actuel est la
     * reference de debut de periode
     *
     * <PRE>
     *  // Calcul le nombre de jour d'un mois donne.
     *  BasicDate startDate = new BasicDate( yearNumber, monthNumber, 1 );
     *  BasicDate endDate   = new BasicDate( yearNumber, monthNumber, 1 );
     *  endDate.incMonth();
     *
     * int countOfDayInTheMonth = startDate.countOfDay( endDate );
     * </PRE>
     *
     * @param endOfPeriod
     *            BasicDate initialise e la fin de la periode
     *
     * @return duree (en jours) entre la date de debut et la date de fin
     */
    public int countOfDay( final BasicDate endOfPeriod ) // ------------------------
    {

        final long msBeginOfPeriod = this.getJavaDate().getTime();
        final long dayBeginOfPeriod = msBeginOfPeriod / MILLISECONDS_BY_DAY;

        final long msEndOfPeriod = endOfPeriod.getJavaDate().getTime();
        final long dayEndOfPeriod = msEndOfPeriod / MILLISECONDS_BY_DAY;

        final long countofday = dayEndOfPeriod - dayBeginOfPeriod;

        return (int)countofday;
    }

    /**
**
*/
    protected void check() throws BasicDateException // ----------------------
    {
        //
        // Verification que la date est consistante.
        //
        final BasicDate checkDate = new BasicDate( this.getJavaDate() );

        if( this.day != checkDate.getDay() ) {
            throw new BasicDateException( "BasicDate 'day' invalid = " + this.toString() );
        } else if( this.month != checkDate.getMonth() ) {
            throw new BasicDateException( "BasicDate 'month' invalid = " + this.toString() );
        } else if( this.year != checkDate.getYear() ) {
            throw new BasicDateException( "BasicDate 'year' invalid = " + this.toString() );
        }
    }


    @Override
    public BasicDate add( final BasicDate anotherDate ) throws BasicDateTimeException
    {
        throw new UnsupportedOperationException( "$$$$ NOT YET IMPLEMENTED $$$$" );
    }

    @Override
    public BasicDate sub( final BasicDate anotherDate ) throws BasicDateTimeException
    {
        throw new UnsupportedOperationException( "$$$$ NOT YET IMPLEMENTED $$$$" );
    }

    private void writeObject( final ObjectOutputStream stream )
        throws IOException
    {
        stream.defaultWriteObject();

        stream.writeInt( this.year );
        stream.writeInt( this.month );
        stream.writeInt( this.day );
    }

    private void readObject( final ObjectInputStream stream )
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();

        this.year  = stream.readInt();
        this.month = stream.readInt();
        this.day   = stream.readInt();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.day;
        result = (prime * result) + this.month;
        result = (prime * result) + this.year;
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( !(obj instanceof BasicDate) ) {
            return false;
        }
        final BasicDate other = (BasicDate)obj;
        if( this.day != other.day ) {
            return false;
        }
        if( this.month != other.month ) {
            return false;
        }
        if( this.year != other.year ) {
            return false;
        }
        return true;
    }


//    /**
//     ** Ajoute de UN jour e la date courante.
//     ** <P>
//     * Passe automatiquement au mois et e l'annee suivante si besoin.
//     */
//    public void incDay() // ---------------------------------------------------
//    {
//        final int day = this.getDay() + 1; // Incremente
//
//        if( day > 28 ) {
//            // Il y a un risque potentiel de changement de mois,
//            // dans ce cas on utilise un calendrier.
//            final FullCalendar calendar = new FullCalendar( this );
//
//            // System.out.println( "cal :" + calendar.getDate( SIMPLE_DATE_FORMAT ) );
//            // System.out.println( "dat :" + dateYYYYMMDD );
//
//            calendar.add( FullCalendar.DATE, 1 );
//
//            // Recupere une date toute propre ;-)
//            dateYYYYMMDD = calendar.getDate( SIMPLE_DATE_FORMAT );
//
//            // System.out.println( "cal :" + calendar.getDate( SIMPLE_DATE_FORMAT ) );
//            // System.out.println( "dat :" + dateYYYYMMDD );
//        } else {
//            try {
//                setDay( day );
//            }
//            catch( final BasicDateException ignore ) {
//                commun.util.mail.SendAnException.sendAnException( 5, this, ignore, null, "incDay() INTERNAL ERROR" );
//            }
//        }
//    }

//    /**
//     ** Ajoute 'numberOfDay' jours e la date.
//     **
//     ** @param numberOfDay
//     *            nombres de jours e ajouter e partir de la date courante (valeur <B>positive uniquement</B>).
//     */
//    public void incDay( int numberOfDay ) // ----------------------------------
//    {
//        if( numberOfDay > 0 ) {
//            while( numberOfDay-- > 0 ) {
//                this.incDay();
//            }
//        }
//    }

//    /**
//     ** Retire UN jour e la date courante.
//     ** <P>
//     * Ajoute automatiquement le mois et l'annee si besoin.
//     */
//    public void decDay() // ---------------------------------------------------
//    {
//        this.decDay( 1 );
//    }

//    /**
//     ** Retire 'numberOfDay' jours e la date.
//     **
//     ** @param numberOfDay
//     *            nombres de jours e retirer e partir de la date courante (valeur <B>positive uniquement</B>).
//     */
//    public void decDay( final int numberOfDay ) // ----------------------------------
//    {
//        if( numberOfDay > 0 ) {
//            final FullCalendar calendar = new FullCalendar( this );
//
//            calendar.add( FullCalendar.DATE, -numberOfDay );
//            dateYYYYMMDD = calendar.getDate( SIMPLE_DATE_FORMAT );
//        }
//    }

} // class
