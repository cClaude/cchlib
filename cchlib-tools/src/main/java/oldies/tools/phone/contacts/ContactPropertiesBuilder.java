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
    public Collection<? extends String> getNames();

    /**
     *
     * @return TODOC
     */
    public Collection<? extends ContactValueType> getTypes();

    /**
     *
     * @return TODOC
     */
    public Collection<? extends String> getDefaultValues();
}
