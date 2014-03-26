package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import java.util.List;
import java.util.Set;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.DefaultContact;
import flexjson.JSON;

public class JSONContactImpl extends DefaultContact 
{
    private static final long serialVersionUID = 1L;

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
