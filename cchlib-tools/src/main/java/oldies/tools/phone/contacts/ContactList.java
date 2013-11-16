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
    ContactProperties getContactProperties();

    void add( Contact contact );
    boolean remove( Contact contact );
    int size();
}
