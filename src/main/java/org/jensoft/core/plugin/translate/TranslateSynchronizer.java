/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.translate;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.projection.Projection;

/**
 * <code>TranslateSynchronizer</code>
 * 
 * @author sebastien janaud
 */
public class TranslateSynchronizer implements TranslatePluginListener {

    /** the translate plug ins to synchronize */
    private List<TranslatePlugin> translateList;

    /** dispatchingEvent flag */
    private boolean dispathingEvent = false;

    /**
     * create Translate synchronizer for the specified translates
     * 
     * @param translates
     */
    public TranslateSynchronizer(TranslatePlugin... translates) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            translateList = new ArrayList<TranslatePlugin>();
            for (int i = 0; i < translates.length; i++) {
                TranslatePlugin translatePlugin = translates[i];
                translatePlugin.addTranslateListener(this);
                translatePlugin.addPluginListener(this);
                // translatePlugin.dynamicEffect = false;
                translateList.add(translatePlugin);
            }
            dispathingEvent = false;
        }
    }

    /**
     * create Translate synchronizer for the specified translates
     * <p>
     * list parameter is only read, an internal list is created and register given Translate plug in instance.
     * 
     * @see TranslateSynchronizer#translateList
     * @param translates
     *            the translates plugins to synchronize when actions occurs
     */
    public TranslateSynchronizer(List<TranslatePlugin> translates) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            translateList = new ArrayList<TranslatePlugin>();
            for (int i = 0; i < translates.size(); i++) {
                TranslatePlugin translatePlugin = translates.get(i);
                translatePlugin.addTranslateListener(this);
                translatePlugin.addPluginListener(this);
                translateList.add(translatePlugin);
            }
            dispathingEvent = false;
        }
    }

   
   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.PluginListener#pluginSelected(org.jensoft.core.plugin.PluginEvent)
     */
    @Override
    public void pluginSelected(PluginEvent<TranslatePlugin> pluginEvent) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            TranslatePlugin sourceTranslatePlugin = (TranslatePlugin) pluginEvent
                    .getSource();
            for (TranslatePlugin targetTranslatePlugin : translateList) {
                if (!targetTranslatePlugin.equals(sourceTranslatePlugin)) {
                    targetTranslatePlugin.lockSelected();
                }
            }
            dispathingEvent = false;
        }
    }
    
  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.PluginListener#pluginUnlockSelected(org.jensoft.core.plugin.PluginEvent)
     */
    @Override
    public void pluginUnlockSelected(PluginEvent<TranslatePlugin> pluginEvent) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            TranslatePlugin sourceTranslatePlugin = (TranslatePlugin) pluginEvent
                    .getSource();
            for (TranslatePlugin targetTranslatePlugin : translateList) {
                if (!targetTranslatePlugin.equals(sourceTranslatePlugin)) {
                    targetTranslatePlugin.unlockSelected();
                }
            }
            dispathingEvent = false;
        }
    }

   
   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.translate.TranslatePluginListener#translateL2RChanged(org.jensoft.core.plugin.translate.TranslatePluginEvent)
     */
    @Override
    public void translateL2RChanged(TranslatePluginEvent pluginEvent) {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.translate.TranslatePluginListener#translateB2TChanged(org.jensoft.core.plugin.translate.TranslatePluginEvent)
     */
    @Override
    public void translateB2TChanged(TranslatePluginEvent pluginEvent) {
    }


    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.translate.TranslatePluginListener#translateStarted(org.jensoft.core.plugin.translate.TranslatePluginEvent)
     */
    @Override
    public void translateStarted(TranslatePluginEvent pluginEvent) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            TranslatePlugin sourceTranslate = (TranslatePlugin) pluginEvent.getSource();
            for (TranslatePlugin tp : translateList) {
                if (!tp.equals(sourceTranslate)) {
                    Point2D deviceStartDevice = sourceTranslate.getTranslateStartDevicePoint();
                    tp.startTranslate(new Point2D.Double((int) deviceStartDevice.getX(), (int) deviceStartDevice.getY()));
                    tp.fireTranslateStarted();
                }
            }
            dispathingEvent = false;
        }
    }


    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.translate.TranslatePluginListener#translated(org.jensoft.core.plugin.translate.TranslatePluginEvent)
     */
    @Override
    public void translated(TranslatePluginEvent pluginEvent) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            TranslatePlugin sourceTranslate = (TranslatePlugin) pluginEvent
                    .getSource();
            for (TranslatePlugin tp : translateList) {
                if (!tp.equals(sourceTranslate)) {
                    tp.processTranslate(sourceTranslate.getTranslateDx(), sourceTranslate.getTranslateDy());
                    tp.fireTranslate();
                }
            }
            sourceTranslate.getProjection().getDevice2D().repaintDevice();
            dispathingEvent = false;
        }
    }


    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.translate.TranslatePluginListener#translateStoped(org.jensoft.core.plugin.translate.TranslatePluginEvent)
     */
    @Override
    public void translateStoped(TranslatePluginEvent pluginEvent) {
        if (!dispathingEvent) {
            dispathingEvent = true;
            TranslatePlugin sourceTranslate = (TranslatePlugin) pluginEvent
                    .getSource();
            for (TranslatePlugin tp : translateList) {
                if (!tp.equals(sourceTranslate)) {
                    Projection w2d = tp.getProjection();
                    Point2D deviceCurentDevice = sourceTranslate.getTranslateCurrentDevicePoint();
                    tp.stopTranslate((int) deviceCurentDevice.getX(), (int) deviceCurentDevice.getY());
                    w2d.getDevice2D().repaintDevice();
                    tp.fireTranslateStopped();
                }
            }
            dispathingEvent = false;
        }
    }

}
