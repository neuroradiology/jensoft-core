/*
 * JenSoft API - Charting Framework
 * http://www.jensoft.org
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package org.jensoft.core.x2d.template.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jensoft.core.catalog.nature.JenSoftView;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PiePlugin;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.PieToolkit;
import org.jensoft.core.plugin.pie.Pie.PieNature;
import org.jensoft.core.plugin.pie.painter.effect.CubicEffectFrame;
import org.jensoft.core.plugin.pie.painter.effect.PieCompoundEffect;
import org.jensoft.core.plugin.pie.painter.effect.PieCubicEffect;
import org.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import org.jensoft.core.plugin.pie.painter.effect.PieReflectionEffect;
import org.jensoft.core.plugin.pie.painter.fill.PieRadialFill;
import org.jensoft.core.plugin.pie.painter.label.PieRadialLabel;
import org.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel.Style;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;
import org.jensoft.core.x2d.X2D;
import org.jensoft.core.x2d.X2DException;
import org.w3c.dom.Document;

@JenSoftView
public class PieDeflate3 extends View {

	private static final long serialVersionUID = 156889765687899L;
	public static void main(String[] args) {
		PieDeflate3 v = new PieDeflate3();
		v.deflate();
	}

	public void deflate() {
		try {
			X2D x2d = new X2D();
			x2d.registerView(this);
			Document x2ddoc = x2d.getX2dDocument();

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			DOMSource source = new DOMSource(x2ddoc);
			StreamResult result = new StreamResult(new File("C:" + File.separator + "usr" + File.separator + "temp" + File.separator + getName() + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (X2DException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public PieDeflate3() {
		super(2);
		setViewKey("view key");
		setApiKey("5e4f49a6-37af-4bc4-bb37-1d62364114c3");
		setName("deflate3");
		Projection window = new Projection.Linear(-1, 1, -1, 1);
		registerProjection(window);

		PiePlugin piePlugin = new PiePlugin();
		window.registerPlugin(piePlugin);

		Pie pie = new Pie("pie", 80);
		pie.setPieNature(PieNature.User);
		pie.setCenterX(0);
		pie.setCenterY(0);
		pie.setPieFill(new PieRadialFill());
		pie.setStartAngleDegree(40);

		PieLinearEffect linearFX = new PieLinearEffect();
		linearFX.setIncidenceAngleDegree(120);
		linearFX.setOffsetRadius(5);

		PieCubicEffect cubicFX = new PieCubicEffect();
		cubicFX.setCubicKey(CubicEffectFrame.Round4.getKeyFrame());
		pie.setPieEffect(cubicFX);

		PieReflectionEffect reflectionFX = new PieReflectionEffect();
		reflectionFX.setBlurEnabled(false);
		reflectionFX.setOpacity(0.6f);
		reflectionFX.setLength(0.5f);
		reflectionFX.setReflectLabel(false);

		PieCompoundEffect compoundFX = new PieCompoundEffect(linearFX, cubicFX, reflectionFX);
		pie.setPieEffect(compoundFX);

		PieSlice s1 = PieToolkit.createSlice("s1", new Color(240, 240, 240, 240), 45, 0);
		PieSlice s2 = PieToolkit.createSlice("s2", RosePalette.COALBLACK, 5, 0);
		PieSlice s3 = PieToolkit.createSlice("s3", new Color(78, 148, 44), 30, 0);
		PieSlice s4 = PieToolkit.createSlice("s4", RosePalette.AEGEANBLUE, 5, 0);
		PieSlice s5 = PieToolkit.createSlice("s5", RosePalette.INDIGO, 5, 0);
		

		PieToolkit.pushSlices(pie, s1, s2, s3, s4,s5);
		piePlugin.addPie(pie);

		pie.setPassiveLabelAtMinPercent(18);

		// LABELS
		float[] fractions = { 0f, 0.5f, 1f };
		Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255), new Color(0, 0, 0, 255) };
		Stroke s = new BasicStroke(2);
		pie.setPassiveLabelAtMinPercent(0);

		Font f = new Font("Dialog", Font.PLAIN, 12);
		
		// LABEL 1
		PieRadialLabel label1 = PieToolkit.createRadialLabel("SILVER", ColorPalette.WHITE, f, 20);
		label1.setStyle(Style.Both);
		label1.setOutlineStroke(s);
		label1.setShader(fractions, colors);
		label1.setOutlineColor(RosePalette.REDWOOD);
		label1.setOutlineRound(20);
		s1.addSliceLabel(label1);

		// LABEL 2
		PieRadialLabel label2 = PieToolkit.createRadialLabel("GOLD", ColorPalette.WHITE, f, 20);
		label2.setStyle(Style.Both);
		label2.setOutlineStroke(s);
		label2.setShader(fractions, colors);
		label2.setOutlineColor(RosePalette.LIME);
		label2.setOutlineRound(20);
		s2.addSliceLabel(label2);

		// LABEL 3
		PieRadialLabel label3 = PieToolkit.createRadialLabel("COPPER", ColorPalette.WHITE, f, 20);
		label3.setStyle(Style.Both);
		label3.setOutlineStroke(s);
		label3.setShader(fractions, colors);
		label3.setOutlineColor(RosePalette.EMERALD);
		label3.setOutlineRound(20);
		s3.addSliceLabel(label3);

		PieRadialLabel label4 = PieToolkit.createRadialLabel("PLATINIUM", ColorPalette.WHITE, f, 20);
		label4.setStyle(Style.Both);
		label4.setOutlineStroke(s);
		label4.setOutlineColor(RosePalette.COBALT);
		label4.setShader(fractions, colors);
		label4.setOutlineRound(20);
		s4.addSliceLabel(label4);
		
		PieRadialLabel label5 = PieToolkit.createRadialLabel("RHODIUM", ColorPalette.WHITE, f, 20);
		label5.setStyle(Style.Both);
		label5.setOutlineStroke(s);
		label5.setOutlineColor(RosePalette.COBALT);
		label5.setShader(fractions, colors);
		label5.setOutlineRound(20);
		s5.addSliceLabel(label5);

	}
}
