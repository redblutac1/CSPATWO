package com.example.cs5132_patwo.model;

import org.apache.commons.lang3.ArrayUtils;

public class Amount implements Comparable<Amount> {
    private static final String[] order = {"mg", "mL", "mmol", "percentYield"};
    private final String units;
    private final double amount;

    public Amount(String units, double amount) {
        this.units = units;
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Amount o) {
        return ArrayUtils.indexOf(order, o.getUnits()) - ArrayUtils.indexOf(order, units);
    }

    public String toString() {
        return amount + ":" + units;
    }
}
