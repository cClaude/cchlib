/*
** $VER: BasicDate.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/BasicDate.java
** Description   :
**
**  1.03.___ 2000.10.29 Claude CHOISNET - Version initiale
**  1.31.___ 2005.05.16 Claude CHOISNET
**                      Prise en charge de la serialization à travers les
**                      les méthodes writeObject() et readObject()
**                      Changement de la représentation interne
**  2.00.003 2005.09.17 Claude CHOISNET
**                      Adapation JDK1.5 et DateInterface
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.BasicDate
**
*/
package cx.ath.choisnet.util.datetime;

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//** Elle ne gère pas les notions d'heure ou de calendrier, elle pourra être
//** complété en utilisant les méthodes statiques de FullCalendar

/**
**
** Cette classe gère les problèmes de date au sens commun (jour, mois, année)
** <P>
** Elle ne gère pas les notions d'heure ou de calendrier.
** <P>
** <PRE>
**  Exemple 1:
**      BasicDate simpledate = new BasicDate();
**
**      // Produit une sortie au format yyyyMMdd
**      System.err.println( "Nous sommes le : " + simpledate );
**
** </PRE>
** <P>
** <PRE>
**  Exemple 2: Parcours d'une période.
**      BasicDate  firstDayDate = new BasicDate( getDebutDePeriode() );
**      BasicDate  lastDayDate  = new BasicDate( getFinDePeriode() );
**
**      for(    BasicDate currentDay = new BasicDate( firstDayDate );
**              currentDay.compareTo( lastDayDate ) <= 0;
**              currentDay.incDay()
**              ) {
**          // Reste du traitement : function( currentDay );
**          }
**
** </PRE>
** <P>
** NOTE:<br/>
** - Cette classe est {@link Serializable}<br/>
** - Mais cette classe n'est pas "compatible an 10 000" ;-)
**
** @author  Claude CHOISNET
** @version 2.00.003
** @see     BasicTime
**
*/
public class BasicDate
    implements
        Serializable,
        Cloneable,
        DateInterface
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Chaîne de formatage pour la class BasicDateFormat de
** la méthode toString()
*/
protected final static String DATEFMT = "yyyyMMdd";

/**
** Object de formatage pour la class BasicDateFormat de
** la méthode toString()
*/
protected final static SimpleDateFormat DATE_FMT = new SimpleDateFormat( DATEFMT );

/** Integer contenant le numéro de l'année de 0 à 9999 */
protected transient int year = -1;

/** Integer contenant le numéro du mois 1 à 12 */
protected transient int month = -1;

/** Integer contenant le numéro du jour 1 à 31 */
protected transient int day = -1;

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
** @param javaDate Date au format java.util.Date
**
** @see #getJavaDate()
*/
public BasicDate( java.util.Date javaDate ) // ----------------------------
{
 set( javaDate );
}

/**
** Construit à partir d'un autre object BasicDate
** <P>
** @param date  BasicDate <B>valide</B>
*/
public BasicDate( BasicDate date ) // -----------------------------------
{
// class_year     = new Integer( date.getYear () );
// class_month    = new Integer( date.getMonth() );
// class_day      = new Integer( date.getDay  () );
 this.year   = date.getYear ();
 this.month  = date.getMonth();
 this.day    = date.getDay  ();
}

/**
** Construit un calendrier avec une chaîne au format spécifiée
**
** @param date  date sous forme de chaîne.
** @param fmt   format de la chaîne, conformément à la classe BasicDateFormat.
**
** @exception java.text.ParseException
*/
public BasicDate( String date, SimpleDateFormat fmt ) // -----------------
    throws java.text.ParseException
{
 this( fmt.parse( date ) ); // -> BasicDate( java.util.Date javadate )
}

///*
//*
//** Construit une date BasicDate avec une chaîne ayant le format YYYYMMDD
//**
//** @param date  date au format YYYYMMDD
//**
//** @exception BasicDateException
//*/
//public BasicDate( String date ) throws BasicDateException // ------------
//{
// if( date.length() != 8 ) {
//    throw new BasicDateException( "BasicDate bad length" );
//    }
//
// SimpleDateFormat fmt = new SimpleDateFormat( SIMPLE_DATE_FORMAT );
//
//  try {
//    set( fmt.parse( date ) );
//    }
//  catch( ParseException bug ) {
//    throw new RuntimeException( "BasicDate( String ) INTERNAL ERROR" );
//    }
//}


/**
** Construit une date BasicDate avec la date spécifiée
**
** @param year  l'année
** @param month le mois intervalle [1..12]
** @param day   le jour
**
** @see #set( int, int, int )
**
** @exception BasicDateException
*/
public BasicDate( int year, int month, int day ) // ----------------------
    throws BasicDateException
{
 set( year, month, day );
}

