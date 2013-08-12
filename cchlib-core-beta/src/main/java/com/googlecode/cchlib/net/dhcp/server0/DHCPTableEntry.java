package com.googlecode.cchlib.net.dhcp.server0;

public interface DHCPTableEntry {
    public String getHostname();
    public String getIPAddress();
    public String getLeaseStartTime();
    public String getLeaseTime();
    public String getMACAddress();
}
//private byte[][]       ipTable         = new byte[254][4]; // 0,255 reserved(1-254 assignable)
//private byte[][]       macTable        = new byte[254][6];
//private byte[][]       hostNameTable   = new byte[254][];
//private long[]         leaseStartTable = new long[254];
//private int[]          leaseTimeTable  = new int[254];
