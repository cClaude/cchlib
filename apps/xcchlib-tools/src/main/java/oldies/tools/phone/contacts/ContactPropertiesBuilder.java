package oldies.tools.phone.contacts;

import java.util.Collection;

/**
 *
 * All collections must have same size
 */
public interface ContactPropertiesBuilder
{
    /**
     *
     * @return TODOC
     */
    Collection<? extends String> getNames();

    /**
     *
     * @return TODOC
     */
    Collection<? extends ContactValueType> getTypes();

    /**
     *
     * @return TODOC
     */
    Collection<? extends String> getDefaultValues();
}
