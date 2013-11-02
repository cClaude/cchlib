package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnObjects_actionAdapter
    implements ActionListener
{
    private RegExpBuilderPanel adaptee;

    REBF_btnObjects_actionAdapter( final RegExpBuilderPanel adaptee )
    {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnObjects_actionPerformed( e );
    }
}
