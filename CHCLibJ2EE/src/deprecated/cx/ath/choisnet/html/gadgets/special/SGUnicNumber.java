package deprecated.cx.ath.choisnet.html.gadgets.special;

import java.util.Date;

// Referenced classes of package cx.ath.choisnet.html.gadgets.special:
//            SGHidden

public class SGUnicNumber extends SGHidden
{

    public SGUnicNumber()
    {
        this("UNIC");
    //    0    0:aload_0
    //    1    1:ldc1            #1   <String "UNIC">
    //    2    3:invokespecial   #2   <Method void SGUnicNumber(String)>
    //    3    6:return
    }

    public SGUnicNumber(String gadgetName)
    {
        super(gadgetName, String.valueOf((new Date()).getTime()));
    //    0    0:aload_0
    //    1    1:aload_1
    //    2    2:new             #3   <Class java.util.Date>
    //    3    5:dup
    //    4    6:invokespecial   #4   <Method void Date()>
    //    5    9:invokevirtual   #5   <Method long java.util.Date.getTime()>
    //    6   12:invokestatic    #6   <Method String String.valueOf(long)>
    //    7   15:invokespecial   #7   <Method void SGHidden(String, String)>
    //    8   18:return
    }
}
