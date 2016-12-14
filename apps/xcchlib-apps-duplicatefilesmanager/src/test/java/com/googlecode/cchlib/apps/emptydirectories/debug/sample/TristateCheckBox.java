package com.googlecode.cchlib.apps.emptydirectories.debug.sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ActionMapUIResource;

public class TristateCheckBox extends JCheckBox {
    static final long serialVersionUID = 1;

    /** This is a type-safe enumerated type */
    public static class State {
        private State()
        {}
    }

    public final State              NOT_SELECTED = new State();
    public final State              SELECTED     = new State();
    public static final State       DONT_CARE    = new State();

    private final TristateDecorator model;

    public TristateCheckBox( final String text, final Icon icon, final State initial )
    {
        super( text, icon );
        // Add a listener for when the mouse is pressed
        super.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed( final MouseEvent e )
            {
                grabFocus();
                TristateCheckBox.this.model.nextState();
            }
        } );

        // Reset the keyboard action map
        final ActionMap map = new ActionMapUIResource();
        map.put( "pressed", new AbstractAction() {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( final ActionEvent e )
            {
                grabFocus();
                TristateCheckBox.this.model.nextState();
            }
        } );

        map.put( "released", null );

        SwingUtilities.replaceUIActionMap( this, map );

        // set the model to the adapted model
        this.model = new TristateDecorator( getModel() );
        setModel( this.model );
        setState( initial );
    }

    // Constractor types:
    public TristateCheckBox( final String text, final State initial )
    {
        this( text, null, initial );
    }

    public TristateCheckBox( final String text )
    {
        this( text, DONT_CARE );
    }

    public TristateCheckBox()
    {
        this( null );
    }

    /** No one may add mouse listeners, not even Swing! */
    @Override
    public void addMouseListener( final MouseListener l )
    {
        // Empty
    }

    /**
     * Set the new state to either SELECTED, NOT_SELECTED or DONT_CARE. If state == null, it is treated as DONT_CARE.
     */
    public void setState( final State state )
    {
        this.model.setState( state );
    }

    /**
     * Return the current state, which is determined by the selection status of the model.
     */
    public State getState()
    {
        return this.model.getState();
    }

    @Override
    public void setSelected( final boolean b )
    {
        if( b ) {
            setState( this.SELECTED );
        } else {
            setState( this.NOT_SELECTED );
        }
    }

    /**
     * Exactly which Design Pattern is this? Is it an Adapter, a Proxy or a Decorator? In this case, my vote lies with
     * the Decorator, because we are extending functionality and "decorating" the original model with a more powerful
     * model.
     */
    private class TristateDecorator implements ButtonModel {
        private final ButtonModel other;

        private TristateDecorator( final ButtonModel other )
        {
            this.other = other;
        }

        private void setState( final State state )
        {
            if( state == TristateCheckBox.this.NOT_SELECTED ) {
                this.other.setArmed( false );
                setPressed( false );
                setSelected( false );
            } else if( state == TristateCheckBox.this.SELECTED ) {
                this.other.setArmed( false );
                setPressed( false );
                setSelected( true );
            } else { // either "null" or DONT_CARE
                this.other.setArmed( true );
                setPressed( true );
                setSelected( false );
            }
        }

        /**
         * The current state is embedded in the selection / armed state of the model.
         *
         * We return the SELECTED state when the checkbox is selected but not armed, DONT_CARE state when the checkbox
         * is selected and armed (grey) and NOT_SELECTED when the checkbox is deselected.
         */
        private State getState()
        {
            if( isSelected() && !isArmed() ) {
                // normal black tick
                return TristateCheckBox.this.SELECTED;
            } else if( isSelected() && isArmed() ) {
                // don't care grey tick
                return DONT_CARE;
            } else {
                // normal deselected
                return TristateCheckBox.this.NOT_SELECTED;
            }
        }

        /** We rotate between NOT_SELECTED, SELECTED and DONT_CARE. */
        private void nextState()
        {
            final State current = getState();
            if( current == TristateCheckBox.this.NOT_SELECTED ) {
                setState( TristateCheckBox.this.SELECTED );
            } else if( current == TristateCheckBox.this.SELECTED ) {
                setState( DONT_CARE );
            } else if( current == DONT_CARE ) {
                setState( TristateCheckBox.this.NOT_SELECTED );
            }
        }

        /** Filter: No one may change the armed status except us. */
        @Override
        public void setArmed( final boolean b )
        {
            // Empty
        }

        /**
         * We disable focusing on the component when it is not enabled.
         */
        @Override
        public void setEnabled( final boolean b )
        {
            setFocusable( b );
            this.other.setEnabled( b );
        }

        /**
         * All these methods simply delegate to the "other" model that is being decorated.
         */
        @Override
        public boolean isArmed()
        {
            return this.other.isArmed();
        }

        @Override
        public boolean isSelected()
        {
            return this.other.isSelected();
        }

        @Override
        public boolean isEnabled()
        {
            return this.other.isEnabled();
        }

        @Override
        public boolean isPressed()
        {
            return this.other.isPressed();
        }

        @Override
        public boolean isRollover()
        {
            return this.other.isRollover();
        }

        @Override
        public int getMnemonic()
        {
            return this.other.getMnemonic();
        }

        @Override
        public String getActionCommand()
        {
            return this.other.getActionCommand();
        }

        @Override
        public Object[] getSelectedObjects()
        {
            return this.other.getSelectedObjects();
        }

        @Override
        public void setSelected( final boolean b )
        {
            this.other.setSelected( b );
        }

        @Override
        public void setPressed( final boolean b )
        {
            this.other.setPressed( b );
        }

        @Override
        public void setRollover( final boolean b )
        {
            this.other.setRollover( b );
        }

        @Override
        public void setMnemonic( final int key )
        {
            this.other.setMnemonic( key );
        }

        @Override
        public void setActionCommand( final String s )
        {
            this.other.setActionCommand( s );
        }

        @Override
        public void setGroup( final ButtonGroup group )
        {
            this.other.setGroup( group );
        }

        @Override
        public void addActionListener( final ActionListener l )
        {
            this.other.addActionListener( l );
        }

        @Override
        public void removeActionListener( final ActionListener l )
        {
            this.other.removeActionListener( l );
        }

        @Override
        public void addItemListener( final ItemListener l )
        {
            this.other.addItemListener( l );
        }

        @Override
        public void removeItemListener( final ItemListener l )
        {
            this.other.removeItemListener( l );
        }

        @Override
        public void addChangeListener( final ChangeListener l )
        {
            this.other.addChangeListener( l );
        }

        @Override
        public void removeChangeListener( final ChangeListener l )
        {
            this.other.removeChangeListener( l );
        }

    }
}
