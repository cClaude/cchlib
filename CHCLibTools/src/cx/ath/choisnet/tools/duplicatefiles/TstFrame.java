package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import org.apache.log4j.Logger;
import cx.ath.choisnet.swing.XComboBox;
import cx.ath.choisnet.swing.XComboBoxPattern;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class TstFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( TstFrame.class );
    private XComboBox xComboBox0;
    private XComboBoxPattern xComboBoxPattern1;
    private JPanel jPanel0;

    public TstFrame()
    {
        initComponents();

        xComboBox0.setMaximumItem( 6 );
        xComboBoxPattern1.setMaximumItem(5);
    }

    private void initComponents() {
        setLayout(new FlowLayout());
        add(getJPanel0());
        setSize(320, 240);
    }

    private JPanel getJPanel0() {
        if (jPanel0 == null) {
            jPanel0 = new JPanel();
            jPanel0.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
            jPanel0.setLayout(new BorderLayout());
            jPanel0.add(getXComboBoxPattern1(), BorderLayout.NORTH);
            jPanel0.add(getXComboBox0(), BorderLayout.SOUTH);
        }
        return jPanel0;
    }

    private XComboBoxPattern getXComboBoxPattern1() {
        if (xComboBoxPattern1 == null) {
            xComboBoxPattern1 = new XComboBoxPattern(Color.RED);
            xComboBoxPattern1.setModel(new DefaultComboBoxModel(new Object[] {"AAPattern","BB"}));
        }
        return xComboBoxPattern1;
    }

    private XComboBox getXComboBox0() {
        if (xComboBox0 == null) {
            xComboBox0 = new XComboBox();
            xComboBox0.setModel(new DefaultComboBoxModel(new Object[] {"ZA","ZB"}));
        }
        return xComboBox0;
    }

    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                TstFrame frame = new TstFrame();
                frame.setDefaultCloseOperation( TstFrame.EXIT_ON_CLOSE );
                frame.setTitle( "TstFrame" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
            }
        } );
    }

}