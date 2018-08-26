package com.example.diana.easyinvest.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Calculations {

    private Calculations() {
    }

    /**
     * Calculates payback period
     * @param moneyFlows positive or negative values for money flow for each year
     *                   moneyFlows[0] - investments for start
     *                   moneyFlows[1] - [profit - investments] at the end of 1st year
     *                   moneyFlows[2] - [profit - investments] at the end of 2nd year
     *                   and so on
     * @return number of year, after which project pays back all the investments
     *         -1 if {@param moneyFlows} is empty
     *         -1 if project doesn't pay off
     */
    public static int getPP(@NonNull double[] moneyFlows) {
        if (moneyFlows.length == 0) {
            return -1;
        }
        int period = 0;
        double sum = moneyFlows[0];
        int index = 1;
        while (sum < 0 && index < moneyFlows.length) {
            sum += moneyFlows[index];
            period++;
            index++;
        }
        if (sum < 0) { // project doesn't pay off
            return -1;
        }
        return period;
    }

    /**
     * Accounting Rate of Return
     *
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @return ARR value
     */

    public static double getARR(double[] moneyFlows) {
        if (moneyFlows.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        double iMid = 0f;
        double pMid = 0f;
        for (double d : moneyFlows) {
            if (d >= 0) {
                pMid += Math.abs(d);
            } else {
                iMid += Math.abs(d);
            }
        }
        int duration = moneyFlows.length - 1; // because 0th is initial investments
        pMid = pMid / duration;
        iMid = iMid / duration;

        if (iMid == 0) {
            return Float.POSITIVE_INFINITY;
        }
        return pMid / iMid * 100;
    }

    /**
     * Accounting Rate of Return
     *
     * @param investments investments yearly (must not contain 0's)
     * @param profits profits yearly  (must not contain 0's)
     * @param duration length of project in years
     * @return ARR value
     * @throws IllegalAccessException if arrays are empty or duration <= 0
     */
    public static double getARR(@NonNull double[] investments, @NonNull double[] profits, int duration) {
        if (investments.length == 0 || profits.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be > 0");
        }
        float iMid = 0f;
        for (double f : investments) {
            iMid += f;
        }
        iMid = iMid / duration;

        float pMid = 0f;
        for (double f: profits) {
            pMid += f;
        }
        pMid = pMid / duration;

        if (iMid == 0) {
            return Float.POSITIVE_INFINITY;
        }
        return pMid / iMid * 100;
    }

    /**
     * Discount coefficient
     *
     * @param r discount rate
     * @param yearNum number of year
     * @return DFt
     */
    private static double DFt(double r, int yearNum) {
        return 1/(Math.pow((1 + r), yearNum));
    }


    /**
     * Net Present Value
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @param r - discount rate, 0 < r < 1
     * @return NPV value
     *         the results means:
     *         < 0 - project is unprofitable
     *         = 0 - projects will pay off, but nothing more
     *         > 0 - project is profitale
     * @throws IllegalArgumentException if array is empty
     */
    public static double getNPV(@NonNull double[] moneyFlows, double r) {
        if (moneyFlows.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        double sum = 0f;
        for (int t = 0; t < moneyFlows.length; t++) {
            sum += moneyFlows[t] * DFt(r, t);;
        }
        return sum;
    }

    /**
     * Internal Rate of Return
     *
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @param precision maximum value, such that NPV(IRR) <= {@param precision} would be counted as zero
     * @return IRR value
     *         IRR < r - project is unprofitable
     *         IRR = r - project pays off, but nothing more
     *         IRR > r - project is profitable
     * @throws IllegalArgumentException if array is empty
     */
    public static double getIRR(@NonNull double[] moneyFlows, double precision) {
        if (moneyFlows.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        double rUp = 0.9f;
        double rDown = 0.1f;
        double IRR;
        double NPV_IRR;
        do {
            // detect rUp such that NPV(rUp) < 0
            while (!(getNPV(moneyFlows, rUp) < 0)) {
                rUp *= 1.1f;
            }
            // detect rDown such that NPV(rDown) > 0
            while (!(getNPV(moneyFlows, rDown) > 0)) {
                rDown /= 1.1f;
            }
            IRR = getIRR(moneyFlows, rUp, rDown);
            NPV_IRR = getNPV(moneyFlows, IRR);

            // rUp & rDown for next iterations
            if (NPV_IRR > 0) {
                rDown = IRR;
                rUp = IRR * 1.1f;
            } else {
                rUp = IRR;
                rDown = IRR / 1.1f;
            }

        } while(!(Math.abs(NPV_IRR) <= precision));
        return IRR;
    }

    /**
     * Calculates IRR fo given rUp and rDown.
     * The formula us used for calculating approximate value of IRR
     * {@link #getIRR(double[], double)}
     *
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @param rUp - r, for which definitely NPV < 0
     * @param rDown - r for which definitely NPV > 0
     * @return IRR fo given rUp and rDown.
     * @throws IllegalAccessException if rUp <= rDown
     */
    private static double getIRR(@NonNull double[] moneyFlows, double rUp, double rDown) {
        if (rUp <= rDown) {
            throw new IllegalArgumentException("rUp should be greater than rDown");
        }
        double npvUp = getNPV(moneyFlows, rUp);
        double npvDown = getNPV(moneyFlows, rDown);
        return rDown + (rUp - rDown)/(npvDown - npvUp) * npvDown;
    }

    /**
     * Modified Internal Rate of Return
     *
     * @param investments investments yearly (for every year, including 0th)
     * @param profits profits yearly (for every year, including 0th)
     * @param r discount rate, 0 < r < 1
     * @return MIRR
     *         MIRR < r - project is unprofitable
     *         MIRR = r - project pays off, but nothing more
     *         MIRR > r - project is profitable
     * @throws IllegalArgumentException if any of arrays is empty or their length doesn't match
     */
    public static double getMIRR(@NonNull double[] investments, @NonNull double[] profits, double r) {
        if (investments.length == 0 || profits.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        if (investments.length != profits.length) {
            throw  new IllegalArgumentException("rrays should have equal length");
        }
        double FV = 0;
        double duration = profits.length - 1;
        for (int i = 0; i < profits.length; i++) {
            FV += profits[i]/ Math.pow((1 + r), (duration - i));
        }
        double PV = 0;
        for (int i = 0; i < investments.length; i++) {
            PV += investments[i]/ Math.pow((1 + r), i);
        }
        return Math.pow(FV / PV, ((double) 1 / duration)) - 1;
    }

    /**
     * Modified Internal Rate of Return
     *
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @param r discount rate, 0 < r < 1
     * @return MIRR {@link #getMIRR(double[], double)}
     */
    public static double getMIRR(@NonNull double[] moneyFlows, double r) {
        if (moneyFlows.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        double PV = 0;
        double FV = 0;
        double duration = moneyFlows.length - 1;
        for (int i = 0; i < moneyFlows.length; i++) {
            if (moneyFlows[i] >= 0) {
                FV += moneyFlows[i]/ Math.pow((1 + r), (duration - i));
            } else {
                PV += Math.abs(moneyFlows[i])/ Math.pow((1 + r), i);
            }
        }
        return Math.pow(FV / PV, ((double) 1 / (duration))) - 1;
    }

    /**
     * Profitability Index
     *
     * @param investments investments yearly (for every year, right order is important)
     * @param profits     profits yearly (for every year, right order is important)
     * @param r - discount rate, 0 < r < 1
     *
     * @return PI
     *         PI = 1 - every investment unit returns the same profit unit
     *         PI > 1 - every investment unit returns bigger profit
     *         PI < 1 - every investment unit returns less profit
     * @throws IllegalArgumentException if any of arrays is empty
     */

    public static double getPI(@NonNull double[] investments, @NonNull double[] profits, double r) {
        if (investments.length == 0 || profits.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        double cofSum = 0;
        for (int i = 0; i < investments.length; i++) {
            cofSum += investments[i] / DFt(r, i);
        }
        double cifSum = 0;
        for (int i = 0; i < profits.length; i++) {
            cifSum += profits[i] / DFt(r, i);
        }
        return cifSum / cofSum;
    }

    /**
     * Profitability Index
     *
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @param r - discount rate, 0 < r < 1
     * @return PI {@link #getPI(double[], double)}
     */
    public static double getPI(@NonNull double[] moneyFlows, double r) {
        if (moneyFlows.length == 0) {
            throw new IllegalArgumentException("Array shouldn't be void");
        }
        double cofSum = 0;
        double cifSum = 0;
        for (int i = 0; i < moneyFlows.length; i++) {
            if (moneyFlows[i] >= 0) {
                cifSum += moneyFlows[i] / DFt(r, i);
            } else {
                cofSum += Math.abs(moneyFlows[i]) / DFt(r, i);
            }
        }
        return cifSum / cofSum;
    }

    /**
     * Discounted  Payback Period
     *
     * @param moneyFlows positive or negative values for money flow for each year
     *                   (see {@link #getPP(double[])})
     * @param r - discount rate, 0 < r < 1
     *
     * @return number of year, after which project pays back all the investments
     *         -1 if {@param moneyFlows} is empty
     *         -1 if project doesn't pay off
     */
    public static int getDPP(@NonNull double[] moneyFlows, double r) {
        if (moneyFlows.length == 0) {
            return -1;
        }
        int period = 0;
        double sum = moneyFlows[0];
        int index = 1;
        while (sum < 0 && index < moneyFlows.length) {
            sum += moneyFlows[index] * DFt(r, index);
            period++;
            index++;
        }
        if (sum < 0) { // project doesn't pay off
            return -1;
        }
        return period;
    }
}
