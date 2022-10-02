package com.example.demo.model;

import org.openscience.cdk.interfaces.IAtomContainer;

import java.util.ArrayList;

public class Reaction {
    private ArrayList<ReagentUse> reactants;
    private ArrayList<ReagentUse> products;
    private ArrayList<ReagentUse> solvents;
    private ArrayList<ReagentUse> catalysts;

    public Reaction(ArrayList<ReagentUse> reactants, ArrayList<ReagentUse> products, ArrayList<ReagentUse> solvents, ArrayList<ReagentUse> catalysts) {
        this.reactants = reactants;
        this.products = products;
        this.solvents = solvents;
        this.catalysts = catalysts;
    }

    public Reaction() {
        reactants = new ArrayList<>();
        products = new ArrayList<>();
        solvents = new ArrayList<>();
        catalysts = new ArrayList<>();
    }

    public void addReactant(ReagentUse r) {
        reactants.add(r);
    }

    public void addProduct(ReagentUse r) {
        products.add(r);
    }

    public void addSolvents(ReagentUse r) {
        solvents.add(r);
    }

    public void addCatalyst(ReagentUse r) {
        catalysts.add(r);
    }
}
