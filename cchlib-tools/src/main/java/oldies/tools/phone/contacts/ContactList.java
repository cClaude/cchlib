package oldies.tools.phone.contacts;

import java.io.Serializable;

/**
 *
 *
 */
public interface ContactList
    extends Iterable<Contact>, Serializable
{
    /**
     * Returns {@link ContactProperties} for this contact
     * @return contact properties for this contact
     */
    public ContactProperties getContactProperties();

    public void add( Contact contact );
    public boolean remove( Contact contact );
    public int size();
}
