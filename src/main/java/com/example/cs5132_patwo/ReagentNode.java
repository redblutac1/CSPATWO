package com.example.cs5132_patwo;

import java.util.ArrayList;
import java.util.List;

public class ReagentNode<T> extends Node<T> {
    public static final int MAX_NEIGHBOURS = 10; // It is reasonably assumed that chemical reactions will not have more than 10 reactants.
    private int numNeighbours; // Those that are not null

    public ReagentNode(T item) {
        super(item);
        numNeighbours = 0;
    }

    public ReagentNode(T item, int numNeighbours) {
        super(item, numNeighbours);
        this.numNeighbours = 0;
    }

    public ReagentNode(T item, Node<T>[] neighbours) {
        super(item, neighbours);
        numNeighbours += neighbours.length;
    }

    public ReagentNode(Node<T> n) {
        super(n);
        this.numNeighbours = 0;
    }

    public ReagentNode<T> findNode(String name) {
        for (Node<T> n : neighbours) {
            if (n.toString().equals(name)) return (ReagentNode<T>) n;
        }
        return null;
    }

    public void addNeighbour(T neighbour) {
        neighbours[getNumNeighbours()] = new ReagentNode<T>(neighbour);
        numNeighbours++;
    }

    public void addNeighbour(ReagentNode<T> neighbour) {
        neighbours[getNumNeighbours()] = neighbour;
        numNeighbours++;
    }

    public void addNeighbours(List<T> neighbours) {
        for (int i = 0; i < neighbours.size(); i++) {
            this.neighbours[i + numNeighbours] = new ReagentNode<T>(neighbours.get(i), MAX_NEIGHBOURS);
        }
        numNeighbours += neighbours.size();
    }

    public int getNumNeighbours() {
        return numNeighbours;
    }
}
