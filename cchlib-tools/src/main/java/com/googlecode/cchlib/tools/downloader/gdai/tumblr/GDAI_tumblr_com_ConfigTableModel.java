package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
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

    /**
     * @param dataVector 
     */
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

//    @Override
//    public String getColumnName( final int column)
//    {
//        switch( column ) {
//            case 0 : return "hostname";
//            case 1 : return "Description";
//            default: return null;
//        }
//    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }
}