package uk.co.cpascoe.huffman;

import java.lang.*;

public abstract class Node implements Comparable<Node> {

    public abstract int getFrequency();

    public abstract Node decode(BitManager bm);

    public void print(StringBuilder str) {
        this.print(str, "");
    }

    public abstract void print(StringBuilder str, String line);

    // public abstract void generateEncoding();

    public int compareTo(Node other) {
        return this.getFrequency() - other.getFrequency();
    }
}
