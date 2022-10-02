package com.example.demo.model;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.inchi.InChIGeneratorFactory;

public class ReagentUse {
    private Reagent reagent;
    private Amount amount = null;

    public ReagentUse(Reagent reagent, Amount units) {
        this.reagent = reagent;
        this.amount = units;
    }

    public ReagentUse(Reagent reagent) {
        this.reagent = reagent;
    }

    public void print() {
        if(amount != null) {
            System.out.println(reagent.getName() + " " + amount.getAmount() + " " + amount.getUnits());
        }
        else {
            System.out.println(reagent.getName());
        }
    }

    public String toString() {
        if(amount != null) {
            return reagent.toString() + ":" + amount.toString();
        }
        else {
            return reagent.toString();
        }
    }

}
