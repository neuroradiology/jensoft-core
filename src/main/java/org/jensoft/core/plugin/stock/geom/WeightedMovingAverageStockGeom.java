package org.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.plugin.function.source.UserSourceFunction;
import org.jensoft.core.plugin.stock.Stock;

/**
 * <code>WeightedMovingAverageStockGeom</code> defines a weighted moving average curve geometry
 * for a set of stock items.
 * <p>
 * move count property define the moving average session count.
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class WeightedMovingAverageStockGeom extends CurveStockGeom {

	/** move count */
	private int moveCount = 20;

	/**
	 * create stock fixing geometry
	 */
	public WeightedMovingAverageStockGeom() {
	}

	/**
	 * create moving geometry with given average count
	 * @param moveCount
	 */
	public WeightedMovingAverageStockGeom(int moveCount) {
		super();
		this.moveCount = moveCount;
	}

	/**
	 * get the move count for this average
	 * 
	 * @return move count
	 */
	public int getMoveCount() {
		return moveCount;
	}

	public class StockComparator implements Comparator<Stock> {

		public int compare(Stock s1, Stock s2) {
			return s1.getFixing().compareTo(s2.getFixing());
		}
	}

	/**
	 * set move count for averge
	 * 
	 * @param moveCount
	 */
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jensoft.core.plugin.stock.geom.StockGeometry#solveGeometry()
	 */
	@Override
	public void solveGeometry() {
		List<Point2D> points = new ArrayList<Point2D>();
		List<Stock> stocks = getLayer().getHost().getStocks();
		Collections.sort(stocks, new StockComparator());
		for (int i = moveCount; i < stocks.size(); i++) {
			Stock root = stocks.get(i);
			double sum = 0;
			double divider = 0;
			for (int j = 0; j < moveCount; j++) {
				Stock s = stocks.get(i - j);
				sum = sum + (moveCount-j)*s.getClose();
				divider = divider + (moveCount-j);
			}
			double movingAverage = sum / divider;
			points.add(new Point2D.Double(new Long(root.getFixing().getTime()).doubleValue(), movingAverage));
		}
		SourceFunction sourceFunction = new UserSourceFunction.LineSource(points);
		getPathFunction().setSource(sourceFunction);
	}

}
