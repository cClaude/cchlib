package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractConfig;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class ConfigJSONImpl extends AbstractConfig implements Config
{   
    @Override
    protected Contact newContact( String name )
    {
        return new ContactJSONImpl( name );
    }
}
