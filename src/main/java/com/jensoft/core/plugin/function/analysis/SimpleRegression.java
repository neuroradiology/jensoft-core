/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.analysis;

import java.io.Serializable;

/**
 * Estimates an ordinary least squares regression model with one independent
 * variable.
 * <p>
 * <code> y = intercept + slope * x  </code>
 * <p>
 */
public class SimpleRegression implements Serializable {

    /** Serializable version identifier */
    private static final long serialVersionUID = -1747083806449272399L;

    /** sum of x values */
    private double sumX = 0d;

    /** total variation in x (sum of squared deviations from xbar) */
    private double sumXX = 0d;

    /** sum of y values */
    private double sumY = 0d;

    /** total variation in y (sum of squared deviations from ybar) */
    private double sumYY = 0d;

    /** sum of products */
    private double sumXY = 0d;

    /** number of observations */
    private long n = 0;

    /** mean of accumulated x values, used in updating formulas */
    private double xbar = 0;

    /** mean of accumulated y values, used in updating formulas */
    private double ybar = 0;

    // ---------------------Public methods--------------------------------------

    /**
     * Create an empty SimpleRegression instance
     */
    public SimpleRegression() {
        super();
    }

    /**
     * Adds the observation (x,y) to the regression data set.
     * <p>
     * Uses updating formulas for means and sums of squares defined in "Algorithms for Computing the Sample Variance:
     * Analysis and Recommendations", Chan, T.F., Golub, G.H., and LeVeque, R.J. 1983, American Statistician, vol. 37,
     * pp. 242-247, referenced in Weisberg, S. "Applied Linear Regression". 2nd Ed. 1985
     * 
     * @param x
     *            independent variable value
     * @param y
     *            dependent variable value
     */
    public void addData(double x, double y) {
        if (n == 0) {
            xbar = x;
            ybar = y;
        }
        else {
            double dx = x - xbar;
            double dy = y - ybar;
            sumXX += dx * dx * n / (n + 1.0);
            sumYY += dy * dy * n / (n + 1.0);
            sumXY += dx * dy * n / (n + 1.0);
            xbar += dx / (n + 1.0);
            ybar += dy / (n + 1.0);
        }
        sumX += x;
        sumY += y;
        n++;
    }

    /**
     * Adds the observations represented by the elements in <code>data</code>.
     * <p>
     * <code>(data[0][0],data[0][1])</code> will be the first observation, then <code>(data[1][0],data[1][1])</code>,
     * etc.
     * <p>
     * This method does not replace data that has already been added. The observations represented by <code>data</code>
     * are added to the existing dataset.
     * <p>
     * To replace all data, use <code>clear()</code> before adding the new data.
     * 
     * @param data
     *            array of observations to be added
     */
    public void addData(double[][] data) {
        for (int i = 0; i < data.length; i++) {
            addData(data[i][0], data[i][1]);
        }
    }

    /**
     * Clears all data from the model.
     */
    public void clear() {
        sumX = 0d;
        sumXX = 0d;
        sumY = 0d;
        sumYY = 0d;
        sumXY = 0d;
        n = 0;
    }

    /**
     * Returns the number of observations that have been added to the model.
     * 
     * @return n number of observations that have been added.
     */
    public long getN() {
        return n;
    }

    /**
     * Returns the "predicted" <code>y</code> value associated with the supplied <code>x</code> value, based on the data
     * that has been added to the model
     * when this method is activated.
     * <p>
     * <code> predict(x) = intercept + slope * x </code>
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double,NaN</code> is returned.</li>
     * </ul>
     * 
     * @param x
     *            input <code>x</code> value
     * @return predicted <code>y</code> value
     */
    public double predict(double x) {
        double b1 = getSlope();
        return getIntercept(b1) + b1 * x;
    }

    /**
     * Returns the intercept of the estimated regression line.
     * <p>
     * The least squares estimate of the intercept is computed using the <a
     * href="http://www.xycoon.com/estimation4.htm">normal equations</a>. The intercept is sometimes denoted b0.
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double,NaN</code> is returned.</li>
     * </ul>
     * 
     * @return the intercept of the regression line
     */
    public double getIntercept() {
        return getIntercept(getSlope());
    }

    /**
     * Returns the slope of the estimated regression line.
     * <p>
     * The least squares estimate of the slope is computed using the <a
     * href="http://www.xycoon.com/estimation4.htm">normal equations</a>. The slope is sometimes denoted b1.
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double.NaN</code> is returned.</li>
     * </ul>
     * 
     * @return the slope of the regression line
     */
    public double getSlope() {
        if (n < 2) {
            return Double.NaN; // not enough data
        }
        if (Math.abs(sumXX) < 10 * Double.MIN_VALUE) {
            return Double.NaN; // not enough variation in x
        }
        return sumXY / sumXX;
    }

