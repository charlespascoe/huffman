package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;

public class BitManager {
    private byte[] data;
    private int pos;
    private static final byte[] POWERS = new byte[] {(byte)128, 64, 32, 16, 8, 4, 2, 1};

    public BitManager() {
        this.data = new byte[] {0};
        this.pos = 0;
    }

    public BitManager(byte[] initialData) {
        this.data = Arrays.copyOf(initialData, initialData.length);
        this.pos = 0;
    }

    public byte getBit(int p) {
        if (p < 0 || (p / 8) >= this.data.length) {
            return 0;
        } else {
            return (byte)((this.data[p / 8] & BitManager.POWERS[p % 8]) != 0 ? 1 : 0);
        }
    }

    public byte getBit() {
        return this.getBit(this.pos);
    }

    public byte peekBit(int disp) {
        return this.getBit(this.pos + disp);
    }

    public void setBit(byte bit) {
        byte currentBit = this.getBit();

        if (currentBit != bit) {
            this.data[this.pos / 8] = (byte)(this.data[this.pos / 8] ^ BitManager.POWERS[this.pos % 8]);
        }
    }

    public void next() {
        this.pos++;
        if (this.pos / 8 >= this.data.length) {
            this.data = Arrays.copyOf(data, data.length * 2);
        }
    }

    public void prev() {
        if (this.pos > 0) {
            this.pos--;
        }
    }

    public byte[] getData() {
        return Arrays.copyOf(this.data, (this.pos / 8) + 1);
    }
}
