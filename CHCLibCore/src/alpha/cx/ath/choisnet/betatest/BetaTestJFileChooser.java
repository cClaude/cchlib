package alpha.cx.ath.choisnet.betatest;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import cx.ath.choisnet.swing.filechooser.JFileChooserInitializer;
import cx.ath.choisnet.swing.filechooser.accessory.FindAccessory;

//VS 4E -- DO NOT REMOVE THIS LINE!
public class BetaTestJFileChooser extends JFrame
{
    private static final long   serialVersionUID        = 1L;
    private JButton jButtonGO;
    private JTextArea jTextAreaLogs;
    private JScrollPane jScrollPane0;
    private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    private JFileChooser jFileChooser;
    private JFileChooserInitializer jFileChooserInitializer;
    
    public BetaTestJFileChooser()
    {
        initComponents();
        
        this.jFileChooserInitializer = new JFileChooserInitializer();
    }

    private void initComponents() {
    	add(getJButtonGO(), BorderLayout.NORTH);
        add(getJScrollPane0(), BorderLayout.CENTER);
    	setSize(320, 85);
    }

    private JScrollPane getJScrollPane0() {
    	if (jScrollPane0 == null) {
    		jScrollPane0 = new JScrollPane();
    		jScrollPane0.setViewportView(getJTextAreaLogs());
    	}
    	return jScrollPane0;
    }

    private JTextArea getJTextAreaLogs() {
    	if (jTextAreaLogs == null) {
    	    jTextAreaLogs = new JTextArea();
    	    jTextAreaLogs.setText("----\n");
    	}
    	return jTextAreaLogs;
    }

    private JButton getJButtonGO() {
    	if (jButtonGO == null) {
    	    jButtonGO = new JButton();
    	    jButtonGO.setText("Go !");
    	    jButtonGO.addMouseListener(new MouseAdapter() {
    
    			public void mousePressed(MouseEvent event) {
    				jButtonGO_MouseMousePressed(event);
    			}
    		});
    	}
    	return jButtonGO;
    }

    @SuppressWarnings("unused")
    private static void installLnF()
    {
        try {
            String lnfClassname = PREFERRED_LOOK_AND_FEEL;
            if( lnfClassname == null )
                lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel( lnfClassname );
        }
        catch( Exception e ) {
            System.err.println( "Cannot install " + PREFERRED_LOOK_AND_FEEL
                    + " on this platform:" + e.getMessage() );
        }
    }

    /**
     * Main entry of the class.
     * Note: This class is only created so that you can easily preview the result at runtime.
     * It is not expected to be managed by the designer.
     * You can modify it as you like.
     * @param args 
     */
    public static void main( String[] args )
    {
        installLnF();
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                BetaTestJFileChooser frame = new BetaTestJFileChooser();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( "TesterJFileChooser2" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
            }
        } );
    }

    private void jButtonGO_MouseMousePressed(MouseEvent event) 
    {
        long   begin = System.currentTimeMillis();
        String msg;
        
        msg = "start: " + begin;
        System.out.println( msg );
        jTextAreaLogs.append( msg + "\n" );
       
        //jFileChooser = new JFileChooser();
        jFileChooser = jFileChooserInitializer.getJFileChooser();
        
        msg = "2: " + (System.currentTimeMillis()-begin);
        System.out.println( msg );
        jTextAreaLogs.append( msg + "\n" );

        jFileChooser.setAccessory(new FindAccessory(jFileChooser));

        msg = "3: " + (System.currentTimeMillis()-begin);
        System.out.println( msg );
        jTextAreaLogs.append( msg + "\n" );
        
        jFileChooser.showOpenDialog( this );
    }

}
