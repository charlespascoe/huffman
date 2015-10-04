package uk.co.cpascoe.huffman;

import java.util.*;
import java.lang.*;

public abstract class HuffmanCompressor {
    public static byte[] compress(byte[] data) {
        int[] frequencies = new int[256];

        for (byte b : data) {
            frequencies[Utils.toUnsignedByte(b)]++;
        }

        Node rootNode = HuffmanCompressor.buildTree(frequencies, false);

        HuffmanEncodingTables encTables = new HuffmanEncodingTables();
        List<Byte> currentEncoding = new LinkedList<>();

        rootNode.generateEncoding(encTables, currentEncoding);

        byte[][] encoding = encTables.getEncoding();

        BitManager bm = new BitManager();

        HuffmanCompressor.huffmanTreeToBits(rootNode, bm);

        bm.add(HuffmanCompressor.lengthToBits(data.length));

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

    public static byte[] decompress(byte[] data) {
        BitManager bitMgr = new BitManager(data);

        Node rootNode = HuffmanCompressor.huffmanTreeFromBits(bitMgr);

        int dataLength = HuffmanCompressor.lengthFromBits(bitMgr);

        byte[] out = new byte[dataLength];

        for (int i = 0; i < dataLength; i++) {
            out[i] = rootNode.decode(bitMgr);
        }

        return out;
    }

    public static Node buildTree(int[] frequencies, Boolean includeZeroEntries) {
        if (frequencies.length != 256) {
            throw new IllegalArgumentException("Invalid frequencies length");
        }

        List<Node> nodes = new LinkedList<>();

        for (int i = 0; i < 256; i++) {
            if (frequencies[i] > 0 || includeZeroEntries) {
                nodes.add(new Leaf((byte)i, frequencies[i]));
            }
        }

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

    public static byte[] lengthToBits(int length) {
        if (length < 256) {
            // Represent the length as an 8-bit unsigned integer
            // (0 = 8 bit unsigned length)
            return Utils.concat(new byte[] { 0 }, Utils.toBits(length, 8));
        } else {
            // Represent the length as an 8-bit signed integer
            // (1 = 32 bit signed length)
            return Utils.concat(new byte[] { 1 }, Utils.toBits(length, 32));
        }
    }

    public static int lengthFromBits(BitManager bitMgr) {
        int bitLength = (bitMgr.getBit() == 0 ? 8 : 32);

        bitMgr.next();

        return Utils.intFromBits(bitMgr.getBits(bitLength));
    }

    public static void huffmanTreeToBits(Node node, BitManager bitMgr) {
        if (node instanceof NodePair) {
            NodePair np = (NodePair)node;

            // 0 indicates NodePair
            bitMgr.add((byte)0);

            HuffmanCompressor.huffmanTreeToBits(np.node0, bitMgr);
            HuffmanCompressor.huffmanTreeToBits(np.node1, bitMgr);
        } else if (node instanceof Leaf) {
            Leaf l = (Leaf)node;

            // 1 indicates Leaf
            bitMgr.add((byte)1);

            bitMgr.add(Utils.toBits(l.getSymbol()));
        }
    }

    public static Node huffmanTreeFromBits(BitManager bitMgr) {
        if (bitMgr.getBit() == 0) {
            // 0 indicates current node is a NodePair
            bitMgr.next();

            Node node0 = HuffmanCompressor.huffmanTreeFromBits(bitMgr);
            Node node1 = HuffmanCompressor.huffmanTreeFromBits(bitMgr);

            NodePair np = new NodePair(node0, node1);

            return np;
        } else {
            // Current bit is 1, indicates current node is Leaf
            bitMgr.next();

            byte symbol = Utils.byteFromBits(bitMgr.getBits(8));

            return new Leaf(symbol);
        }
    }
}
