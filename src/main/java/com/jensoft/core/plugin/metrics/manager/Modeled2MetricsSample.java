/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.plugin.translate.TranslateCompassWidget;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.plugin.zoom.box.ZoomBoxDefaultDeviceContext;
import com.jensoft.core.plugin.zoom.box.ZoomBoxDonutWidget;
import com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.Portfolio;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.DarkViewBackground;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>ModeledMetricsDemo</code>
 * 
 * @author JenSoft API
 */
@JenSoftView(background=DarkViewBackground.class)
public class Modeled2MetricsSample extends View2D {

	
	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new Modeled2MetricsSample());
		//System.out.println(3/4d*29*12);
	}
	/**
	 * create the modeled metrics demo
	 */
	public Modeled2MetricsSample() {
		super();
		setPlaceHolder(100, WindowPart.South);
		setPlaceHolder(80, WindowPart.West);

		// create linear window
		Window2D.Linear window = new Window2D.Linear(-700, 1200, -140, 300);
		window.setThemeColor(RosePalette.LEMONPEEL);
		registerWindow2D(window);

		// register a zoom wheel plug-in
		ZoomWheelPlugin wheel = new ZoomWheelPlugin();
		window.registerPlugin(wheel);

		// register a translate with context menu plug-in and lock selected for
		// direct use
		TranslatePlugin translate = new TranslatePlugin();
		translate.registerContext(new TranslateDefaultDeviceContext());
		translate.registerWidget(new TranslateCompassWidget());
		window.registerPlugin(translate);

		// register a zoom box with context menu and widget history
		ZoomBoxPlugin zoomBox = new ZoomBoxPlugin();
		window.registerPlugin(zoomBox);
		zoomBox.registerWidget(new ZoomBoxDonutWidget());
		zoomBox.registerContext(new ZoomBoxDefaultDeviceContext());

		Font font = new Font("lucida console", Font.PLAIN, 10);

		// create modeled axis plug-in in south part
		AxisMetricsPlugin.Modeled2Metrics southMetrics = new AxisMetricsPlugin.Modeled2Metrics.S();
		//southMetrics.setPaintAxisBaseLine(true);
		southMetrics.setMetricsBaseLineColor(Color.ORANGE);
		southMetrics.setAxisSpacing(0);
		window.registerPlugin(southMetrics);
		southMetrics.setMetricsFont(font);
		southMetrics.setMetricsLabelColor(TangoPalette.SCARLETRED3.brighter());
		// southMetrics.setMetricsMarkerColor(TangoPalette.SCARLETRED3);

		// create modeled axis plug-in in west part
		AxisMetricsPlugin.Modeled2Metrics westMetrics = new AxisMetricsPlugin.Modeled2Metrics.W();
		//window.registerPlugin(westMetrics);
		westMetrics.setMetricsFont(font);
//		westMetrics.setMetricsLabelColor(TangoPalette.SKYBLUE3.brighter());
//		// westMetrics.setMetricsMarkerColor(TangoPalette.SKYBLUE3);

		// register device outline plug-in
	   window.registerPlugin(new OutlinePlugin());

	}

	@Portfolio(name = "Metrics-Modeled", width = 500, height = 250)
	public static View2D getPortofolio() {
		Modeled2MetricsSample demo = new Modeled2MetricsSample();

		RoundViewFill viewBackground = new RoundViewFill();
		Shader s = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		viewBackground.setShader(s);
		viewBackground.setOutlineStroke(new BasicStroke(2.5f));

		demo.setBackgroundPainter(viewBackground);
		return demo;
	}
}