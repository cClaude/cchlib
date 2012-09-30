package cx.ath.choisnet.swing.introspection;

import java.text.ParseException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cx.ath.choisnet.lang.introspection.IntrospectionException;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;
import cx.ath.choisnet.swing.introspection.SwingIntrospector;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorException;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorIllegalAccessException;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorRootItem;

/**
 *
 */
public class IntrospectionTest
{
    private static Logger slogger = Logger.getLogger(IntrospectionTest.class);
    private TstFrame tstFrame;
    private SwingIntrospector<TstFrame, TstObject, DefaultIntrospectionItem<TstObject>> swingIntrospector;

    @Before
    public void setUp() throws Exception
    {
        slogger.info( "-- setUp()" );
        
        this.tstFrame = new TstFrame();
        this.swingIntrospector = tstFrame.getSwingIntrospector();
    }

    @Test
    public void test_SwingIntrospector()
    {
        slogger.info( "-- test_SwingIntrospector()" );
        Map<String, SwingIntrospectorRootItem<TstFrame>> map = this.swingIntrospector.getItemMap();

        Assert.assertEquals("Bad SwingIntrospectorRootItem count !", 7, map.size());

        //TODO: need some extra check !
    }

    @Test
    public void test_initComponentsWithException()
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        slogger.info( "-- test_initComponentsWithException()" );
        tstFrame.initComponentsWithException();
        //TODO: need some extra check !
    }

    @Test
    public void test_populateObject()
        throws  SwingIntrospectorException,
                IntrospectionException,
                ParseException
    {
        slogger.info( "-- test_populateObject()" );
        tstFrame.initComponentsWithException();

        tstFrame.randomObject();
        slogger.info( "RND :" + tstFrame.getTstObject() );
        tstFrame.populateObject();
        slogger.info( "F=>O:" + tstFrame.getTstObject() );

        //need some extra check ?
        compareFrameObject( tstFrame );
    }

    @Test
    public void test_populateFrame()
        throws  IntrospectionInvokeException,
                SwingIntrospectorException,
                ParseException
    {
        slogger.info( "-- test_populateFrame()" );
        tstFrame.initComponentsWithException();

        tstFrame.randomObject();
        slogger.info( "RND :" + tstFrame.getTstObject() );
        tstFrame.populateFrame();
        slogger.info( "O=>F" );

        //need some extra check ?
        compareFrameObject( tstFrame );
    }

    private void compareFrameObject( TstFrame frame )
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
