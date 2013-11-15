package com.googlecode.cchlib.net.dhcp.server0;

public interface DHCPTableEntry {
    String getHostname();
    String getIPAddress();
    String getLeaseStartTime();
    String getLeaseTime();
    String getMACAddress();
}
//private byte[][]       ipTable         = new byte[254][4]; // 0,255 reserved(1-254 assignable)
//private byte[][]       macTable        = new byte[254][6];
//private byte[][]       hostNameTable   = new byte[254][];
//private long[]         leaseStartTable = new long[254];
//private int[]          leaseTimeTable  = new int[254];
