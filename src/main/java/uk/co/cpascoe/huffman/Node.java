package uk.co.cpascoe.huffman;

import java.lang.Comparable;

public abstract class Node implements Comparable<Node> {

    public abstract int getFrequency();

    public int compareTo(Node other) {
        return this.getFrequency() - other.getFrequency();
    }
}
