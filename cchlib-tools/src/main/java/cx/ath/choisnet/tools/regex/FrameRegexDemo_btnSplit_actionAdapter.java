package cx.ath.choisnet.tools.regex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnSplit_actionAdapter
    implements ActionListener
{
    RegexFrame adaptee;

    FrameRegexDemo_btnSplit_actionAdapter( RegexFrame adaptee )
    {
        this.adaptee = adaptee;
    }

    public FrameRegexDemo_btnSplit_actionAdapter(RegExpBuilderWB regExpBuilderWB)
    {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed( ActionEvent e )
    {
        adaptee.btnSplit_actionPerformed( e );
    }
}