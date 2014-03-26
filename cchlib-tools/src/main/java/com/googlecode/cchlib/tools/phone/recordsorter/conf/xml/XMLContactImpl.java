package com.googlecode.cchlib.tools.phone.recordsorter.conf.xml;

import java.util.Set;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.DefaultContact;

public class XMLContactImpl extends DefaultContact
{
    private static final long serialVersionUID = 1L;

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
