package com.googlecode.cchlib.tools.phone.recordsorter.conf.xml;

import java.util.Set;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractContact;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

/**
 *
 */
public class ContactXMLImpl extends AbstractContact implements Contact
{
    /**
     *
     */
    public ContactXMLImpl()
    {
        super( null );
    }

    public ContactXMLImpl( String name )
    {
        super( name );
    }

    public Set<String> getNumberSet()
    {
        return super.internalNumberSet();
    }

    public void setNumberSet( Set<String> numberSet )
    {
        Set<String> set = super.internalNumberSet();

        set.clear();

        for( String number : numberSet ) {
            set.add( number );
        }
    }

}
