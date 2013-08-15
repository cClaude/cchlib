package samples.batchrunner.phone.recordsorter.conf.json;

import samples.batchrunner.phone.recordsorter.conf.AbstractConfig;
import samples.batchrunner.phone.recordsorter.conf.Config;
import samples.batchrunner.phone.recordsorter.conf.Contact;

public class ConfigJSONImpl extends AbstractConfig implements Config
{   
    @Override
    protected Contact newContact( String name )
    {
        return new ContactJSONImpl( name );
    }
}
