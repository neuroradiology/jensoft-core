/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.animator;

import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DEvent;
import org.jensoft.core.plugin.donut2d.Donut2DListener;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.view.View;

/**
 * <code>AbstractDonut2DAnimator</code> Abstract base definition to animate donut2D
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut2DAnimator implements Donut2DListener {

    /** the target donut for this animator */
    private Donut2D donut2D;

    /**
     * create animator
     */
    public AbstractDonut2DAnimator() {
    }

    /**
     * @return the donut2D
     */
    public Donut2D getDonut2D() {
        return donut2D;
    }

    /**
     * @param donut2d
     *            the donut2D to set
     */
    public void setDonut2D(Donut2D donut2d) {
        donut2D = donut2d;
    }

    /**
     * call when the mouse enter in the specified slice
     * 
     * @param slice
     */
    protected void onEntered(Donut2DSlice slice) {
    }

    /**
     * call when the mouse exited of the specified slice
     * 
     * @param slice
     */

    protected void onExited(Donut2DSlice slice) {
    }

    /**
     * call when the mouse clicked (pressed and released) in the specified
     * slice
     * 
     * @param slice
     */
    protected void onClicked(Donut2DSlice slice) {
    }

    /**
     * call when the mouse pressed in the specified slice
     * 
     * @param slice
     */
    protected void onPressed(Donut2DSlice slice) {
    }

    /**
     * call when the mouse pressed in the specified slice
     * 
     * @param slice
     */
    protected void onReleased(Donut2DSlice slice) {
    }

    /**
     * <p>
     * helper method to create animator.
     * </p>
     * <p>
     * imagine that you have an animator that make something on slice or donut, you can return this animator with the
     * slice argument if you want animate slice or null if the animator is for all donut. it's just an helper method
     * </p>
     * 
     * @param slice
     *            the slice
     * @return runnable animator
     */
    protected abstract Runnable getAnimator(Donut2DSlice slice);

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceClicked(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public final void donut2DSliceClicked(Donut2DEvent e) {
        Donut2DSlice slice = e.getDonut2DSlice();
        onClicked(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSlicePressed(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public final void donut2DSlicePressed(Donut2DEvent e) {
        Donut2DSlice slice = e.getDonut2DSlice();
        onPressed(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceReleased(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public final void donut2DSliceReleased(Donut2DEvent e) {
        Donut2DSlice slice = e.getDonut2DSlice();
        onReleased(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceEntered(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public final void donut2DSliceEntered(Donut2DEvent e) {
        Donut2DSlice slice = e.getDonut2DSlice();
        onEntered(slice);
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceExited(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public final void donut2DSliceExited(Donut2DEvent e) {
        Donut2DSlice slice = e.getDonut2DSlice();
        onExited(slice);
    }

    /**
     * get view that host specified slice
     * 
     * @param slice
     * @return host view
     */
    public View getView(Donut2DSlice slice) {
        return slice.getHost().getHostPlugin().getProjection().getView();
    }

}
