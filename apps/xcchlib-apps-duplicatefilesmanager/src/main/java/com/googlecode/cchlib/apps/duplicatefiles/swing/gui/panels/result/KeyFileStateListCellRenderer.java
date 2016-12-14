package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import javax.swing.JList;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.list.DefaultListCellRenderer;

// NOT public
@SuppressWarnings("squid:MaximumInheritanceDepth")
class KeyFileStateListCellRenderer
        extends DefaultListCellRenderer<KeyFileState>
            implements SerializableListCellRenderer<KeyFileState>
{
    private static final LinkOption DEFAULT_LINK_OPTIONS = LinkOption.NOFOLLOW_LINKS;

    private static final Logger     LOGGER               = Logger.getLogger( KeyFileStateListCellRenderer.class );

    private static final long       serialVersionUID     = 1L;

    @I18nString private final String executableStr;
    @I18nString private final String hiddenStr;
    @I18nString private final String notExecutableStr;
    @I18nString private final String notHiddenStr;
    @I18nString private final String notReadableStr;
    @I18nString private final String notWritableStr;
    @I18nString private final String readableStr;
    @I18nString private final String writableStr;

    public KeyFileStateListCellRenderer()
    {
        this.executableStr    = "Executable";
        this.notExecutableStr = "Not Executable";
        this.hiddenStr        = "Hidden";
        this.notHiddenStr     = "Visible";
        this.readableStr      = "Readable";
        this.notReadableStr   = "Not Readable";
        this.writableStr      = "Writable";
        this.notWritableStr   = "Not Writable";
    }

    @Override
    public Component getListCellRendererComponent(
            final JList<? extends KeyFileState> list,
            final KeyFileState                  value,
            final int                           index,
            final boolean                       isSelected,
            final boolean                       cellHasFocus
            )
    {
        final Path   path    = value.toPath();
        final String permStr = getPermissionString( path );
        final String sizeStr = getSizeString( path );

        super.setToolTipText( "<html>" + sizeStr + "<br/>" + permStr + "</html>" );

        return super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
    }

    private String getSizeString( final Path path )
    {
        String sizeStr;

        try {
            sizeStr = Long.toString( Files.size( path ) ) + " o";
        }
        catch( final NoSuchFileException e ) {
            //
            sizeStr = e.getMessage();

            final String message = "File not found " + path;

            if( LOGGER.isTraceEnabled() ) {
                // Don't care the exception
                LOGGER.trace( message, e );
            } else {
                LOGGER.debug( message );
            }
        }
        catch( final IOException e ) {
            sizeStr = e.getMessage();

            LOGGER.debug( "Can not compute File size of " + path, e );
        }

        return sizeStr;
    }

    private String getPermissionString( final Path path )
    {
        String permStr;

        try {
            permStr = getPosixFilePermissions( path );
        }
        catch( final IOException e ) {
            permStr = e.getMessage();

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Can not compute File Permissions of " + path, e );
            }
        }

        return permStr;
    }

    private String getPosixFilePermissions( final Path path ) throws IOException
    {
        try {
            final Set<PosixFilePermission> perms = Files.getPosixFilePermissions( path, DEFAULT_LINK_OPTIONS );

            return Enums.toString( perms );
        }
        catch( final UnsupportedOperationException e ) {
            final String message = "Can not compute Posix File Permissions of " + path;

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, e );
            } else {
                LOGGER.warn( message );
            }

            return getPermsString( path );
        }
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
