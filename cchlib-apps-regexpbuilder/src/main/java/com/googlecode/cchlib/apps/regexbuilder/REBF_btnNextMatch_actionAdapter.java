package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnNextMatch_actionAdapter
    implements ActionListener
{
    RegExpBuilderWB adaptee;

    REBF_btnNextMatch_actionAdapter( final RegExpBuilderWB adaptee )
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnNextMatch_actionPerformed( e );
    }
}
