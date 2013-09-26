package deprecated.cx.ath.choisnet.system.impl.win32;
//
//import org.apache.log4j.Logger;
//import com.ice.jni.registry.Registry;
//import com.ice.jni.registry.RegistryException;
//import com.ice.jni.registry.RegistryKey;
//
///**
// * @deprecated No replacement
// */
//@Deprecated
//public class RegWin32Key
//{
//    final private static Logger slogger = Logger.getLogger(RegWin32Key.class);
//    final String      regKeyName;
//    final RegistryKey registryTop;
//    final String      regName;
//
//    public RegWin32Key(String regKeyName) throws EnvArcRegWin32EnvArcException
//    {
//        this.regKeyName = regKeyName;
//        int separator = regKeyName.indexOf("\\");
//
//        if(separator <= 0) {
//            String msg = "Bad format : \"" + regKeyName + "\"";
//            slogger.warn( msg );
//            throw new EnvArcRegWin32EnvArcException(msg);
//        }
//
//        String begin = regKeyName.substring(0, separator).toUpperCase();
//        String end   = regKeyName.substring(separator + 1);
//        regName      = end;
//        registryTop  = Registry.getTopLevelKey( begin );
//
//        if( registryTop == null ) {
//            throw new EnvArcRegWin32EnvArcException(
//                    "Unkwon how to handle TopRegistry : \""
//                    + begin
//                    + "\""
//                    );
//        }
//    }
//
//    public RegistryKey getParentRegistryKey()
//    {
//        return registryTop;
//    }
//
//    public String getRegistryName()
//    {
//        return regName;
//    }
//
//    public RegistryKey getRegistryKeyReadOnly()
//        throws RegistryException
//    {
//        return registryTop.openSubKey(regName);
//    }
//
//    public RegistryKey getRegistryKeyReadWrite()
//        throws RegistryException
//    {
//        return registryTop.openSubKey(regName, RegistryKey.ACCESS_WRITE);
//    }
//
//    @Override
//    public String toString()
//    {
//        return regKeyName;
//    }
//}
