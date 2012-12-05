package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnSplit_actionAdapter
    implements ActionListener
{
    RegExpBuilderPanel adaptee;

    REBF_btnSplit_actionAdapter( final RegExpBuilderPanel adaptee )
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnSplit_actionPerformed( e );
    }
}
