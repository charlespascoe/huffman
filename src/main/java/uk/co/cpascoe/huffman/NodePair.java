package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class NodePair extends Node {
    public Node node0;
    public Node node1;

    public NodePair(Node node0, Node node1) {
        this.node0 = node0;
        this.node1 = node1;
    }

    public Node getNode(byte node) {
        if (node == 0) {
            return this.node0;
        } else if (node == 1) {
            return this.node1;
        } else {
            return null;
        }
    }

    @Override
    public Node decode(BitManager bm) {
        byte nextBit = bm.getBit();
        bm.next();

        return this.getNode(nextBit).decode(bm);
    }

    @Override
    public int getFrequency() {
        return
            (this.node0 == null ? 0 : this.node0.getFrequency()) +
            (this.node1 == null ? 0 : this.node1.getFrequency());
    }

    @Override
    public void print(StringBuilder str, String line) {
        str.append(String.format("%sNodePair (%s)%n", line, this.getFrequency()));
        this.node0.print(str, line + "   |");
        this.node1.print(str, line + "   |");
    }
}
