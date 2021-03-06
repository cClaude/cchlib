// $codepro.audit.disable
package com.googlecode.cchlib.misc.homebrew.nds;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * NDS ROM file format
 * <pre>
 * Header format
 * Field                                Start   End Size    Example data (Metroid demo)
 * Game title                           0x000   0x00B   12  "FIRST HUNT "
 * Game code                            0x00C   0x00F   4   "AMFE"
 * Maker code                           0x010   0x011   2   "01" (Nintendo)
 * Unit code                            0x012   0x012   1   0x00
 * Device code                          0x013   0x013   1   0x00
 * Card size                            0x014   0x014   1   0x07 (2^(20 + 7) = 128Mb = 16 MB)
 * Card info                            0x015   0x01E   10  0x00's
 * Flags                                0x01F   0x01F   1   0x00
 * ARM9 source (ROM)                    0x020   0x023   4   0x00004000 (must be 4 KB aligned)
 * ARM9 execute addr                    0x024   0x027   4   0x02004800
 * ARM9 copy to addr                    0x028   0x02B   4   0x02004000
 * ARM9 binary size                     0x02C   0x02F   4   0x00081D58
 * ARM7 source (ROM)                    0x030   0x033   4   0x000B3000
 * ARM7 execute addr                    0x034   0x037   4   0x02380000
 * ARM7 copy to addr                    0x038   0x03B   4   0x02380000
 * ARM7 binary size                     0x03C   0x03F   4   0x00026494
 * Filename table offset (ROM)          0x040   0x043   4   0x000D9600
 * Filename table size                  0x044   0x047   4   0x11B6
 * FAT offset (ROM)                     0x048   0x04B   4   0x000DA800
 * FAT size                             0x04C   0x04F   4   0x678
 * ARM9 overlay src (ROM)               0x050   0x053   4   0x00085E00
 * ARM9 overlay size                    0x054   0x057   4   0x60
 * ARM7 overlay src (ROM)               0x058   0x05B   4   0
 * ARM7 overlay size                    0x05C   0x05F   4   0
 * Control register flags for read      0x060   0x063   4   0x00586000
 * Control register flags for init      0x064   0x067   4   0x001808F8
 * Icon+titles (ROM)                    0x068   0x06B   4   0x000DB000
 * Secure CRC16                         0x06C   0x06D   2   0xC44D
 * ROM timeout                          0x06E   0x06F   2   0x051E
 * ARM9 unk addr                        0x070   0x073   4   0x020049EC
 * ARM7 unk addr                        0x074   0x077   4   0x02380110
 * Magic number for unencrypted mode    0x078   0x07F   8   0x00's
 * ROM size                             0x080   0x083   4   0x00EE3E44
 * Header size                          0x084   0x087   4   0x4000
 * Unknown 5                            0x088   0x0BF   56  0x00's
 * GBA logo                             0x0C0   0x15B   156 data
 * Logo CRC16                           0x15C   0x15D   2   0xCF56
 * Header CRC16                         0x15E   0x15F   2   0x00F8
 * Reserved                             0x160   0x1FF   160 0x00's
 *
 * The control register flags contain latency settings and depends
 * on the ROM manufacturer (Macronix, Matrix Memory).
 *
 * Unknown2a and 'Header Size' contain flags that are (somewhat)
 * used during boot as part of card CR writes. Unknown2b contains
 * the size of a certain type of transfer done during boot, but
 * it's range checked and cannot be reduced. Unknown3c is seemingly
 * unused, and some code paths get data from 0x160 onward (only
 * 0x170 bytes of a header fetch are actually retained by the BIOS)
 *
 * Secure CRC16 calculation is performed on the ROM from 0x4000
 * to 0x7FFF, with an initial value of 0xFFFF.
 *
 * Logo CRC16 calculation is performed on the header from 0x0C0
 * to 0x15B, with an initial value of 0xFFFF.
 *
 * Header CRC16 calculation is performed on the header (after the
 * previous two CRCs are filled) from 0x000 to 0x15D with an
 * initial value of 0xFFFF.
 *
 * Device code needs lower 3 bits to be 0. This is a similar
 * behavior as GBA header.
 * Icon + logo format (the banner)
 *
 * This is a structure of size 32+512+32+256*6 = 2112 bytes with
 * the following format:
 *
 * Banner structure
 * Offset  Size    Description
 * 0       2       Version (always 1)
 * 2       2       CRC-16 of structure, not including first 32 bytes
 * 4       28      Reserved
 * 32      512     Tile Data
 * 544     32      Palette
 * 576     256     Japanese Title
 * 832     256     English Title
 * 1088    256     French Title
 * 1344    256     German Title
 * 1600    256     Italian Title
 * 1856    256     Spanish Title
 *
 * The icon is a 32x32 picture formed out of 4x4 16 color tiles and a single 16 color palette.
 *
 * Following the icon data (icon offset + 576 bytes), are 6 unicode game titles, displayed
 * in the DS menu depending on the selected language mode. Each title is padded to 128
 * characters (256 bytes).
 * </pre>
 */
