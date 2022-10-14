package com.example.cs5132_patwo.model;

public class ReagentUse {
    private final Reagent reagent;
    private Amount amount = null;

    public ReagentUse(Reagent reagent, Amount units) {
        this.reagent = reagent;
        this.amount = units;
    }

    public ReagentUse(Reagent reagent) {
        this.reagent = reagent;
    }

    public void print() {
        if (amount != null) {
            System.out.println(reagent.getName() + " " + amount.getAmount() + " " + amount.getUnits());
        } else {
            System.out.println(reagent.getName());
        }
    }

    public String toString() {
        if (amount != null) {
            return reagent.toString() + ":" + amount.toString();
        } else {
            return reagent.toString();
        }
    }

}
