package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class REBF_btnObjSplit_actionAdapter
    implements ActionListener
{
    RegExpBuilderWB adaptee;

    REBF_btnObjSplit_actionAdapter( final RegExpBuilderWB adaptee )
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed( ActionEvent e )
    {
        adaptee.btnObjSplit_actionPerformed( e );
    }
}
