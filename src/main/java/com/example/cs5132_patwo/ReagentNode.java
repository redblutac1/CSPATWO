package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class ReagentNode<T> extends Node<T> {
    public static final int MAX_NEIGHBOURS = 100;
    private int numNeighbours; // Number of neighbours that are not null

    public ReagentNode(T item) {
        super(item);
        neighbours = new ReagentNode[MAX_NEIGHBOURS];
        numNeighbours = 0;
    }

    public ReagentNode(T item, int numNeighbours) {
        super(item, numNeighbours);
        this.numNeighbours = 0;
    }

    public ReagentNode(T item, Node<T>[] neighbours) {
        this(item);
        this.neighbours = new ReagentNode[neighbours.length];
        for (int i = 0; i < neighbours.length; i++) {
            this.neighbours[i] = new ReagentNode(neighbours[i].getItem());
        }
        numNeighbours += neighbours.length;
    }

    public ReagentNode(Node<T> n) {
        super(n);
        this.numNeighbours = 0;
    }

    public static ReagentNode<Reagent> deserialize(String str) {
        ReagentNode<Reagent> root = null;
        Stack<ReagentNode<Reagent>> stack = new Stack<>();
        String[] tree = str.split("#");
        for (String node : tree) {
            if (node.equals("$")) {
                ReagentNode<Reagent> childNode = stack.pop();
                if (root == null && childNode.toString().equals("ROOT")) root = childNode;
                if (stack.size() > 0) {
                    ReagentNode<Reagent> parentNode = stack.peek();
                    parentNode.addNeighbour(childNode);
                }
            } else {
                stack.push(new ReagentNode<Reagent>(new Reagent(node), MAX_NEIGHBOURS));
            }
        }
        return root;
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

    public void addNeighbours(T[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {
            this.neighbours[i + numNeighbours] = new ReagentNode<T>(neighbours[i], MAX_NEIGHBOURS);
        }
        numNeighbours += neighbours.length;
    }

    public int getNumNeighbours() {
        return numNeighbours;
    }

    public String serialize() {
        return serialize(this, new HashSet<String>(), new StringBuilder());
    }

    public String serialize(Node<T> node, HashSet<String> visited, StringBuilder str) {
        if (node == null) return str.toString();

        visited.add(node.toString());
        str.append(node).append("#");

        for (Node<T> child : node.neighbours) {
            serialize(child, visited, str);
        }

        if (visited.contains(node.toString())) {
            str.append("$#");
        }

        return str.toString();
    }

    public Node<T>[] getNonNullNeighbours() {
        return Arrays.copyOfRange(neighbours, 0, numNeighbours);
    }
}
