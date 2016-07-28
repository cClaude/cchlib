package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.awt.Component;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.list.DefaultListCellRenderer;

/**
 *
 */
//NOT public
class KeyFileStateListCellRenderer
    extends DefaultListCellRenderer<KeyFileState>
        implements ListCellRenderer<KeyFileState>, Serializable
{
    public static class Enums
    {
        public static <T extends Enum<T>> String toString( final Set<T> set )
        {
            final StringBuilder sb    = new StringBuilder();
            boolean       first = true;

            for( final T v : set ) {
                if( first ) {
                    first = false;
                    }
                else {
                    sb.append( ',' );
                    }

                sb.append( v.name() );
                }

            return sb.toString();
        }
    }

    private static final long serialVersionUID = 1L;
    private static final LinkOption DEFAULT_LINK_OPTIONS = LinkOption.NOFOLLOW_LINKS;

    @I18nString private final String executableStr = "Executable";
    @I18nString private final String notExecutableStr = "Not Executable";
    @I18nString private final String hiddenStr = "Hidden";
    @I18nString private final String notHiddenStr = "Visible";
    @I18nString private final String readableStr = "Readable";
    @I18nString private final String notReadableStr = "Not Readable";
    @I18nString private final String writableStr = "Writable";
    @I18nString private final String notWritableStr = "Not Writable";

    @Override
    public Component getListCellRendererComponent(
            final JList<? extends KeyFileState> list,
            final KeyFileState                  value,
            final int                           index,
            final boolean                       isSelected,
            final boolean                       cellHasFocus
            )
    {
        final Path    path  = value.toPath();
        String  permStr;
        String  sizeStr;

        try {
            try {
                final Set<PosixFilePermission> perms = Files.getPosixFilePermissions( path, DEFAULT_LINK_OPTIONS );

                permStr = Enums.toString( perms );
                }
            catch( final UnsupportedOperationException e ) { // $codepro.audit.disable logExceptions
                permStr = getPermsString( path );
                }
            }
        catch( final IOException e ) {
            permStr = e.getMessage();
            }

        try {
            sizeStr = Long.toString( Files.size( path ) ) + " o";
            }
        catch( final IOException e ) {
            sizeStr = e.getMessage();
            }

        super.setToolTipText( "<html>" + sizeStr + "<br/>" + permStr + "</html>" );

        return super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
    }

    private String getPermsString( final Path path ) throws IOException
    {
        final StringBuilder sb = new StringBuilder();

        if( Files.isExecutable( path ) ) {
            sb.append( this.executableStr );
        } else {
            sb.append( this.notExecutableStr );
        }

        sb.append( ',' );

        if( Files.isHidden( path ) ) {
            sb.append( this.hiddenStr );
        } else {
            sb.append( this.notHiddenStr );
        }

        sb.append( ',' );

        if( Files.isReadable( path ) ) {
            sb.append( this.readableStr );
        } else {
            sb.append( this.notReadableStr );
        }

        sb.append( ',' );

        if( Files.isWritable( path ) ) {
            sb.append( this.writableStr );
        } else {
            sb.append( this.notWritableStr );
        }

        return sb.toString();
    }

}
