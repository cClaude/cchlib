package cx.ath.choisnet.util.enumeration;

import java.util.Enumeration;

/**
 * Build a new Enumeration that consume first
 * Enumeration and second Enumeration for it's
 * results (Order is preserve)
 *
 * @param <T> content type
 */
public abstract class BiEnumeration<T>
    implements Enumeration<T>
{
    private Enumeration<T> firstEnum;
    private Enumeration<T> secondEnum;

    public BiEnumeration(Enumeration<T> firstEnum, Enumeration<T> secondEnum)
    {
        this.firstEnum = firstEnum;

        this.secondEnum = secondEnum;
    }

    public boolean hasMoreElements()
    {
        if(firstEnum.hasMoreElements()) {
            return true;
        }
        else {
            return secondEnum.hasMoreElements();
        }
    }

    public T next()
        throws java.util.NoSuchElementException
    {
        if(firstEnum.hasMoreElements()) {
            return firstEnum.nextElement();
        }
        else {
            return secondEnum.nextElement();
        }
    }
}
