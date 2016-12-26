package cx.ath.choisnet.util.datetime;

/**
 *
 * @param <T> NEEDDOC
 * @since 1.53
 */
public interface DateInterface<T extends DateInterface<T>>
    extends DateTimeInterface, Comparable<T>
{
    boolean isBefore( T anotherDate );
    boolean isAfter( T anotherDate );

    T add( T anotherDate ) throws BasicDateTimeException;
    T sub( T anotherDate ) throws BasicDateTimeException;

    /**
     * Compare deux object horaire.
     *
     * @param anotherDate NEEDDOC
     * @return la valeur 0 si les 2 horaires correspondent, une valeur negative
     *         si l'object courant est plus ancien que l'horaire donne en
     *         parametre. une valeur positive si l'horaire de l'object courant
     *         est plus recent que l'horaire passe en parametre.
     */
    @Override
    int compareTo( T anotherDate );

    /**
     * Compare deux object horaire.
     *
     * @param anotherDate NEEDDOC
     * @return true si les 2 horaires correspondent.
     */
    boolean isEqualTo( T anotherDate );
}
