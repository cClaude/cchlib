package samples.batchrunner.phone.recordsorter.conf.json;

import java.util.List;
import java.util.Set;
import flexjson.JSON;
import samples.batchrunner.phone.recordsorter.conf.AbstractContact;
import samples.batchrunner.phone.recordsorter.conf.Contact;

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
