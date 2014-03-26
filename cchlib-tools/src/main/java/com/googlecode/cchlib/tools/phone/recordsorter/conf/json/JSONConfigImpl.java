package com.googlecode.cchlib.tools.phone.recordsorter.conf.json;

import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.AbstractReadWriteConfig;

public class JSONConfigImpl extends AbstractReadWriteConfig
{
    private static final long serialVersionUID = 1L;

    @Override
    public Contact newContact()
    {
        return  new JSONContactImpl();
    }
}
