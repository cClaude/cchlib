// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: annotate fullnames braces deadcode fieldsfirst splitstr(nl) 
// Source File Name:   Maths.java

package samples.undocumented;


public class Maths
{

    public Maths()
    {
    //    0    0:aload_0         
    //    1    1:invokespecial   #1   <Method void Object()>
    //    2    4:return          
    }

    public static final double sd(double values[])
    {
        double mean = 0.0D;
    //    0    0:dconst_0        
    //    1    1:dstore_1        
        double arr1$[] = values;
    //    2    2:aload_0         
    //    3    3:astore_3        
        int len1$ = arr1$.length;
    //    4    4:aload_3         
    //    5    5:arraylength     
    //    6    6:istore          4
        for(int i$ = 0; i$ < len1$; i$++)
    //*   7    8:iconst_0        
    //*   8    9:istore          5
    //*   9   11:iload           5
    //*  10   13:iload           4
    //*  11   15:icmpge          35
        {
            double value = arr1$[i$];
    //   12   18:aload_3         
    //   13   19:iload           5
    //   14   21:daload          
    //   15   22:dstore          6
            mean += value;
    //   16   24:dload_1         
    //   17   25:dload           6
    //   18   27:dadd            
    //   19   28:dstore_1        
        }

    //   20   29:iinc            5  1
    //*  21   32:goto            11
        mean /= values.length;
    //   22   35:dload_1         
    //   23   36:aload_0         
    //   24   37:arraylength     
    //   25   38:i2d             
    //   26   39:ddiv            
    //   27   40:dstore_1        
        double diffSquareTotal = 0.0D;
    //   28   41:dconst_0        
    //   29   42:dstore_3        
        double arr$[] = values;
    //   30   43:aload_0         
    //   31   44:astore          5
        int len$ = arr$.length;
    //   32   46:aload           5
    //   33   48:arraylength     
    //   34   49:istore          6
        for(int i$ = 0; i$ < len$; i$++)
    //*  35   51:iconst_0        
    //*  36   52:istore          7
    //*  37   54:iload           7
    //*  38   56:iload           6
    //*  39   58:icmpge          92
        {
            double value = arr$[i$];
    //   40   61:aload           5
    //   41   63:iload           7
    //   42   65:daload          
    //   43   66:dstore          8
            double diffSquare = value - mean;
    //   44   68:dload           8
    //   45   70:dload_1         
    //   46   71:dsub            
    //   47   72:dstore          10
            diffSquare *= diffSquare;
    //   48   74:dload           10
    //   49   76:dload           10
    //   50   78:dmul            
    //   51   79:dstore          10
            diffSquareTotal += diffSquare;
    //   52   81:dload_3         
    //   53   82:dload           10
    //   54   84:dadd            
    //   55   85:dstore_3        
        }

    //   56   86:iinc            7  1
    //*  57   89:goto            54
        double sdSquared = diffSquareTotal / (double)(values.length - 1);
    //   58   92:dload_3         
    //   59   93:aload_0         
    //   60   94:arraylength     
    //   61   95:iconst_1        
    //   62   96:isub            
    //   63   97:i2d             
    //   64   98:ddiv            
    //   65   99:dstore          5
        return Math.sqrt(sdSquared);
    //   66  101:dload           5
    //   67  103:invokestatic    #2   <Method double java.lang.Math.sqrt(double)>
    //   68  106:dreturn         
    }
}
