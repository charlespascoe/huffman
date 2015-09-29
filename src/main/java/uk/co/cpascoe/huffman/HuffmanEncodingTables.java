package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class HuffmanEncodingTables {
    private byte[][] encoding;
    private byte[] endEncoding;

    public HuffmanEncodingTables() {
        this.encoding = new byte[256][];

        for (int i = 0; i < this.encoding.length; i++) {
            this.encoding[i] = new byte[0];
        }

        this.endEncoding = new byte[0];
    }

    public void setEncoding(byte symb, List<Byte> synbEnc) {
        byte[] enc = new byte[synbEnc.size()];

        for (int i = 0; i < synbEnc.size(); i++) {
            enc[i] = (byte)(synbEnc.get(i) > 0 ? 1 : 0);
        }

        this.encoding[symb] = enc;
    }

    public void setEndEncoding(List<Byte> endEnc) {
        this.endEncoding = new byte[endEnc.size()];

        for (int i = 0; i < endEnc.size(); i++) {
            this.endEncoding[i] = (byte)(endEnc.get(i) > 0 ? 1 : 0);
        }
    }

    public byte[][] getEncoding() {
        return this.encoding;
    }

    public byte[] getEndEncoding() {
        return this.endEncoding;
    }
}
