package cx.ath.choisnet.swing.table;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @deprecated use {@link com.googlecode.cchlib.swing.table.LeftDotTableCellRenderer}
 * instead
 */
@Deprecated
public class LeftDotTableCellRenderer extends DefaultTableCellRenderer
{
    private static final long serialVersionUID = 1L;
    private static final String DOTS = "...";

    @Override
    public Component getTableCellRendererComponent(
        JTable  table,
        Object  value,
        boolean isSelected,
        boolean hasFocus,
        int     row,
        int     column
        )
    {
        super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column
                );

        int availableWidth = table.getColumnModel().getColumn(column).getWidth();
        availableWidth -= table.getIntercellSpacing().getWidth();
        Insets borderInsets = getBorder().getBorderInsets((Component)this);
        availableWidth -= (borderInsets.left + borderInsets.right);
        
        final String 		cellText 	= getText();
        final FontMetrics 	fm 			= getFontMetrics( getFont() );
        final int 			dotWidth 	= fm.stringWidth( DOTS );

        if( fm.stringWidth( cellText ) > availableWidth ) {
            int textWidth 	= dotWidth;
            int nChars 		= cellText.length() - 1;
            
            for(; nChars > 0; nChars--) {
                textWidth += fm.charWidth(cellText.charAt(nChars));

                if( textWidth > availableWidth ) {
                    break;
                	}
            	}

            setText( DOTS + cellText.substring(nChars + 1) );
        	}

        return this;
    }
}