/**
** Construit une date BasicDate avec une date SQL
**
** @param sqlDate Date au format java.sql.Date
**
** @see #getSQLDate()
*/
public BasicDate( java.sql.Date sqlDate ) // -----------------------------
{
 String strDate = sqlDate.toString();

 // Formats a date in JDBC date escape format.
 // Returns: a String in yyyy-mm-dd format
 //
 // 0123456789
 // YYYY-MM-DD

 this.year     = Integer.parseInt( strDate.substring( 0, 4 ) );
 this.month    = Integer.parseInt( strDate.substring( 5, 7 ) );
 this.day      = Integer.parseInt( strDate.substring( 8 ) );
}

/**
**
** @exception BasicDateException
public void set( int year, int month, int day ) // ------------------------
    throws BasicDateException
{
 set( new Integer( year ), new Integer( month ), new Integer( day ) );
}
*/

/**
** Initialise l'objet BasicDate à partir de l'année, du mois et du jour.
** <P>
** Cette méthode lance un exception si les paramètres de la date ne sont pas
** consistant vis-à-vis du calendrier.
**
** @param year  l'année interval [0..9999]
** @param month le mois interval [1..12]
** @param day   le jour interval [1..31] (dépend du mois et de l'année)
**
** @exception BasicDateException
*/
public void set( int year, int month, int day ) // ------------------------
    throws BasicDateException
{
 if( (year <0) || (year > 9999) ) {
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
** Modifie le numéro de l'année de l'objet BasicDate
**
** @param year le numéro de l'année [0..9999]
**
** @exception BasicDateException
*/
public void setYear( int year ) throws BasicDateException // -------------
{
 set( year, -1, -1 );
}

/**
** Modifie le numéro du mois de l'objet BasicDate
**
** @param month   le mois [1..12]
**
** @exception BasicDateException
*/
public void setMonth( int month ) throws BasicDateException // -----------
{
 set( -1, month, -1 );
}

/**
** Modifie le numéro du jour de l'objet BasicDate
**
** @param day   le jour [1..31]
**
** @exception BasicDateException
*/
public void setDay( int day ) throws BasicDateException // ---------------
{
 set( -1, -1, day );
}

/**
** Initialise l'objet BasicDate à partir d'un object  java.util.Date
**
** @param javaDate date
*/
public void set( java.util.Date javaDate  ) // ----------------------------
{
 setWithFmtString( DATE_FMT.format( javaDate ) );
}

/**
** Initialise l'objet BasicDate avec une String formatée avec
** le format interne
** <P>
*/
protected void setWithFmtString( String fmtTime ) // ----------------------
{
//** @see #getSimpleDateFormat
 // yyyyMMdd
 // 012345678
 this.year  = Integer.parseInt( fmtTime.substring( 0, 4 ) );
 this.month = Integer.parseInt( fmtTime.substring( 4, 6 ) );
 this.day   = Integer.parseInt( fmtTime.substring( 6 ) );
}

/**
** retourne le numéro du jour.
**
** @return le numéro du jour [1..31]
*/
public int getDay() { return this.day; } // -------------------------------

/**
** retourne le numéro du mois.
**
** @return le numéro du mois [1..12]
*/
public int getMonth() { return this.month; } // ---------------------------

/**
** retourne le numéro de l'année.
**
** @return le numéro de l'année
*/
public int getYear() { return this.year; } // -----------------------------

/**
** retourne la date au format java.util.Date.
**
** @return une date au format java.util.Date
**
** @see #BasicDate( java.util.Date )
*/
public java.util.Date getJavaDate() // ------------------------------------
{
 try {
    return DATE_FMT.parse( this.toString() );
    }
 catch( ParseException bug ) {
    throw new RuntimeException( "BasicDate.getJavaDate() INTERNAL ERROR" );
    }
}

/**
** retourne la date au format java.sql.Date.
**
** @return une date au format java.sql.Date
**
** @see #BasicDate( java.sql.Date )
*/
public java.sql.Date getSQLDate() // --------------------------------------
{
 return new java.sql.Date( this.getJavaDate().getTime() );
}

/**
** @see #equals( DateInterface )
*/
public boolean equals( Object o ) // --------------------------------------
{
 try {
    return this.equals( (DateInterface)o );
    }
 catch( ClassCastException e ) {
    return false;
    }
}

/**
** Compare deux BasicDate.
**
** @return true si les 2 dates correspondent au même jour, false autrement.
*/
public boolean equals( DateInterface anotherDate ) // ---------------------
{
 return (this.compareTo( anotherDate ) == 0);
}

/**
** @see #compareTo( DateInterface )
public int compareTo( Object o ) // ---------------------------------------
    throws ClassCastException
{
 return compareTo( (DateInterface)o );
}
*/

/**
** Compare deux BasicDate.
**
** @return  la valeur 0 si les 2 dates correspondent au même jour, une valeur
**          négative si la date de l'object courant est plus vielle que la
**          date passé en paramètre. une valeur positive si la date de l'object
**          courant est plus récente que la date passé en paramètre.
*/
public int compareTo( DateInterface anotherDate ) // -----------------------
{
 if( anotherDate instanceof BasicDate ) {
    BasicDate aBasicDate = (BasicDate)anotherDate;

    int cmp = this.year - aBasicDate.year;

    if( cmp == 0 ) {
        cmp = this.month - aBasicDate.month;

        if( cmp == 0 ) {
            cmp = this.day - aBasicDate.day;
            }
        }

    return cmp;
    }
 else {
    long res = this.longValue() - anotherDate.longValue();

    if( res > 0 ) {
        return 1;
        }
    else if( res == 0 ) {
        return 0;
        }
    else {
        return -1;
        }
    }
}

/**
** Tests if this date is before the specified date.
**
** @param anOtherDate  a BasicDate.
**
** @return  true if and only if the instant of time represented by this
**          BasicDate object is strictly earlier than the instant
**          represented by when; false otherwise.
**
** @exception ClassCastException
*/
public boolean isBefore( DateInterface anOtherDate ) // -------------------
    throws ClassCastException
{
 return ( this.compareTo( anOtherDate ) > 0);
}

/**
** Tests if this date is after the specified date.
**
** @param anOtherDate  a BasicDate.
**
** @return  true if and only if the instant represented by thisBasicDate
**          object is strictly later than the instant represented
**          by when; false otherwise.
**
** @exception ClassCastException
*/
public boolean isAfter( DateInterface anOtherDate ) // --------------------
    throws ClassCastException
{
 return ( this.compareTo( anOtherDate ) < 0);
}

/**
** Surcharge de la méthode toString() de la classe Object, permet d'utiliser
** la classe comme une chaîne. Retourne la date au format interne de la
** classe BasicDate.
**
** @return la date au format YYYYMMDD
*/
public String toString() // -----------------------------------------------
{
 return toStringYear() + toStringMonth() + toStringDay();
}

/**
**
*/
public String toStringYear() // -------------------------------------------
{
 final String yearStr = "000" + this.year;

 return yearStr.substring( yearStr.length() - 4 );
}

/**
**
*/
public String toStringMonth() // ------------------------------------------
{
 return ((this.month>9) ? "" : "0") + this.month;
}

/**
**
*/
public String toStringDay() // --------------------------------------------
{
 return ((this.day>9) ? "" : "0") + this.day;
}

/**
** Renvoie la date associée au calendrier conformément à l'oject de formatage.
**
** @param formatter     Object BasicDateFormat contenant les caractéristiques
**                      du format attendu.
**
** @return la date formatée
*/
public String toString( Format formatter ) // -----------------------------
{
 return formatter.format( this.getJavaDate() );
}

private final static long MILLISECONDS_BY_DAY = (1000L*60L*60L*24L);

/**
** NOTE: cette méthode devrait retourner le nombre de jour depuis
** le 1er janvier de l'an 1.
**
** @return le nombre de jour depuis le 1er January 1970,
*/
public long longValue() // ------------------------------------------------
{
 return this.getJavaDate().getTime() / MILLISECONDS_BY_DAY;
}

/**
** Ajoute de UN an à la date courante.
** <P>
** Passe automatiquement à l'année suivante, en cas de dépassement de capacité
** de l'année (9999) retour à l'an 0.
*/
public void incYear() // -------------------------------------------------
{
 int year = this.getYear() + 1; // Incrémente

 if( year > 9999 ) {
    // Afin d'éviter à transmettre une exception, presque inutile ;-)
    year = 0;
    }

  try {
    set( year, this.getMonth(), this.getDay() );
    }
  catch( BasicDateException ignore ) {
    throw new RuntimeException( "incMonth() INTERNAL ERROR" );
    }
}

/**
** Ajoute de UN mois à la date courante.
** <P>
** Passe automatiquement à l'année suivante, en cas de dépassement de capacité
** de l'année (9999) retour à l'an 0.
*/
public void incMonth() // -------------------------------------------------
{
 int year   = this.getYear();
 int month  = this.getMonth() + 1; // Incrémente

 if( month > 12 ) {
    month = 1; // Janvier
    year += 1;

    if( year > 9999 ) {
        // Afin d'éviter à transmettre une exception, presque inutile ;-)
        year = 0;
        }
    }

  try {
    set( year, month, this.getDay() );
    }
  catch( BasicDateException ignore ) {
    throw new RuntimeException( "incMonth() INTERNAL ERROR" );
    }
}

/**
** Méthode permettant de calculer la durée en jours entre 2 dates BasicDate
** la date le l'object actuel est la référence de début de période
** <P>
** <PRE>
**  // Calcul le nombre de jour d'un mois donné.
**  BasicDate startDate = new BasicDate( yearNumber, monthNumber, 1 );
**  BasicDate endDate   = new BasicDate( yearNumber, monthNumber, 1 );
**  endDate.incMonth();
**
**  int countOfDayInTheMonth = startDate.countOfDay( endDate );
** </PRE>
**
** @param endOfPeriod BasicDate initialisé à la fin de la période
**
** @return durée (en jours) entre la date de début et la date de fin
*/
public int countOfDay( BasicDate endOfPeriod ) // ------------------------
{
 final long MILLISECONDS_BY_DAY = (1000L*60L*60L*24L);

 long       msBeginOfPeriod     = this.getJavaDate().getTime();
 long       dayBeginOfPeriod    = msBeginOfPeriod / MILLISECONDS_BY_DAY;

 long       msEndOfPeriod       = endOfPeriod.getJavaDate().getTime();
 long       dayEndOfPeriod      = msEndOfPeriod / MILLISECONDS_BY_DAY;

 long       countofday          = dayEndOfPeriod - dayBeginOfPeriod;

 return (int)countofday;
}

/**
**
*/
protected void check() throws BasicDateException // ----------------------
{
 //
 // Vérification que la date est consistante.
 //
 BasicDate checkDate = new BasicDate( this.getJavaDate() );

 if( this.day != checkDate.getDay() ) {
    throw new BasicDateException( "BasicDate 'day' invalid = " + this.toString() );
    }
 else if( this.month != checkDate.getMonth() ) {
    throw new BasicDateException( "BasicDate 'month' invalid = " + this.toString() );
    }
 else if( this.year != checkDate.getYear() ) {
    throw new BasicDateException( "BasicDate 'year' invalid = " + this.toString() );
    }
}

/**
**
*/
public DateInterface add( DateInterface anotherDate ) // ------------------
{
 throw new RuntimeException( "$$$$ NOT YET IMPLEMENTED $$$$" );
}

/**
**
*/
public DateInterface sub( DateInterface anotherDate ) // ------------------
{
 throw new RuntimeException( "$$$$ NOT YET IMPLEMENTED $$$$" );
}

/**
**
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeInt( this.year  );
 stream.writeInt( this.month );
 stream.writeInt( this.day   );
}

/**
**
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.year  = stream.readInt();
 this.month = stream.readInt();
 this.day   = stream.readInt();
}

} // class

/*
*
** Ajoute de UN jour à la date courante.
** <P>
** Passe automatiquement au mois et à l'année suivante si besoin.
*
/
public void incDay() // ---------------------------------------------------
{
 int day = this.getDay() + 1; // Incrémente

 if( day > 28 ) {
    // Il y a un risque potentiel de changement de mois,
    // dans ce cas on utilise un calendrier.
    FullCalendar calendar = new FullCalendar( this );

    // System.out.println( "cal :" + calendar.getDate( SIMPLE_DATE_FORMAT ) );
    // System.out.println( "dat :" + dateYYYYMMDD );

    calendar.add( FullCalendar.DATE, 1 );

    // Récupère une date toute propre ;-)
    dateYYYYMMDD = calendar.getDate( SIMPLE_DATE_FORMAT );

    // System.out.println( "cal :" + calendar.getDate( SIMPLE_DATE_FORMAT ) );
    // System.out.println( "dat :" + dateYYYYMMDD );
    }
 else {
    try {
        setDay( day );
        }
    catch( BasicDateException ignore ) {
        commun.util.mail.SendAnException.sendAnException(
            5, this, ignore, null, "incDay() INTERNAL ERROR"
            );
        }
    }
}
*/

/*
*
** Ajoute 'numberOfDay' jours à la date.
**
** @param numberOfDay   nombres de jours à ajouter à partir de la date courante
**                      (valeur <B>positive uniquement</B>).
*
/
public void incDay( int numberOfDay ) // ----------------------------------
{
 if( numberOfDay > 0 ) {
    while( numberOfDay-- > 0 ) {
        this.incDay();
        }
    }
}
*/

/*
*
** Retire UN jour à la date courante.
** <P>
** Ajoute automatiquement le mois et l'année si besoin.
*
/
public void decDay() // ---------------------------------------------------
{
 this.decDay( 1 );
}


/*
*
** Retire 'numberOfDay' jours à la date.
**
** @param numberOfDay   nombres de jours à retirer à partir de la date courante
**                      (valeur <B>positive uniquement</B>).
*
/
public void decDay( int numberOfDay ) // ----------------------------------
{
 if( numberOfDay > 0 ) {
    FullCalendar calendar = new FullCalendar( this );

    calendar.add( FullCalendar.DATE, - numberOfDay );
    dateYYYYMMDD = calendar.getDate( SIMPLE_DATE_FORMAT );
    }
}
*/
