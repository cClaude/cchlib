package cx.ath.choisnet.swing.table;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * TODO:
 * 
 * @author Claude CHOISNET
 */
public class LeftDotRenderer extends DefaultTableCellRenderer
{
    private static final long serialVersionUID = 1L;
    private static final String DOTS = "...";

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
        String cellText = getText();
        FontMetrics fm = getFontMetrics( getFont() );

        if (fm.stringWidth(cellText) > availableWidth) {
            int textWidth = fm.stringWidth( DOTS );
            int nChars = cellText.length() - 1;
            for(; nChars > 0; nChars--) {
                textWidth += fm.charWidth(cellText.charAt(nChars));

                if (textWidth > availableWidth) {
                    break;
                }
            }

            setText( DOTS + cellText.substring(nChars + 1) );
        }

        return this;
    }
}