/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.area.painter;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.function.area.Area;

/**
 * <code>AbstractAreaPainter</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractAreaPainter implements AreaPainter {

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.function.area.painter.AreaPainter#paintArea(java.awt.Graphics2D, org.jensoft.core.plugin.function.area.AreaFunction)
     */
    @Override
    public void paintArea(Graphics2D g2d, Area areaCurve) {
    }

}
