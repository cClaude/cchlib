package alpha.cx.ath.choisnet.system;

import alpha.cx.ath.choisnet.system.impl.java.JavaSystemEnvironmentVar;

public class SystemEnvironmentVarFactory
{
    private SystemEnvironmentVarFactory()
    {
    }

    public static SystemEnvironmentVar getDefaultSystemEnvironmentVar()
    {
        return getJavaSystemEnvironmentVar();
    }

    public static SystemEnvironmentVar getJavaSystemEnvironmentVar()
    {
        return JavaSystemEnvironmentVar.getSystemEnvironmentVar();
    }
}
