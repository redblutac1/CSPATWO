package com.example.cs5132_patwo;

import java.util.ArrayList;

public class ChemisTREE<T> {
    private final ReagentNode<T> root;

    public ChemisTREE(ArrayList<T> reagents) {
        if (reagents.size() == 0) throw new IllegalArgumentException("Empty item array!");
        root = new ReagentNode(reagents.get(0), 20); // It is reasonably assumed that chemical reactions will not have more than 20 reactants
        root.addNeighbours(reagents);
    }

    // This is for superChemisTREE construction. It is the tree that contains all the reaction pathways.
    // TODO: MAKE NEIGHBOURS SUCH THAT IT AUTO EXPANDS AND AUTO COPIES. Let us limit to 2000.
    public ChemisTREE() {
        root = new ReagentNode<T>(null, 1000);
    }

    public void insert(ReagentNode<T> reagent) {
        root.addNeighbour(reagent);
    }

    public void insert(T reagent) {
        root.addNeighbour(reagent);
    }

    public int getNumNeighbours() {
        return root.getNumNeighbours();
    }

    public ReagentNode<T> getRoot() {
        return root;
    }

    public T getProduct() {
        return root.getItem();
    }

    public Node<T>[] getChildren() {
        return root.neighbours;
    }

    public String[] getStringChildren() {
        String[] stringNeighbours = new String[getNumNeighbours()];

        for (int i = 0; i < getNumNeighbours(); i++) {
            stringNeighbours[i] = root.neighbours[i].toString();
        }

        return stringNeighbours;
    }
}