public class NDSFileFormat extends FileStructure
{
    private static FSField[] structure = {
        new FSField("Game title"                        , 0x000,   0x00B,   12 , FSType.STRING ), // "FIRST HUNT "
        new FSField("Game code"                         , 0x00C,   0x00F,   4  , FSType.STRING ), // "AMFE"
        new FSField("Maker code"                        , 0x010,   0x011,   2  , FSType.STRING ), // "01" (Nintendo)
        new FSField("Unit code"                         , 0x012,   0x012,   1  , FSType.HEX    ), // 0x00
        new FSField("Device code"                       , 0x013,   0x013,   1  , FSType.HEX    ), // 0x00
        new FSField("Card size"                         , 0x014,   0x014,   1  ), // 0x07 (2^(20 + 7) = 128Mb = 16 MB)
        new FSField("Card info"                         , 0x015,   0x01E,   10 ), // 0x00's
        new FSField("Flags"                             , 0x01F,   0x01F,   1  , FSType.HEX    ), // 0x00
        new FSField("ARM9 source (ROM)"                 , 0x020,   0x023,   4  , FSType.HEX    ), // 0x00004000 (must be 4 KB aligned)
        new FSField("ARM9 execute addr"                 , 0x024,   0x027,   4  , FSType.HEX    ), // 0x02004800
        new FSField("ARM9 copy to addr"                 , 0x028,   0x02B,   4  , FSType.HEX    ), // 0x02004000
        new FSField("ARM9 binary size"                  , 0x02C,   0x02F,   4  , FSType.HEX    ), // 0x00081D58
        new FSField("ARM7 source (ROM)"                 , 0x030,   0x033,   4  , FSType.HEX    ), // 0x000B3000
        new FSField("ARM7 execute addr"                 , 0x034,   0x037,   4  , FSType.HEX    ), // 0x02380000
        new FSField("ARM7 copy to addr"                 , 0x038,   0x03B,   4  , FSType.HEX    ), // 0x02380000
        new FSField("ARM7 binary size"                  , 0x03C,   0x03F,   4  , FSType.HEX    ), // 0x00026494
        new FSField("Filename table offset (ROM)"       , 0x040,   0x043,   4  , FSType.HEX    ), // 0x000D9600
        new FSField("Filename table size"               , 0x044,   0x047,   4  , FSType.HEX    ), // 0x11B6
        new FSField("FAT offset (ROM)"                  , 0x048,   0x04B,   4  , FSType.HEX    ), // 0x000DA800
        new FSField("FAT size"                          , 0x04C,   0x04F,   4  , FSType.HEX    ), // 0x678
        new FSField("ARM9 overlay src (ROM)"            , 0x050,   0x053,   4  , FSType.HEX    ), // 0x00085E00
        new FSField("ARM9 overlay size"                 , 0x054,   0x057,   4  , FSType.HEX    ), // 0x60
        new FSField("ARM7 overlay src (ROM)"            , 0x058,   0x05B,   4  ), // 0
        new FSField("ARM7 overlay size"                 , 0x05C,   0x05F,   4  ), // 0
        new FSField("Control register flags for read"   , 0x060,   0x063,   4  , FSType.HEX    ), // 0x00586000
        new FSField("Control register flags for init"   , 0x064,   0x067,   4  , FSType.HEX    ), // 0x001808F8
        new FSField("Icon+titles (ROM)"                 , 0x068,   0x06B,   4  , FSType.HEX    ), // 0x000DB000
        new FSField("Secure CRC16"                      , 0x06C,   0x06D,   2  , FSType.HEX    ), // 0xC44D
        new FSField("ROM timeout"                       , 0x06E,   0x06F,   2  , FSType.HEX    ), // 0x051E
        new FSField("ARM9 unk addr"                     , 0x070,   0x073,   4  , FSType.HEX    ), // 0x020049EC
        new FSField("ARM7 unk addr"                     , 0x074,   0x077,   4  , FSType.HEX    ), // 0x02380110
        new FSField("Magic number for unencrypted mode" , 0x078,   0x07F,   8  ), // 0x00's
        new FSField("ROM size"                          , 0x080,   0x083,   4  , FSType.HEX    ), // 0x00EE3E44
        new FSField("Header size"                       , 0x084,   0x087,   4  , FSType.HEX    ), // 0x4000
        new FSField("Unknown 5"                         , 0x088,   0x0BF,   56 ), // 0x00's
        new FSField("GBA logo"                          , 0x0C0,   0x15B,   156), // data
        new FSField("Logo CRC16"                        , 0x15C,   0x15D,   2  , FSType.HEX    ), // 0xCF56
        new FSField("Header CRC16"                      , 0x15E,   0x15F,   2  , FSType.HEX    ), // 0x00F8
        new FSField("Reserved"                          , 0x160,   0x1FF,   160), // 0x00's
        };

