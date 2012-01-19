package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnObjReplace_actionAdapter
    implements ActionListener
{
    RegExpBuilderWB adaptee;

    REBF_btnObjReplace_actionAdapter( final RegExpBuilderWB adaptee )
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnObjReplace_actionPerformed( e );
    }
}
