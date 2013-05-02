package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnObjReplace_actionAdapter
    implements ActionListener
{
    RegExpBuilderPanel adaptee;

    REBF_btnObjReplace_actionAdapter( final RegExpBuilderPanel adaptee )
    {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnObjReplace_actionPerformed( e );
    }
}
