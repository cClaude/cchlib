package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("squid:S00101")
public class GDAI_tumblr_com_ConfigTableModel
    extends DefaultTableModel
{
    private static final long serialVersionUID = 1L;

    private static Class<?>[] columnTypes = new Class[] {
        String.class, String.class
        };
    private static String[] columnNames = {
        "Hostname", "Description",
        };

    @SuppressWarnings("squid:S1149") // Really need Vector
    public GDAI_tumblr_com_ConfigTableModel(
        final Vector<Vector<?>> dataVector
        )
    {
        super( dataVector.size(), columnTypes.length );

        super.setDataVector(
            dataVector,
            DefaultTableModel.convertToVector( columnNames )
            );
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return columnTypes[columnIndex];
    }
}
