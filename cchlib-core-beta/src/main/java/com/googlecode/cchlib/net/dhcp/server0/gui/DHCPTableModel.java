package com.googlecode.cchlib.net.dhcp.server0.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.googlecode.cchlib.net.dhcp.server0.DHCPTableEntry;
import com.googlecode.cchlib.net.dhcp.server0.DHCPTablable;

public class DHCPTableModel extends AbstractTableModel implements DHCPTablable
{
    private enum Column {
        IPAddress,
        MACAddress,
        Hostname,
        LeaseStartTime,
        LeaseTime
    }

    private static final long serialVersionUID = 1L;
    private String[] columnNames = {
            "IP Address",
            "MAC Address",
            "Hostname",
            "Lease Start Time",
            "Lease Time"
            };
    private List<DHCPTableEntry> data = new ArrayList<DHCPTableEntry>();

    @Override
    public String getColumnName( int col )
    {
        return columnNames[ col ].toString();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return data.size();
    }

    @Override
    public Object getValueAt( int row, int col )
    {
        Column          column  = Column.values()[ col ];
        DHCPTableEntry rowData = data.get( row );

        switch( column ) {
            case Hostname:
                return rowData.getHostname();
            case IPAddress:
                return rowData.getIPAddress();
            case LeaseStartTime:
                return rowData.getLeaseStartTime();
            case LeaseTime:
                return rowData.getLeaseTime();
            case MACAddress:
                return rowData.getMACAddress();
        }

        return null;
    }

    @Override
    public void add( DHCPTableEntry entry )
    {
        data.add( entry );

        super.fireTableDataChanged();
    }
}
