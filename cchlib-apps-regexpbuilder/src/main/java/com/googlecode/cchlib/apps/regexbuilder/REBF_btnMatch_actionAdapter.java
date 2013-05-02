package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnMatch_actionAdapter
    implements ActionListener
{
	RegExpBuilderPanel adaptee;

    REBF_btnMatch_actionAdapter( final RegExpBuilderPanel adaptee )
    {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnMatch_actionPerformed( e );
    }
}
