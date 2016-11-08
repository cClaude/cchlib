package cx.ath.choisnet.bytesaccess;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for {@link BytesAccess}
 *
 * @see BytesAccess#compareTo(BytesAccess)
 */
public class BytesAccessComparator implements Comparator<BytesAccess>, Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public int compare( BytesAccess o1, BytesAccess o2 )
    {
        return o1.compareTo( o2 );
    }
}
