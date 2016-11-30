/*
 * Copyright (c) 2005-2007 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package test.slider.org.jvnet.flamingo.slider.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import test.slider.org.jvnet.flamingo.slider.FlexiRangeModel;
import test.slider.org.jvnet.flamingo.slider.JFlexiSlider;

/**
 * Basic UI for flexi slider {@link JFlexiSlider}.
 *
 * @author Kirill Grouchnikov
 */
public class BasicFlexiSliderUI extends FlexiSliderUI
{
    /**
     * The associated flexi slider.
     */
    protected JFlexiSlider flexiSlider;

    protected JLabel[] controlPointLabels;

    protected JSlider slider;

    protected CellRendererPane sliderRendererPane;

    protected MouseListener mouseListener;

    protected MouseMotionListener mouseMotionListener;

    protected ChangeListener flexiSliderChangeListener;

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(final JComponent c) {
        return new BasicFlexiSliderUI();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.plaf.ComponentUI#installUI(javax.swing.JComponent)
     */
    @Override
    public void installUI(final JComponent c)
    {
        this.flexiSlider = (JFlexiSlider) c;
        installDefaults();
        installComponents();
        installListeners();

        c.setLayout(createLayoutManager());
        c.setBorder(new EmptyBorder(1, 1, 1, 1));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.plaf.ComponentUI#uninstallUI(javax.swing.JComponent)
     */
    @Override
    public void uninstallUI(final JComponent c) {
        c.setLayout(null);
        uninstallListeners();
        uninstallComponents();
        uninstallDefaults();

        this.flexiSlider = null;
    }

    public void installDefaults()
    {
        // For testing
    }

    public void installComponents()
    {
        final int controlPointCount = this.flexiSlider.getControlPointCount();
        this.controlPointLabels = new JLabel[controlPointCount];
        for (int i = 0; i < controlPointCount; i++) {
            this.controlPointLabels[i] = new JLabel(this.flexiSlider
                    .getControlPointText(i));
            this.controlPointLabels[i].setIcon(this.flexiSlider
                    .getControlPointIcon(i));
            //this.controlPointLabels[i].setBorder(new LineBorder(Color.blue));
            this.flexiSlider.add(this.controlPointLabels[i]);
        }
        this.slider = new JSlider(SwingConstants.VERTICAL);
        this.slider.setFocusable(false);
        // this.flexiSlider.add(this.slider);

        this.sliderRendererPane = new CellRendererPane();
        this.flexiSlider.add(this.sliderRendererPane);

    }

    public void installListeners()
    {
        this.mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                // not use
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                BasicFlexiSliderUI.this.flexiSlider.getModel().setValueIsAdjusting(false);
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                BasicFlexiSliderUI.this.flexiSlider.getModel().setValueIsAdjusting(true);
                final int y = e.getY();
                final FlexiRangeModel.Value modelValue = sliderValueToModelValue(y);
                BasicFlexiSliderUI.this.flexiSlider.setValue(modelValue);
                // the following lines does the "magic" of snapping
                // the slider thumb to control points of discrete ranges.
                BasicFlexiSliderUI.this.slider.setValue(modelValueToSliderValue(modelValue));
            }
        };
        this.flexiSlider.addMouseListener(this.mouseListener);

        this.mouseMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                BasicFlexiSliderUI.this.flexiSlider.getModel().setValueIsAdjusting(true);
                final int y = e.getY();
                final FlexiRangeModel.Value modelValue = sliderValueToModelValue(y);
                if (modelValue == null) {
                    return;
                }
                BasicFlexiSliderUI.this.flexiSlider.setValue(modelValue);
                // the following lines does the "magic" of snapping
                // the slider thumb to control points of discrete ranges.
                BasicFlexiSliderUI.this.slider.setValue(modelValueToSliderValue(modelValue));
            }
        };
        this.flexiSlider.addMouseMotionListener(this.mouseMotionListener);

        this.flexiSliderChangeListener = e -> BasicFlexiSliderUI.this.flexiSlider.repaint();
        this.flexiSlider.getModel().addChangeListener(
                this.flexiSliderChangeListener);
    }

    public void uninstallDefaults() {
        for (final JLabel label : this.controlPointLabels) {
            this.flexiSlider.remove(label);
        }
        this.controlPointLabels = null;
        this.flexiSlider.remove(this.sliderRendererPane);
        this.sliderRendererPane = null;
    }

    public void uninstallComponents()
    {
        // for testing
    }

    public void uninstallListeners() {
        this.flexiSlider.removeMouseListener(this.mouseListener);
        this.mouseListener = null;

        this.flexiSlider.removeMouseMotionListener(this.mouseMotionListener);
        this.mouseMotionListener = null;

        this.flexiSlider.getModel().removeChangeListener(
                this.flexiSliderChangeListener);
        this.flexiSliderChangeListener = null;
    }

    @Override
    public void paint(final Graphics g, final JComponent c) {
        super.paint(g, c);
        this.paintSlider(g);
    }

    protected void paintSlider(final Graphics g) {
        final Rectangle sliderBounds = this.sliderRendererPane.getBounds();
        this.sliderRendererPane.paintComponent(g, this.slider,
                this.flexiSlider, sliderBounds.x, sliderBounds.y,
                sliderBounds.width, sliderBounds.height, true);
    }

    protected int modelValueToSliderValue(final FlexiRangeModel.Value modelValue)
    {
        if (modelValue == null) {
            return 0;
        }

        // get the range and the model
        final FlexiRangeModel.Range range = modelValue.range;
        final FlexiRangeModel model = this.flexiSlider.getModel();
        // find the range in the model
        for (int i = 0; i < model.getRangeCount(); i++) {
            if (range.equals(model.getRange(i))) {
                // get locations of control point labels
                int rangeStartLoc = this.controlPointLabels[i].getY();
                if (i != 0) {
                    rangeStartLoc += this.controlPointLabels[i].getHeight() / 2;
                } else {
                    rangeStartLoc += this.controlPointLabels[i].getHeight();
                }
                int rangeEndLoc = this.controlPointLabels[i + 1].getY();
                if (i != (model.getRangeCount() - 1)) {
                    rangeEndLoc += this.controlPointLabels[i + 1].getHeight() / 2;
                }
                final Insets ins = this.flexiSlider.getInsets();
                rangeStartLoc -= ins.top;
                rangeEndLoc -= ins.top;

                // apply the range fraction
                final int result = rangeStartLoc
                        + (int) (modelValue.rangeFraction * (rangeEndLoc - rangeStartLoc));
                return result;
            }
        }
        return 0;
    }

    protected FlexiRangeModel.Value sliderValueToModelValue(final int sliderValue) {
        // get the model
        final FlexiRangeModel model = this.flexiSlider.getModel();
        // iterate over the ranges and try to find range that contains the
        // specified
        // value
        for (int i = 0; i < model.getRangeCount(); i++) {
            final FlexiRangeModel.Range currRange = model.getRange(i);
            // get slider values that correspond to the control points
            // of this range
            final int startSliderValue = this
                    .modelValueToSliderValue(new FlexiRangeModel.Value(
                            currRange, 0.0));
            final int endSliderValue = this
                    .modelValueToSliderValue(new FlexiRangeModel.Value(
                            currRange, 1.0));
            if ((sliderValue >= endSliderValue)
                    && (sliderValue <= startSliderValue)) {
                // we have a match. Now check whether the range is discrete
                if (currRange.isDiscrete()) {
                    // find the closest control point
                    final int deltaStart = startSliderValue - sliderValue;
                    final int deltaEnd = sliderValue - endSliderValue;
                    if (deltaStart < deltaEnd) {
                        // closer to start
                        return new FlexiRangeModel.Value(currRange, 0.0);
                    } else {
                        // closer to end
                        return new FlexiRangeModel.Value(currRange, 1.0);
                    }
                } else {
                    // compute the range fraction
                    return new FlexiRangeModel.Value(
                            currRange,
                            (double) (startSliderValue - sliderValue)
                                    / (double) (startSliderValue - endSliderValue));
                }
            }
        }
        return null;
    }

    /**
     * Invoked by <code>installUI</code> to create a layout manager object to
     * manage the {@link JFlexiSlider}.
     *
     * @return a layout manager object
     */
    protected LayoutManager createLayoutManager()
    {
        return new FlexiSliderLayout();
    }

    /**
     * Layout for the flexi slider.
     *
     * @author Kirill Grouchnikov
     */
    protected class FlexiSliderLayout implements LayoutManager
    {
        /*
         * (non-Javadoc)
         *
         * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
         *      java.awt.Component)
         */
        @Override
        public void addLayoutComponent(final String name, final Component c)
        {
            // Empty
        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
         */
        @Override
        public void removeLayoutComponent(final Component c)
        {
            // Empty
        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
         */
        @Override
        public Dimension preferredLayoutSize(final Container c)
        {
            int width = 0;
            int height = 0;
            final Insets ins = c.getInsets();

            final JFlexiSlider flexiSlider = (JFlexiSlider) c;
            // get the control point count
            final int controlPointCount = flexiSlider.getControlPointCount();
            // the preferred height is the combined height of all labels +
            // vertical gaps in between
            for (int i = 0; i < controlPointCount; i++) {
                height += BasicFlexiSliderUI.this.controlPointLabels[i].getPreferredSize().height;
            }
            height += 4 * (controlPointCount - 1);

            // the preferred width is the width of the slider + the width
            // of the widest label
            int maxLabelWidth = 0;
            for (int i = 0; i < controlPointCount; i++) {
                maxLabelWidth = Math.max(maxLabelWidth, BasicFlexiSliderUI.this.controlPointLabels[i]
                        .getPreferredSize().width);
            }

            width = BasicFlexiSliderUI.this.slider.getPreferredSize().width + 4 + maxLabelWidth;

            return new Dimension(width + ins.left + ins.right, height + ins.top
                    + ins.bottom);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
         */
        @Override
        public Dimension minimumLayoutSize(final Container c)
        {
            return this.preferredLayoutSize(c);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
         */
        @Override
        public void layoutContainer(final Container c)
        {
            final Insets ins = c.getInsets();
            final int width = c.getWidth();
            final int height = c.getHeight();

            final JFlexiSlider flexiSlider = (JFlexiSlider) c;
            // get the model
            final FlexiRangeModel model = flexiSlider.getModel();
            // get the number of ranges
            final int rangeCount = model.getRangeCount();

            // get the preferred height
            final int prefHeight = this.preferredLayoutSize(c).height;

            // compute the extra height for distribution among the
            // contiguous ranges
            int extraHeight = height /*- ins.top - ins.bottom */- prefHeight;

            // start from the first range (bottom to top). If extra height
            // is negative, all ranges get smaller heights (even if it means
            // overlapping icons). If extra height is positive and there are no
            // contiguous ranges, all ranges get bigger heights. If extra
            // height is positive and there is at least one contiguous range,
            // the extra height is distributed among these ranges according to
            // their weights
            double totalContiguousWeight = 0.0;
            for (int i = 0; i < rangeCount; i++) {
                final FlexiRangeModel.Range range = model.getRange(i);
                if (!range.isDiscrete()) {
                    totalContiguousWeight += range.getWeight();
                }
            }

            final int bumpY = ((totalContiguousWeight == 0.0) || (extraHeight < 0.0)) ? /*(int)*/ (extraHeight / rangeCount)
                    : 0;
            // the first control point is at the bottom
            final int labelX = BasicFlexiSliderUI.this.slider.getPreferredSize().width + ins.left + 4;
            int currentY = height - ins.bottom
                    - BasicFlexiSliderUI.this.controlPointLabels[0].getPreferredSize().height;
            BasicFlexiSliderUI.this.controlPointLabels[0].setBounds(labelX, currentY, width - labelX
                    - ins.left - ins.right, BasicFlexiSliderUI.this.controlPointLabels[0]
                    .getPreferredSize().height);
            for (int i = 0; i < rangeCount; i++) {
                final FlexiRangeModel.Range range = model.getRange(i);
                final JLabel endLabel = BasicFlexiSliderUI.this.controlPointLabels[i + 1];
                if (range.isDiscrete()) {
                    final int deltaY = endLabel.getPreferredSize().height + 4 + bumpY;
                    currentY -= deltaY;
                    BasicFlexiSliderUI.this.controlPointLabels[i + 1].setBounds(labelX, currentY, width
                            - labelX - ins.left - ins.right, endLabel
                            .getPreferredSize().height);
                } else {
                    final double rangeWeight = range.getWeight();
                    int currBump = 0;

                    if (bumpY < 0.0) {
                        currBump = bumpY;
                    } else {
                        // a little bit of bookkeeping here. If we just use
                        // the proportionate fractions, the rounding errors
                        // (rounding a double to int always goes down) will
                        // result in extra pixels above the last control point
                        // label, which will not be properly aligned with the
                        // top bound of the slider.
                        currBump = (int) ((extraHeight * rangeWeight) / totalContiguousWeight);
                        // reduce the total remaining weight and total remaining
                        // extra height. At the last contiguous range the entire
                        // remaining height will go to it.
                        totalContiguousWeight -= rangeWeight;
                        extraHeight -= currBump;
                    }
                    final int deltaY = endLabel.getPreferredSize().height + 4
                            + currBump;
                    currentY -= deltaY;
                    BasicFlexiSliderUI.this.controlPointLabels[i + 1].setBounds(labelX, currentY, width
                            - labelX - ins.left - ins.right, endLabel
                            .getPreferredSize().height);
                }
            }

            final int firstLabelCenter = BasicFlexiSliderUI.this.controlPointLabels[0].getY()
                    + BasicFlexiSliderUI.this.controlPointLabels[0].getHeight();// / 2;
            final int lastLabelCenter = BasicFlexiSliderUI.this.controlPointLabels[rangeCount].getY();
            // + controlPointLabels[rangeCount].getHeight() / 2;
            final int sliderHeight = firstLabelCenter - lastLabelCenter;

            // int
            // lastLabel
            // sliderHeight = height - ins.top - ins.bottom;

            BasicFlexiSliderUI.this.sliderRendererPane.setBounds(ins.left, lastLabelCenter, BasicFlexiSliderUI.this.slider
                    .getPreferredSize().width, sliderHeight);
            BasicFlexiSliderUI.this.slider.setMinimum(0);
            BasicFlexiSliderUI.this.slider.setMaximum(sliderHeight - 1);
            BasicFlexiSliderUI.this.slider.setInverted(true);
            BasicFlexiSliderUI.this.slider.setValue(modelValueToSliderValue(flexiSlider.getValue()));
        }
    }
}
