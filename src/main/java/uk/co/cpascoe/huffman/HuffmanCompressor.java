package uk.co.cpascoe.huffman;

import java.util.*;
import java.lang.*;

public class HuffmanCompressor {
    public byte[] compress(byte[] data) {
        int[] frequencies = new int[256];

        for (byte b : data) {
            frequencies[b]++;
        }

        Node rootNode = this.buildTree(frequencies, false);

        HuffmanEncodingTables encTables = new HuffmanEncodingTables();

        List<Byte> currentEncoding = new LinkedList<>();

        rootNode.generateEncoding(encTables, currentEncoding);

        byte[][] encoding = encTables.getEncoding();

        BitManager bm = new BitManager();

        for (byte dataByte : data) {
            for (byte bit : encoding[dataByte]) {
                bm.setBit(bit);
                bm.next();
            }
        }

        for (byte bit : encTables.getEndEncoding()) {
            bm.setBit(bit);
            bm.next();
        }

        bm.prev();

        return bm.getData();
    }

    public Node buildTree(int[] frequencies, Boolean includeZeroEntries) {
        if (frequencies.length != 256) {
            throw new IllegalArgumentException("Invalid frequencies length");
        }

        List<Node> nodes = new LinkedList<>();

        for (int i = 0; i < 256; i++) {
            if (frequencies[i] > 0 || includeZeroEntries) {
                nodes.add(new Leaf((byte)i, frequencies[i]));
            }
        }

        nodes.add(new EndNode());

        Collections.sort(nodes);

        while (nodes.size() > 1) {
            NodePair np = new NodePair(nodes.remove(0), nodes.remove(0));
            int freq = np.getFrequency();

            Boolean inserted = false;

            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).getFrequency() > freq) {
                    nodes.add(i, np);
                    inserted = true;
                    break;
                }
            }

            if (!inserted) {
                nodes.add(np);
            }
        }

        return nodes.get(0);
    }
}
