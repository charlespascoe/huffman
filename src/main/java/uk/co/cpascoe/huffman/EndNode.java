package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class EndNode extends Node {
    @Override
    public Node decode(BitManager bm) {
        return this;
    }

    @Override
    public int getFrequency() {
        return 0;
    }

    @Override
    public void print(StringBuilder str, String line) {
        str.append(String.format("%sEndNode%n", line));
    }
}
