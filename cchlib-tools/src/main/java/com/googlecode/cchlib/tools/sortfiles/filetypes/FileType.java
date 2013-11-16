package com.googlecode.cchlib.tools.sortfiles.filetypes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FileType
{
    image_ico ( Paths.get( "[image]", "ico"  ), new IgnoreCaseExtensionsFileFilter( "ico", "cur", "ani" ) ),
    image_gif ( Paths.get( "[image]", "gif"  ), new IgnoreCaseExtensionsFileFilter( "gif" ) ),
    image_jpeg( Paths.get( "[image]", "jpeg" ), new IgnoreCaseExtensionsFileFilter( "jpeg", "jpg" ) ),
    image_png ( Paths.get( "[image]", "png"  ), new IgnoreCaseExtensionsFileFilter( "png" ) ),
    image_bmp ( Paths.get( "[image]", "bmp"  ), new IgnoreCaseExtensionsFileFilter( "bmp", "wmf" ) ),
    image_tga ( Paths.get( "[image]", "tga"  ), new IgnoreCaseExtensionsFileFilter( "tga" ) ),
    image_tif ( Paths.get( "[image]", "tga"  ), new IgnoreCaseExtensionsFileFilter( "tif", "tiff" ) ),
    image_emf ( Paths.get( "[image]", "emf"  ), new IgnoreCaseExtensionsFileFilter( "emf" ) ),
    image_svg ( Paths.get( "[image]", "svg"  ), new IgnoreCaseExtensionsFileFilter( "svg" ) ),

    sound_mp3( Paths.get( "[sound]", "mp3" ), new IgnoreCaseExtensionsFileFilter( "mp3" ) ),
    sound_wav( Paths.get( "[sound]", "wav" ), new IgnoreCaseExtensionsFileFilter( "wav" ) ),
    sound_wma( Paths.get( "[sound]", "wma" ), new IgnoreCaseExtensionsFileFilter( "wma" ) ),
    sound_Ogg( Paths.get( "[sound]", "Ogg" ), new IgnoreCaseExtensionsFileFilter( "Ogg" ) ),
    sound_mod( Paths.get( "[sound]", "mod" ), new IgnoreCaseExtensionsFileFilter( "mod", "mo3" ) ),
    sound_mid( Paths.get( "[sound]", "mid" ), new IgnoreCaseExtensionsFileFilter( "mid", "kar" ) ),

    movie_avi( Paths.get( "[movie]", "avi" ), new IgnoreCaseExtensionsFileFilter( "m4a", "avi", "mkv" ) ),
    movie_mp4( Paths.get( "[movie]", "mp4" ), new IgnoreCaseExtensionsFileFilter( "mp4" ) ),
    movie_flv( Paths.get( "[movie]", "flv" ), new IgnoreCaseExtensionsFileFilter( "flv" ) ),
    movie_wmv( Paths.get( "[movie]", "wmv" ), new IgnoreCaseExtensionsFileFilter( "wmv" ) ),
    movie_VOB( Paths.get( "[movie]", "vob" ), new IgnoreCaseExtensionsFileFilter( "vob" ) ),
    movie_mov( Paths.get( "[movie]", "mov" ), new IgnoreCaseExtensionsFileFilter( "mov" ) ),

    arch_7z ( Paths.get( "[arch]", "7z"  ), new IgnoreCaseExtensionsFileFilter( "7z"  ) ),
    arch_zip( Paths.get( "[arch]", "zip" ), new IgnoreCaseExtensionsFileFilter( "zip" ) ),
    arch_rar( Paths.get( "[arch]", "rar" ), new IgnoreCaseExtensionsFileFilter( "rar" ) ),
    arch_wim( Paths.get( "[arch]", "wim" ), new IgnoreCaseExtensionsFileFilter( "wim" ) ),
    arch_gz ( Paths.get( "[arch]", "gz"  ), new IgnoreCaseExtensionsFileFilter( "gz", "tgz", "z" ) ),
    arch_000( Paths.get( "[arch]", "000" ), new IgnoreCaseExtensionsFileFilter( "000", "001", "002", "003" ) ),

    web_html( Paths.get( "[web]", "html" ), new IgnoreCaseExtensionsFileFilter( "html", "htm", "css", "js", "json", "php", "xhtml" ) ),
    web_swf ( Paths.get( "[web]", "swf"  ), new IgnoreCaseExtensionsFileFilter( "swf" ) ),
    web_hta ( Paths.get( "[web]", "hta"  ), new IgnoreCaseExtensionsFileFilter( "hta" ) ),

    db_dbx ( Paths.get( "[db]", "dbx"  ), new IgnoreCaseExtensionsFileFilter( "dbx" ) ),

    doc_xls( Paths.get( "[doc]", "xls" ), new IgnoreCaseExtensionsFileFilter( "xls" ) ),
    doc_doc( Paths.get( "[doc]", "doc" ), new IgnoreCaseExtensionsFileFilter( "doc", "odt", "docx", "dot", "dotx", "dotm" ) ),
    doc_pdf( Paths.get( "[doc]", "pdf" ), new IgnoreCaseExtensionsFileFilter( "pdf" ) ),
    doc_ppt( Paths.get( "[doc]", "ppt" ), new IgnoreCaseExtensionsFileFilter( "ppt" ) ),
    doc_rtf( Paths.get( "[doc]", "rtf" ), new IgnoreCaseExtensionsFileFilter( "rtf" ) ),
    doc_txt( Paths.get( "[doc]", "txt" ), new IgnoreCaseExtensionsFileFilter( "txt" ) ),
    doc_xps( Paths.get( "[doc]", "xps" ), new IgnoreCaseExtensionsFileFilter( "xps" ) ),

    URL( new IgnoreCaseExtensionsFileFilter( "url" ) ),

    exe_exe    ( Paths.get( "[exe]", "exe"      ), new IgnoreCaseExtensionsFileFilter( "ex_", "exe" ) ),
    exe_com    ( Paths.get( "[exe]", "com"      ), new IgnoreCaseExtensionsFileFilter( "com" ) ),
    exe_dll    ( Paths.get( "[exe]", "dll"      ), new IgnoreCaseExtensionsFileFilter( "dl_", "dll" ) ),
    exe_cab    ( Paths.get( "[exe]", "cab"      ), new IgnoreCaseExtensionsFileFilter( "cab" ) ),
    exe_misc   ( Paths.get( "[exe]", "misc"     ), new IgnoreCaseExtensionsFileFilter( "cat", "sys", "wiz", "vsd", "scr", "cpl", "ocx" ) ),
    exe_install( Paths.get( "[exe]", "install"  ), new IgnoreCaseExtensionsFileFilter( "msi", "msp" ) ),
    exe_diagpkg( Paths.get( "[exe]", "diagpkg"  ), new IgnoreCaseExtensionsFileFilter( "diagpkg" ) ),
    exe_reg    ( Paths.get( "[exe]", "reg"      ), new IgnoreCaseExtensionsFileFilter( "reg" ) ),


    java_jar       ( Paths.get( "[java]", "jar"        ), new IgnoreCaseExtensionsFileFilter( "jar", "war", "ear", "class" ) ),
    java_java      ( Paths.get( "[java]", "java"       ), new IgnoreCaseExtensionsFileFilter( "java" ) ),
    java_properties( Paths.get( "[java]", "properties" ), new IgnoreCaseExtensionsFileFilter( "properties" ) ),
    java_policy    ( Paths.get( "[java]", "policy"     ), new IgnoreCaseExtensionsFileFilter( "policy" ) ),
    java_manifest  ( Paths.get( "[java]", "manifest"   ), new IgnoreCaseExtensionsFileFilter( "manifest" ) ),

    shortcut_library( Paths.get( "[shortcut]", "library" ), new IgnoreCaseExtensionsFileFilter( "library-ms" ) ),
    shortcut_lnk    ( Paths.get( "[shortcut]", "lnk"     ), new IgnoreCaseExtensionsFileFilter( "lnk" ) ),

    ms_xrm_ms                  (  Paths.get( "[ms]", "xrm-ms"                   ), new IgnoreCaseExtensionsFileFilter( "xrm-ms" ) ),
    ms_customDestinations_ms   (  Paths.get( "[ms]", "customDestinations-ms"    ), new IgnoreCaseExtensionsFileFilter( "customDestinations-ms" ) ),
    ms_automaticDestinations_ms(  Paths.get( "[ms]", "automaticDestinations-ms" ), new IgnoreCaseExtensionsFileFilter( "automaticDestinations-ms" ) ),
    ms_theme                   (  Paths.get( "[ms]", "theme"                    ), new IgnoreCaseExtensionsFileFilter( "theme" ) ),

    fonts_ttf( Paths.get( "[fonts]", "truetype" ), new IgnoreCaseExtensionsFileFilter( "ttf" ) ),
    fonts_fon( Paths.get( "[fonts]", "fon"      ), new IgnoreCaseExtensionsFileFilter( "fon" ) ),
    fonts_otf( Paths.get( "[fonts]", "otf"      ), new IgnoreCaseExtensionsFileFilter( "otf" ) ),

    dev_cpp( Paths.get( "[dev]", "c"   ), new IgnoreCaseExtensionsFileFilter( "cpp", "c", "h", "rc", "dfm", "res" ) ),
    dev_a  ( Paths.get( "[dev]", "a"   ), new IgnoreCaseExtensionsFileFilter( "a"   ) ),
    dev_s  ( Paths.get( "[dev]", "s"   ), new IgnoreCaseExtensionsFileFilter( "s"   ) ),
    dev_pas( Paths.get( "[dev]", "pas" ), new IgnoreCaseExtensionsFileFilter( "pas" ) ),

    certificate_cer( Paths.get( "[certificate]", "cer" ), new IgnoreCaseExtensionsFileFilter( "cer" ) ),

    unix_so ( Paths.get( "[unix]", "so" ), new ExtensionsFileFilter( "so" ) ),
    
    bat( new IgnoreCaseExtensionsFileFilter( "bat", "cmd" ) ),
    bin( new IgnoreCaseExtensionsFileFilter( "bin" ) ),
    chm( new IgnoreCaseExtensionsFileFilter( "chm" ) ),
    hlp( new IgnoreCaseExtensionsFileFilter( "hlp" ) ),
    inf( new IgnoreCaseExtensionsFileFilter( "inf" ) ),
    ini( new IgnoreCaseExtensionsFileFilter( "ini" ) ),
    ksh( new IgnoreCaseExtensionsFileFilter( "ksh" ) ),
    lib( new IgnoreCaseExtensionsFileFilter( "lib" ) ),
    log( new IgnoreCaseExtensionsFileFilter( "log" ) ),
    man( new IgnoreCaseExtensionsFileFilter( "man" ) ),
    mdb( new IgnoreCaseExtensionsFileFilter( "mdb" ) ),
    mf ( new IgnoreCaseExtensionsFileFilter( "mf"  ) ),
  
    BAK( new IgnoreCaseExtensionsFileFilter( "bak" ) ),
    CHK( new IgnoreCaseExtensionsFileFilter( "CHK" ) ),
    OLD( new IgnoreCaseExtensionsFileFilter( "OLD" ) ),

    ERR( new IgnoreCaseExtensionsFileFilter( "ERR" ) ),
    IDX( new IgnoreCaseExtensionsFileFilter( "IDX" ) ),
    IDY( new IgnoreCaseExtensionsFileFilter( "IDY" ) ),
    INS( new IgnoreCaseExtensionsFileFilter( "INS" ) ),
    JCL( new IgnoreCaseExtensionsFileFilter( "JCL" ) ),
    LBR( new IgnoreCaseExtensionsFileFilter( "LBR" ) ),
    OPA( new IgnoreCaseExtensionsFileFilter( "OPA" ) ),
    PLS( new IgnoreCaseExtensionsFileFilter( "PLS" ) ),
    PSL( new IgnoreCaseExtensionsFileFilter( "PSL" ) ),
    PTR( new IgnoreCaseExtensionsFileFilter( "PTR" ) ),
    RSA( new IgnoreCaseExtensionsFileFilter( "RSA" ) ),
    SF ( new IgnoreCaseExtensionsFileFilter( "SF" ) ),
    TRA( new IgnoreCaseExtensionsFileFilter( "TRA" ) ),
    VER( new IgnoreCaseExtensionsFileFilter( "VER" ) ),
    XRR( new IgnoreCaseExtensionsFileFilter( "XRR" ) ),
    XSG( new IgnoreCaseExtensionsFileFilter( "XSG" ) ),
    app( new IgnoreCaseExtensionsFileFilter( "app" ) ),
    cbl( new IgnoreCaseExtensionsFileFilter( "cbl" ) ),
    cfg( new IgnoreCaseExtensionsFileFilter( "cfg" ) ),
    cls( new IgnoreCaseExtensionsFileFilter( "cls" ) ),
    cnt( new IgnoreCaseExtensionsFileFilter( "cnt" ) ),
    cpb( new IgnoreCaseExtensionsFileFilter( "cpb" ) ),
    cpy( new IgnoreCaseExtensionsFileFilter( "cpy" ) ),
    crw( new IgnoreCaseExtensionsFileFilter( "crw" ) ),
    csv( new IgnoreCaseExtensionsFileFilter( "csv" ) ),
    dat( new IgnoreCaseExtensionsFileFilter( "dat" ) ),
    dcu( new IgnoreCaseExtensionsFileFilter( "dcu" ) ),
    dta( new IgnoreCaseExtensionsFileFilter( "dta" ) ),
    ebc( new IgnoreCaseExtensionsFileFilter( "ebc" ) ),
    gnt( new IgnoreCaseExtensionsFileFilter( "gnt" ) ),
    gs ( new IgnoreCaseExtensionsFileFilter( "gs"  ) ),
    mui( new IgnoreCaseExtensionsFileFilter( "mui" ) ),
    mvs( new IgnoreCaseExtensionsFileFilter( "mvs" ) ),
    nvi( new IgnoreCaseExtensionsFileFilter( "nvi" ) ),
    obj( new IgnoreCaseExtensionsFileFilter( "obj" ) ),

    sav( new IgnoreCaseExtensionsFileFilter( "sav" ) ),
    sql( new IgnoreCaseExtensionsFileFilter( "sql" ) ),
    tab( new IgnoreCaseExtensionsFileFilter( "tab" ) ),
    vbs( new IgnoreCaseExtensionsFileFilter( "vbs" ) ),
    xml( new IgnoreCaseExtensionsFileFilter( "xml", "dtd", "xsl" ) ),
    gm ( new IgnoreCaseExtensionsFileFilter( "gm" ) ),
    db ( new IgnoreCaseExtensionsFileFilter( "db" ) ),
    
    model( new IgnoreCaseExtensionsFileFilter( "model", "dds" ) ),
    chunk( new IgnoreCaseExtensionsFileFilter( "chunk", "cdata" ) ),
    pyc  ( new IgnoreCaseExtensionsFileFilter( "pyc",   "kc" ) ),

    TMP( new IgnoreCaseExtensionsFileFilter( "TMP", "temp" ) ),

    db_journal( new IgnoreCaseExtensionsFileFilter( "db-journal" ) ),
    MSMessageStore( new IgnoreCaseExtensionsFileFilter( "MSMessageStore" ) ),

    sqlite_sqlite ( Paths.get( "[sqlite]", "sqlite"  ), new IgnoreCaseExtensionsFileFilter( "sqlite" ) ),
    sqlite_journal( Paths.get( "[sqlite]", "journal" ), new IgnoreCaseExtensionsFileFilter( "sqlite-journal" ) ),
    sqlite_wal    ( Paths.get( "[sqlite]", "wal" ), new IgnoreCaseExtensionsFileFilter( "sqlite-wal" ) ),
    sqlite_shm    ( Paths.get( "[sqlite]", "shm" ), new IgnoreCaseExtensionsFileFilter( "sqlite-shm" ) ),

    torrent( new IgnoreCaseExtensionsFileFilter( "torrent" ) ),

    zzz_cdf_ms( new IgnoreCaseExtensionsFileFilter( "cdf-ms" ) ),


    zzz_32( new IgnoreCaseExtensionsFileFilter( "32" ) ),
    zzz__AdvMapObjectLink_( new IgnoreCaseExtensionsFileFilter( "(AdvMapObjectLink)" ) ),
    zzz_access( new IgnoreCaseExtensionsFileFilter( "access" ) ),
    zzz_acs2( new IgnoreCaseExtensionsFileFilter( "acs2" ) ),
    zzz_adml( new IgnoreCaseExtensionsFileFilter( "adml" ) ),
    zzz_admx( new IgnoreCaseExtensionsFileFilter( "admx" ) ),
    zzz_amx( new IgnoreCaseExtensionsFileFilter( "amx" ) ),
    zzz_an( new IgnoreCaseExtensionsFileFilter( "an" ) ),
    zzz_anca( new IgnoreCaseExtensionsFileFilter( "anca" ) ),
    zzz_apl( new IgnoreCaseExtensionsFileFilter( "apl" ) ),
    zzz_apx( new IgnoreCaseExtensionsFileFilter( "apx" ) ),
    zzz_arx( new IgnoreCaseExtensionsFileFilter( "arx" ) ),
    zzz_asi( new IgnoreCaseExtensionsFileFilter( "asi" ) ),
    zzz_aspx( new IgnoreCaseExtensionsFileFilter( "aspx" ) ),
    zzz_asr( new IgnoreCaseExtensionsFileFilter( "asr" ) ),
    zzz_atc( new IgnoreCaseExtensionsFileFilter( "atc" ) ),
    zzz_atn( new IgnoreCaseExtensionsFileFilter( "atn" ) ),
    zzz_au( new IgnoreCaseExtensionsFileFilter( "au" ) ),
    zzz_avs( new IgnoreCaseExtensionsFileFilter( "avs" ) ),
    zzz_ax( new IgnoreCaseExtensionsFileFilter( "ax" ) ),
    zzz_bcf( new IgnoreCaseExtensionsFileFilter( "bcf" ) ),
    zzz_BDR( new IgnoreCaseExtensionsFileFilter( "BDR" ) ),
    zzz_bfc( new IgnoreCaseExtensionsFileFilter( "bfc" ) ),
    zzz_blf( new IgnoreCaseExtensionsFileFilter( "blf" ) ),
    zzz_box( new IgnoreCaseExtensionsFileFilter( "box" ) ),
    zzz_bsp( new IgnoreCaseExtensionsFileFilter( "bsp" ) ),
    zzz_bsp2( new IgnoreCaseExtensionsFileFilter( "bsp2" ) ),
    zzz_cache( new IgnoreCaseExtensionsFileFilter( "cache" ) ),
    zzz_cdb( new IgnoreCaseExtensionsFileFilter( "cdb" ) ),
    zzz_cfs( new IgnoreCaseExtensionsFileFilter( "cfs" ) ),
    zzz_chr( new IgnoreCaseExtensionsFileFilter( "chr" ) ),
    zzz_ci( new IgnoreCaseExtensionsFileFilter( "ci" ) ),
    zzz_col( new IgnoreCaseExtensionsFileFilter( "col" ) ),
    zzz_compiled( new IgnoreCaseExtensionsFileFilter( "compiled" ) ),
    zzz_CompositeFont( new IgnoreCaseExtensionsFileFilter( "CompositeFont" ) ),
    zzz_conf( new IgnoreCaseExtensionsFileFilter( "conf" ) ),
    zzz_config( new IgnoreCaseExtensionsFileFilter( "config" ) ),
    zzz_crt( new IgnoreCaseExtensionsFileFilter( "crt" ) ),
    zzz_Crwl( new IgnoreCaseExtensionsFileFilter( "Crwl" ) ),
    zzz_ctree( new IgnoreCaseExtensionsFileFilter( "ctree" ) ),
    zzz_cus( new IgnoreCaseExtensionsFileFilter( "cus" ) ),
    zzz_dcf( new IgnoreCaseExtensionsFileFilter( "dcf" ) ),
    zzz_dct( new IgnoreCaseExtensionsFileFilter( "dct" ) ),
    zzz_def( new IgnoreCaseExtensionsFileFilter( "def" ) ),
    zzz_deleteme( new IgnoreCaseExtensionsFileFilter( "deleteme" ) ),
    zzz_dgn( new IgnoreCaseExtensionsFileFilter( "dgn" ) ),
    zzz_dic( new IgnoreCaseExtensionsFileFilter( "dic" ) ),
    zzz_dir( new IgnoreCaseExtensionsFileFilter( "dir" ) ),
    zzz_dist( new IgnoreCaseExtensionsFileFilter( "dist" ) ),
    zzz_djvu( new IgnoreCaseExtensionsFileFilter( "djvu" ) ),
    zzz_dmp( new IgnoreCaseExtensionsFileFilter( "dmp" ) ),
    zzz_downloading( new IgnoreCaseExtensionsFileFilter( "downloading" ) ),
    zzz_DPV( new IgnoreCaseExtensionsFileFilter( "DPV" ) ),
    zzz_dst( new IgnoreCaseExtensionsFileFilter( "dst" ) ),
    zzz_dun( new IgnoreCaseExtensionsFileFilter( "dun" ) ),
    zzz_dwg( new IgnoreCaseExtensionsFileFilter( "dwg" ) ),
    zzz_dwt( new IgnoreCaseExtensionsFileFilter( "dwt" ) ),
    zzz_dxp( new IgnoreCaseExtensionsFileFilter( "dxp" ) ),
    zzz_ebd( new IgnoreCaseExtensionsFileFilter( "ebd" ) ),
    zzz_edb( new IgnoreCaseExtensionsFileFilter( "edb" ) ),
    zzz_efi( new IgnoreCaseExtensionsFileFilter( "efi" ) ),
    zzz_eftx( new IgnoreCaseExtensionsFileFilter( "eftx" ) ),
    zzz_ELM( new IgnoreCaseExtensionsFileFilter( "ELM" ) ),
    zzz_embedded( new IgnoreCaseExtensionsFileFilter( "embedded" ) ),
    zzz_et( new IgnoreCaseExtensionsFileFilter( "et" ) ),
    zzz_etl( new IgnoreCaseExtensionsFileFilter( "etl" ) ),
    zzz_exp( new IgnoreCaseExtensionsFileFilter( "exp" ) ),
    zzz_Extra( new IgnoreCaseExtensionsFileFilter( "Extra" ) ),
    zzz_FAE( new IgnoreCaseExtensionsFileFilter( "FAE" ) ),
    zzz_fdt( new IgnoreCaseExtensionsFileFilter( "fdt" ) ),
    zzz_FLT( new IgnoreCaseExtensionsFileFilter( "FLT" ) ),
    zzz_fmt( new IgnoreCaseExtensionsFileFilter( "fmt" ) ),
    zzz_fow( new IgnoreCaseExtensionsFileFilter( "fow" ) ),
    zzz_fsb( new IgnoreCaseExtensionsFileFilter( "fsb" ) ),
    zzz_fxo( new IgnoreCaseExtensionsFileFilter( "fxo" ) ),
    zzz_gif_filedata( new IgnoreCaseExtensionsFileFilter( "gif_filedata" ) ),
    zzz_gmx( new IgnoreCaseExtensionsFileFilter( "gmx" ) ),
    zzz_grs( new IgnoreCaseExtensionsFileFilter( "grs" ) ),
    zzz_gsm( new IgnoreCaseExtensionsFileFilter( "gsm" ) ),
    zzz_gthr( new IgnoreCaseExtensionsFileFilter( "gthr" ) ),
    zzz_h1s( new IgnoreCaseExtensionsFileFilter( "h1s" ) ),
    zzz_hdi( new IgnoreCaseExtensionsFileFilter( "hdi" ) ),
    zzz_HDP( new IgnoreCaseExtensionsFileFilter( "HDP" ) ),
    zzz_hdr( new IgnoreCaseExtensionsFileFilter( "hdr" ) ),
    zzz_heu( new IgnoreCaseExtensionsFileFilter( "heu" ) ),
    zzz_HOL( new IgnoreCaseExtensionsFileFilter( "HOL" ) ),
    zzz_hpk( new IgnoreCaseExtensionsFileFilter( "hpk" ) ),
    zzz_htc( new IgnoreCaseExtensionsFileFilter( "htc" ) ),
    zzz_HXC( new IgnoreCaseExtensionsFileFilter( "HXC" ) ),
    zzz_HXK( new IgnoreCaseExtensionsFileFilter( "HXK" ) ),
    zzz_HXS( new IgnoreCaseExtensionsFileFilter( "HXS" ) ),
    zzz_HXT( new IgnoreCaseExtensionsFileFilter( "HXT" ) ),
    zzz_icc( new IgnoreCaseExtensionsFileFilter( "icc" ) ),
    zzz_icns( new IgnoreCaseExtensionsFileFilter( "icns" ) ),
    zzz_IDX_DLL( new IgnoreCaseExtensionsFileFilter( "IDX_DLL" ) ),
    zzz_iec( new IgnoreCaseExtensionsFileFilter( "iec" ) ),
    zzz_IFO( new IgnoreCaseExtensionsFileFilter( "IFO" ) ),
    zzz_ilg( new IgnoreCaseExtensionsFileFilter( "ilg" ) ),
    zzz_IME( new IgnoreCaseExtensionsFileFilter( "IME" ) ),
    zzz_inc( new IgnoreCaseExtensionsFileFilter( "inc" ) ),
    zzz_INF_loc( new IgnoreCaseExtensionsFileFilter( "INF_loc" ) ),
    zzz_ips( new IgnoreCaseExtensionsFileFilter( "ips" ) ),
    zzz_isp( new IgnoreCaseExtensionsFileFilter( "isp" ) ),
    zzz_ITS( new IgnoreCaseExtensionsFileFilter( "ITS" ) ),
    zzz_IXB( new IgnoreCaseExtensionsFileFilter( "IXB" ) ),
    zzz_IXC( new IgnoreCaseExtensionsFileFilter( "IXC" ) ),
    zzz_ja( new IgnoreCaseExtensionsFileFilter( "ja" ) ),
    zzz_java_wrapper( new IgnoreCaseExtensionsFileFilter( "java_wrapper" ) ),
    zzz_jhtml( new IgnoreCaseExtensionsFileFilter( "jhtml" ) ),
    zzz_jrs( new IgnoreCaseExtensionsFileFilter( "jrs" ) ),
    zzz_jsa( new IgnoreCaseExtensionsFileFilter( "jsa" ) ),
    zzz_key( new IgnoreCaseExtensionsFileFilter( "key" ) ),
    zzz_kic( new IgnoreCaseExtensionsFileFilter( "kic" ) ),
    zzz_lax( new IgnoreCaseExtensionsFileFilter( "lax" ) ),
    zzz_lcf( new IgnoreCaseExtensionsFileFilter( "lcf" ) ),
    zzz_lck( new IgnoreCaseExtensionsFileFilter( "lck" ) ),
    zzz_LCN( new IgnoreCaseExtensionsFileFilter( "LCN" ) ),
    zzz_lde( new IgnoreCaseExtensionsFileFilter( "lde" ) ),
    zzz_LES( new IgnoreCaseExtensionsFileFilter( "LES" ) ),
    zzz_lex( new IgnoreCaseExtensionsFileFilter( "lex" ) ),
    zzz_lfr( new IgnoreCaseExtensionsFileFilter( "lfr" ) ),
    zzz_LIT( new IgnoreCaseExtensionsFileFilter( "LIT" ) ),
    zzz_lkg( new IgnoreCaseExtensionsFileFilter( "lkg" ) ),
    zzz_lmp( new IgnoreCaseExtensionsFileFilter( "lmp" ) ),
    zzz_LNG( new IgnoreCaseExtensionsFileFilter( "LNG" ) ),
    zzz_LOG1( new IgnoreCaseExtensionsFileFilter( "LOG1" ) ),
    zzz_lsp( new IgnoreCaseExtensionsFileFilter( "lsp" ) ),
    zzz_lst( new IgnoreCaseExtensionsFileFilter( "lst" ) ),
    zzz_lt( new IgnoreCaseExtensionsFileFilter( "lt" ) ),
    zzz_lua( new IgnoreCaseExtensionsFileFilter( "lua" ) ),
    zzz_lut( new IgnoreCaseExtensionsFileFilter( "lut" ) ),
    zzz_lv( new IgnoreCaseExtensionsFileFilter( "lv" ) ),
    zzz_lwi( new IgnoreCaseExtensionsFileFilter( "lwi" ) ),
    zzz_map( new IgnoreCaseExtensionsFileFilter( "map" ) ),
    zzz_mapping( new IgnoreCaseExtensionsFileFilter( "mapping" ) ),
    zzz_mcaf( new IgnoreCaseExtensionsFileFilter( "mcaf" ) ),
    zzz_mdl( new IgnoreCaseExtensionsFileFilter( "mdl" ) ),
    zzz_mfl( new IgnoreCaseExtensionsFileFilter( "mfl" ) ),
    zzz_mgc( new IgnoreCaseExtensionsFileFilter( "mgc" ) ),
    zzz_mk( new IgnoreCaseExtensionsFileFilter( "mk" ) ),
    zzz_mlt( new IgnoreCaseExtensionsFileFilter( "mlt" ) ),
    zzz_MML( new IgnoreCaseExtensionsFileFilter( "MML" ) ),
    zzz_mo( new IgnoreCaseExtensionsFileFilter( "mo" ) ),
    zzz_mof( new IgnoreCaseExtensionsFileFilter( "mof" ) ),
    zzz_msc( new IgnoreCaseExtensionsFileFilter( "msc" ) ),
    zzz_mst( new IgnoreCaseExtensionsFileFilter( "mst" ) ),
    zzz_msu( new IgnoreCaseExtensionsFileFilter( "msu" ) ),
    zzz_mtk( new IgnoreCaseExtensionsFileFilter( "mtk" ) ),
    zzz_mum( new IgnoreCaseExtensionsFileFilter( "mum" ) ),
    zzz_mzz( new IgnoreCaseExtensionsFileFilter( "mzz" ) ),
    zzz_new( new IgnoreCaseExtensionsFileFilter( "new" ) ),
    zzz_NFI( new IgnoreCaseExtensionsFileFilter( "NFI" ) ),
    zzz_nib( new IgnoreCaseExtensionsFileFilter( "nib" ) ),
    zzz_nlp( new IgnoreCaseExtensionsFileFilter( "nlp" ) ),
    zzz_nls( new IgnoreCaseExtensionsFileFilter( "nls" ) ),
    zzz_nod( new IgnoreCaseExtensionsFileFilter( "nod" ) ),
    zzz_notes( new IgnoreCaseExtensionsFileFilter( "notes" ) ),
    zzz_ODF( new IgnoreCaseExtensionsFileFilter( "ODF" ) ),
    zzz_oeaccount( new IgnoreCaseExtensionsFileFilter( "oeaccount" ) ),
    zzz_OLB( new IgnoreCaseExtensionsFileFilter( "OLB" ) ),
    zzz_ONE( new IgnoreCaseExtensionsFileFilter( "ONE" ) ),
    zzz_onepkg( new IgnoreCaseExtensionsFileFilter( "onepkg" ) ),
    zzz_opal( new IgnoreCaseExtensionsFileFilter( "opal" ) ),
    zzz_ori( new IgnoreCaseExtensionsFileFilter( "ori" ) ),
    zzz_original( new IgnoreCaseExtensionsFileFilter( "original" ) ),
    zzz_ovr( new IgnoreCaseExtensionsFileFilter( "ovr" ) ),
    zzz_pak( new IgnoreCaseExtensionsFileFilter( "pak" ) ),
    zzz_pal( new IgnoreCaseExtensionsFileFilter( "pal" ) ),
    zzz_pat( new IgnoreCaseExtensionsFileFilter( "pat" ) ),
    zzz_patch( new IgnoreCaseExtensionsFileFilter( "patch" ) ),
    zzz_pbk( new IgnoreCaseExtensionsFileFilter( "pbk" ) ),
    zzz_pd6( new IgnoreCaseExtensionsFileFilter( "pd6" ) ),
    zzz_pdb( new IgnoreCaseExtensionsFileFilter( "pdb" ) ),
    zzz_pf( new IgnoreCaseExtensionsFileFilter( "pf" ) ),
    zzz_pfm( new IgnoreCaseExtensionsFileFilter( "pfm" ) ),
    zzz_pk2( new IgnoreCaseExtensionsFileFilter( "pk2" ) ),
    zzz_pl( new IgnoreCaseExtensionsFileFilter( "pl" ) ),
    zzz_plc( new IgnoreCaseExtensionsFileFilter( "plc" ) ),
    zzz_plist( new IgnoreCaseExtensionsFileFilter( "plist" ) ),
    zzz_PNF( new IgnoreCaseExtensionsFileFilter( "PNF" ) ),
    zzz_POC( new IgnoreCaseExtensionsFileFilter( "POC" ) ),
    zzz_ppchain( new IgnoreCaseExtensionsFileFilter( "ppchain" ) ),
    zzz_prof( new IgnoreCaseExtensionsFileFilter( "prof" ) ),
    zzz_psd( new IgnoreCaseExtensionsFileFilter( "psd" ) ),
    zzz_pse8db( new IgnoreCaseExtensionsFileFilter( "pse8db" ) ),
    zzz_psh( new IgnoreCaseExtensionsFileFilter( "psh" ) ),
    zzz_pso( new IgnoreCaseExtensionsFileFilter( "pso" ) ),
    zzz_pss( new IgnoreCaseExtensionsFileFilter( "pss" ) ),
    zzz_ptc( new IgnoreCaseExtensionsFileFilter( "ptc" ) ),
    zzz_ptxml( new IgnoreCaseExtensionsFileFilter( "ptxml" ) ),
    zzz_qm( new IgnoreCaseExtensionsFileFilter( "qm" ) ),
    zzz_qss( new IgnoreCaseExtensionsFileFilter( "qss" ) ),
    zzz_ran( new IgnoreCaseExtensionsFileFilter( "ran" ) ),
    zzz_rbf( new IgnoreCaseExtensionsFileFilter( "rbf" ) ),
    zzz_rcc( new IgnoreCaseExtensionsFileFilter( "rcc" ) ),
    zzz_rdf( new IgnoreCaseExtensionsFileFilter( "rdf" ) ),
    zzz_reanim( new IgnoreCaseExtensionsFileFilter( "reanim" ) ),
    zzz_RLL( new IgnoreCaseExtensionsFileFilter( "RLL" ) ),
    zzz_RPT( new IgnoreCaseExtensionsFileFilter( "RPT" ) ),
    zzz_scc( new IgnoreCaseExtensionsFileFilter( "scc" ) ),
    zzz_sdb( new IgnoreCaseExtensionsFileFilter( "sdb" ) ),
    zzz_security( new IgnoreCaseExtensionsFileFilter( "security" ) ),
    zzz_sep( new IgnoreCaseExtensionsFileFilter( "sep" ) ),
    zzz_ses( new IgnoreCaseExtensionsFileFilter( "ses" ) ),
    zzz_setup( new IgnoreCaseExtensionsFileFilter( "setup" ) ),
    zzz_sfk( new IgnoreCaseExtensionsFileFilter( "sfk" ) ),
    zzz_SFX( new IgnoreCaseExtensionsFileFilter( "SFX" ) ),
    zzz_sha( new IgnoreCaseExtensionsFileFilter( "sha" ) ),
    zzz_sif( new IgnoreCaseExtensionsFileFilter( "sif" ) ),
    zzz_sig( new IgnoreCaseExtensionsFileFilter( "sig" ) ),
    zzz_sl( new IgnoreCaseExtensionsFileFilter( "sl" ) ),
    zzz_sol( new IgnoreCaseExtensionsFileFilter( "sol" ) ),
    zzz_spr( new IgnoreCaseExtensionsFileFilter( "spr" ) ),
    zzz_spt( new IgnoreCaseExtensionsFileFilter( "spt" ) ),
    zzz_sqm( new IgnoreCaseExtensionsFileFilter( "sqm" ) ),
    zzz_start_xfs( new IgnoreCaseExtensionsFileFilter( "start_xfs" ) ),
    zzz_stb( new IgnoreCaseExtensionsFileFilter( "stb" ) ),
    zzz_strings( new IgnoreCaseExtensionsFileFilter( "strings" ) ),
    zzz_stub( new IgnoreCaseExtensionsFileFilter( "stub" ) ),
    zzz_tag( new IgnoreCaseExtensionsFileFilter( "tag" ) ),
    zzz_tbl( new IgnoreCaseExtensionsFileFilter( "tbl" ) ),
    zzz_template( new IgnoreCaseExtensionsFileFilter( "template" ) ),
    zzz_th( new IgnoreCaseExtensionsFileFilter( "th" ) ),
    zzz_thmx( new IgnoreCaseExtensionsFileFilter( "thmx" ) ),
    zzz_time( new IgnoreCaseExtensionsFileFilter( "time" ) ),
    zzz_tlb( new IgnoreCaseExtensionsFileFilter( "tlb" ) ),
    zzz_tlx( new IgnoreCaseExtensionsFileFilter( "tlx" ) ),
    zzz_trail( new IgnoreCaseExtensionsFileFilter( "trail" ) ),
    zzz_ttc( new IgnoreCaseExtensionsFileFilter( "ttc" ) ),
    zzz_tudb( new IgnoreCaseExtensionsFileFilter( "tudb" ) ),
    zzz_ui( new IgnoreCaseExtensionsFileFilter( "ui" ) ),
    zzz_ukx( new IgnoreCaseExtensionsFileFilter( "ukx" ) ),
    zzz_usm( new IgnoreCaseExtensionsFileFilter( "usm" ) ),
    zzz_UTF8( new IgnoreCaseExtensionsFileFilter( "UTF8" ) ),
    zzz_utx( new IgnoreCaseExtensionsFileFilter( "utx" ) ),
    zzz_uwv( new IgnoreCaseExtensionsFileFilter( "uwv" ) ),
    zzz_vdf( new IgnoreCaseExtensionsFileFilter( "vdf" ) ),
    zzz_vdm( new IgnoreCaseExtensionsFileFilter( "vdm" ) ),
    zzz_vpx( new IgnoreCaseExtensionsFileFilter( "vpx" ) ),
    zzz_vsh( new IgnoreCaseExtensionsFileFilter( "vsh" ) ),
    zzz_WAD( new IgnoreCaseExtensionsFileFilter( "WAD" ) ),
    zzz_wer( new IgnoreCaseExtensionsFileFilter( "wer" ) ),
    zzz_wid( new IgnoreCaseExtensionsFileFilter( "wid" ) ),
    zzz_win32manifest( new IgnoreCaseExtensionsFileFilter( "win32manifest" ) ),
    zzz_wmdb( new IgnoreCaseExtensionsFileFilter( "wmdb" ) ),
    zzz_work( new IgnoreCaseExtensionsFileFilter( "work" ) ),
    zzz_wpf( new IgnoreCaseExtensionsFileFilter( "wpf" ) ),
    zzz_wpl( new IgnoreCaseExtensionsFileFilter( "wpl" ) ),
    zzz_ws( new IgnoreCaseExtensionsFileFilter( "ws" ) ),
    zzz_xap( new IgnoreCaseExtensionsFileFilter( "xap" ) ),
    zzz_xdb( new IgnoreCaseExtensionsFileFilter( "xdb" ) ),
    zzz_XLAM( new IgnoreCaseExtensionsFileFilter( "XLAM" ) ),
    zzz_xltx( new IgnoreCaseExtensionsFileFilter( "xltx" ) ),
    zzz_xpt( new IgnoreCaseExtensionsFileFilter( "xpt" ) ),
    zzz_xsd( new IgnoreCaseExtensionsFileFilter( "xsd" ) ),
    zzz_xtra( new IgnoreCaseExtensionsFileFilter( "xtra" ) ),
    zzz_xul( new IgnoreCaseExtensionsFileFilter( "xul" ) ),
    zzz_zap( new IgnoreCaseExtensionsFileFilter( "zap" ) ),
    zzz_zdct( new IgnoreCaseExtensionsFileFilter( "zdct" ) ),
    
    //NO Extension
    no_extension( Paths.get( "[no_ext]" ), new NoExtentionFileFilter() );

    private Path folder;
    private XFileFilter fileFilter;

    private FileType( final Path folder, final XFileFilter fileFilter )
    {
        this.folder     = (folder == null) ? Paths.get( name() ) : folder;
        this.fileFilter = fileFilter;
        this.fileFilter.setFileType( this );
    }

    private FileType( final XFileFilter fileFilter )
    {
        this( (Path)null, fileFilter );
    }

    public XFileFilter getFileFilter()
    {
        return fileFilter;
    }

    public Path getFolder()
    {
        return folder;
    }

    public static String toString( final FileType fileType )
    {
        StringBuilder sb = new StringBuilder();

        sb.append( "FileType[" );

        if( fileType != null ) {
            sb.append( fileType.name() );
            sb.append( "(P(" );
            sb.append( fileType.getFolder() );
            sb.append( "):" );
            sb.append( fileType.getFileFilter().toDisplay() );
            sb.append( ')' );
            }
        sb.append( ']' );

        return sb.toString();
    }

    public static final void check()
    {
        final Map<String,FileType> map = new HashMap<>();

        for( FileType fileType : FileType.values() ) {
            XFileFilter ff = fileType.getFileFilter();

            if( ff instanceof AbstractExtensionsFileFilter ) {
                List<String> extensions = AbstractExtensionsFileFilter.class.cast( ff ).getEndsWithsList();

                for( String extension : extensions ) {
                    if( map.containsKey( extension ) ) {
                        System.err.println( "Extention '" + extension + "' define in " + fileType
                                + " already define in " + map.get( extension ) );
                        }
                    else {
                        map.put( extension, ff.getFileType() );
                        }
                    }
                }
            }

        System.err.println( "----------------------------" );
    }
}
