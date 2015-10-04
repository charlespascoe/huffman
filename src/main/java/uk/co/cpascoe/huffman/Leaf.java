package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class Leaf extends Node {
    private int frequency;
    private byte symbol;

    public Leaf(byte symbol) {
        this.symbol = symbol;
    }

    public Leaf(byte symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public byte getSymbol() {
        return this.symbol;
    }

    @Override
    public byte decode(BitManager bm) {
        return this.getSymbol();
    }

    @Override
    public int getFrequency() {
        return this.frequency;
    }

    @Override
    public void generateEncoding(HuffmanEncodingTables encoding, List<Byte> currentEncoding) {
        encoding.setEncoding(this.getSymbol(), currentEncoding);
    }

    @Override
    public void print(StringBuilder str, String line) {
        str.append(String.format("%sLeaf %s (%s)%n", line, this.getSymbol(), this.getFrequency()));
    }
}
