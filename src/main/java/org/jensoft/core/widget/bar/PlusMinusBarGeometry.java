/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.bar;

import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

/**
 * create plus minus geometry
 * 
 * @author Sebastien Janaud
 */
public class PlusMinusBarGeometry extends AbstractBarGeometry {

    /**
     * create plus minus geometry with specified bar widget orientation
     * 
     * @param barWidgetOrientation
     *            bar widget orientation
     */
    public PlusMinusBarGeometry(BarWidgetOrientation barWidgetOrientation) {
        super(barWidgetOrientation);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.bar.AbstractBarGeometry#solveButton1Geometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButton1Geometry(Rectangle2D rect1) {
        GeneralPath button1 = new GeneralPath();
        if (getBarWidgetOrientation() == BarWidgetOrientation.Horizontal) {
            button1.moveTo(rect1.getX(), rect1.getY() + rect1.getHeight() / 2);
            button1.lineTo(rect1.getX() + rect1.getWidth(), rect1.getY()
                    + rect1.getHeight() / 2);
            setButton1(button1);
        }
        else {
            button1.moveTo(rect1.getX() + rect1.getWidth() / 2, rect1.getY());
            button1.lineTo(rect1.getX() + rect1.getWidth() / 2, rect1.getY()
                    + rect1.getHeight());
            button1.moveTo(rect1.getX(), rect1.getY() + rect1.getHeight() / 2);
            button1.lineTo(rect1.getX() + rect1.getWidth(), rect1.getY()
                    + rect1.getHeight() / 2);
            setButton1(button1);
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.bar.AbstractBarGeometry#solveButton2Geometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButton2Geometry(Rectangle2D rect2) {
        GeneralPath button2 = new GeneralPath();
        if (getBarWidgetOrientation() == BarWidgetOrientation.Horizontal) {
            button2.moveTo(rect2.getX() + rect2.getWidth() / 2, rect2.getY());
            button2.lineTo(rect2.getX() + rect2.getWidth() / 2, rect2.getY()
                    + rect2.getHeight());
            button2.moveTo(rect2.getX(), rect2.getY() + rect2.getHeight() / 2);
            button2.lineTo(rect2.getX() + rect2.getWidth(), rect2.getY()
                    + rect2.getHeight() / 2);
            setButton2(button2);
        }
        else {
            button2.moveTo(rect2.getX(), rect2.getY() + rect2.getHeight() / 2);
            button2.lineTo(rect2.getX() + rect2.getWidth(), rect2.getY()
                    + rect2.getHeight() / 2);
            setButton2(button2);
        }
    }

}
