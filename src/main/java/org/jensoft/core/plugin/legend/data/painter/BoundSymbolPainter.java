/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.data.painter;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.plugin.legend.data.DataLegend.Item;

/**
 * <code>BoundSymbolPainter</code> to show bound clip region for item symbol
 * 
 * @since 1.1
 * @author sebastien janaud
 *
 */
public class BoundSymbolPainter extends AbstractDataItemSymbolPainter {

	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.legend.data.painter.AbstractDataItemSymbolPainter#paintItemSymbol(java.awt.Graphics2D, java.awt.geom.Rectangle2D, org.jensoft.core.plugin.legend.data.DataLegend.Item)
	 */
	@Override
	public void paintItemSymbol(Graphics2D g2d,Rectangle2D symbolBound, Item item) {
		g2d.setColor(item.getColor());
		g2d.draw(symbolBound);
	}
}