    /**
     * Returns the <a href="http://www.xycoon.com/SumOfSquares.htm"> sum of
     * squared errors</a> (SSE) associated with the regression model.
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double,NaN</code> is returned.</li>
     * </ul>
     * 
     * @return sum of squared errors associated with the regression model
     */
    public double getSumSquaredErrors() {
        return getSumSquaredErrors(getSlope());
    }

    /**
     * Returns the sum of squared deviations of the y values about their mean.
     * <p>
     * This is defined as SSTO <a href="http://www.xycoon.com/SumOfSquares.htm">here</a>.
     * <p>
     * If <code>n < 2</code>, this returns <code>Double.NaN</code>.
     * 
     * @return sum of squared deviations of y values
     */
    public double getTotalSumSquares() {
        if (n < 2) {
            return Double.NaN;
        }
        return sumYY;
    }

    /**
     * Returns the sum of squared deviations of the predicted y values about
     * their mean (which equals the mean of y).
     * <p>
     * This is usually abbreviated SSR or SSM. It is defined as SSM <a
     * href="http://www.xycoon.com/SumOfSquares.htm">here</a>
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double.NaN</code> is returned.</li>
     * </ul>
     * 
     * @return sum of squared deviations of predicted y values
     */
    public double getRegressionSumSquares() {
        return getRegressionSumSquares(getSlope());
    }

    /**
     * Returns the sum of squared errors divided by the degrees of freedom,
     * usually abbreviated MSE.
     * <p>
     * If there are fewer than <strong>three</strong> data pairs in the model, or if there is no variation in
     * <code>x</code>, this returns <code>Double.NaN</code>.
     * 
     * @return sum of squared deviations of y values
     */
    public double getMeanSquareError() {
        if (n < 3) {
            return Double.NaN;
        }
        return getSumSquaredErrors() / (n - 2);
    }

    /**
     * Returns <a
     * href="http://mathworld.wolfram.com/CorrelationCoefficient.html">
     * Pearson's product moment correlation coefficient</a>, usually denoted r.
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double,NaN</code> is returned.</li>
     * </ul>
     * 
     * @return Pearson's r
     */
    public double getR() {
        double b1 = getSlope();
        double result = Math.sqrt(getRSquare(b1));
        if (b1 < 0) {
            result = -result;
        }
        return result;
    }

    /**
     * Returns the <a href="http://www.xycoon.com/coefficient1.htm"> coefficient
     * of determination</a>, usually denoted r-square.
     * <p>
     * <strong>Preconditions</strong>:
     * <ul>
     * <li>At least two observations (with at least two different x values) must have been added before invoking this
     * method. If this method is invoked before a model can be estimated, <code>Double,NaN</code> is returned.</li>
     * </ul>
     * 
     * @return r-square
     */
    public double getRSquare() {
        return getRSquare(getSlope());
    }

    /**
     * Returns the <a href="http://www.xycoon.com/standarderrorb0.htm"> standard
     * error of the intercept estimate</a>, usually denoted s(b0).
     * <p>
     * If there are fewer that <strong>three</strong> observations in the model, or if there is no variation in x, this
     * returns <code>Double.NaN</code>.
     * 
     * @return standard error associated with intercept estimate
     */
    public double getInterceptStdErr() {
        return Math.sqrt(getMeanSquareError()
                * (1d / n + xbar * xbar / sumXX));
    }

    /**
     * Returns the <a href="http://www.xycoon.com/standerrorb(1).htm">standard
     * error of the slope estimate</a>, usually denoted s(b1).
     * <p>
     * If there are fewer that <strong>three</strong> data pairs in the model, or if there is no variation in x, this
     * returns <code>Double.NaN</code>.
     * 
     * @return standard error associated with slope estimate
     */
    public double getSlopeStdErr() {
        return Math.sqrt(getMeanSquareError() / sumXX);
    }

    /**
     * Returns the intercept of the estimated regression line, given the slope.
     * <p>
     * Will return <code>NaN</code> if slope is <code>NaN</code>.
     * 
     * @param slope
     *            current slope
     * @return the intercept of the regression line
     */
    private double getIntercept(double slope) {
        return (sumY - slope * sumX) / n;
    }

    /**
     * Returns the sum of squared errors associated with the regression model,
     * using the slope of the regression line.
     * <p>
     * Returns NaN if the slope is NaN.
     * 
     * @param b1
     *            current slope
     * @return sum of squared errors associated with the regression model
     */
    private double getSumSquaredErrors(double b1) {
        return sumYY - sumXY * sumXY / sumXX;
    }

    /**
     * Computes r-square from the slope.
     * <p>
     * will return NaN if slope is Nan.
     * 
     * @param b1
     *            current slope
     * @return r-square
     */
    private double getRSquare(double b1) {
        double ssto = getTotalSumSquares();
        return (ssto - getSumSquaredErrors(b1)) / ssto;
    }

    /**
     * Computes SSR from b1.
     * 
     * @param slope
     *            regression slope estimate
     * @return sum of squared deviations of predicted y values
     */
    private double getRegressionSumSquares(double slope) {
        return slope * slope * sumXX;
    }

}