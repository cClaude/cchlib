package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import java.util.List;
import java.util.Set;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractContact;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import flexjson.JSON;

public class ContactJSONImpl extends AbstractContact implements Contact
{
    public ContactJSONImpl()
    {
        super( null );
    }
    
    public ContactJSONImpl( String name )
    {
        super( name );
    }

    @JSON
    public void setNumbers( List<String> numbers )
    {
        Set<String> set = internalNumberSet();
        
        set.clear();
        for( String number : numbers ) {
            set.add( number );
        }
    }
}