    public NDSFileFormat(final byte[] file)
    {
        super( file );
    }

    @Override
    protected FSField getFieldInfo( final int i )
    {
        return structure[ i ];
    }

    @Override
    protected int getFieldCount()
    {
        return structure.length;
    }

    public static NDSFileFormat load( final File file ) throws IOException
    {
        final int    size   = getSize();
        final byte[] buffer = new byte[ size ];

        int len;

        try( BufferedInputStream is = new BufferedInputStream( new FileInputStream( file ) ) ) {
            len = is.read( buffer );
            }

        assert len == size;

        return new NDSFileFormat( buffer );
    }

    private static int getSize()
    {
        return structure[ structure.length - 1 ].getEnd();
    }

    public static void main( final String[] args ) throws IOException
    {
        final File          file = new File( args[ 0 ] );
        final NDSFileFormat nds  = NDSFileFormat.load( file );

        final String gameTitle = nds.getFieldAsString( "Game title" );

        for( int i = 0; i<nds.getFieldCount(); i++ ) {
            final FSField f    = nds.getFieldInfo( i );

            f.getType().printField( System.out, f, nds, i );
        }
    }

    public static void printUField( final PrintStream out, final FSField f, final NDSFileFormat nds, final int i )
    {
        printField( out, f, "UNKNOWN" );
    }

    public static void printField( final PrintStream out, final FSField f, final NDSFileFormat nds, final int i )
    {
        printField( out, f, convertToHexa( nds.getBytes( i ) ) );
    }

    public static void printString( final PrintStream out , final FSField f, final NDSFileFormat nds, final int i  )
    {
        out.println( f.getField() + " = \"" + nds.getFieldAsString( i ) + '\"' );
    }

    private static void printField( final PrintStream out , final FSField f, final String value )
    {
        out.println( f.getField() + " = " + value );
    }

    @SuppressWarnings("boxing")
    private static String convertToHexa( final byte[] bytes )
    {
        final StringBuilder sb = new StringBuilder();

        for (final byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
    }
}
