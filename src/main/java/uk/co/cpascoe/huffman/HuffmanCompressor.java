package uk.co.cpascoe.huffman;

import java.util.*;
import java.lang.*;

public class HuffmanCompressor {
    public byte[] compress(byte[] data) {
        int[] frequencies = new int[256];

        for (byte b : data) {
            frequencies[Utils.toUnsignedByte(b)]++;
        }

        Node rootNode = this.buildTree(frequencies, false);

        HuffmanEncodingTables encTables = new HuffmanEncodingTables();
        List<Byte> currentEncoding = new LinkedList<>();

        rootNode.generateEncoding(encTables, currentEncoding);

        byte[][] encoding = encTables.getEncoding();

        BitManager bm = new BitManager();

        this.huffmanTreeToBits(rootNode, bm);

        for (byte dataByte : data) {
            bm.add(encoding[Utils.toUnsignedByte(dataByte)]);
        }

        bm.add(encTables.getEndEncoding());

        // Moves to the last written bit
        // since getData always includes the byte
        // the position is pointing at
        bm.prev();

        return bm.getData();
    }

    public byte[] decompress(byte[] data) {
        BitManager bitMgr = new BitManager(data);

        Node rootNode = this.huffmanTreeFromBits(bitMgr);

        List<Byte> outData = new ArrayList<>();

        while (true) {
            Node decodedValue = rootNode.decode(bitMgr);

            if (decodedValue instanceof Leaf) {
                Leaf l = (Leaf)decodedValue;
                outData.add(new Byte(l.getSymbol()));
            } else if (decodedValue instanceof EndNode) {
                break;
            }
        }

        byte[] out = new byte[outData.size()];

        for (int i = 0; i < out.length; i++) {
            out[i] = outData.get(i).byteValue();
        }

        return out;
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

    private void huffmanTreeToBits(Node node, BitManager bitMgr) {
        if (node instanceof NodePair) {
            NodePair np = (NodePair)node;

            // 0 indicates NodePair
            bitMgr.add((byte)0);

            this.huffmanTreeToBits(np.node0, bitMgr);
            this.huffmanTreeToBits(np.node1, bitMgr);
        } else if (node instanceof Leaf) {
            Leaf l = (Leaf)node;

            // 10 indicates Leaf
            bitMgr.add((byte)1);
            bitMgr.add((byte)0);

            bitMgr.add(Utils.toBits(l.getSymbol()));
        } else if (node instanceof EndNode) {
            // 11 indicates EndNode
            bitMgr.add((byte)1);
            bitMgr.add((byte)1);
        }
    }

    private Node huffmanTreeFromBits(BitManager bitMgr) {
        if (bitMgr.getBit() == 0) {
            // 0 indicates current node is a NodePair
            bitMgr.next();

            Node node0 = this.huffmanTreeFromBits(bitMgr);
            Node node1 = this.huffmanTreeFromBits(bitMgr);

            NodePair np = new NodePair(node0, node1);

            return np;
        } else {
            // Current bit is 1, move to next bit
            bitMgr.next();

            if (bitMgr.getBit() == 0) {
                // 10 indicates current node is Leaf
                bitMgr.next();

                byte symbol = Utils.byteFromBits(bitMgr.getBits(8));

                return new Leaf(symbol);
            } else {
                // 11 indicates current node is EndNode
                bitMgr.next();

                return new EndNode();
            }
        }
    }
}
