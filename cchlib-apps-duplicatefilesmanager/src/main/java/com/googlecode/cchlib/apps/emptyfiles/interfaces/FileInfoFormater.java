package com.googlecode.cchlib.apps.emptyfiles.interfaces;

import java.io.File;

public interface FileInfoFormater 
{
    String formatAttributs( File file );
    String formatAttributsDelete();
    String formatLength( File file );
}
