package cx.ath.choisnet.swing.filechooser.accessory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class ResourcesUtilsTest
{
    private static final Logger logger = Logger.getLogger( ResourcesUtilsTest.class );

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void myTestJButton()
    {
        ResourcesUtils  resourcesUtils = new ResourcesUtils( getClass() );
        JButton         jButton         = resourcesUtils.getJButton( "bookmark-add.png" );
        JFrame          frame = new JFrame();

        frame.add( jButton );
        frame.setSize(200,200);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info( "myTestJButton() done" );
        try {
            Thread.sleep( 3 * 1000);
            }
        catch( InterruptedException e ) {
            e.printStackTrace();
            }
    }

    @Test
    public void myTestImageIcon()
    {
        ResourcesUtils  resourcesUtils  = new ResourcesUtils( getClass() );
        ImageIcon       icon            = resourcesUtils.getImageIcon( "fakeName" );
        JFrame          frame           = new JFrame();
        JCheckBox       checkbox        = new JCheckBox( "Test" );

        checkbox.setIcon( icon );

        frame.add( checkbox );
        frame.setSize(200,200);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info( "myTestImageIcon() done" );
        try {
            Thread.sleep( 3 * 1000);
            }
        catch( InterruptedException e ) {
            e.printStackTrace();
            }
        }

    abstract class TstFrame extends JFrame //implements ActionListener
    {
        private static final long serialVersionUID = 1L;
    }

    public static void main( String[] args )
    {
        new ResourcesUtilsTest().myTestJButton();
        new ResourcesUtilsTest().myTestImageIcon();
    }
}
