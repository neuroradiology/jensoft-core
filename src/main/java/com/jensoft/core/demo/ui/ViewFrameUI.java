/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.jnlp.ClipboardService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;
import com.jensoft.core.demo.source.SourcePane;
import com.jensoft.core.demo.styles.SectionStyle;
import com.jensoft.core.demo.styles.SourceStyle;
import com.jensoft.core.demo.styles.WordStyle;
import com.jensoft.core.desktop.viewsbase.SScrollPane;
import com.jensoft.core.palette.FilPalette;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.view.View2D;

/**
 * <code>ViewDemoFrameUI</code>
 * 
 * @author sebastien janaud
 */
public class ViewFrameUI extends JFrame {

	private static final long serialVersionUID = 156889765687899L;

	/** view 2D */
	private View2D view;
	
	/** style context */
	private StyleContext styleContext;

	/** JNLP clip board service */
	private ClipboardService cs = null;

	/**
	 * Create and Show frame UI with the given view
	 * 
	 * @param view
	 */
	public ViewFrameUI(View2D view) {
		this.view = view;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				create();
			}
		});
		pack();
		setSize(1024, 700);
	}

	/**
	 * show the given demo in the demo frame
	 * 
	 * @param demo
	 *            the demo to show
	 */
	private void create() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		try {
			cs = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
		} catch (UnavailableServiceException e) {
		}
		
		createStyle();
		
		ImageIcon iconFrame = ImageResource.getInstance().createImageIcon("jensoft.png", "");
		setIconImage(iconFrame.getImage());
		setTitle("JenSoft API");
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.BLACK);

		masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		masterPane.setLayout(new BorderLayout());

		DemoTabSet tabSet = new DemoTabSet();
		tabSet.setTitle("JenSoft");

		DemoTab demoTab = new DemoTab("Demo");
		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);
		tabSet.addComandTab(demoTab, view);

		DemoTab uiTab = new DemoTab("UI");
		uiTab.setTabColor(FilPalette.GREEN3);
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		uiTab.setTabIcon(icon2);
		SourcePane uiSourcePane = new SourcePane(styleContext, cs);
		tabSet.addComandTab(uiTab, uiSourcePane);
		loadSource(uiSourcePane, this.getClass());
		applyStyles(uiSourcePane);

		DemoTab dashboardTab = new DemoTab(view.getClass().getSimpleName());
		dashboardTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		dashboardTab.setTabIcon(icon);
		SourcePane dashboardSourcePane = new SourcePane(styleContext, cs);
		tabSet.addComandTab(dashboardTab, dashboardSourcePane);
		loadSource(dashboardSourcePane, view.getClass());
		applyStyles(dashboardSourcePane);

		dashboardSourcePane.getSourceTextPane().setCaretPosition(0);
		uiSourcePane.getSourceTextPane().setCaretPosition(0);

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

	private void createStyle() {
		styleContext = new StyleContext();

		final Style javaSourceStyle = styleContext.addStyle("java-source", null);
		StyleConstants.setLeftIndent(javaSourceStyle, 60);
		StyleConstants.setRightIndent(javaSourceStyle, 16);
		StyleConstants.setFirstLineIndent(javaSourceStyle, 60);
		StyleConstants.setFontFamily(javaSourceStyle, "lucida console");
		StyleConstants.setFontSize(javaSourceStyle, 11);
		StyleConstants.setForeground(javaSourceStyle, TangoPalette.ALUMINIUM6);

		final Style wordJavaStyle = styleContext.addStyle("java-modifier", null);
		StyleConstants.setFontFamily(wordJavaStyle, "lucida console");
		StyleConstants.setFontSize(wordJavaStyle, 11);
		StyleConstants.setForeground(wordJavaStyle, RosePalette.COALBLACK);
		StyleConstants.setBold(wordJavaStyle, true);

		final Style wordJavaComment = styleContext.addStyle("java-comment1", null);
		StyleConstants.setFontFamily(wordJavaComment, "lucida console");
		StyleConstants.setFontSize(wordJavaComment, 11);
		StyleConstants.setForeground(wordJavaComment, RosePalette.CORALRED);

		final Style wordJavaComment2 = styleContext.addStyle("java-comment2", null);
		StyleConstants.setFontFamily(wordJavaComment2, "lucida console");
		StyleConstants.setFontSize(wordJavaComment2, 11);
		StyleConstants.setForeground(wordJavaComment2, new Color(63, 127, 95));
		StyleConstants.setForeground(wordJavaComment2, RosePalette.COBALT);

		final Style wordJavaComment3 = styleContext.addStyle("java-comment3", null);
		StyleConstants.setFontFamily(wordJavaComment3, "lucida console");
		StyleConstants.setFontSize(wordJavaComment3, 11);
		StyleConstants.setForeground(wordJavaComment3, new Color(63, 127, 95));
		StyleConstants.setForeground(wordJavaComment3, TangoPalette.ALUMINIUM2);

		final Style stringStyle = styleContext.addStyle("java-string", null);
		StyleConstants.setFontFamily(stringStyle, "lucida console");
		StyleConstants.setFontSize(stringStyle, 11);
		StyleConstants.setForeground(stringStyle, new Color(63, 127, 95));
		StyleConstants.setForeground(stringStyle, TangoPalette.ORANGE1);

		final Style wordJavaAnnotation = styleContext.addStyle("java-annotation", null);
		StyleConstants.setFontFamily(wordJavaAnnotation, "lucida console");
		StyleConstants.setFontSize(wordJavaAnnotation, 11);
		StyleConstants.setForeground(wordJavaAnnotation, TangoPalette.BUTTER1);

	}

	/**
	 * apply attributes style to demo source
	 */
	private void applyStyles(SourcePane sourcePane) {

		final SourceStyle uijavaStyle = new SourceStyle(sourcePane.getSourceTextPane(), sourcePane.getStyledDocument().getStyle("java-source"));
		uijavaStyle.apply();

		final WordStyle uiworldStyle = new WordStyle(sourcePane.getSourceTextPane(), sourcePane.getStyledDocument().getStyle("java-modifier"), " new ", " super", "\tsuper", " private ", "\nprivate ", "\tprivate ", " void ", "\nvoid ", "\npublic ", " public ", "\tpublic ", " class ", "\nclass ", "\npackage ", "\tpackage ", " package ", "\nimport ", "\timport ", " import ", " extends ", " return ", "\nreturn ", "\treturn ", "\nfinal ", " final ", "\nfloat ", " float ", "\ndouble ", " double ", "\nint ", " int ", "\nlong ", " long ", "\nshort ", " short ");
		uiworldStyle.apply();

		final SectionStyle uicommentStyle1 = new SectionStyle(sourcePane.getSourceTextPane(), "/**", "*/", sourcePane.getStyledDocument().getStyle("java-comment2"));
		final SectionStyle uicommentStyle2 = new SectionStyle(sourcePane.getSourceTextPane(), "/*", "*/", sourcePane.getStyledDocument().getStyle("java-comment1"));
		final SectionStyle uicommentStyle3 = new SectionStyle(sourcePane.getSourceTextPane(), "//", "\n", sourcePane.getStyledDocument().getStyle("java-comment3"));

		final SectionStyle uistringStyleSection = new SectionStyle(sourcePane.getSourceTextPane(), "\"", "\"", sourcePane.getStyledDocument().getStyle("java-string"));

		uistringStyleSection.apply();
		uicommentStyle3.apply();
		uicommentStyle2.apply();
		uicommentStyle1.apply();

		final WordStyle uiannotationStyle = new WordStyle(sourcePane.getSourceTextPane(), sourcePane.getStyledDocument().getStyle("java-annotation"), "@JenSoftAPIDemo", "@Generated", "@FrameUI", "@AppletUI", "@JensoftView", "@Override", "@Portfolio");
		uiannotationStyle.apply();
	}

	/**
	 * load the view source file in the current class loader
	 */
	private void loadSource(SourcePane sourcePane, Class source) {
		System.out.println("JenSoft API - Load View source");
		String inputSourceName = "NA";
		try {
			ClassLoader cloader = source.getClassLoader();
			String packageName = source.getPackage().getName();
			inputSourceName = packageName.replace('.', '/') + "/" + source.getSimpleName() + ".java";

			InputStream is = cloader.getResourceAsStream(inputSourceName);
			InputStreamReader isreader = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isreader);
			String line = null;

			while ((line = in.readLine()) != null) {
				try {
					StyledDocument doc = sourcePane.getStyledDocument();
					doc.insertString(doc.getLength(), line + "\n", doc.getStyle("java-default"));
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			System.err.println("JenSoft API - Load source of demo failed " + inputSourceName);
		}
	}

}
