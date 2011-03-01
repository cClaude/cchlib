/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.bytesaccess;

import java.util.Comparator;

/**
 * Comparator for {@link BytesAccess}
 *
 * @author Claude CHOISNET
 * @see BytesAccess#compareTo(BytesAccess)
 */
public class BytesAccessComparator implements Comparator<BytesAccess> 
{
    @Override
    public int compare( BytesAccess o1, BytesAccess o2 )
    {
        return o1.compareTo( o2 );
    }
}
