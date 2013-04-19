package com.jensoft.core.x2d.binding.outline;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

public class OutlineDeflater extends AbstractX2DPluginDeflater<OutlinePlugin>  implements X2DOutlineElement{		

	public OutlineDeflater() {
		super(new OutlinePlugin());
		setXSIType("OutlinePlugin");
	}

	public OutlineDeflater(OutlinePlugin plugin) {
		super(plugin);
		setXSIType("OutlinePlugin");
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate() {
		Element pluginElement = createPluginRootElement();		
		Element outlineColorElement = createColorElement(getX2dDocument(), ELEMENT_OUTLINE_COLOR, getPlugin().getThemeColor());
		pluginElement.appendChild(outlineColorElement);	
		return pluginElement;
	}

}
