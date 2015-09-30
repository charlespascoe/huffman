package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Huffman!");

        HuffmanCompressor h = new HuffmanCompressor();

        System.out.println(Utils.byteFromBits(Utils.toBits((byte)100)));

        byte[] compressed = h.compress(new byte[] {100, 50, 100, 100, 0, 50});

        for (byte b : h.decompress(compressed)) {
            System.out.println(b);
        }
    }
}
