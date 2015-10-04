package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        boolean compress = true;
        // TODO: Better argument parsing
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.equals("-d")) {
                    compress = false;
                }
            }
        }

        byte[] inputData;

        try {
            inputData = Utils.readAllBytesFromStream(System.in);

            if (inputData.length == 0) { return; }

        } catch (IOException ex) {
            System.err.println("Failed to read data");
            return;
        }

        byte[] outputData;

        try {
            outputData = compress ? HuffmanCompressor.compress(inputData) : HuffmanCompressor.decompress(inputData);
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return;
        }

        System.out.write(outputData, 0, outputData.length);
    }
}
