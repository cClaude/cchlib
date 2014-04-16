package com.googlecode.cchlib.tools.phone.recordsorter.conf.util;

import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public abstract class AbstractReadWriteConfig extends AbstractReadConfig
{
    private static final long serialVersionUID = 1L;

    public AbstractReadWriteConfig()
    {
    }

    @Override
    public Contact addContact( final Contact person )
    {
        internalContactCollection().add( person );

        return person;
    }
}

//@Override
//public Contact addContact( final String name )
//{
//  return addContact( newContact( name ) );
//}
//
//protected abstract Contact newContact( final String name );
//
//@Override
//public Contact addContact( final String name, final String number )
//{
//  Contact c = addContact( name );
//
//  c.addNumber( number );
//
//  return c;
//}
