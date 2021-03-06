/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.watch;

import java.awt.Color;
import java.awt.Font;

import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.Side;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import org.jensoft.core.glyphmetrics.painter.marker.RectangleMarker;
import org.jensoft.core.glyphmetrics.painter.marker.RoundMarker;
import org.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import org.jensoft.core.palette.InputFonts;
import org.jensoft.core.palette.TexturePalette;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.plugin.gauge.core.GaugeBackground;
import org.jensoft.core.plugin.gauge.core.GaugeBody;
import org.jensoft.core.plugin.gauge.core.GaugeEnvelope;
import org.jensoft.core.plugin.gauge.core.GaugeGlass;
import org.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.plugin.gauge.core.GaugeGlass.GlassCubicEffect;
import org.jensoft.core.plugin.gauge.core.binder.AnchorBinder;
import org.jensoft.core.plugin.gauge.core.binder.PathBinder;
import org.jensoft.core.plugin.gauge.core.binder.anchor.AnchorBaseBinder;
import org.jensoft.core.plugin.gauge.core.binder.anchor.AnchorValueBinder;
import org.jensoft.core.plugin.gauge.core.binder.path.PathArcManualBinder;
import org.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicWatchHour;
import org.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicWatchMinute;
import org.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicWatchSecond;
import org.jensoft.core.plugin.pie.painter.effect.CubicEffectFrame;

/**
 * <code>Watch</code> base model helps developer to learn gauge modeling.
 * 
 * @since1.0
 * @author sebastien janaud
 * 
 */
public class Watch extends RadialGauge {

	private GaugeBody body;
	private static int gaugeRadius = 90;
	private static int centerUserX = 0;
	private static int centerUserY = 0;

	private GaugeMetricsPath hourMetricsManager;
	private GaugeMetricsPath minuteMetricsManager;
	private GaugeMetricsPath secondMetricsManager;


	private GaugeMetricsPath miniMetricsManager;

	public Watch() {
		super(centerUserX, centerUserY, gaugeRadius);

		GaugeEnvelope cisero = new GaugeEnvelope.Cisero();
		setEnvelop(cisero);


		GaugeBackground bg = new GaugeBackground.Circular.Texture(TexturePalette.getSquareCarbonFiber());
		addBackground(bg);


		GaugeGlass g3 = new GaugeGlass.GlassIncubator();
		GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
		GaugeGlass g6 = new GaugeGlass.JenSoftAPILabel();

		GlassCubicEffect gCubic = new GaugeGlass.GlassCubicEffect(CubicEffectFrame.Wave6);
		gCubic.setIncidenceAngleDegree(60);
		addGlass(gCubic);

		createWatch();
		
		hourMetricsManager.setCurrentValue(2);
		minuteMetricsManager.setCurrentValue(30);
		secondMetricsManager.setCurrentValue(38);

	}

	private void createWatch() {
		
		body = new GaugeBody();
		addBody(body);
		
		PathBinder pathBinder = new PathArcManualBinder(gaugeRadius - 10, 90, -360);
		AnchorBinder needleBase = new AnchorBaseBinder();
		AnchorBinder needleHourAnchor = new AnchorValueBinder(50, Side.SideRight);
		AnchorBinder needleMinuteAnchor = new AnchorValueBinder(20, Side.SideRight);
		AnchorBinder needleSecondAnchor = new AnchorValueBinder(20, Side.SideRight);

		hourMetricsManager = new GaugeMetricsPath();
		hourMetricsManager.setRange(0, 12);
		hourMetricsManager.setPathBinder(pathBinder);
		hourMetricsManager.setNeedleBaseAnchorBinder(needleBase);
		hourMetricsManager.setNeedleValueAnchorBinder(needleHourAnchor);
		hourMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchHour());

