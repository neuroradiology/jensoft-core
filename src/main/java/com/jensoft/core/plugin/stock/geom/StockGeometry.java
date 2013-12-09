package com.jensoft.core.plugin.stock.geom;

import com.jensoft.core.plugin.stock.StockLayer;

public abstract class StockGeometry {

	private StockLayer<?> layer;

	public StockGeometry() {
	}

	public StockLayer<?> getLayer() {
		return layer;
	}

	public void setLayer(StockLayer<?> layer) {
		this.layer = layer;
	}

	public abstract void solveGeometry();

}