package uk.co.cpascoe.huffman;

import java.lang.*;
import java.util.*;
import java.io.InputStream;
import java.io.IOException;

public class Utils {
    /**
     * Returns the big-endian representation of a byte value
     *
     * @param  val The value to convert to bits
     * @return     The big-endian array of bits (as bytes)
     */
    public static byte[] toBits(byte val) {
        return Utils.toBits(Utils.toUnsignedByte(val), 8);
    }

    public static byte[] toBits(int val, int bitLength) {
        if (bitLength < 0) {
            return new byte[0];
        }

        byte[] bits = new byte[bitLength];

        int mask = 1;

        for (int i = bitLength - 1; i >= 0; i--) {
            bits[i] = (byte)((val & mask) != 0 ? 1 : 0);
            mask = (mask << 1);
        }

        return bits;
    }

    public static byte byteFromBits(byte[] bits) {
        if (bits.length != 8) {
            return (byte)0;
        }

        // Casting int (0 to 255) to byte (-128 to 127)
        return (byte)Utils.intFromBits(bits);
    }

    public static int intFromBits(byte[] bits) {
        int val = 0;

        int bitValue = 1;

        for (int i = bits.length - 1; i >= 0; i--) {
            val += bits[i] * bitValue;
            bitValue = (int)(bitValue << 1);
        }

        return val;
    }

    public static byte[] readAllBytesFromStream(InputStream strm) throws IOException {
        byte[] data = new byte[0];
        byte[] buffer = new byte[65536];
        int readCount = 0;
        int prevLength = 0;

        while (true) {
            readCount = strm.read(buffer, 0, buffer.length);
            if (readCount == -1) { break; }

            prevLength = data.length;

            data = Arrays.copyOf(data, data.length + readCount);
            System.arraycopy(buffer, 0, data, prevLength, readCount);
        }

        return data;
    }

    public static int toUnsignedByte(byte b) {
        return b + (b < 0 ? 256 : 0);
    }

    public static byte[] concat(byte[]... arrays) {
        int length = 0;

        for (byte[] array : arrays) {
            length += array.length;
        }

        byte[] out = new byte[length];

        int pos = 0;

        for (byte[] array : arrays) {
            System.arraycopy(array, 0, out, pos, array.length);
            pos += array.length;
        }

        return out;
    }
}