		minuteMetricsManager = new GaugeMetricsPath();
		minuteMetricsManager.setRange(0, 60);
		minuteMetricsManager.setPathBinder(pathBinder);
		minuteMetricsManager.setNeedleBaseAnchorBinder(needleBase);
		minuteMetricsManager.setNeedleValueAnchorBinder(needleMinuteAnchor);
		minuteMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchMinute());

		secondMetricsManager = new GaugeMetricsPath();
		secondMetricsManager.setRange(0, 60);
		secondMetricsManager.setPathBinder(pathBinder);
		secondMetricsManager.setNeedleBaseAnchorBinder(needleBase);
		secondMetricsManager.setNeedleValueAnchorBinder(needleSecondAnchor);
		secondMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchSecond(NanoChromatique.ORANGE));

		
		body.registerGaugeMetricsPath(hourMetricsManager);
		body.registerGaugeMetricsPath(minuteMetricsManager);
		body.registerGaugeMetricsPath(secondMetricsManager);
		

		createMainTicks();
		createMainMetrics();
		createMiniGauge();
	}

	private void createMiniGauge() {
		
		GaugeBackground bg = new GaugeBackground.Circular.RadialGradient(gaugeRadius / 6, (int) (gaugeRadius / 2.3), 145);
		addBackground(bg);
		
		miniMetricsManager = new GaugeMetricsPath();
		miniMetricsManager.setRange(0, 24);
		// miniMetricsManager.setPathPainter(new
		// MetricsPathDefaultDraw(NanoChromatique.ORANGE.brighter(), new
		// BasicStroke(1.8f)));
		miniMetricsManager.setPathBinder(new PathArcManualBinder(gaugeRadius / 6, 0, 360, (int) (gaugeRadius / 2.3), 145));

		AnchorBinder needleMiniBaseAnchor = new AnchorBaseBinder((int) (gaugeRadius / 2.3), 145);
		miniMetricsManager.setNeedleBaseAnchorBinder(needleMiniBaseAnchor);

		AnchorBinder needleMiniValueAnchor = new AnchorValueBinder(5, Side.SideLeft);
		miniMetricsManager.setNeedleValueAnchorBinder(needleMiniValueAnchor);
		miniMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchSecond(Color.WHITE));

		Font f = new Font("Dialog", Font.PLAIN, 8);
		
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED.brighter()));
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("12");
		metric.setDivergence(-5);
		metric.setFont(f);
		miniMetricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(6);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("24");
		metric.setDivergence(-5);
		metric.setFont(f);
		miniMetricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(12);
		metric.setGlyphMetricFill(new GlyphFill(Color.GRAY, NanoChromatique.BLUE.brighter()));
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("18");
		metric.setDivergence(-5);
		metric.setFont(f);
		miniMetricsManager.addMetric(metric);

		body.registerGaugeMetricsPath(miniMetricsManager);

	}

	private void createMainTicks() {
		RoundMarker rm = new RoundMarker(NanoChromatique.ORANGE, NanoChromatique.WHITE, 4);
		GlyphFill gf = new GlyphFill(Color.WHITE, NanoChromatique.BLUE);
		
		RectangleMarker rectangle = new RectangleMarker(NanoChromatique.ORANGE.brighter(),Color.WHITE, 3, 8);
		//TicTacMarker ttm = new TicTacMarker(NanoChromatique.YELLOW);
		//ttm.setSize(4);
		//ttm.setDivergence(4);
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 16);
		for (int i = 0; i < 12; i++) {
			GlyphMetric metric = new GlyphMetric();
			metric.setValue(i);
			metric.setStylePosition(StylePosition.Default);
			metric.setMetricsLabel(i + "");
			metric.setFont(f);
			metric.setDivergence(12);
			if (i == 0 || i == 3 || i == 6 || i == 9) {
				// metric.setGlyphMetricMarkerPainter(new
				// RoundMarker(NanoChromatique.ORANGE, NanoChromatique.WHITE,
				// 4));
				// metric.setGlyphMetricMarkerPainter(ttm);
			} else {
				metric.setGlyphMetricFill(gf);
				metric.setGlyphMetricMarkerPainter(rectangle);
				hourMetricsManager.addMetric(metric);
			}

		}

		for (int i = 0; i < 60; i = i+5) {
			for(int j=i+1;j < i+5;j++){
				GlyphMetric metric = new GlyphMetric();
				metric.setValue(j);
				// metric.setDivergence(16);
				metric.setGlyphMetricMarkerPainter(new DefaultMarker(NanoChromatique.ORANGE.brighter(), 2));
				minuteMetricsManager.addMetric(metric);
			}
		}
	}

	private void createMainMetrics() {
		GlyphFill gf = new GlyphFill(Color.WHITE, NanoChromatique.BLUE.darker());
		TicTacMarker ttm = new TicTacMarker(NanoChromatique.RED);
		ttm.setSize(3);
		ttm.setDivergence(3);
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 36);

		// 6 o'clock
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(0);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("12");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		metric.setFont(f);
		hourMetricsManager.addMetric(metric);

		// 3 o'clock
		metric = new GlyphMetric();
		metric.setValue(3);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("3");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		metric.setFont(f);
		hourMetricsManager.addMetric(metric);

		// 6
		metric = new GlyphMetric();
		metric.setValue(6);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("6");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		// metric.setGlyphMetricMarkerPainter(ttm);
		metric.setFont(f);
		hourMetricsManager.addMetric(metric);

		// 9
		metric = new GlyphMetric();
		metric.setValue(9);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("9");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		// metric.setGlyphMetricMarkerPainter(ttm);
		metric.setFont(f);

		hourMetricsManager.addMetric(metric);
	}

}
