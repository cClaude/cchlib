package com.googlecode.cchlib.swing.batchrunner.misc;

import java.awt.Image;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.resources.ResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResourcesBuilder;

/**
 * NEEDDOC
 */
public class DefaultBRXLocaleResources implements BRXLocaleResources
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultBRXLocaleResources.class );

    private final String progressMonitorMessage;
    private final String frameTitle;
    private final String frameIconImageKey;
    private transient Image frameIconImage;
    private final Class<?> type;

    /**
     * NEEDDOC
     * @param builder NEEDDOC
     * @throws MissingResourceValueException if a resource is missing
     */
    public DefaultBRXLocaleResources( final DefaultBRLocaleResourcesBuilder builder )
        throws MissingResourceValueException
    {
        this.type                   = builder.getResourceRefType();
        this.progressMonitorMessage = builder.getString( "ProgressMonitorMessage" );
        this.frameTitle             = builder.getString( "FrameTitle" );
        this.frameIconImageKey      = builder.getString( "FrameIcon" );

        loadImage();
    }

    private void loadImage() throws MissingResourceValueException
    {
        try {
            this.frameIconImage = ResourcesLoader.getImage( this.type, this.frameIconImageKey );
        }
        catch( final ResourcesLoaderException e ) {
            throw new MissingResourceValueException( this.frameIconImageKey, e );
        }
    }

    @Override
    public String getProgressMonitorMessage()
    {
        return this.progressMonitorMessage;
    }

    @Override
    public String getFrameTitle()
    {
        return this.frameTitle;
    }

    @Override
    public Image getFrameIconImage()
    {
        if( this.frameIconImage == null ) {
            try {
                loadImage();
            }
            catch( final MissingResourceValueException e ) {
                LOGGER.error( "Can not relad image frameIconImage", e );
            }
        }

        return this.frameIconImage;
    }
}
