package cx.ath.choisnet.swing.introspection;

import static org.junit.Assume.assumeTrue;
import java.text.ParseException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import cx.ath.choisnet.lang.introspection.IntrospectionException;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;

public class IntrospectionTest
{
    private static Logger LOGGER = Logger.getLogger( IntrospectionTest.class );

    private TstFrame tstFrame;
    private SwingIntrospector<TstFrame, TstObject, DefaultIntrospectionItem<TstObject>> swingIntrospector;

    @Before
    public void setUp() throws Exception
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        LOGGER.info( "-- setUp()" );

        this.tstFrame          = new TstFrame();
        this.swingIntrospector = this.tstFrame.getSwingIntrospector();
    }

    @Test
    public void test_SwingIntrospector()
    {
        LOGGER.info( "-- test_SwingIntrospector()" );
        final Map<String, SwingIntrospectorRootItem<TstFrame>> map = this.swingIntrospector.getItemMap();

        Assert.assertEquals("Bad SwingIntrospectorRootItem count !", 7, map.size());

        //TODO: need some extra check !
    }

    @Test
    public void test_initComponentsWithException()
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        LOGGER.info( "-- test_initComponentsWithException()" );
        this.tstFrame.initComponentsWithException();
        //TODO: need some extra check !
    }

    @Test
    public void test_populateObject()
        throws  SwingIntrospectorException,
                IntrospectionException,
                ParseException
    {
        LOGGER.info( "-- test_populateObject()" );
        this.tstFrame.initComponentsWithException();

        this.tstFrame.randomObject();
        LOGGER.info( "RND :" + this.tstFrame.getTstObject() );
        this.tstFrame.populateObject();
        LOGGER.info( "F=>O:" + this.tstFrame.getTstObject() );

        //need some extra check ?
        compareFrameObject( this.tstFrame );
    }

    @Test
    public void test_populateFrame()
        throws  IntrospectionInvokeException,
                SwingIntrospectorException,
                ParseException
    {
        LOGGER.info( "-- test_populateFrame()" );
        this.tstFrame.initComponentsWithException();

        this.tstFrame.randomObject();
        LOGGER.info( "RND :" + this.tstFrame.getTstObject() );
        this.tstFrame.populateFrame();
        LOGGER.info( "O=>F" );

        //need some extra check ?
        compareFrameObject( this.tstFrame );
    }

    private void compareFrameObject( final TstFrame frame )
        throws ParseException
    {
        final TstObject object = frame.getTstObject();

        Assert.assertEquals("TestBoolean",
                object.isTestBoolean(),
                frame.getJCheckBox_TestBoolean().isSelected()
                );
        frame.getJFormattedTextField_TestFMTString().commitEdit();
        Assert.assertEquals("TestFMTString",
                object.getTestFMTString(),
                frame.getJFormattedTextField_TestFMTString().getValue()
                );
        Assert.assertEquals("TestIntegerJComboBox",
                object.getTestIntegerJComboBox(),
                frame.getJComboBox_TestIntegerJComboBox().getSelectedIndex()
                    + TstObject.testIntegerJComboBoxMin
                );
        Assert.assertEquals("TestIntegerJSlider",
                object.getTestIntegerJSlider(),
                frame.getJSlider_TestIntegerJSlider().getValue()
                );
        Assert.assertEquals("TestIntegerJSpinner",
                object.getTestIntegerJSpinner(),
                frame.getJSpinner_TestIntegerJSpinner().getValue()
                );
        Assert.assertEquals("TestIntegerLimitedIntegerJTextField",
                object.getTestIntegerLimitedIntegerJTextField(),
                frame.getJTextField_TestIntegerLimitedIntegerJTextField().getValue()
                );
        Assert.assertEquals("TestString",
                object.getTestString(),
                frame.getJTextField_TestString().getText()
                );

    }
}
