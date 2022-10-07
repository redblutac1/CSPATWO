package com.example.cs5132_patwo.model;

import java.util.ArrayList;

public class ReagentNode<T> extends Node<T> {
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

    public void addNeighbour(T neighbour) {
        neighbours[getNumNeighbours()] = new ReagentNode<T>(neighbour);
        numNeighbours++;
    }

    public void addNeighbour(ReagentNode<T> neighbour) {
        neighbours[getNumNeighbours()] = neighbour;
        numNeighbours++;
    }

    public void addNeighbours(ArrayList<T> neighbours) {
        for (int i = 0; i < neighbours.size(); i++) {
            this.neighbours[i + numNeighbours] = new ReagentNode<T>(neighbours.get(i), 20); // It is reasonably assumed that chemical reactions will not have more than 20 reactants
        }
        numNeighbours += neighbours.size();
    }

    public int getNumNeighbours() {
        return numNeighbours;
    }
}
