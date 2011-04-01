package cx.ath.choisnet.lang.reflect;

import java.util.Map;

/**
 * Give a view of all Attributes(name,value) for this object, could be use to
 * generate XML files, HTML, debugging, ...
 *
 * @see MappableHelper
 * @see MappableHelperFactory
 * @author Claude CHOISNET
 */
public interface Mappable
{
    /**
     * Returns a Map of attributes for this object.
     * <p>
     * Key should be attribute name, an Value should be a String representation
     * for this attribute value.
     * </p>
     * @return a Map of attributes for this object.
     */
    public abstract Map<String,String> toMap();
}
