package com.example.diana.easyinvest.utils;

import org.junit.Test;

public class CalculationsTest {
    private double[]  moneyFlows = {-20, 6, 8, 14};
    private double[]  investments = {20};
    private double[]  profits = {6, 8, 14};
    private double r = 0.4;
    private int duration = 3;

    @Test
    public void getPP() {
        double result = Calculations.getPP(moneyFlows);
        System.out.println("PP " + result);
    }

    @Test
    public void getARR() {
        double result = Calculations.getARR(moneyFlows);
        System.out.println("ARR " + result);
        double result2 = Calculations.getARR(investments, profits, duration);
        System.out.println("ARR2 " + result2);
    }

    @Test
    public void getNPV() {
        double result = Calculations.getNPV(moneyFlows, r);
        System.out.println("NPV " + result);
    }

    @Test
    public void getIRR() {
        double result = Calculations.getIRR(moneyFlows, 1);
        System.out.println("IRR " + result);
    }

    @Test
    public void getMIRR() {
        double[] investments = {20, 0, 0, 0};
        double[] profits = {0, 6, 8, 14};
        double result = Calculations.getMIRR(investments, profits, 0.1);
        System.out.println("MIRR " + result);
        double result2 = Calculations.getMIRR(moneyFlows, 0.1);
        System.out.println("MIRR2 " + result2);
    }

    @Test
    public void getPI() {
        double[] investments = {20, 0, 0, 0};
        double[] profits = {0, 6, 8, 14};
        double result = Calculations.getPI(investments, profits, r);
        System.out.println("PI " + result);
        double result2 = Calculations.getPI(moneyFlows, r);
        System.out.println("PI2 " + result2);
    }

    @Test
    public void getDPP() {
        double result = Calculations.getDPP(moneyFlows, 0.1);
        System.out.println("DPP " + result);
    }
}