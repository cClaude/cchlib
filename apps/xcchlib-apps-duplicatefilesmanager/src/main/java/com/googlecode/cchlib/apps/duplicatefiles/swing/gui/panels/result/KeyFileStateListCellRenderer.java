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

    @I18nString private String executableStr;
    @I18nString private String hiddenStr;
    @I18nString private String notExecutableStr;
    @I18nString private String notHiddenStr;
    @I18nString private String notReadableStr;
    @I18nString private String notWritableStr;
    @I18nString private String readableStr;
    @I18nString private String writableStr;

    public KeyFileStateListCellRenderer()
    {
        ensureNonFinalI18n();
    }

    private void ensureNonFinalI18n()
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
        catch( final NoSuchFileException cause ) {
            //
            sizeStr = getMessage( cause );

            final String message = "File not found " + path;

            if( LOGGER.isTraceEnabled() ) {
                // Don't care the exception
                LOGGER.trace( message, cause );
            } else {
                LOGGER.debug( message );
            }
        }
        catch( final IOException cause ) {
            sizeStr = getMessage( cause );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Can not compute file size of " + path, cause );
            }
        }

        return sizeStr;
    }

    private String getPermissionString( final Path path )
    {
        String permStr;

        try {
            permStr = getPosixFilePermissions( path );
        }
        catch( final NoSuchFileException cause ) {
            final String exceptionMessage = getMessage( cause );

            permStr = exceptionMessage;

            final String message = "File removed: " + path + " (" + exceptionMessage + ')';

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, cause );
            } else {
                LOGGER.warn( message );
            }
        }
        catch( final IOException cause ) {
            final String exceptionMessage = getMessage( cause );

            permStr = exceptionMessage;

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace(
                    "Can not compute File Permissions of " + path + " (" + exceptionMessage + ')',
                    cause
                    );
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
        catch( final NoSuchFileException cause ) {
            final String message = "File removed: " + path + " (" + getMessage( cause ) + ')';

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, cause );
            } else {
                LOGGER.warn( message );
            }

            throw cause;
        }
        catch( final UnsupportedOperationException cause ) {
            final String exceptionMessage = getMessage( cause );

            final String message = "Can not compute Posix File Permissions of " + path
                            + " (" + exceptionMessage + ')';

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, cause );
            } else {
                LOGGER.warn( message );
            }

            return getPermsString( path );
        }
    }

    private String getMessage( final Throwable exception )
    {
        String exceptionMessage = exception.getMessage();

        if( exceptionMessage == null ) {
            exceptionMessage = exception.getClass().getName();
        }

        return exceptionMessage;
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
