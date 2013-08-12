package com.googlecode.cchlib.net.dhcp;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//import java.util.Properties;
//import com.googlecode.cchlib.util.properties.PropertiesHelper;
//
///**
// *
// *
// */
//public class DHCPOptionsProperties extends AbstractDHCPOptions
//{
//    private static final long serialVersionUID = 1L;
//    private Properties prop;
//    //private String ressourceName;
//    //private File ressourceFile;
//
//    public DHCPOptionsProperties() throws IllegalArgumentException, IOException
//    {
//       this( AbstractDHCPOptions.class.getClassLoader(), "DHCPOptions.properties" );
//    }
//
//    public DHCPOptionsProperties( ClassLoader classLoader, String ressourceName ) throws IllegalArgumentException, IOException
//    {
//        prop = PropertiesHelper.getResourceAsProperties( classLoader, ressourceName );
//    }
//
//    public DHCPOptionsProperties( File propertiesFile ) throws IllegalArgumentException, IOException
//    {
//        this.prop = PropertiesHelper.loadProperties( propertiesFile );
//    }
//    
//    public DHCPOptionsProperties( Map<String,String> propertiesMap )
//    {
//        this.prop = PropertiesHelper.cloneFrom( propertiesMap );
//    }
//
//    @Override
//    public String getProperty(String name)
//    {
////        if( prop == null ) {
////            prop = new Properties();
////
////            InputStream is = null;
////
////            try {
////                is = getInputStream();
////                }
////            catch( IOException e) {
////                throw new RuntimeException("Can't read : " + getResourceString(), e);
////                }
////
////            if( is != null ) {
////                try {
////                    //DHCPOptions _tmp2 = this;
////                    prop.load(is);
////                    }
////                catch( IOException e) {
////                    throw new RuntimeException("Can't read : " + getResourceString(), e);
////                    }
////                finally {
////                    try {
////                        is.close();
////                        }
////                    catch( IOException e ) {
////                        throw new RuntimeException( e );
////                        }
////                    }
////                }
////            else {
////                throw new RuntimeException("Can't find : " + getResourceString() );
////                }
////            }
//        return prop.getProperty( name );
//    }
//
////    private InputStream getInputStream() throws FileNotFoundException
////    {
////        if( ressourceName != null ) {
////            return getClass().getClassLoader().getResourceAsStream(ressourceName);
////        } else {
////            return new FileInputStream( ressourceFile );
////        }
////    }
////
////    private String getResourceString()
////    {
////        if( ressourceName != null ) {
////            return ressourceName;
////        } else {
////            return ressourceFile.toString();
////        }
////    }
//}
