package com.jensoft.core.plugin.plot.catalog;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManagerOLD.MetricsModelRangeCollections;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.plugin.plot.PlotAnchorsPlugin;
import com.jensoft.core.plugin.plot.PlotPlugin;
import com.jensoft.core.plugin.plot.spline.BSpline;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftView
public class BSplinePlotView extends View2D {

	public BSplinePlotView() {
		super(80);
		
		
		Window2D w2d = new Window2D.Linear(-100, 100, -100, 100);
		w2d.setThemeColor(NanoChromatique.GREEN);
		registerWindow2D(w2d);
		
		w2d.registerPlugin(new OutlinePlugin());
		
		AxisMetricsPlugin.ModeledMetricsOLD metrics = new AxisMetricsPlugin.ModeledMetricsOLD(Axis.AxisSouth);
		metrics.registerMetricsModels(MetricsModelRangeCollections.FemptoPeta);
		w2d.registerPlugin(metrics);
		
		AxisMetricsPlugin.ModeledMetricsOLD metrics2 = new AxisMetricsPlugin.ModeledMetricsOLD(Axis.AxisWest);
		metrics2.registerMetricsModels(MetricsModelRangeCollections.FemptoPeta);
		w2d.registerPlugin(metrics2);
		
		
		PlotPlugin plotsPlugin = new PlotPlugin();
		w2d.registerPlugin(plotsPlugin);
		
		
		
	
		//BSPLINE PLOT
		BSpline plot = new BSpline();
		plot.addPoint(-80, 30);
		plot.addPoint(-60, -20);
		plot.addPoint(-40, 70);
		plot.addPoint(-20, 20);
		
		plot.addPoint(20, -20);
		plot.addPoint(40, 50);
		plot.addPoint(60, -20);
		plot.addPoint(80, 90);
		
		plot.setPlotDrawColor(NanoChromatique.RED);
		plotsPlugin.addPlot(plot);
		
	
		
		
		//Plot point Handler
		PlotAnchorsPlugin anchorsPlugin = new PlotAnchorsPlugin();
		w2d.registerPlugin(anchorsPlugin);
		
		anchorsPlugin.addPlot(plot);
		
		//Zoom wheel
		ZoomWheelPlugin wheel = new ZoomWheelPlugin();
		w2d.registerPlugin(wheel);
		
	}


	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new BSplinePlotView());
	}
	

}
