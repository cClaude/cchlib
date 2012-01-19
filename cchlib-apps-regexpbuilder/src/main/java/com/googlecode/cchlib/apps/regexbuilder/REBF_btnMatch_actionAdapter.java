package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnMatch_actionAdapter
    implements ActionListener
{
	RegExpBuilderWB adaptee;

    REBF_btnMatch_actionAdapter( final RegExpBuilderWB adaptee )
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnMatch_actionPerformed( e );
    }
}
