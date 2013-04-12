/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.analysis;

/**
 * <code>SplineInterpolator</code>
 * Computes a natural (also known as "free", "unclamped") cubic spline
 * interpolation for the data set.
 * <p>
 * The {@link #interpolate(double[], double[])} method returns a
 * {@link PolynomialSplineFunction} consisting of n cubic polynomials, defined
 * over the subintervals determined by the x values, x[0] < x[i] ... < x[n]. The
 * x values are referred to as "knot points."
 * </p>
 * <p>
 * The value of the PolynomialSplineFunction at a point x that is greater than
 * or equal to the smallest knot point and strictly less than the largest knot
 * point is computed by finding the subinterval to which x belongs and computing
 * the value of the corresponding polynomial at <code>x - x[i] </code> where
 * <code>i</code> is the index of the subinterval. See
 * {@link PolynomialSplineFunction} for more details.
 * </p>
 * <p>
 * The interpolating polynomials satisfy:
 * <ol>
 * <li>The value of the PolynomialSplineFunction at each of the input x values
 * equals the corresponding y value.</li>
 * <li>Adjacent polynomials are equal through two derivatives at the knot points
 * (i.e., adjacent polynomials "match up" at the knot points, as do their first
 * and second derivatives).</li>
 * </ol>
 * </p>
 * <p>
 * The cubic spline interpolation algorithm implemented is as described in R.L.
 * Burden, J.D. Faires, <u>Numerical Analysis</u>, 4th Ed., 1989, PWS-Kent, ISBN
 * 0-53491-585-X, pp 126-131.
 * </p>
 */
public class SplineInterpolator implements UnivariateRealInterpolator {

	/**
	 * Computes an interpolating function for the data set.
	 * 
	 * @param x
	 *            the arguments for the interpolation points
	 * @param y
	 *            the values for the interpolation points
	 * @return a function which interpolates the data set
	 */
	@Override
	public PolynomialSplineFunction interpolate(double x[], double y[]) {
		if (x.length != y.length) {

			throw new IllegalArgumentException("x and y array values dimensions mismatch");
		}

		if (x.length < 3) {
			throw new IllegalArgumentException("the number of points must be greater than 3");
		}

		// Number of intervals. The number of data points is n + 1.
		int n = x.length - 1;

		checkOrder(x, OrderDirection.INCREASING, true);

		// Differences between knot points
		double h[] = new double[n];
		for (int i = 0; i < n; i++) {
			h[i] = x[i + 1] - x[i];
		}

		double mu[] = new double[n];
		double z[] = new double[n + 1];
		mu[0] = 0d;
		z[0] = 0d;
		double g = 0;
		for (int i = 1; i < n; i++) {
			g = 2d * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
			mu[i] = h[i] / g;
			z[i] = (3d * (y[i + 1] * h[i - 1] - y[i] * (x[i + 1] - x[i - 1]) + y[i - 1] * h[i]) / (h[i - 1] * h[i]) - h[i - 1] * z[i - 1]) / g;
		}

		// cubic spline coefficients -- b is linear, c quadratic, d is cubic
		// (original y's are constants)
		double b[] = new double[n];
		double c[] = new double[n + 1];
		double d[] = new double[n];

		z[n] = 0d;
		c[n] = 0d;

		for (int j = n - 1; j >= 0; j--) {
			c[j] = z[j] - mu[j] * c[j + 1];
			b[j] = (y[j + 1] - y[j]) / h[j] - h[j] * (c[j + 1] + 2d * c[j]) / 3d;
			d[j] = (c[j + 1] - c[j]) / (3d * h[j]);
		}

		PolynomialFunction polynomials[] = new PolynomialFunction[n];
		double coefficients[] = new double[4];
		for (int i = 0; i < n; i++) {
			coefficients[0] = y[i];
			coefficients[1] = b[i];
			coefficients[2] = c[i];
			coefficients[3] = d[i];
			polynomials[i] = new PolynomialFunction(coefficients);
		}

		return new PolynomialSplineFunction(x, polynomials);
	}

	/**
	 * Specification of ordering direction.
	 */
	public static enum OrderDirection {
		/** Constant for increasing direction. */
		INCREASING,
		/** Constant for decreasing direction. */
		DECREASING
	}

	/**
	 * Checks that the given array is sorted.
	 * 
	 * @param val
	 *            Values.
	 * @param dir
	 *            Ordering direction.
	 * @param strict
	 *            Whether the order should be strict.
	 */
	public static void checkOrder(double[] val, OrderDirection dir, boolean strict) {
		double previous = val[0];
		boolean ok = true;

		int max = val.length;
		for (int i = 1; i < max; i++) {
			switch (dir) {
			case INCREASING:
				if (strict) {
					if (val[i] <= previous) {
						ok = false;
					}
				} else {
					if (val[i] < previous) {
						ok = false;
					}
				}
				break;
			case DECREASING:
				if (strict) {
					if (val[i] >= previous) {
						ok = false;
					}
				} else {
					if (val[i] > previous) {
						ok = false;
					}
				}
				break;
			default:
				// Should never happen.
				throw new IllegalArgumentException();
			}

			if (!ok) {
				throw new IllegalArgumentException("Spline interpolator can not be used with values which are not order");
			}
			previous = val[i];
		}
	}

}
