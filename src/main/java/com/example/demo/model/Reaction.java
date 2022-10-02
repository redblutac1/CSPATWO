package com.example.demo.model;

import org.openscience.cdk.interfaces.IAtomContainer;

import java.util.ArrayList;

public class Reaction {
    private ArrayList<ReagentUse> reactants;
    private ArrayList<ReagentUse> products;
    private ArrayList<ReagentUse> solvents;
    //private ArrayList<ReagentUse> catalysts;
    private double yield = -1.0;

    public Reaction(ArrayList<ReagentUse> reactants, ArrayList<ReagentUse> products, ArrayList<ReagentUse> solvents, ArrayList<ReagentUse> catalysts, int yield) {
        this.reactants = reactants;
        this.products = products;
        this.solvents = solvents;
        //this.catalysts = catalysts;
        this.yield = yield;
    }
    public Reaction(ArrayList<ReagentUse> reactants, ArrayList<ReagentUse> products, ArrayList<ReagentUse> solvents, ArrayList<ReagentUse> catalysts) {
        this.reactants = reactants;
        this.products = products;
        this.solvents = solvents;
        //this.catalysts = catalysts;
    }

    public Reaction() {
        reactants = new ArrayList<>();
        products = new ArrayList<>();
        solvents = new ArrayList<>();
        //catalysts = new ArrayList<>();
    }

    public void addReactant(ReagentUse r) {
        reactants.add(r);
    }

    public void addProduct(ReagentUse r) {
        products.add(r);
    }

    public void addSolvent(ReagentUse r) {
        solvents.add(r);
    }

    //public void addCatalyst(ReagentUse r) {
    //    catalysts.add(r);
    //}

    public void setYield(double yield) {
        this.yield = yield;
    }

    public void add(ReagentUse r, int number) {
        switch (number) {
            case 1:
                addReactant(r);
                break;
            case 2:
                addProduct(r);
                break;
            case 3:
                addSolvent(r);
                break;
            //case 4:
            //    addCatalyst(r);
            //    break;
        }
    }

    public void print() {
        System.out.println("Reactants: ");
        for(ReagentUse r : reactants) {
            r.print();
        }
        System.out.println();
        System.out.println("Products: ");
        for(ReagentUse r : products) {
            r.print();
        }
        System.out.println();
        System.out.println("Solvents: ");
        for(ReagentUse r : solvents) {
            r.print();
        }
    }

    public String toString() {
        String s = "";
        for(ReagentUse ru : reactants) {
            s += ru.toString();
            s += "~";
        }
        s = s.replaceAll("~$", "\\\\");
        for(ReagentUse ru : products) {
            s += ru.toString();
            s += "~";
        }
        s = s.replaceAll("~$", "\\\\");
        for(ReagentUse ru : solvents) {
            s += ru.toString();
            s += "~";
        }
        if(s.charAt(s.length() - 1) == ';') {
            s = s.replaceAll("~$", "\\\\");
        }
        else {
            s += "\\";
        }
        s += yield;

        return s;
    }
}
