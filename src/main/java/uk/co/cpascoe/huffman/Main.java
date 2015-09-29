package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Huffman!");

        HuffmanCompressor h = new HuffmanCompressor();

        for (byte b : h.compress(new byte[] {100, 50, 100, 100, 0, 50})) {
            System.out.println(b);
        }
    }
}
