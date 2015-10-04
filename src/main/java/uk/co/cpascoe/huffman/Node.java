package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public abstract class Node implements Comparable<Node> {

    public abstract int getFrequency();

    public abstract byte decode(BitManager bm);

    public void print(StringBuilder str) {
        this.print(str, "");
    }

    public abstract void generateEncoding(HuffmanEncodingTables encoding, List<Byte> currentEncoding);

    public abstract void print(StringBuilder str, String line);

    public int compareTo(Node other) {
        return this.getFrequency() - other.getFrequency();
    }
}
