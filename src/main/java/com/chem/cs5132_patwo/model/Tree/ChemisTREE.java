package com.chem.cs5132_patwo.model.Tree;

import com.chem.cs5132_patwo.model.Reagent;

import java.util.ArrayList;

public class ChemisTREE<T extends Comparable<? super T>> {
    private final Node<Reagent> root;
    private final ArrayList<Node<Reagent>> reactants;

    public ChemisTREE(Reagent product, ArrayList<Reagent> reactants) {
        this.root = new Node<Reagent>(product);
        this.reactants = new ArrayList<Node<Reagent>>();

        for (Reagent r : reactants) {
            this.reactants.add(new Node<Reagent>(r));
        }
    }

    public void insert(Reagent reagent) {
        reactants.add(new Node<Reagent>(reagent));
    }

    public int numChildren() {
        return reactants.size();
    }

    public Node<Reagent> getRoot() {
        return root;
    }

    public Reagent getProduct() {
        return root.getItem();
    }
}
