package cx.ath.choisnet.swing.list;

import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
public class LeftDotListCellRenderer extends DefaultListCellRenderer
{
    private static final long serialVersionUID = 1L;
    private static final String DOTS = "...";
    private Container container;

    /**
     * Create a LeftDotListCellRenderer using JList or JScrollPane
     * to compute width of JLis
     * 
     * @param jList	JList to customize.
     * @param useParentJScrollPane if true, look for parent JScrollPane 
     * that view port is the giving jList to use to compute text size. If
     * false or if JScrollPane is not found use JList to compute that size.
     */
    public LeftDotListCellRenderer(
            final JList<?> jList,
            final boolean  useParentJScrollPane
            )
    {
        if( useParentJScrollPane ) {
            Container c = jList.getParent();

            if( c instanceof JViewport ) {
                c = c.getParent();

                if( c instanceof JScrollPane ) {
                    this.container = c;
                    }
                else {
                    this.container = jList;
                    }
                }
            else {
                this.container = jList;
                }
            }
        else {
            this.container = jList;
            }
    }

    /**
     * Create a LeftDotListCellRenderer using giving JScrollPane to
     * compute size of JList
     * 
     * @param jScrollPane JScrollPane to use to compute text size.
     */
    public LeftDotListCellRenderer( final JScrollPane jScrollPane )
    {
        this.container = jScrollPane;
    }

    @Override
    public Component getListCellRendererComponent(
            final JList<?>	list,
            final Object 	value,
            final int 		index,
            final boolean 	isSelected,
            final boolean 	cellHasFocus
            )
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        //spy();
        
        final int			availableWidth = this.container.getWidth();
        final String 		text 		= getText();
        final FontMetrics 	fm 			= getFontMetrics( getFont() );
        final int   		dotsWidth 	= fm.stringWidth( DOTS );

        //System.out.println( "availableWidth = " + availableWidth );
        //System.out.println( "dotsWidth = " + dotsWidth );
        //System.out.println( "text width = " + fm.stringWidth( text ) );

        if( fm.stringWidth( text ) > availableWidth ) {
            int textWidth 	= dotsWidth;
            int nChars 		= text.length() - 1;

            for(; nChars > 0; nChars--) {
                textWidth += fm.charWidth( text.charAt(nChars) );

                if( textWidth > availableWidth ) {
                    break;
                    }
                }

            setText( DOTS + text.substring(nChars + 1) );
            }

        // Add full text on ToolTip
        setToolTipText( text );
        
        return this;
    }

    /*
    private final java.util.Map<String,java.util.Map<String,String>> spyMap
        = new java.util.HashMap<String,java.util.Map<String,String>>();

    private void spy()
    {
        final String 						keyTxt = getText();
        final java.util.Map<String,String>	oldMap = spyMap.get( keyTxt );
        final MappableBuilderFactory 		factory
            = new DefaultMappableBuilderFactory()
                .add( MappableItem.ALL_PRIMITIVE_TYPE )
                .add( MappableItem.DO_ARRAYS )
                .add( MappableItem.DO_PARENT_CLASSES )
                .add( MappableItem.TRY_PROTECTED_METHODS );
        final MappableBuilder		builder 	= new MappableBuilder( factory );
        final Map<String,String> 	mapThis		= builder.toMap( this.jList );

        System.err.println( "#####" );
        if( oldMap == null ) {
            System.err.println( "NEW ENTRY" );

            for( java.util.Map.Entry<String,String> e : mapThis.entrySet() ) {
                String 	k 	= e.getKey();
                String 	nv	= e.getValue();
                spyDisplay( k, nv, "NEW", keyTxt );
                }
            }
        else {
            for( java.util.Map.Entry<String,String> e : mapThis.entrySet() ) {
                String 	k 	= e.getKey();
                String 	nv	= e.getValue();
                String 	ov	= oldMap.get( k );
                boolean hasChange;

                if( nv == null ) {
                    hasChange = !( ov == null );
                    }
                else {
                    hasChange = !( nv.equals( ov ));
                    }

                if( hasChange ) {
                    spyDisplay( k, nv, ov, keyTxt );
                    }
                }
            }
        spyMap.put( keyTxt, mapThis );
    }

    private void spyDisplay(
        final String k,
        final String nv,
        final String ov,
        final String keyTxt
        )
    {
        System.err.println(
                " ."
                + k
                + " = "
                + nv
                + " * OLD = "
                + ov
                + " * TXT:"
                + keyTxt
                );
    }
*/
}
