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
     * @return
     */
    public Collection<? extends String> getNames();

    /**
     *
     * @return
     */
    public Collection<? extends ContactValueType> getTypes();

    /**
     *
     * @return
     */
    public Collection<? extends String> getDefaultValues();
}
