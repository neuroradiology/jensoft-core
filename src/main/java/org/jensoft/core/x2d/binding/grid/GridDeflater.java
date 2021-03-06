/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.grid;

import org.jensoft.core.plugin.grid.GridPlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.w3c.dom.Element;

/**
 * <code>GridDeflater</code>
 * 
 * @author sebastien janaud
 * 
 */
public class GridDeflater extends AbstractX2DPluginDeflater<GridPlugin> {

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(GridPlugin plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}

}
