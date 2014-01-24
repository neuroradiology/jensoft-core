/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker.marker;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import com.jensoft.core.view.View2D;

/**
 * <code>MarkerLabel</code>
 */
public class MarkerLabel extends AbstractMarker {

    private JLabel jlabel;
    private String label;

    /**
     * create default target marker
     */
    public MarkerLabel(String label) {
        this.label = label;
        jlabel = new JLabel();
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.marker.marker.AbstractMarker#paintMarker(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    public final void paintMarker(View2D view2d, Graphics2D g2d) {

        //System.out.println("paint marker");
        view2d.getDevice2D().remove(jlabel);
        jlabel.setText(label);
        jlabel.setForeground(Color.RED);
        jlabel.setBounds((int) getMarkerPoint().getX(), (int) getMarkerPoint()
                .getY(), (int) jlabel.getPreferredSize().getWidth(),
                         (int) jlabel.getPreferredSize().getHeight());
        view2d.getDevice2D().add(jlabel);

    }

}
