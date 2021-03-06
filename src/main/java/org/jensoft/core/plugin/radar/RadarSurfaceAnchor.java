/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

/**
 * <code>RadarSurfaceAnchor</code> defines a surface anchor on radar dimension
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public class RadarSurfaceAnchor {

    /** anchor dimension */
    private RadarDimension dimension;

    /** anchor metrics value */
    private RadarMetrics radarMetrics;
    
    /**metrics enable flag*/
    private boolean metricsEnable = false;

    public RadarSurfaceAnchor(RadarDimension dimension,
            AnchorMetrics radarMetrics) {
        double metricsValue = radarMetrics.getValue();
        if (metricsValue < dimension.getMinDimension()
                || metricsValue > dimension.getMaxDimension()) {
            throw new IllegalArgumentException(
                                               "anchor metrics value out of dimension range.");
        }
        this.dimension = dimension;
        this.radarMetrics = radarMetrics;
    }

    /**
     * @return the dimension
     */
    public RadarDimension getDimension() {
        return dimension;
    }

    /**
     * @param dimension
     *            the dimension to set
     */
    public void setDimension(RadarDimension dimension) {
        this.dimension = dimension;
    }

    /**
     * @return the radar metrics
     */
    public RadarMetrics getRadarMetrics() {
        return radarMetrics;
    }

    /**
     * @param radarMetrics
     *            the radar metrics to set
     */
    public void setRadarMetrics(RadarMetrics radarMetrics) {
        this.radarMetrics = radarMetrics;
    }

    
    /**
     * @return the metricsEnable
     */
    public boolean isMetricsEnable() {
        return metricsEnable;
    }

    
    /**
     * @param metricsEnable the metricsEnable to set
     */
    public void setMetricsEnable(boolean metricsEnable) {
        this.metricsEnable = metricsEnable;
    }
    
    

}
