package com.googlecode.cchlib.util.properties;

/**
 * TODOC
 */
public abstract class AbstractPopulatorContener<E>
    implements PopulatorContener<E>
{
    private E content;

    @Override
    public void set( E content )
    {
        this.content = content;
    }

    @Override
    public E get()
    {
        return this.content;
    }

    /***
     * TODOC
     *
     * @param content
     * @return TODOC
     */
    public abstract String toString( E content );

    @Override
    public String toString()
    {
        return content == null ? "" : toString( content );
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
            }
        if (obj == null) {
            return false;
            }
        if (getClass() != obj.getClass()) {
            return false;
            }

        @SuppressWarnings("unchecked")
        AbstractPopulatorContener<E> other = AbstractPopulatorContener.class.cast( obj );

        if (content == null) {
            if (other.content != null) {
                return false;
                }
            }
        else if (!content.equals(other.content)) {
            return false;
            }
        return true;
    }


}
