package com.googlecode.cchlib.xutil.google.googlecontact.types;

import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;
import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

public interface OrganizationEntry extends GoogleContactType {
    public String getType();
    @Header("Type")
    public void setType( String type );

    public String getName();
    @Header("Name")
    public void setName( String name );

    public String getYomiName();
    @Header("Yomi Name")
    public void setYomiName( String yomiName );

    public String getTitle();
    @Header("Title")
    public void setTitle( String title );

    public String getDepartment();
    @Header("Department")
    public void setDepartment( String department );

    public String getSymbol();
    @Header("Symbol")
    public void setSymbol( String symbol );

    public String getLocation();
    @Header("Location")
    public void setLocation( String location );

    public String getJobDescription();
    @Header("Job Description")
    public void setJobDescription( String jobDescription );
}
