package cx.ath.choisnet.tools.regex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FrameRegexDemo_btnAdvancedReplace_actionAdapter
    implements ActionListener
{
    RegexFrame adaptee;

    FrameRegexDemo_btnAdvancedReplace_actionAdapter( RegexFrame adaptee )
    {
        this.adaptee = adaptee;
    }

    public FrameRegexDemo_btnAdvancedReplace_actionAdapter(
			RegExpBuilderWB regExpBuilderWB) {
		// TODO Auto-generated constructor stub
	}

	public void actionPerformed( ActionEvent e )
    {
        adaptee.btnAdvancedReplace_actionPerformed( e );
    }
}