package com.example.cs5132_patwo;

import java.util.ArrayList;
import java.util.Arrays;

public class ChemisTREE<T> {
    private final ReagentNode<T> root;

    public ChemisTREE(ArrayList<T> reagents) {
        if (reagents.size() == 0) throw new IllegalArgumentException("Empty item array!");
        root = new ReagentNode(reagents.get(0), ReagentNode.MAX_NEIGHBOURS);
        root.addNeighbours(reagents.subList(1, reagents.size()));
    }

    public ChemisTREE(T[] reagents) {
        if (reagents.length == 0) throw new IllegalArgumentException("Empty item array!");
        root = new ReagentNode(reagents[0], ReagentNode.MAX_NEIGHBOURS);
        root.addNeighbours(Arrays.copyOfRange(reagents, 1, reagents.length));
    }

    // This is for superChemisTREE construction. It is the tree that contains all the reaction pathways.
    // TODO: MAKE NEIGHBOURS SUCH THAT IT AUTO EXPANDS AND AUTO COPIES. Let us limit to 2000.
    public ChemisTREE(T root) {
        this.root = new ReagentNode<T>(root, 3);
    }

    public void insert(ReagentNode<T> reagent) {
        root.addNeighbour(reagent);
    }

    public void insert(T reagent) {
        root.addNeighbour(reagent);
    }

    public ReagentNode<T> findNode(String name) {
        return root.findNode(name);
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
